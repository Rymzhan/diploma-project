package com.diploma.stats.model.ws

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class Event {
    companion object {


        internal fun <T> createEvent(model: T): String {
            return Gson().toJson(model)
        }

        internal fun getSimpleJsonObject(jsonStr: String): JsonObject {
            return JsonParser().parse(jsonStr).asJsonObject
        }

    }
}