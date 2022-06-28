package org.dyrka.sanitar.database.util

class Startup {

    suspend fun startup() {
        createDataBase()
    }

    private fun createDataBase() {
        createLevelDatabase()
    }

    init {
        System.load("${System.getProperty("user.dir")}/libdatabase_rs${if (System.getProperty("os.name") == "Windows") ".dll" else ".so"}")
    }

    private external fun createLevelDatabase()

}