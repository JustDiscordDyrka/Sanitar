package org.dyrka.sanitar.database.level

import org.dyrka.sanitar.database.DataBaseAPI
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LevelDataBaseTest {

    @Test
    fun testLevels() {

        val level = DataBaseAPI.instance!!.levels.getLevel(1000)

        println("Level before assign: $level")

        assertEquals(-5, level)

        DataBaseAPI.instance!!.levels.setLevel(1000, 100)

        val newLevel = DataBaseAPI.instance!!.levels.getLevel(1000)

        println("Level after assign: $newLevel")

        assertEquals(100, newLevel)

    }

    @Test
    fun testXp() {

        val xp = DataBaseAPI.instance!!.levels.getXp(1001)

        println("Xp before assign: $xp")

        assertEquals(-5, xp)

        DataBaseAPI.instance!!.levels.setXp(1001, 150)

        val newXp = DataBaseAPI.instance!!.levels.getXp(1001)

        println("Xp after assign: $newXp")

        assertEquals(150, newXp)

    }

}