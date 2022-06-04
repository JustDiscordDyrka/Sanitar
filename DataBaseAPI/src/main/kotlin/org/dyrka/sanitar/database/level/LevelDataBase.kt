package org.dyrka.sanitar.database.level

import org.dyrka.sanitar.database.level.`object`.Levels
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class LevelDataBase {

    fun setDefaultLevelIfNotExists(memberId: Long) {
        transaction {
            val newMemberLevel = Levels.select { Levels.id eq memberId }.firstOrNull()

            if (newMemberLevel == null) {
                Levels.insert {
                    it[id] = memberId
                    it[level] = 0
                }
            }

            commit()
        }
    }

    fun getLevel(id: Long) = transaction { Levels.select { Levels.id eq id }.first()[Levels.level] }

    fun setLevel(id: Long, level: Long) = transaction {
        Levels.update( { Levels.id eq id } ) {
            it[Levels.level] = level
        }
        commit()
    }

}