package com.github.rising3.lifegame.core

interface LifeGameRule {
    fun eval(target: Boolean, aliveCount:Int ): Boolean
}