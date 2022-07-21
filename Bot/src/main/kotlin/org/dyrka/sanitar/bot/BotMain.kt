package org.dyrka.sanitar.bot

import dev.minn.jda.ktx.events.CoroutineEventManager
import kotlinx.coroutines.delay
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import org.dyrka.sanitar.bot.level.levelStuff
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.io.File
import javax.security.auth.login.LoginException

class BotMain {

    @kotlin.jvm.Throws(LoginException::class)
    suspend fun botMain(token: String) {
        val intents = mutableListOf<GatewayIntent>()

        for (intent in GatewayIntent.values()) {
            intents.add(intent)
        }

        val jda: JDA = JDABuilder.create(
            token,
            intents
        ).apply {
            setEventManager(CoroutineEventManager())

            setActivity(Activity.watching("на Дурку"))
        }.build()

        val dataFolder = File("./data/")

        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }

        val jdaModule = module {
            single { jda }
        }

        loadKoinModules(jdaModule)

        delay(5000L)

        botInit(jda)
    }

    private suspend fun botInit(jda: JDA) {
        val dyrka = jda.getGuildById("621722954002333696")
        levelStuff(dyrka!!)

        registerCommands()
    }

    private fun registerCommands() {
        registerBotCommands()
    }
}