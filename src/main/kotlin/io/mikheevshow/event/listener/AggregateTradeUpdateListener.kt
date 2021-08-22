package io.mikheevshow.event.listener

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AggregateTradeUpdate(
    @JsonProperty("E")
    val eventTime: Long,
    @JsonProperty("s")
    val symbol: String,
    @JsonProperty("a")
    val aggregateTradeId: Long,
    @JsonProperty("p")
    val price: Double,
    @JsonProperty("q")
    val quantity: Long,
    @JsonProperty("f")
    val firstTradeId: Long,
    @JsonProperty("l")
    val lastTradeId: Long,
    @JsonProperty("T")
    val tradeTime: Long,
    @JsonProperty("m")
    val isBuyerMarketMaker:Boolean,
    @JsonProperty("M")
    val ignore: Boolean
)

interface AggregateTradeUpdateListener : BinanceListener<AggregateTradeUpdate>