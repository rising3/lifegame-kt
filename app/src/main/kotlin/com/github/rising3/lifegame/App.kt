/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.github.rising3.lifegame

import com.github.rising3.lifegame.ui.LifeGamePanel
import javax.swing.SwingUtilities

class App() {
    init {
        SwingUtilities.invokeLater { LifeGamePanel.createAndShowGUI() }
    }
}

fun main() {
    App()
}
