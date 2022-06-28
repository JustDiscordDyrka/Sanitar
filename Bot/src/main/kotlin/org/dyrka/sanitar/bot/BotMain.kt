package org.dyrka.sanitar.bot

import com.google.common.io.Resources
import dev.minn.jda.ktx.events.CoroutineEventManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import org.dyrka.sanitar.bot.level.levelStuff
import org.dyrka.sanitar.bot.music.registerMusicCommands
import org.dyrka.sanitar.database.DataBaseAPI
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.io.FileOutputStream
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

        val jdaModule = module {
            single { jda }
        }

        loadKoinModules(jdaModule)

        delay(5000L)

        botInit(jda)
    }

    private suspend fun botInit(jda: JDA) {
        val url = javaClass.classLoader.getResource("libdatabase_rs${if (System.getProperty("os.name") == "Windows") ".dll" else ".so"}")

        if (url != null) {
            withContext(Dispatchers.IO) {
                Resources.copy(
                    url,
                    FileOutputStream("${System.getProperty("user.dir")}/libdatabase_rs${if (System.getProperty("os.name") == "Windows") ".dll" else ".so"}")
                )
            }
        }

        DataBaseAPI.instance!!.startup.startup()

        val dyrka = jda.getGuildById("621722954002333696")
        levelStuff(dyrka!!)

        registerCommands()
    }

    private suspend fun registerCommands() {
        registerMusicCommands()
    }

}