package com.github.rising3.lifegame.ui

import com.github.rising3.lifegame.core.LifeGamePattern
import com.github.rising3.lifegame.core.LifeGameStage
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.Timer

@ExperimentalUnsignedTypes
class CellPanel(private val stage: LifeGameStage) : JPanel(GridLayout(stage.rows, stage.cols)) {
    private val timer: Timer

    init {
        (0 until stage.rows).forEach { row ->
            (0 until stage.cols).forEach { col ->
                add(Cell(row, col, stage))
            }
        }
        repaint()
        timer = Timer(30) { next() }
    }

    fun start() {
        if (!timer.isRunning) timer.start()
    }

    fun stop() {
        if (timer.isRunning) timer.stop()
    }

    fun next() {
        stage.next()
        repaint()
    }

    fun reset(pattern: LifeGamePattern) {
        stage.load(pattern.reset())
        components.forEach {
            if (it is Cell) {
                it.clear()
            }
        }
        repaint()
    }
}