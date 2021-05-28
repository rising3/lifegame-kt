package com.github.rising3.lifegame.ui

import com.github.rising3.lifegame.core.LifeGameCatalog
import com.github.rising3.lifegame.core.LifeGamePattern
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import javax.swing.*


@ExperimentalUnsignedTypes
class ControlPanel(private val cellPanel: CellPanel) : JPanel(), ActionListener, ItemListener {
    private val patternComboBox: JComboBox<LifeGamePattern>
    private val startButton: JButton
    private val stopButton: JButton
    private val nextButton: JButton
    private val resetButton: JButton

    init {
        val patterns = DefaultComboBoxModel(LifeGameCatalog.getInstance().catalog.toTypedArray())
        patternComboBox = JComboBox(patterns)
        patternComboBox.addActionListener(this)
        patternComboBox.addItemListener(this)
        patternComboBox.preferredSize = Dimension(256, 32)
        startButton = newButton("START")
        startButton.preferredSize = Dimension(128, 32)
        stopButton = newButton("STOP")
        stopButton.preferredSize = Dimension(128, 32)
        nextButton = newButton("NEXT")
        nextButton.preferredSize = Dimension(128, 32)
        resetButton = newButton("RESET")
        resetButton.preferredSize = Dimension(128, 32)
        add(patternComboBox)
        add(startButton)
        add(stopButton)
        add(nextButton)
        add(resetButton)
        reset()
    }

    private fun newButton(text: String): JButton {
        val button = JButton(text)
        button.addActionListener(this)
        return button
    }

    private fun start() {
        patternComboBox.isEnabled = false
        startButton.isEnabled = false
        stopButton.isEnabled = true
        nextButton.isEnabled = false
        resetButton.isEnabled = false
        cellPanel.start()
    }

    private fun stop() {
        patternComboBox.isEnabled = true
        startButton.isEnabled = true
        stopButton.isEnabled = false
        nextButton.isEnabled = true
        resetButton.isEnabled = true
        cellPanel.stop()
    }

    private fun next() {
        cellPanel.next()
    }

    private fun reset() {
        patternComboBox.isEnabled = true
        startButton.isEnabled = true
        stopButton.isEnabled = false
        nextButton.isEnabled = true
        resetButton.isEnabled = true
        val item = patternComboBox.selectedItem as LifeGamePattern
        cellPanel.reset(item)
    }

    override fun actionPerformed(e: ActionEvent?) {
        e as ActionEvent
        when (e.actionCommand) {
            "START" -> start()
            "STOP" -> stop()
            "NEXT" -> next()
            "RESET" -> reset()
        }
    }

    override fun itemStateChanged(e: ItemEvent?) {
        e as ItemEvent
        if (e.stateChange == ItemEvent.SELECTED) {
            reset()
        }
    }
}
