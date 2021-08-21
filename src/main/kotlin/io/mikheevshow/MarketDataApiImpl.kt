package io.mikheevshow

import io.mikheevshow.http.get
import java.net.URI
import java.net.http.HttpClient

class MarketDataApiImpl : MarketDataApi {

    private val httpClient = HttpClient.newHttpClient()

    override fun ping(): Boolean {
        return httpClient.get<Any>(uri = URI("$api/api/v3/ping")).statusCode() == 200
    }

    override fun serverTime(): ServerTime {
        val uri = URI("$api/api/v3/time")
        return httpClient.get<ServerTime>(uri).body()
    }

    override fun exchangeInfo(vararg symbols: String): ExchangeInfo {
        val queryParams = if (symbols.isEmpty()) {
            ""
        } else if (symbols.size == 1) {
            "?symbol=${symbols[0]}"
        } else {
            "?symbols=[${symbols.joinToString()}]"
        }
        val uri = URI("$api/api/v3/exchangeInfo$queryParams")
        return httpClient.get<ExchangeInfo>(uri).body()
    }

    override fun depth(symbol: String, limit: Int): MarketDepth {
        val uri = URI("$api/api/v3/depth?symbol=$symbol&limit=$limit")
        return httpClient.get<MarketDepth>(uri).body()
    }

    override fun trades(symbol: String, limit: Int): List<Trade> {
        val uri = URI("$api/api/v3/trades?symbol=$symbol&limit=$limit")
        return httpClient.get<List<Trade>>(uri).body()
    }

    override fun historicalTrades(symbol: String, limit: Int, mbxApiKey: String, fromId: Long?): List<Trade> {
        val queryParams = fromId?.let { "&fromId=$it" } ?: ""
        val uri = URI("$api/api/v3/historicalTrades?symbol=$symbol&limit=$limit$queryParams")
        return httpClient.get<List<Trade>>(uri = uri, headers = mapOf("X-MBX-APIKEY" to mbxApiKey)).body()
    }

    override fun candlesticks(symbol: String, interval: String, startTime: Long?, endTime: Long?, limit: Int): List<Candlestick> {
        val startTimeQueryParameter = startTime?.let { "&startTime=$it" } ?: ""
        val endTimeQueryParameter = endTime?.let { "&endTime=$it" } ?: ""
        val uri = URI("$api/api/v3/klines?symbol=$symbol&interval=$interval&limit=$limit$startTimeQueryParameter$endTimeQueryParameter")
        val candlesticks = httpClient.get<List<List<String>>>(uri).body()
        return candlesticks.map {
            Candlestick(
                openTime = it[0].toLong(),
                open = it[1].toDouble(),
                high = it[2].toDouble(),
                low = it[3].toDouble(),
                close = it[4].toDouble(),
                volume = it[5].toDouble(),
                closeTime = it[6].toLong(),
                quoteAssetVolume = it[7].toDouble(),
                numberOfTrades = it[8].toLong(),
                takerBuyBaseAssetVolume = it[9].toDouble(),
                takerBuyQuoteAssetVolume = it[10].toDouble(),
                ignore = it[11].toDouble()
            )
        }
    }

    override fun averagePrice(symbol: String): AveragePrice {
        val uri = URI("$api/api/v3/avgPrice?symbol=$symbol")
        return httpClient.get<AveragePrice>(uri).body()
    }

    override fun dailyTicker(symbol: String) {
        TODO("Not yet implemented")
    }

    override fun priceTicker(symbol: String?) {
        TODO("Not yet implemented")
    }

    override fun orderBookTicker(symbol: String?) {
        TODO("Not yet implemented")
    }

    companion object {
        const val api = "https://api1.binance.com"
    }
}