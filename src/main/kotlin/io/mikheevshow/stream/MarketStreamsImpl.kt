package io.mikheevshow.stream

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.net.http.WebSocket
import kotlin.random.Random
import kotlin.random.nextUInt

data class Subscription(
    val method: String = "SUBSCRIBE",
    val params: List<String>,
    val id: UInt
)

@Component
class MarketStreamsImpl(val binanceWebSocketChannel: WebSocket, val objectMapper: ObjectMapper) : MarketStreams {

    override fun subscribe(vararg streams: String) {
        if (!binanceWebSocketChannel.isInputClosed) {
            val subscription = Subscription(params = streams.asList(), id = Random.nextUInt())
            binanceWebSocketChannel.sendText(objectMapper.writeValueAsString(subscription), true)
        }
    }

    override fun unsubscribe(vararg streams: String) {
        TODO("Not yet implemented")
    }
}