package com.github.TirgA;

import com.github.TirgA.Secret;
import com.github.TirgA.Modes.FurryMode;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.*;

import java.util.Optional;

import org.javacord.api.entity.message.embed.EmbedBuilder;


public class Main {

    public static void main(String[] args) {
        // Insert your bot's token here
        String token = Secret.returnToken();

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();


        Optional<TextChannel> channel = api.getTextChannelById(Constants.BOT_CHANNEL);
        // A text channel with the id 123 exists. It's safe to call #get() now
        channel.ifPresent(textChannel -> textChannel.sendMessage("Hello this is the bot. type .help for more commands. okokok"));


        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase(".help") ) {
                if (event.getChannel().getIdAsString().equals(Constants.BOT_CHANNEL)) {
                    //System.out.println(event.getChannel().getIdAsString());
                    new MessageBuilder()
                            //.append("Look at these ")
                            //.append(" animal pictures! :smile:")
                            //.appendCode("java", "System.out.println(\"Sweet!\");")
                            .setEmbed(new EmbedBuilder()
                                            .setImage("")
                                            .setTitle("COMMANDS")
                                            .addField(".help", "prints list of commands")
                                    //.setDescription("Really cool pictures!")
                            )
                            //.setColor(Color.ORANGE))
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