package io.mikheevshow.stream

import io.mikheevshow.event.EventType.*

fun aggTrade(symbol: String): String {
    return "${symbol.lowercase()}@${AGGREGATE_TRADE.stream}"
}

fun trade(symbol: String): String {
   return "${symbol.lowercase()}@${TRADE.stream}"
}

const val one_minute = "1m"
const val three_minutes = "3m"
const val five_minutes = "5m"
const val fifteen_minutes = "15m"
const val thirteen_minutes = "30m"
const val one_hour = "1h"
const val two_hours = "2h"
const val four_hours = "4h"
const val six_hours = "6h"
const val eight_hours = "8h"
const val twelve_hours = "12h"
const val one_day = "1d"
const val three_days = "3d"
const val one_week = "1w"
const val one_month = "1M"
val availableIntervals = setOf(
    one_minute,
    three_minutes,
    five_minutes,
    fifteen_minutes,
    thirteen_minutes,
    one_hour,
    two_hours,
    four_hours,
    six_hours,
    eight_hours,
    twelve_hours,
    one_day,
    three_days,
    one_week,
    one_month
)
fun kline(symbol: String, interval: String): String {
    if (availableIntervals.contains(interval)) {
        return "${symbol.lowercase()}@kline_$interval"
    } else {
        throw RuntimeException("Incorrect interval `$interval`, use one of $availableIntervals")
    }
}

fun miniTicker(symbol: String): String {
    return "${symbol.lowercase()}@miniTicker"
}

fun ticker(symbol: String): String {
    return "${symbol.lowercase()}@ticker"
}

const val five = 5
const val ten = 10
const val twenty = 20
val availableLimits = setOf(five, ten, twenty)
fun partialDepth(symbol: String, limit: Int): String {
    if (availableLimits.contains(limit)) {
        return "${symbol.lowercase()}@depth$limit"
    } else {
        throw RuntimeException("Incorrect limit `$limit`, use one of $availableLimits")
    }
}
fun partialDepth100ms(symbol: String, limit: Int): String {
    if (availableLimits.contains(limit)) {
        return "${symbol.lowercase()}@depth$limit@100ms"
    } else {
        throw RuntimeException("Incorrect limit `$limit`, use one of $availableLimits")
    }
}

fun depth(symbol: String): String {
    return "${symbol.lowercase()}@depth"
}

fun depth100ms(symbol: String): String {
    return "${symbol.lowercase()}@depth@100ms"
}