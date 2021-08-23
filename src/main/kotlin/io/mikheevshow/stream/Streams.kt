package io.mikheevshow.stream

import io.mikheevshow.event.EventType.*

/**
 * The Aggregate Trade Streams push trade information that is aggregated for a single taker order.
 *
 * @param symbol currency pair symbol
 * @see io.mikheevshow.event.listener.AggregateTradeUpdateListener
 */
fun aggTrade(symbol: String): String {
    return "${symbol.lowercase()}@${AGGREGATE_TRADE.stream}"
}

/**
 * The Trade Streams push trade information; each trade has a unique buyer and seller.
 *
 * @param symbol currency pair symbol
 * @see io.mikheevshow.event.listener.TradeUpdateListener
 */
fun trade(symbol: String): String {
   return "${symbol.lowercase()}@${TRADE.stream}"
}

/**
 * The Kline/Candlestick Stream push updates to the current klines/candlestick every second.
 *
 * @param symbol currency pair symbol
 * @param interval candlestick interval
 *
 * @see io.mikheevshow.event.listener.CandlestickUpdateListener
 */
fun kline(symbol: String, interval: String): String {
    if (availableIntervals.contains(interval)) {
        return "${symbol.lowercase()}@kline_$interval"
    } else {
        throw RuntimeException("Incorrect interval `$interval`, use one of $availableIntervals")
    }
}

/**
 * 24hr rolling window mini-ticker statistics. These are NOT the statistics of the UTC day, but a 24hr rolling window
 * for the previous 24hrs.
 *
 * @param symbol currency pair symbol
 *
 */
fun miniTicker(symbol: String): String {
    return "${symbol.lowercase()}@miniTicker"
}

/**
 * 24hr rolling window ticker statistics for a single symbol. These are NOT the statistics of the UTC day, but a 24hr
 * rolling window for the previous 24hrs.
 *
 * @param symbol currency pair symbol
 */
fun ticker(symbol: String): String {
    return "${symbol.lowercase()}@ticker"
}

/**
 * Top bids and asks, Valid are 5, 10, or 20.
 *
 * @param symbol currency pair symbol
 */
fun partialDepth(symbol: String, limit: Int): String {
    if (availableLimits.contains(limit)) {
        return "${symbol.lowercase()}@depth$limit"
    } else {
        throw RuntimeException("Incorrect limit `$limit`, use one of $availableLimits")
    }
}

/**
 * Top bids and asks, Valid are 5, 10, or 20.
 *
 * @param symbol currency pair symbol
 */
fun partialDepth100ms(symbol: String, limit: Int): String {
    if (availableLimits.contains(limit)) {
        return "${symbol.lowercase()}@depth$limit@100ms"
    } else {
        throw RuntimeException("Incorrect limit `$limit`, use one of $availableLimits")
    }
}

/**
 * Order book price and quantity depth updates used to locally manage an order book.
 *
 * @param symbol currency pair symbol
 *
 * @see io.mikheevshow.event.listener.MarketDepthUpdateListener
 */
fun depth(symbol: String): String {
    return "${symbol.lowercase()}@depth"
}

/**
 * Order book price and quantity depth updates used to locally manage an order book.
 *
 * @param symbol currency pair symbol
 *
 * @see io.mikheevshow.event.listener.MarketDepthUpdateListener
 */
fun depth100ms(symbol: String): String {
    return "${symbol.lowercase()}@depth@100ms"
}