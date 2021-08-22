package io.mikheevshow.event.listener

import io.mikheevshow.event.EventType
import io.mikheevshow.event.EventType.*

class ListenerProviderImpl(binanceListeners: List<BinanceListener<*>>) : ListenerProvider {

    private var listenersMap: Map<EventType, List<BinanceListener<*>>>

    init {
        val candlestickUpdateListeners = mutableListOf<CandlestickUpdateListener>()
        val marketDepthUpdateListeners = mutableListOf<MarketDepthUpdateListener>()
        val tradeUpdateListeners = mutableListOf<TradeUpdateListener>()
        val aggregateTradeUpdateListeners = mutableListOf<AggregateTradeUpdateListener>()

        binanceListeners.forEach {
            when(it) {
                is CandlestickUpdateListener -> candlestickUpdateListeners.add(it)
                is MarketDepthUpdateListener -> marketDepthUpdateListeners.add(it)
                is TradeUpdateListener -> tradeUpdateListeners.add(it)
                is AggregateTradeUpdateListener -> aggregateTradeUpdateListeners.add(it)
            }
        }

        listenersMap = mapOf(
            KLINE to candlestickUpdateListeners,
            DEPTH_UPDATE to marketDepthUpdateListeners,
            TRADE to tradeUpdateListeners,
            AGGREGATE_TRADE to aggregateTradeUpdateListeners
        )
    }

    override fun get(eventType: EventType): List<BinanceListener<*>> {
        return listenersMap[eventType] ?: emptyList()
    }
}