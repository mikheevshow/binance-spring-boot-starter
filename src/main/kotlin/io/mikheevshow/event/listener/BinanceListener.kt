package io.mikheevshow.event.listener

interface BinanceListener<T> {
    fun newEvent(event: T)
}