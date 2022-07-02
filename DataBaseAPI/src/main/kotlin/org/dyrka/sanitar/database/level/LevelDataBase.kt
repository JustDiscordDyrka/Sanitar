package org.dyrka.sanitar.database.level

import org.astonbitecode.j4rs.api.Instance
import org.astonbitecode.j4rs.api.java2rust.Java2RustUtils

class LevelDataBase {

    fun setDefaultLevelIfNotExists(memberId: Long) {
        if (getLevel(memberId) == -5L) {
            setLevel(memberId, 0L)
            setXp(memberId, 0L)
        }
    }

    init {
        System.load("${System.getProperty("user.dir")}/libdatabase_rs${if (System.getProperty("os.name") == "Windows") ".dll" else ".so"}")
    }

    fun getLevel(id: Long): Long = Java2RustUtils.getObjectCasted<Long>(getLevelRs(Java2RustUtils.createInstance(id)))

    fun setLevel(id: Long, level: Long) = setLevelRs(Java2RustUtils.createInstance<Long>(id), Java2RustUtils.createInstance<Long>(level))

    private external fun getLevelRs(id: Instance<Long>): Instance<Long>

    private external fun setLevelRs(id: Instance<Long>, level: Instance<Long>)

    fun getXp(id: Long): Long = Java2RustUtils.getObjectCasted<Long>(getXpRs(Java2RustUtils.createInstance(id)))

    fun setXp(id: Long, xp: Long) = setXpRs(Java2RustUtils.createInstance<Long>(id), Java2RustUtils.createInstance<Long>(xp))

    private external fun getXpRs(id: Instance<Long>): Instance<Long>

    private external fun setXpRs(id: Instance<Long>, xp: Instance<Long>)

}