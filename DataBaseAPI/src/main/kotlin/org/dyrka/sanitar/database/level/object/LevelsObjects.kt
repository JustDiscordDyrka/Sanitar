package org.dyrka.sanitar.database.level.`object`

import org.jetbrains.exposed.sql.Table

object Levels: Table() {
    val id = long("id")
    val level = long("level")

    override val primaryKey = PrimaryKey(id)
}