/*  Author: Avery Quan
    https://github.com/TirgA

Resources used:
    Google Cloud Translation: https://cloud.google.com/translate/docs
    Javacord: https://github.com/Javacord/Javacord

Main Function:
    Allows users to talk to each other in different languages, the bot will translate to
    the desired language.

 */


package com.github.TirgA;

import com.github.TirgA.Modes.TranslateMode;
import com.github.TirgA.Secret;
import com.github.TirgA.Modes.FurryMode;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.*;

import java.awt.*;
import java.util.Optional;

import org.javacord.api.entity.message.embed.EmbedBuilder;



public class Main {

    public static void main(String[] args) {
        // Insert your discord bot's token here
        String token = Secret.TOKEN;

        System.setProperty("GOOGLE_API_KEY", Secret.GOOGLE_API_KEY);
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        //Uncomment to print url to invite bot, replace last digit with 8
        //System.out.println(api.createBotInvite());

        // check if channel exists
        Optional<TextChannel> channel = api.getTextChannelById(Secret.BOT_CHANNEL);
        channel.ifPresent(textChannel -> textChannel.sendMessage("Hello I am a Language bot. \n type \".help\" for more commands."));

        addFurryListener(api);
        addCommandListener(api);
        addTranslateListener(api);

    }

    //MODIFIES: DiscordApi
    //EFFECTS: Adds a message listener to all channels to translate messages
    public static void addTranslateListener(DiscordApi api) {
        TranslateMode translate = new TranslateMode();

        // Add Translate Listener
        api.addMessageCreateListener(event -> {

            if (!event.getMessageAuthor().isBotUser() && !event.getChannel().getIdAsString().equals(Secret.FURRY_CHANNEL) && !event.getChannel().getIdAsString().equals(Secret.BOT_CHANNEL)) {
                if (translate.validCommand(event.getMessageContent(), event.getMessageAuthor().getIdAsString())) {
                    String result = translate.processCommand(event.getMessageAuthor().getIdAsString(), event.getMessageContent());

                    new MessageBuilder()
                            .setEmbed(new EmbedBuilder()
                                    .setColor(Color.BLUE)
                                    .setThumbnail(event.getMessageAuthor().getAvatar())
                                    .setDescription(result)
                                    .setTitle(event.getMessageAuthor().getDisplayName())
                            )
                            .send(event.getChannel());


                }
                else if (translate.userRegistered(event.getMessageAuthor().getIdAsString())) {

                    new MessageBuilder()
                            .setEmbed(new EmbedBuilder()
                                    .setColor(Color.YELLOW)
                                    .setAuthor(event.getMessageAuthor())
                                    .setDescription(translate.getMsg(event.getMessageAuthor().getIdAsString(), event.getMessageContent()))

                            )
                            .send(event.getChannel());

                    event.deleteMessage();
                }

            }
        });
    }

    //MODIFIES: DiscordApi
    //EFFECTS: adds message listener to all channels to listen for commands
    public static void addCommandListener(DiscordApi api) {
        // General commands
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase(".help") ) {
                if (event.getChannel().getIdAsString().equals(Secret.BOT_CHANNEL)) {
                    new MessageBuilder()

                            .setEmbed(new EmbedBuilder()
                                    .setTitle("COMMANDS")
                                    .addField(".help", "prints list of commands", true)
                                    .addField(".translateon <languagecode>", "Translates all messages to the target language." +
                                            " A list of language codes can be found at:       https://cloud.google.com/translate/docs/languages")
                                    .addField(".translateoff", "Stop translating your messages")
                                    .setFooter("Go talk in the furry channel for a surprise!!")
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
    }

    //MODIFIES: DiscordApi
    //EFFECTS: adds message listener to all channels to convert messages to baby talk
    public static void addFurryListener(DiscordApi api) {
        api.addMessageCreateListener(event -> {
            if (event.getChannel().getIdAsString().equals(Secret.FURRY_CHANNEL)) {
                if (!event.getMessageAuthor().isBotUser()) {

                    new MessageBuilder()
                            .setEmbed(new EmbedBuilder()
                                    .setColor(Color.YELLOW)
                                    .setAuthor(event.getMessageAuthor())
                                    .setDescription(FurryMode.convertMsg( event.getMessageContent()))

                            )
                            .send(event.getChannel());

                    event.deleteMessage();
                }
            }

        });
    }


}


