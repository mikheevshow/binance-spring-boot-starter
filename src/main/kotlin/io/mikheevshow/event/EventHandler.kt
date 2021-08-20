package io.mikheevshow.event

interface EventHandler {
    fun handleEvent(rawData: CharSequence)
}