package com.github.rising3.lifegame.core

import java.lang.IllegalArgumentException
import kotlin.math.abs

@ExperimentalUnsignedTypes
internal class BitArray private constructor(
    val rows: Int,
    val cols: Int,
    val isVirtualArray: Boolean,
    private val bitArray: Array<UByte>,
) {
    fun getBit(row: Int, col: Int): Boolean =
        try {
            (bitArray[idx(row, col)] and onMask(col)).countOneBits() == 1
        } catch (e: IllegalArgumentException) {
            false
        }

    fun setBit(row: Int, col: Int, bit: Boolean = true): BitArray =
        try {
            bitArray[idx(row, col)] = if (bit) bitArray[idx(row, col)] or onMask(col) else bitArray[idx(row, col)] and offMask(col)
            this
        } catch (e: IllegalArgumentException) {
            this
        }

    fun countBits(): Int = bitArray.sumOf { it.countOneBits() }

    fun copy(isVirtualArray: Boolean = this.isVirtualArray): BitArray = BitArray(
        this.rows,
        this.cols,
        isVirtualArray,
        Array(this.bitArray.size) { this.bitArray[it] },
    )

    fun clear(): BitArray {
        bitArray.indices.forEach { bitArray[it] = 0u }
        return this
    }

    fun stringBuilder(): StringBuilder = StringBuilderImpl(bitArray, rows, colsMax())

    private fun onMask(col: Int): UByte = (0x80u shr colSafe(col) % 8).toUByte()

    private fun offMask(col: Int): UByte = onMask(col) xor 0xFFu

    private fun colsMax(): Int = colsMax(cols)

    private fun idx(row: Int, col: Int): Int = when (isVirtualArray) {
        true -> colSafe(col) / 8 + rowSafe(row) * colsMax()
        false -> when {
            ((row < 0) or (row >= rows)) -> throw IllegalArgumentException()
            ((col < 0) or (col >= cols)) -> throw IllegalArgumentException()
            else -> row * colsMax() + col / 8
        }
    }

    private fun rowSafe(row: Int): Int = if (row >= rows) (row % rows) else if (row < 0) rows - twoComp(row, rows + 1) else row

    private fun colSafe(col: Int): Int = if (col >= cols) (col % cols) else if (col < 0) cols - twoComp(col, cols + 1) else col

    private fun twoComp(a: Int, b: Int): Int = abs(if (a % b == 0) 1 else a % b)

    companion object {
        @JvmStatic
        fun new(
            rows: Int,
            cols: Int,
            isVirtualArray: Boolean = false,
        ): BitArray {
            require(rows >= 1) { "Int |rows| must be non-negative. rows=$rows" }
            require(cols >= 8) { "Int |cols| must be non-negative. cols=$cols" }
            return BitArray(
                rows,
                cols,
                isVirtualArray,
                Array(rows * colsMax(cols)) { 0u },
            )
        }

        private fun colsMax(cols: Int): Int = cols / 8 + if (cols % 8 == 0) 0 else 1
    }

    interface StringBuilder {
        fun withLineSeparator(_lineSeparator: CharSequence): StringBuilder
        fun withLinePrefix(_linePrefix: CharSequence): StringBuilder
        fun withLinePostfix(_linePostfix: CharSequence): StringBuilder
        fun withByteSeparator(_byteSeparator: CharSequence): StringBuilder
        fun withBytePrefix(_bytePrefix: CharSequence): StringBuilder
        fun withBytePostfix(_bytePostfix: CharSequence): StringBuilder
        fun withBitSeparator(_bitSeparator: CharSequence): StringBuilder
        fun withBitPrefix(_bitPrefix: CharSequence): StringBuilder
        fun withBitPostfix(_bitPostfix: CharSequence): StringBuilder
        fun build(): String
    }

    internal class StringBuilderImpl(private val bitArray:Array<UByte>, private val rows: Int, private val cols: Int): StringBuilder {
        private var lineSeparator: CharSequence = ""
        private var linePrefix: CharSequence = ""
        private var linePostfix: CharSequence = ""
        private var byteSeparator: CharSequence = ""
        private var bytePrefix: CharSequence = ""
        private var bytePostfix: CharSequence = ""
        private var bitSeparator: CharSequence = ""
        private var bitPrefix: CharSequence = ""
        private var bitPostfix: CharSequence = ""

        override fun withLineSeparator(_lineSeparator: CharSequence): StringBuilder {
            lineSeparator = _lineSeparator
            return this
        }

        override fun withLinePrefix(_linePrefix: CharSequence): StringBuilder {
            linePrefix = _linePrefix
            return this
        }

        override fun withLinePostfix(_linePostfix: CharSequence): StringBuilder {
            linePostfix = _linePostfix
            return this
        }

        override fun withByteSeparator(_byteSeparator: CharSequence): StringBuilder {
            byteSeparator = _byteSeparator
            return this
        }

        override fun withBytePrefix(_bytePrefix: CharSequence): StringBuilder {
            bytePrefix = _bytePrefix
            return this
        }

        override fun withBytePostfix(_bytePostfix: CharSequence): StringBuilder {
            bytePostfix = _bytePostfix
            return this
        }

        override fun withBitSeparator(_bitSeparator: CharSequence): StringBuilder {
            bitSeparator = _bitSeparator
            return this
        }

        override fun withBitPrefix(_bitPrefix: CharSequence): StringBuilder {
            bitPrefix = _bitPrefix
            return this
        }

        override fun withBitPostfix(_bitPostfix: CharSequence): StringBuilder {
            bitPostfix = _bitPostfix
            return this
        }

        override fun build(): String =
            Array(rows) { build(it * cols) }.joinToString(
                separator = lineSeparator,
                prefix = linePrefix,
                postfix = linePostfix
            )

        private fun build(offset: Int = 0): String =
            Array(cols) { build(bitArray[offset + it]) }.joinToString(
                separator = byteSeparator,
                prefix = bytePrefix,
                postfix = bytePostfix
            )

        private fun build(b: UByte): String =
            Array(8) { if ((b and (0x80u shr it).toUByte()).countOneBits() == 1) "1" else "0" }.joinToString(
                separator = bitSeparator,
                prefix = bitPrefix,
                postfix = bitPostfix
            )
    }
}