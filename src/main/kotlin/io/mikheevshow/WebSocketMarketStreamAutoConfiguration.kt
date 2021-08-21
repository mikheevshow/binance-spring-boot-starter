package io.mikheevshow

import io.mikheevshow.event.EventHandler
import io.mikheevshow.event.EventHandlerImpl
import io.mikheevshow.event.listener.BinanceListener
import io.mikheevshow.event.listener.ListenerProvider
import io.mikheevshow.event.listener.ListenerProviderImpl
import io.mikheevshow.stream.BinanceWebSocketListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.http.HttpClient
import java.net.http.WebSocket

@Configuration
class WebSocketMarketStreamAutoConfiguration {

    @Bean
    fun listeners(@Autowired binanceListeners: List<BinanceListener<*>>) = ListenerProviderImpl(binanceListeners)

    @Bean
    fun eventHandler(listenerProvider: ListenerProvider) = EventHandlerImpl(listenerProvider)

    @Bean
    fun binanceWebSocketListener(eventHandler: EventHandler) = BinanceWebSocketListener(eventHandler)

    @Bean(destroyMethod = "abort")
    fun binanceWebSocketChannel(binanceWebSocketListener: BinanceWebSocketListener): WebSocket {
        return HttpClient
            .newHttpClient()
            .newWebSocketBuilder()
            .buildAsync(
                URI("wss://stream.binance.com:9443/ws"),
                binanceWebSocketListener)
            .get()
    }
}