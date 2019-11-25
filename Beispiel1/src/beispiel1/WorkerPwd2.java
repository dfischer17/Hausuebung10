/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beispiel1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Daniel Fischer
 */
public class WorkerPwd2 implements Callable<String> {
    static String pwd2 = "a8d6f454f4b4ff90aef14abe614f61eede264190e088dee0995e17434d1dc2bf";
    private int startIndex;
    private int endIndex;

    public WorkerPwd2(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
            
    @Override
    public String call() throws Exception {
        List<Character> possibleCharacters = new ArrayList<>();

        // Kleinbuchstaben
        for (int i = 97;
                i <= 122; i++) {
            possibleCharacters.add((char) i);
        }

        // Grossbuchstaben
        for (int i = 65;
                i <= 90; i++) {
            possibleCharacters.add((char) i);
        }

        // Zahlen
        for (int i = 48;
                i <= 57; i++) {
            possibleCharacters.add((char) i);
        }

        // Bruteforce
        char[] temp = new char[5];
        String curPwd;

        for (int i = startIndex; i <= endIndex; i++) { // 59 Zeichen
            for (Character character1 : possibleCharacters) {
                for (Character character2 : possibleCharacters) {
                    for (Character character3 : possibleCharacters) {
                        for (Character character4 : possibleCharacters) {
                            temp[0] = possibleCharacters.get(i);
                            temp[1] = character1;
                            temp[2] = character2;
                            temp[3] = character3;
                            temp[4] = character4;

                            curPwd = String.copyValueOf(temp);
                            if (StringUtil.applySha256(curPwd).equals(pwd2)) {
                                return curPwd;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
