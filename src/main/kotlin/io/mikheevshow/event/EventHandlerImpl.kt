package io.mikheevshow.event

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import io.mikheevshow.PriceLevelQuantity
import io.mikheevshow.event.EventType.*
import io.mikheevshow.event.listener.*
import io.mikheevshow.json.getBoolean
import io.mikheevshow.json.getDouble
import io.mikheevshow.json.getLong
import io.mikheevshow.json.getText
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.apache.logging.log4j.kotlin.logger

@Suppress("UNCHECKED_CAST")
class EventHandlerImpl(private val listenerProvider: ListenerProvider) : EventHandler {

    private val logger = logger()
    private val json = jacksonObjectMapper()

    override suspend fun handleEvent(rawData: CharSequence) {
        kotlin.runCatching {
            val event = json.readTree(rawData.toString())
            if (event.has("e")) {
                when (event.getText("e")) {
                    KLINE.event -> {
                        val candlestickUpdate = convertToCandlestickUpdate(event)
                        (listenerProvider.get(KLINE) as List<CandlestickUpdateListener>).forEach {
                            coroutineScope {
                                launch {
                                    it.newEvent(candlestickUpdate)
                                }
                            }
                        }
                    }
                    DEPTH_UPDATE.event -> {
                        val marketDepthUpdate = convertToDepthUpdate(event)
                        (listenerProvider.get(DEPTH_UPDATE) as List<MarketDepthUpdateListener>).forEach {
                            coroutineScope {
                                launch {
                                    it.newEvent(marketDepthUpdate)
                                }
                            }
                        }
                    }
                    AGGREGATE_TRADE.event -> {
                        val aggregateTradeUpdate = json.treeToValue<AggregateTradeUpdate>(event) ?: throw RuntimeException("Can't parse aggregate trade update: $rawData")
                        (listenerProvider.get(AGGREGATE_TRADE) as List<AggregateTradeUpdateListener>).forEach {
                            coroutineScope {
                                launch {
                                    it.newEvent(aggregateTradeUpdate)
                                }
                            }
                        }
                    }
                    TRADE.event -> {
                        val tradeUpdate = json.treeToValue<TradeUpdate>(event) ?: throw RuntimeException("Can't parse trade update: $rawData")
                        (listenerProvider.get(TRADE) as List<TradeUpdateListener>).forEach {
                            coroutineScope {
                                launch {
                                    it.newEvent(tradeUpdate)
                                }
                            }
                        }
                    }
                    TICKER.event -> {

                    }
                    MINI_TICKER.event -> {

                    }
                }
            } else if (event.has("lastUpdateId") && event.has("bids") && event.has("asks")) {
                val partialDepthUpdate = MarketPartialDepthUpdate(
                    lastUpdateId = event.getLong("lastUpdateId"),
                    bids = event.get("bids").mapToPriceLevelQuantity(),
                    asks = event.get("asks").mapToPriceLevelQuantity()
                )
                (listenerProvider.get(PARTIAL_DEPTH_UPDATE) as List<MarketPartialDepthUpdateListener>).forEach {
                    coroutineScope {
                        launch {
                            it.newEvent(partialDepthUpdate)
                        }
                    }
                }
            } else {
                logger.info { "Event received can't do anything with it. Event: $rawData" }
            }
        }
    }

    private fun convertToCandlestickUpdate(updateNode: JsonNode): CandlestickUpdate {
        val candlestickNode = updateNode.get("k")
        return CandlestickUpdate(
            eventTime = updateNode.getLong("E"),
            symbol = updateNode.getText("s"),
            candleStickStartTime = candlestickNode.getLong("t"),
            candleStickCloseTime = candlestickNode.getLong("T"),
            interval = candlestickNode.getText("i"),
            firstTradeId = candlestickNode.getLong("f"),
            lastTradeId = candlestickNode.getLong("L"),
            openPrice = candlestickNode.getDouble("o"),
            closePrice = candlestickNode.getDouble("c"),
            highPrice = candlestickNode.getDouble("h"),
            lowPrice = candlestickNode.getDouble("l"),
            baseAssetVolume = candlestickNode.getLong("v"),
            numberOfTrades = candlestickNode.getLong("n"),
            isCandlestickClosed = candlestickNode.getBoolean("x"),
            quoteAssetVolume = candlestickNode.getDouble("q"),
            takerBuyBaseAssetVolume = candlestickNode.getDouble("V"),
            takerBuyQuoteAssetVolume = candlestickNode.getDouble("Q"),
            ignore = candlestickNode.getDouble("B")
        )
    }

    private fun convertToDepthUpdate(updateNode: JsonNode): MarketDepthUpdate {
        return MarketDepthUpdate(
            eventTime = updateNode.getLong("E"),
            symbol = updateNode.getText("s"),
            firstUpdateId = updateNode.getLong("U"),
            finalUpdateId = updateNode.getLong("u"),
            bids = updateNode.get("b").mapToPriceLevelQuantity(),
            asks = updateNode.get("a").mapToPriceLevelQuantity()
        )
    }

    private fun JsonNode.mapToPriceLevelQuantity(): List<PriceLevelQuantity> {
        val list = mutableListOf<PriceLevelQuantity>()
        this.forEach { node ->
            val arrayNode = node as ArrayNode
            list.add(
                PriceLevelQuantity(
                    priceLevel = arrayNode.getDouble(0),
                    quantity = arrayNode.getLong(1)
                )
            )
        }

        return list
    }
}