package org.dyrka.sanitar.core

import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.dyrka.sanitar.bot.BotMain
import org.koin.core.context.startKoin

fun main(): Unit = runBlocking {

    startKoin {}

    coroutineScope {
        launch {
            var token = System.getenv("TOKEN").takeUnless { text ->
                text == ""
            }

            if (token == null) {
                token = dotenv()["TOKEN"]
            }

            val botMain = BotMain()

            botMain.botMain(token!!)
        }

    }

}