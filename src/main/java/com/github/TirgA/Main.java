package com.github.TirgA;

import com.github.TirgA.Secret;
import com.github.TirgA.Modes.FurryMode;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.*;

import java.util.Optional;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import com.google.cloud.translate.*;


public class Main {

    public static void main(String[] args) {
        // Insert your bot's token here
        String token = Secret.TOKEN;
        System.setProperty("GOOGLE_API_KEY", Secret.GOOGLE_API_KEY);

         //Translate translate = TranslateOptions.getDefaultInstance().getService();
        //Translation translation = translate.translate("JE veux aller la \n" +
                //"ห้องสมุด!");
        //System.out.printf("Translated Text:\n\t%s\n", translation.getTranslatedText());

        try {
            String str = new String("سلام".getBytes(), "UTF-8");
        }
        catch (Exception e) {
            System.out.println("Something went wrong.");
        }

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        // check if channel exists
        Optional<TextChannel> channel = api.getTextChannelById(Secret.BOT_CHANNEL);
        channel.ifPresent(textChannel -> textChannel.sendMessage("Hello I am a Language bot. \n type \".help\" for more commands."));


        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase(".help") ) {
                if (event.getChannel().getIdAsString().equals(Secret.BOT_CHANNEL)) {
                    new MessageBuilder()

                            .setEmbed(new EmbedBuilder()
                                            .setImage("")
                                            .setTitle("COMMANDS")
                                            .addField(".help", "prints list of commands")
                            )
                            .send(event.getChannel());
                }
                else {

                    new MessageBuilder()
                            .setEmbed(new EmbedBuilder()
                                    .setDescription("Use the Bot channel to command the bot")
                            )
                            .send(event.getChannel());
                }
            }
        });

        api.addMessageCreateListener(event -> {
            if (!event.getMessageAuthor().isBotUser()) {

                event.editMessage(FurryMode.convertMsg(event.getMessageContent()));
            }
        });


    }

}