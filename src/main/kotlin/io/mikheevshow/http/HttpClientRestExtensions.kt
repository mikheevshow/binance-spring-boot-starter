package io.mikheevshow.http

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.IOException
import java.io.UncheckedIOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets.UTF_8


val json = jacksonObjectMapper()

inline fun <reified T : Any> jsonBodyHandler(tr: TypeReference<T>): HttpResponse.BodyHandler<T> = HttpResponse.BodyHandler {
    val upstream = HttpResponse.BodySubscribers.ofString(UTF_8)
    HttpResponse.BodySubscribers.mapping(upstream) { body ->
        try {
            json.readValue(body, tr)
        } catch (ex: IOException) {
            throw UncheckedIOException(ex)
        }
    }
}

inline fun <reified T : Any> HttpClient.get(uri: URI, headers: Map<String, String> = emptyMap()): HttpResponse<T> {
    val request = HttpRequest
        .newBuilder()
        .GET()
        .uri(uri)
        .addHeaders(headers)
        .build()

    return this.send(request, jsonBodyHandler(object: TypeReference<T>() {}))
}

inline fun <reified T : Any> HttpClient.post(uri: URI, body: Any, headers: Map<String, String> = emptyMap()): HttpResponse<T> {
    val request = HttpRequest
        .newBuilder()
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofByteArray(json.writeValueAsBytes(body)))
        .uri(uri)
        .addHeaders(headers)
        .build()

    return this.send(request, jsonBodyHandler(object: TypeReference<T>() {}))
}

fun HttpRequest.Builder.addHeaders(headers: Map<String, String>): HttpRequest.Builder {
    headers.forEach { (t, u) -> this.header(t, u) }
    return this
}