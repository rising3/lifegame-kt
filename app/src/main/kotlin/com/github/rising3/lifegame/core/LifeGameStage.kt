package com.github.rising3.lifegame.core

@ExperimentalUnsignedTypes
class LifeGameStage(
    val rows: Int,
    val cols: Int,
    isVirtualArray: Boolean = false,
    private val rule: LifeGameRule = LifeGameRuleA23B3(),
) {
    private val stage: BitArray = BitArray.new(rows, cols, isVirtualArray)
    private var name: String = ""

    fun getName(): String = name

    fun getBit( row: Int, col: Int): Boolean = stage.getBit(row, col)

    fun isDead(): Boolean = stage.countBits() == 0

    fun load(pattern: LifeGamePattern): LifeGameStage {
//        assert(Objects.nonNull(cells)) { "cells[] must be not null" }
//        assert(this.cells.length < cells.length) { "this.length < cells.length" }
//        assert(this.cells.get(0).length() < cells.get(0).length()) { "this[].length < cells[].length" }
        stage.clear()
        name = pattern.name
        val rowOffset: Int = (rows - pattern.rows()) / 2
        val colOffset: Int = (cols - pattern.cols()) / 2
        (0 until pattern.rows()).forEach { row ->
            (0 until pattern.cols()).forEach { col ->
                stage.setBit(colOffset + col, rowOffset + row, pattern.data[row][col] == "1")
            }
        }
        return this
    }

    fun next(): LifeGameStage {
        val wk = stage.copy()
        (0 until wk.rows).forEach { row ->
            (0 until wk.cols).forEach { col ->
                stage.setBit(
                    row,
                    col,
                    deadOrAlive(row, col, wk)
                )
            }
        }
        return this
    }

    override fun toString(): String = stage.stringBuilder().withLineSeparator("\n").build()

    private fun deadOrAlive(row: Int, col: Int, wk: BitArray): Boolean = rule.eval(wk.getBit(row, col), aliveCount(row, col, wk))

    private fun aliveCount(row: Int, col: Int, wk: BitArray): Int =
        booleanArrayOf(
            wk.getBit(row, col - 1),
            wk.getBit(row, col + 1),
            wk.getBit(row - 1, col),
            wk.getBit(row + 1, col),
            wk.getBit(row + 1, col + 1),
            wk.getBit(row - 1, col - 1),
            wk.getBit(row + 1, col - 1),
            wk.getBit(row - 1, col + 1),
        ).filter { it }.count()
}