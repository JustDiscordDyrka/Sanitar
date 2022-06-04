package org.dyrka.sanitar.database

import org.dyrka.sanitar.database.level.LevelDataBase
import org.dyrka.sanitar.database.util.Startup

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

    val startup = Startup()

    val levels = LevelDataBase()

}