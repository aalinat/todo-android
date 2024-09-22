package com.mglabs.twopagetodo.widget

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.io.OutputStream

object TodoWidgetStateDefinition: GlanceStateDefinition<TodoState> {
    private const val DATA_STORE_FILENAME = "todoState"
    private val Context.datastore by dataStore(DATA_STORE_FILENAME, TodoStateSerializer)

    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<TodoState> {
        return context.datastore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(DATA_STORE_FILENAME)
    }

    object TodoStateSerializer : Serializer<TodoState> {
        override val defaultValue = TodoState.Loading

        override suspend fun readFrom(input: InputStream): TodoState = try {
            Json.decodeFromString(
                TodoState.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (exception: SerializationException) {
            throw CorruptionException("Could not read data: ${exception.message}")
        }

        override suspend fun writeTo(t: TodoState, output: OutputStream) {
            output.use {
                it.write(
                    Json.encodeToString(TodoState.serializer(), t).encodeToByteArray()
                )
            }
        }
    }
}