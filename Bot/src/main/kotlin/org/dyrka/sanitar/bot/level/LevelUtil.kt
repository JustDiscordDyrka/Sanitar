package org.dyrka.sanitar.bot.level

import dev.minn.jda.ktx.events.listener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import org.dyrka.sanitar.database.DataBaseAPI
import org.koin.core.context.GlobalContext

fun levelHandlers() {
    val jda = GlobalContext.get().get<JDA>()

    jda.listener<GuildMemberJoinEvent> {
        DataBaseAPI.instance!!.levels.setDefaultLevelIfNotExists(it.member.idLong)
    }
}

fun setDefaultLevelToNewMembers(dyrka: Guild) {
    for (member in dyrka.members) {
        DataBaseAPI.instance!!.levels.setDefaultLevelIfNotExists(member.idLong)
    }
}

fun levelStuff(dyrka: Guild) {
    setDefaultLevelToNewMembers(dyrka)
    levelHandlers()
}