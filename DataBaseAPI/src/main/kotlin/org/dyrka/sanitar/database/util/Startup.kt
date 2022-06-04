package org.dyrka.sanitar.database.util

import org.dyrka.sanitar.database.level.`object`.Levels
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.io.File

class Startup {

    suspend fun startup() {
        createDataBase()
        createLevelsTable()
    }

    private fun createDataBase() {
//        if (!File("dyrkadatabase.db").exists()) {
//            SchemaUtils.createDatabase("dyrkadatabase.db")
//        }

        val connection = Database.connect("jdbc:sqlite:dyrkadatabase.db", driver = "org.sqlite.JDBC")

        val databaseModule = module {
            single { connection }
        }

        loadKoinModules(databaseModule)
    }

    private fun createLevelsTable() {
        transaction {
            SchemaUtils.create(Levels)
        }
    }

}