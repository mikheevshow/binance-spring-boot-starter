package io.mikheevshow.event

enum class EventType(val event: String, val stream: String) {
    AGGREGATE_TRADE("aggTrade", "aggTrade"),
    TRADE("trade", "trade"),
    KLINE("kline", "kline"),
    MINI_TICKER("24hrMiniTicker", "24hrMiniTicker"),
    TICKER("24hrTicker", "ticker"),
    DEPTH_UPDATE("depthUpdate", "depth");

    companion object {
        // Hot Spot optimizations
        val events = listOf(*values())
    }
}