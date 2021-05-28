package com.github.rising3.lifegame.core

class LifeGameRuleA23B3 : LifeGameRule {
    override fun eval(target: Boolean, aliveCount: Int): Boolean =
        (target && (aliveCount == 2 || aliveCount == 3)) || (!target && aliveCount == 3)
}