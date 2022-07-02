package org.dyrka.sanitar.database

import org.dyrka.sanitar.database.level.LevelDataBase

class DataBaseAPI {

    companion object {

        private var INSTANCE: DataBaseAPI? = null
        val instance: DataBaseAPI?
        get() {
            if (INSTANCE == null) {
                INSTANCE = DataBaseAPI()
            }
            return INSTANCE
        }

    }

    val levels = LevelDataBase()

}