package io.mikheevshow.event

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mikheevshow.CandlestickUpdate
import io.mikheevshow.MarketDepthUpdate
import io.mikheevshow.event.EventType.*
import io.mikheevshow.event.listener.CandlestickUpdateListener
import io.mikheevshow.event.listener.Listeners
import io.mikheevshow.event.listener.MarketDepthUpdateListener
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.apache.logging.log4j.kotlin.logger

@Suppress("UNCHECKED_CAST")
class EventHandlerImpl(private val listeners: Listeners) : EventHandler {

    private val logger = logger()
    private val json = jacksonObjectMapper()

    override suspend fun handleEvent(rawData: CharSequence) {
        EventType.events.firstOrNull { rawData.contains(it.event) }?.let {
            val update: Any = when (it) {
                KLINE -> {
                    val candlestickUpdate = convertToCandlestickUpdate(rawData)
                    (listeners.get(it) as List<CandlestickUpdateListener>).forEach {
                        coroutineScope {
                            async {
                                it.newEvent(candlestickUpdate)
                            }
                        }
                    }
                }
                DEPTH_UPDATE -> {
                    val marketDepthUpdate = convertToDepthUpdate(rawData)
                    (listeners.get(it) as List<MarketDepthUpdateListener>).forEach {
                        coroutineScope {
                            async {
                                it.newEvent(marketDepthUpdate)
                            }
                        }
                    }
                }
                AGGREGATE_TRADE -> {}
                TRADE -> {}
                MINI_TICKER -> {}
                TICKER -> {}
            }
        } ?: logger.warn { "Unexpected event $rawData" }
    }

    private fun convertToCandlestickUpdate(rawData: CharSequence): CandlestickUpdate {
        val updateNode = json.readTree(rawData.toString())
        val candlestickNode = updateNode.get("k")
        return CandlestickUpdate(
            eventTime = updateNode.get("E").asLong(),
            symbol = updateNode.get("s").asText(),
            candleStickStartTime = candlestickNode.get("t").asLong(),
            candleStickCloseTime = candlestickNode.get("T").asLong(),
            interval = candlestickNode.get("i").asText(),
            firstTradeId = candlestickNode.get("f").asLong(),
            lastTradeId = candlestickNode.get("L").asLong(),
            openPrice = candlestickNode.get("o").asDouble(),
            closePrice = candlestickNode.get("c").asDouble(),
            highPrice = candlestickNode.get("h").asDouble(),
            lowPrice = candlestickNode.get("l").asDouble(),
            baseAssetVolume = candlestickNode.get("v").asLong(),
            numberOfTrades = candlestickNode.get("n").asLong(),
            isCandlestickClosed = candlestickNode.get("x").asBoolean(),
            quoteAssetVolume = candlestickNode.get("q").asDouble(),
            takerBuyBaseAssetVolume = candlestickNode.get("V").asDouble(),
            takerBuyQuoteAssetVolume = candlestickNode.get("Q").asDouble(),
            ignore = candlestickNode.get("B").asDouble()
        )
    }

    private fun convertToDepthUpdate(rawData: CharSequence): MarketDepthUpdate {
        TODO()
    }
}