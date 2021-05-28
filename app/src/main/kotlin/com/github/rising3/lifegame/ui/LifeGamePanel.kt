package com.github.rising3.lifegame.ui

import com.github.rising3.lifegame.core.LifeGameStage
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*

@ExperimentalUnsignedTypes
class LifeGamePanel : JPanel(BorderLayout()) {
    init {
        val cellPanel = CellPanel(LifeGameStage(80, 80))
        val controlPanel = ControlPanel(cellPanel)
        add("North", controlPanel)
        add(cellPanel)
        this.
        preferredSize = Dimension(1024, 880)
    }

    companion object {
        fun createAndShowGUI() {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            val frame = JFrame("Conway's Game of Life")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.jMenuBar = LifeGameMenu()
            frame.contentPane.add(LifeGamePanel())
            frame.pack()
            frame.setLocationRelativeTo(null)
            frame.isResizable = false
            frame.isVisible = true
        }
    }
}