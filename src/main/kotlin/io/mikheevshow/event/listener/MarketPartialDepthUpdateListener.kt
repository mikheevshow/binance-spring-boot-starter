package io.mikheevshow.event.listener

import io.mikheevshow.PriceLevelQuantity

data class MarketPartialDepthUpdate(
    val lastUpdateId: Long,
    val bids: List<PriceLevelQuantity>,
    val asks: List<PriceLevelQuantity>
)

interface MarketPartialDepthUpdateListener : BinanceListener<Any>