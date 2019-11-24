/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beispiel1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author dfischer17
 */
public class Serial {

    static String pwd0 = "95511ec2b03a441daada2e54cad5a8a7ae990e99b4a9f3512b92f672467186b2";
    static String pwd1 = "6cd2017cdafb4b2d6412eb50c7a8e457dac6e5c5a5a528d03231462e5d774589";
    static String pwd2 = "a8d6f454f4b4ff90aef14abe614f61eede264190e088dee0995e17434d1dc2bf";
    static String pwd3 = "520da0807c1e972fb9a862485009d47ad1c4978db1369652f5ae176085eb9df7";
    static List<Character> all = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(crackLowserCasePassword());
    }

    private static String crackLowserCasePassword() {

        String temp = "aaaa";
        StringBuilder builder = new StringBuilder(temp);

        for (int i = 97; i <= 122; i++) {
            for (int j = 97; j < 122; j++) {
                for (int k = 97; k < 122; k++) {
                    for (int l = 97; l < 122; l++) {
                        builder.setCharAt(0, (char) i);
                        builder.setCharAt(1, (char) j);
                        builder.setCharAt(2, (char) k);
                        builder.setCharAt(3, (char) l);
                        if (StringUtil.applySha256(builder.toString()).equals(pwd0)) {
                            return builder.toString();
                        }
                    }
                }
            }
        }
        return "falsch";
    }

    private static String crackUpperCasePassword() {

        String temp = "aaaaaa";
        StringBuilder builder = new StringBuilder(temp);

        for (int i = 65; i <= 90; i++) {
            for (int j = 65; j <= 90; j++) {
                for (int k = 65; k <= 90; k++) {
                    for (int l = 65; l <= 90; l++) {
                        for (int m = 65; m <= 90; m++) {
                            for (int n = 65; n < 90; n++) {
                                builder.setCharAt(0, (char) i);
                                builder.setCharAt(1, (char) j);
                                builder.setCharAt(2, (char) k);
                                builder.setCharAt(3, (char) l);
                                builder.setCharAt(4, (char) m);
                                builder.setCharAt(5, (char) n);
                                if (StringUtil.applySha256(builder.toString()).equals(pwd1)) {
                                    return builder.toString();
                                }
                            }
                        }
                    }
                }
            }
        }
        return "falsch";
    }

    private static String crackLowerAndUpperCasePassword() {
        // Kleinbuchstaben
        for (int i = 97; i <= 122; i++) {
            all.add((char) i);
        }

        // Grossbuchstaben
        for (int i = 65; i <= 90; i++) {
            all.add((char) i);
        }

        // Zahlen
        for (int i = 48; i <= 57; i++) {
            all.add((char) i);
        }

        String temp = "aaaaa";
        StringBuilder builder = new StringBuilder(temp);

        for (Character character : all) {
            for (Character character1 : all) {
                for (Character character2 : all) {
                    for (Character character3 : all) {
                        for (Character character4 : all) {
                            builder.setCharAt(0, character);
                            builder.setCharAt(1, character1);
                            builder.setCharAt(2, character2);
                            builder.setCharAt(3, character3);
                            builder.setCharAt(4, character4);
                            if (StringUtil.applySha256(builder.toString()).equals(pwd2)) {
                                return builder.toString();
                            }
                        }
                    }
                }
            }
        }
        return "falsch";
    }

    private static String crackStrangePassword() {
        Document doc;
        try {
            doc = Jsoup.connect("https://de.wikipedia.org/wiki/Liste_von_Fabelwesen").get();
            Elements links = doc.select("a[href]");
            List<String> temp = links.eachText();

            // Brute Force
            for (String string : temp) {
                if (StringUtil.applySha256(string).equals(pwd3)) {
                    return string;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Serial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "falsch";
    }
}
