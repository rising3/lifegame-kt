package com.github.rising3.lifegame.core

class LifeGameRuleA16B6 : LifeGameRule {
    override fun eval(target: Boolean, aliveCount: Int): Boolean =
        (target && (aliveCount == 1 || aliveCount == 6)) || (!target && aliveCount == 6)
}