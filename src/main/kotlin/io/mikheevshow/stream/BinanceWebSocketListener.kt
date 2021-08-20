package io.mikheevshow.stream

import io.mikheevshow.event.EventHandler
import org.apache.logging.log4j.kotlin.logger
import java.net.http.WebSocket
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.concurrent.CompletionStage

class BinanceWebSocketListener(val eventHandler: EventHandler) : WebSocket.Listener {

    private val logger = logger()

    override fun onOpen(webSocket: WebSocket) {
        logger.info { "Binance websocket opened" }
        super.onOpen(webSocket)
    }

    override fun onText(webSocket: WebSocket, data: CharSequence, last: Boolean): CompletionStage<*>? {
        logger.debug { "Event received: $data" }
        eventHandler.handleEvent(data)
        return super.onText(webSocket, data, last)
    }

    override fun onPing(webSocket: WebSocket, message: ByteBuffer?): CompletionStage<*>? {
        message?.let {
            val text = StandardCharsets.UTF_8.decode(it)
            logger.info { "Ping frame received from binance: $text" }
        }
        webSocket.sendPong(message)
        return super.onPing(webSocket, message)
    }

    override fun onClose(webSocket: WebSocket, statusCode: Int, reason: String): CompletionStage<*>? {
        logger.info { "Closing binance websocket, status code `$statusCode`, reason: `$reason`" }
        return super.onClose(webSocket, statusCode, reason)
    }

    override fun onError(webSocket: WebSocket, error: Throwable) {
        logger.error(error)
    }
}