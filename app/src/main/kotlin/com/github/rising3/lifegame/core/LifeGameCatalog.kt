package com.github.rising3.lifegame.core

import java.net.URL

class LifeGameCatalog private constructor() {
    val catalog: List<LifeGamePattern> = listOf(
        LifeGamePattern(80, 80),
        // Still life
        LifeGamePattern.parse(LifeGameCatalog::class.java.getResource("/block.json") as URL),
        LifeGamePattern.parse(LifeGameCatalog::class.java.getResource("/tub.json") as URL),
        LifeGamePattern.parse(LifeGameCatalog::class.java.getResource("/boat.json") as URL),
        // Oscillator
        LifeGamePattern.parse(LifeGameCatalog::class.java.getResource("/blinker.json") as URL),
        LifeGamePattern.parse(LifeGameCatalog::class.java.getResource("/traffic_light.json") as URL),
        LifeGamePattern.parse(LifeGameCatalog::class.java.getResource("/toad.json") as URL),
        LifeGamePattern.parse(LifeGameCatalog::class.java.getResource("/beacon.json") as URL),
        LifeGamePattern.parse(LifeGameCatalog::class.java.getResource("/clock.json") as URL),

        LifeGamePattern.parse(LifeGameCatalog::class.java.getResource("/glider.json") as URL),
    )

    companion object {
        private val instance = LifeGameCatalog()
        fun getInstance(): LifeGameCatalog = instance
    }
}
