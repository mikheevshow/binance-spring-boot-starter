package io.mikheevshow

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.mikheevshow.event.EventType

@JsonIgnoreProperties(ignoreUnknown = true)
data class ServerTime(val serverTime: Long)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExchangeInfo(
    val timezone: String,
    val serverTime: Long,
    val symbols: List<ExchangeInfoSymbol>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExchangeInfoSymbol(
    val symbol: String,
    val status: String,
    val baseAsset: String,
    val baseAssetPrecision: Int,
    val quoteAsset: String,
    val quotePrecision: Int,
    val quoteAssetPrecision: Int,
    val orderTypes: List<String>,
    val icebergAllowed: Boolean,
    val ocoAllowed: Boolean,
    val isSpotTradingAllowed: Boolean,
    val isMarginTradingAllowed: Boolean,
    val permissions: List<String>
)

data class Trade(
    val id: Long
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MarketDepth(
    val lastUpdateId: Long,
    val bids: List<Any>,
    val asks: List<Any>
)


data class MarketDepthUpdate(
    val eventTime: Long,
    val symbol: String,
    val firstUpdateId: Long,
    val finalUpdateId: Long,
    val bids: List<PriceLevelQuantity>,
    val asks: List<PriceLevelQuantity>
)

data class PriceLevelQuantity(
    val priceLevel: Double,
    val quantity: Long
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Candlestick(
    val openTime: Long,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double,
    val closeTime: Long,
    val quoteAssetVolume: Double,
    val numberOfTrades: Long,
    val takerBuyBaseAssetVolume: Double,
    val takerBuyQuoteAssetVolume: Double,
    val ignore: Double
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CandlestickUpdate(
    val eventTime: Long,
    val symbol: String,
    val candleStickStartTime: Long,
    val candleStickCloseTime: Long,
    val interval: String,
    val firstTradeId: Long,
    val lastTradeId: Long,
    val openPrice: Double,
    val closePrice: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val baseAssetVolume: Long,
    val numberOfTrades: Long,
    val isCandlestickClosed: Boolean,
    val quoteAssetVolume: Double,
    val takerBuyBaseAssetVolume: Double,
    val takerBuyQuoteAssetVolume: Double,
    val ignore: Double
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AveragePrice(
    val mins: Int,
    val price: Double
)