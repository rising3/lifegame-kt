package com.github.rising3.lifegame.ui

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import kotlin.system.exitProcess


class LifeGameMenu: JMenuBar(), ActionListener {
    init {
        val menuExit = JMenuItem("Exit")
        menuExit.mnemonic = KeyEvent.VK_X
        menuExit.addActionListener(this)
        val menuFile = JMenu("File")
        menuFile.mnemonic = KeyEvent.VK_F
        menuFile.add(menuExit)

        val menuAbout = JMenuItem("About")
        menuAbout.mnemonic = KeyEvent.VK_A
        menuAbout.addActionListener(this)
        val menuHelp = JMenu("Help")
        menuHelp.mnemonic = KeyEvent.VK_H
        menuHelp.add(menuAbout)

        add(menuFile)
        add(menuHelp)
    }

    override fun actionPerformed(e: ActionEvent?) {
        e as ActionEvent
        when (e.actionCommand) {
            "Exit" -> exitProcess(0)
        }
    }
}