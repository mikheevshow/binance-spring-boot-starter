package io.mikheevshow.event.listener

import io.mikheevshow.event.EventType

class ListenerProviderImpl(binanceListeners: List<BinanceListener<*>>) : ListenerProvider {

    private var listenersMap: Map<EventType, List<BinanceListener<*>>>

    init {
        val candlestickUpdateListeners = mutableListOf<CandlestickUpdateListener>()
        val marketDepthUpdateListeners = mutableListOf<MarketDepthUpdateListener>()

        binanceListeners.forEach {
            when(it) {
                is CandlestickUpdateListener -> candlestickUpdateListeners.add(it)
                is MarketDepthUpdateListener -> marketDepthUpdateListeners.add(it)
            }
        }

        listenersMap = mapOf(
            EventType.KLINE to candlestickUpdateListeners,
            EventType.DEPTH_UPDATE to marketDepthUpdateListeners
        )
    }

    override fun get(eventType: EventType): List<BinanceListener<*>> {
        return listenersMap[eventType] ?: emptyList()
    }
}