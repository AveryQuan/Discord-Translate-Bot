## Discord-Translate-Bot


Allows users that speak different languages to communicate.
The discord bot will translate user text to the target language

### Requirements
 You will need to setup [Google Cloud Translate](https://cloud.google.com/translate/docs/setup) and [Javacord](https://github.com/Javacord/Javacord)

### Getting Started
After Cloning the project, create a class called Secret.java. This is where you will store your important keys and IDs.
Copy and paste the code below and replace the constant values


    public static final String BOT_CHANNEL = "Insert Channel ID";

    public static final String FURRY_CHANNEL = "Insert Channel ID";

    public static final String TOKEN = "Insert Bot token";

    public static final String GOOGLE_API_KEY = "Insert google translate api key";
    
- How to generate bot tokens [here](https://www.writebots.com/discord-bot-token/)
- Getting a Google api key [here](https://translatepress.com/docs/automatic-translation/generate-google-api-key/)
- Channels IDs can be found by copying the code below into main and typing in the desired channel.

        api.addMessageCreateListener(event -> {
            System.out.println(event.getChannel().getIdAsString());
        });


Invite your bot to your discord server using the link given. Make sure to replace <client-id> with your bots client id.

https://discordapp.com/oauth2/authorize?&client_id=<CLIENT ID>&scope=bot&permissions=8
