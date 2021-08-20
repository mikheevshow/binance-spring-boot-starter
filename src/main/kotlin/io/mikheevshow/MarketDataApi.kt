package io.mikheevshow

interface MarketDataApi {

    fun ping(): Boolean

    fun serverTime()

    fun exchangeInfo(vararg symbol: String)

    fun depth(symbol: String, limit: Int? = null)

    fun tradesList(symbol: String, limit: Int? = null)

    fun candlesticks(symbol: String, interval: String, startTime: Long? = null, endTime: Long? = null, limit: Int? = null)

    fun averagePrice(symbol: String)

    fun dailyTicker(symbol: String)

    fun priceTicker(symbol: String? = null)

    fun orderBookTicker(symbol: String? = null)
}