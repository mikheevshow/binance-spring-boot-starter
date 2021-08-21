package io.mikheevshow.event

interface EventHandler {
    suspend fun handleEvent(rawData: CharSequence)
}