package org.dyrka.sanitar.database

import org.mapdb.DBMaker

class DataFilesHolder {

    companion object {
        private var INSTANCE: DataFilesHolder? = null
        val instance: DataFilesHolder?
        get() {
            if (INSTANCE == null) {
                INSTANCE = DataFilesHolder()
            }

            return INSTANCE
        }
    }

    val levelsDatabase = DBMaker.fileDB("./data/level.db").apply {
        fileMmapEnable()
        closeOnJvmShutdown()
    }.make()

}