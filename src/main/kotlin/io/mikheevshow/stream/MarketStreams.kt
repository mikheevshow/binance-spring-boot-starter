package io.mikheevshow.stream

interface MarketStreams {

    /**
     * Subscribes to market streams
     *
     * @see aggTrade
     * @see trade
     * @see kline
     * @see miniTicker
     * @see ticker
     * @see partialDepth
     * @see partialDepth100ms
     * @see depth
     * @see depth100ms
     */
    fun subscribe(vararg streams: String)

    /**
     * Unsubscribes from market streams
     *
     * @see aggTrade
     * @see trade
     * @see kline
     * @see miniTicker
     * @see ticker
     * @see partialDepth
     * @see partialDepth100ms
     * @see depth
     * @see depth100ms
     */
    fun unsubscribe(vararg streams: String)
}