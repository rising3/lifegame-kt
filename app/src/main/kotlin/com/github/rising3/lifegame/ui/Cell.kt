package com.github.rising3.lifegame.ui

import com.github.rising3.lifegame.core.LifeGameStage
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JComponent

@ExperimentalUnsignedTypes
internal class Cell(private val row: Int, private val col: Int,  private val stage: LifeGameStage) : JComponent() {
    private var color: Int = 0

    fun clear() {
        color = 0
    }

    override fun getPreferredSize(): Dimension {
        return Dimension(10, 10)
    }

    override fun paintComponent(g: Graphics?) {
        g as Graphics
        super.paintComponent(g)
        color = if (stage.getBit(row, col)) 255 else if (color - 100 > 0) color - 100 else 0
        g.color = Color(0, color, 0)
        g.fillRect(0, 0, width, height)
    }
}
