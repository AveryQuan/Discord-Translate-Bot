package com.github.TirgA.Modes;


import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.*;
import com.google.cloud.translate.*;

import java.net.http.WebSocket;
import java.util.Hashtable;
import java.util.List;

// translates text from a user to their registered language
public class TranslateMode {
    private Hashtable<String, String> userList;
    private List<Language> languages;

    public TranslateMode() {

        userList = new Hashtable<>(5);

        Translate translate = TranslateOptions.getDefaultInstance().getService();
        languages = translate.listSupportedLanguages();

    }

    public boolean userRegistered(String userID) {
        return userList.containsKey(userID);
    }

    //EFFECTS: detects language and returns translated text
    public String getMsg(String userID, String msg) {

        Translate translate = TranslateOptions.getDefaultInstance().getService();
        Translate.TranslateOption text = Translate.TranslateOption.format("text");
        Translation translation = translate.translate(msg, Translate.TranslateOption.targetLanguage(userList.get(userID)), text);


        return translation.getTranslatedText();

    }

    //EFFECTS: determines if command is properly formatted
    public boolean validCommand(String msg, String userID) {
        String command;
        String targetLang;

        try {
            //get first word
            command = msg.substring(0, msg.indexOf(" "));

            //get second word
            targetLang = msg.substring(msg.indexOf(" ") + 1);

        } catch (Exception e) {
            command = msg;
            targetLang = null;
        }

        if (command.equals(".translateoff") && userRegistered(userID)) {
            return true;
        }
        return command.equals(".translateon") && targetLang != null && languageExist(targetLang);
    }

    //REQUIRES: command is a valid command
    //EFFECTS: Adds/removes users and their desired language from translating list
    //MODIFIES: this
    public String processCommand(String userID, String msg) {
        String command;
        String targetLang;

        if (msg.contains(" ")) {
            //get first word
            command = msg.substring(0, msg.indexOf(" "));
            //get second word
            targetLang = msg.substring(msg.indexOf(" ") + 1);
        } else {
            command = msg;
            targetLang = null;
        }

        switch(command) {
            //add user to translate list or change user's desired language
            case ".translateon":
                if (targetLang != null) {
                    if (!userList.containsKey(userID)) {
                        userList.put(userID, targetLang);
                        return "Your language has been changed to " + getLanguages(targetLang);
                    } else {
                        userList.replace(userID, targetLang);
                        return "Your language is now " + getLanguages(targetLang);
                    }
                }

                //remove user from translate list
            case ".translateoff":
                if (userList.containsKey(userID)) {
                    userList.remove(userID);
                }
                return "Your messages will no longer be translated";

            default:
                return "Your command was invalid";
        }

    }

    //EFFECTS: gets language name for a language code
    public String getLanguages(String code) {
        for (Language language : languages) {
            if (code.equals(language.getCode())) {
                return language.getName();
            }
        }
        return "";
    }

    //EFFECTS: determines if a language code is valid
    public boolean languageExist(String code) {
        for (Language language : languages) {
            if (code.equals((language.getCode()))) {
                return true;
            }
        }
        return false;
    }








}
