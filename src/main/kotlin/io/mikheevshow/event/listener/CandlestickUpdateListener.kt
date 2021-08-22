package io.mikheevshow.event.listener

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

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

interface CandlestickUpdateListener: BinanceListener<CandlestickUpdate>