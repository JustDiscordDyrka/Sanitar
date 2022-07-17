package org.dyrka.sanitar.bot.report

import dev.minn.jda.ktx.events.listener
import dev.minn.jda.ktx.events.onCommand
import dev.minn.jda.ktx.generics.getChannel
import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.interactions.components.Modal
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import org.koin.core.context.GlobalContext

fun reportCommandHandler() {
    val jda = GlobalContext.get().get<JDA>()

    jda.onCommand("report") { event ->
        event.replyModal(Modal.create("report", "Жалоба").apply {
            addActionRow(net.dv8tion.jda.api.interactions.components.text.TextInput.create("username", "Пользователь", TextInputStyle.SHORT).apply {
                placeholder = "Никнейм пользователя на которого вы жалуетесь"
            }.build()).build()

            addActionRow(net.dv8tion.jda.api.interactions.components.text.TextInput.create("reportText", "Жалоба", TextInputStyle.PARAGRAPH).apply {
                placeholder = "Ваша жалоба на пользователя"
            }.build()).build()
        }.build()).queue()
    }

    jda.listener<ModalInteractionEvent> {event ->
        if (event.modalId == "report") {
            event.deferReply(true).queue()

            val username = event.getValue("username")
            val reportText = event.getValue("reportText")

            jda.getChannel<TextChannel>(764430220035883008)!!.sendMessageEmbeds(
                Embed {
                    title = "Жалоба на юзера!"
                    description = """
                        Жалоба на: ${username!!.asString}
                        Жалоба: ${reportText!!.asString}
                    """.trimIndent()
                    color = 0xFF0000
                }
            ).queue()

            event.interaction.hook.sendMessage("Спасибо за жалобу!").setEphemeral(true).queue()
        }
    }
}