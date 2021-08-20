package io.mikheevshow

import io.mikheevshow.event.EventHandlerImpl
import io.mikheevshow.stream.BinanceWebSocketListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.http.HttpClient
import java.net.http.WebSocket

@Configuration
class WebSocketMarketStreamAutoConfiguration {

    @Bean
    fun eventHandler() = EventHandlerImpl()

    @Bean
    fun binanceWebSocketListener() = BinanceWebSocketListener(eventHandler())

    @Bean(destroyMethod = "abort")
    fun binanceWebSocketChannel(): WebSocket {
        return HttpClient
            .newHttpClient()
            .newWebSocketBuilder()
            .buildAsync(
                URI("wss://stream.binance.com:9443/ws"),
                binanceWebSocketListener())
            .get()
    }
}