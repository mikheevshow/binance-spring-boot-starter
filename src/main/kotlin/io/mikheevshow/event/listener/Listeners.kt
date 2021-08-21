package io.mikheevshow.event.listener

import io.mikheevshow.event.EventType

interface Listeners {
    fun get(eventType: EventType): List<Listener<*>>
}