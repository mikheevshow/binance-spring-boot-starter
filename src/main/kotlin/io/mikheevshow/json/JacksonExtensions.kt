package io.mikheevshow.json

import com.fasterxml.jackson.databind.JsonNode

fun JsonNode.getText(fieldName: String): String = this.get(fieldName).asText()

fun JsonNode.getDouble(fieldName: String): Double = this.get(fieldName).asDouble()

fun JsonNode.getLong(fieldName: String): Long = this.get(fieldName).asLong()

fun JsonNode.getBoolean(fieldName: String): Boolean = this.get(fieldName).asBoolean()

fun JsonNode.getText(index: Int): String = this.get(index).asText()

fun JsonNode.getDouble(index: Int): Double = this.get(index).asDouble()

fun JsonNode.getLong(index: Int): Long = this.get(index).asLong()