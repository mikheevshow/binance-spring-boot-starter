package io.mikheevshow.stream

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Component
import java.net.http.WebSocket
import kotlin.random.Random
import kotlin.random.nextUInt

data class Subscription(
    val method: String = "SUBSCRIBE",
    val params: List<String>,
    val id: UInt = Random.nextUInt()
)

@Component
class MarketStreamsImpl(val binanceWebSocketChannel: WebSocket, val objectMapper: ObjectMapper) : MarketStreams {

    private val logger = logger()

    override fun subscribe(vararg streams: String) {
        logger.info { "Trying to subscribe to streams: `$streams`" }
        if (!binanceWebSocketChannel.isInputClosed) {
            val subscription = Subscription(params = streams.asList())
            binanceWebSocketChannel.sendText(objectMapper.writeValueAsString(subscription), true)
        }
    }

    override fun unsubscribe(vararg streams: String) {
        logger.info { "Trying to unsubscribe from streams: `$streams`" }
        if (!binanceWebSocketChannel.isInputClosed) {
            val subscription = Subscription(
                method = "UNSUBSCRIBE",
                params = streams.asList()
            )
            binanceWebSocketChannel.sendText(objectMapper.writeValueAsString(subscription), true)
        }
    }
}