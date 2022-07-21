package org.dyrka.sanitar.database.level

import org.dyrka.sanitar.database.DataFilesHolder
import org.koin.core.component.KoinComponent
import org.mapdb.DBMaker
import org.mapdb.Serializer

class LevelDataBase : KoinComponent {

    fun setDefaultLevelIfNotExists(memberId: Long) {
        if (getLevel(memberId) == -5L) {
            setLevel(memberId, 0L)
            setXp(memberId, 0L)
        }
    }

    fun getLevel(id: Long): Long {
        val db = DataFilesHolder.instance!!.levelsDatabase

        val levels = db.hashMap("levels", Serializer.LONG, Serializer.LONG_ARRAY).createOrOpen();

        val level = levels[id]

        if (level != null) {
            return level[0]
        }

        return -5L
    }

    fun setLevel(id: Long, level: Long) {
        val db = DataFilesHolder.instance!!.levelsDatabase

        val levels = db.hashMap("levels", Serializer.LONG, Serializer.LONG_ARRAY).createOrOpen();

        if (getLevel(id) == -5L) {
            levels[id] = longArrayOf(level, 0)
        } else {
            levels[id] = longArrayOf(level, getXp(id))
        }

    }

    fun getXp(id: Long): Long {
        val db = DataFilesHolder.instance!!.levelsDatabase

        val levels = db.hashMap("levels", Serializer.LONG, Serializer.LONG_ARRAY).createOrOpen();

        val level = levels[id]

        if (level != null) {
            return level[1]
        }

        return -5L
    }

    fun setXp(id: Long, xp: Long) {
        val db = DataFilesHolder.instance!!.levelsDatabase

        val levels = db.hashMap("levels", Serializer.LONG, Serializer.LONG_ARRAY).createOrOpen();

        if (getXp(id) == -5L) {
            levels[id] = longArrayOf(0, xp)
        } else {
            levels[id] = longArrayOf(getLevel(id), xp)
        }
    }

}