package io.mikheevshow.event.listener

interface Listener<T> {
    fun newEvent(event: T)
}