package io.mikheevshow.event

interface MarketListener<T> {
    fun newEvent(event: T)
}