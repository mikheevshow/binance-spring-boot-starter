package io.mikheevshow.event.listener

import io.mikheevshow.PriceLevelQuantity

data class MarketDepthUpdate(
    val eventTime: Long,
    val symbol: String,
    val firstUpdateId: Long,
    val finalUpdateId: Long,
    val bids: List<PriceLevelQuantity>,
    val asks: List<PriceLevelQuantity>
)

interface MarketDepthUpdateListener: BinanceListener<MarketDepthUpdate>