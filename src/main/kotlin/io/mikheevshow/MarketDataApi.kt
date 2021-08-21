package io.mikheevshow

interface MarketDataApi {

    fun ping(): Boolean

    fun serverTime(): ServerTime

    fun exchangeInfo(vararg symbols: String): ExchangeInfo

    fun depth(symbol: String, limit: Int = 100): MarketDepth

    fun trades(symbol: String, limit: Int = 500): List<Trade>

    fun historicalTrades(symbol: String, limit: Int = 500, mbxApiKey: String, fromId: Long? = null): List<Trade>

    fun candlesticks(symbol: String, interval: String, startTime: Long? = null, endTime: Long? = null, limit: Int = 500): List<Candlestick>

    fun averagePrice(symbol: String): AveragePrice

    fun dailyTicker(symbol: String)

    fun priceTicker(symbol: String? = null)

    fun orderBookTicker(symbol: String? = null)
}