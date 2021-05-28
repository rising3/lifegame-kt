package com.github.rising3.lifegame.core

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.io.InputStream
import java.net.URL
import kotlin.random.Random

data class LifeGamePattern(
    @JsonProperty
    val name: String,
    @JsonProperty
    val data: Array<Array<CharSequence>>,
) {
    constructor(rows: Int, cols: Int) : this("RANDOM", Array(rows) { Array(cols) { "0" } }) {
        reset()
    }

    fun cols(): Int = data[0].size

    fun rows(): Int = data.size

    fun reset(): LifeGamePattern {
        if ("RANDOM" == name) {
            (0 until rows()).forEach { row ->
                (0 until cols()).forEach { col ->
                    data[row][col] = if (Random.nextInt(100) < 70) "0" else "1"
                }
            }
        }
        return this
    }

    fun toJson(): String = jacksonObjectMapper().writeValueAsString(this)

    override fun toString(): String = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LifeGamePattern

        if (name != other.name) return false
        if (!data.contentDeepEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + data.contentDeepHashCode()
        return result
    }

    companion object {
        fun parse(json: String): LifeGamePattern = jacksonObjectMapper().readValue(json)

        fun parse(url: URL): LifeGamePattern = jacksonObjectMapper().readValue(url)

        fun parse(inputStream: InputStream): LifeGamePattern = jacksonObjectMapper().readValue(inputStream)

        fun parse(file: File): LifeGamePattern = jacksonObjectMapper().readValue(file)
    }
}
