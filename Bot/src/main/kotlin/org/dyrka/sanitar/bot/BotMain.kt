package org.dyrka.sanitar.bot

import dev.minn.jda.ktx.events.CoroutineEventManager
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.*
import javax.security.auth.login.LoginException

class BotMain {

    @kotlin.jvm.Throws(LoginException::class)
    suspend fun botMain(token: String) {
        val intents = mutableListOf<GatewayIntent>()

        for (intent in GatewayIntent.values()) {
            intents.add(intent)
        }

        val jda: JDA = JDABuilder.createDefault(
            token
        ).apply {
            enableIntents(intents)
            setEventManager(CoroutineEventManager())
        }.build()

        val jdaModule = module {
            single { jda }
        }

        loadKoinModules(jdaModule)

        botInit(jda)
    }

    suspend fun botInit(jda: JDA) {

    }

}