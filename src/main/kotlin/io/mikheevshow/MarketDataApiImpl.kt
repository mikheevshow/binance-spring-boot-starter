package io.mikheevshow

class MarketDataApiImpl : MarketDataApi {

    override fun ping(): Boolean {
        TODO("Not yet implemented")
    }

    override fun serverTime() {
        TODO("Not yet implemented")
    }

    override fun exchangeInfo(vararg symbol: String) {
        TODO("Not yet implemented")
    }

    override fun depth(symbol: String, limit: Int?) {
        TODO("Not yet implemented")
    }

    override fun tradesList(symbol: String, limit: Int?) {
        TODO("Not yet implemented")
    }

    override fun candlesticks(symbol: String, interval: String, startTime: Long?, endTime: Long?, limit: Int?) {
        TODO("Not yet implemented")
    }

    override fun averagePrice(symbol: String) {
        TODO("Not yet implemented")
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
        const val api = ""
    }
}