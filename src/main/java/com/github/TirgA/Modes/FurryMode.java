package com.github.TirgA.Modes;
import java.util.Random;

public class FurryMode {

    private static String[] endings = {"\n ~nya <3 \n", " ^OWO^ \n", " uwu \n"};

    public static String convertMsg(String msg) {
        String temp = "";

        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            String replace = "";

            //single character swap
            if (c == 'r' || c == 'l') {
                c = 'w';
                temp += c;
            }
            //double character swap
            else if (c == 'n' && isVowel(msg.charAt(i + 1))) {
                temp += "ny" + msg.charAt(i + 1);
            } else if ((c == ' ' && (msg.charAt(i + 1) + "").equals(" ")) || i == msg.length() - 1 ){
                temp += c + addEnding();

            } else {
                temp += c;
            }


        }

        return temp;
    }

    private static boolean isVowel(char c) {
        return "AEIOUaeiou".indexOf(c) != -1;
    }

    private static String addEnding() {
        Random generator = new Random();
        int num = generator.nextInt(2);
        String ending = "" ;
        if (1 == num) {
            num = generator.nextInt(endings.length);
            ending += endings[num] ;
        }

        return ending;
    }


}
