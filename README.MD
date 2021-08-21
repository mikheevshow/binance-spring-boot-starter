# Binance API Spring Boot Starter

Implements Binance API

## Binance Web Socket Market Events
### Subscribe to Events

Inject `MarketStreams` bean into yours one. Use `subscribe`/`unsubscribe` methods to subscribe to any stream or unsubscribe 
from them.

```kotlin

@Service
class SomeService(val marketStreams: MarketStreams) {
    @PostConstruct
    fun init() {
        marketStreams.subscribe(kline("BNBBTC"), aggTrade("BNBBTC"))
    }
}

```

### Create listeners

Create listener beans to receive events.

```kotlin
@Component
class CustomCandlestickEventListener : CandlestickUpdateListener {
    override fun newEvent(event: CandlestickUpdate) {
        println("New candlestick event received $event")
    }
}

```