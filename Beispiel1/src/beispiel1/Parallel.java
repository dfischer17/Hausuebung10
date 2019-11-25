/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beispiel1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel Fischer
 */
public class Parallel extends Serial {

    public static void main(String[] args) {
        //crackLowserCasePassword(4); // 1s
        //crackUpperCasePassword(4); // 2 min 48s
        crackLowerAndUpperCasePassword(4);
    }

    private static void crackLowserCasePassword(int amountOfThreads) {
        List<String> possiblePasswords = createLowerCasePasswordList();

        int interval = possiblePasswords.size() / amountOfThreads; // 406250

        // Moegliche Passwoerter auf Callables aufteilen
        List<Callable<String>> callables = new ArrayList<>();

        for (int i = 0; i < possiblePasswords.size() - interval; i += interval) {
            if (possiblePasswords.size() - (i + interval) < 1) {
                callables.add(new WorkerPwd0(possiblePasswords.subList(i, i + possiblePasswords.size()), pwd0));
            }
            callables.add(new WorkerPwd0(possiblePasswords.subList(i, i + interval), pwd0));
        }

        // Liste der Callables mit ExecutorService ausfuehren
        ExecutorService executor = Executors.newFixedThreadPool(amountOfThreads);
        List<Future<String>> results = new ArrayList<>();

        try {
            results = executor.invokeAll(callables);
        } catch (InterruptedException ex) {
            Logger.getLogger(Parallel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Passwort (not null) ausgeben
        for (Future<String> result : results) {
            String temp;
            try {
                temp = result.get();
                if (temp != null) {
                    System.out.println("Passwort: " + temp);
                }

                executor.shutdown();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Parallel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void crackUpperCasePassword(int amountOfThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(amountOfThreads);
        List<Callable<String>> workers = new ArrayList<>();
        List<Future<String>> results = new ArrayList<>();

        // weise die 25 moeglichen Kombinationen der ersten Stelle an 5 Callables
        for (int i = 65; i <= 90; i += 6) {
            workers.add(new WorkerPwd1(i, i + 5));
        }

        try {
            // fuehre Workers mit ExecutorService aus und speichere Ergebnisse in result List
            results = executor.invokeAll(workers);
        } catch (InterruptedException ex) {
            Logger.getLogger(Parallel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // richtiges Ergebnis (nicht null) ausgeben
        results.forEach(r -> {
            try {
                String temp;
                temp = r.get();

                if (temp != null) {
                    System.out.println("Passwort:" + temp);
                }

            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Parallel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        executor.shutdown();
    }

    private static void crackLowerAndUpperCasePassword(int amountOfThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(amountOfThreads);
        List<Callable<String>> workers = new ArrayList<>();
        List<Future<String>> results = new ArrayList<>();

        // weise die 59 moeglichen Kombinationen der ersten Stelle an 3 Callables
        for (int i = 0; i <= 59; i += 19) {
            workers.add(new WorkerPwd2(i, i + 19));
        }

        try {
            // fuehre Workers mit ExecutorService aus und speichere Ergebnisse in result List
            results = executor.invokeAll(workers);
        } catch (InterruptedException ex) {
            Logger.getLogger(Parallel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // richtiges Ergebnis (nicht null) ausgeben
        results.forEach(r -> {
            try {
                String temp;
                temp = r.get();

                if (temp != null) {
                    System.out.println("Passwort:" + temp);
                }

            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Parallel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        executor.shutdown();
    }

    private static List<String> createLowerCasePasswordList() {
        List<String> possiblePasswords = new ArrayList<>();

        // Liste moeglicher Passwoerter erstellen
        for (int i = 97; i <= 122; i++) {
            for (int j = 97; j < 122; j++) {
                for (int k = 97; k < 122; k++) {
                    for (int l = 97; l < 122; l++) {
                        char[] temp = new char[4];
                        temp[0] = (char) i;
                        temp[1] = (char) j;
                        temp[2] = (char) k;
                        temp[3] = (char) l;
                        String curPwd = String.copyValueOf(temp);
                        possiblePasswords.add(curPwd);
                    }
                }
            }
        }
        return possiblePasswords;
    }

    private static List<String> createUpperCasePasswordList() {
        List<String> possiblePasswords = new ArrayList<>();

        // Liste moeglicher Passwoerter erstellen
        for (int i = 65; i <= 90; i++) {
            for (int j = 65; j <= 90; j++) {
                for (int k = 65; k <= 90; k++) {
                    for (int l = 65; l <= 90; l++) {
                        for (int m = 65; m <= 90; m++) {
                            for (int n = 65; n < 90; n++) {
                                char[] temp = new char[6];
                                temp[0] = (char) i;
                                temp[1] = (char) j;
                                temp[2] = (char) k;
                                temp[3] = (char) l;
                                temp[4] = (char) m;
                                temp[5] = (char) n;

                                String curPwd = String.copyValueOf(temp);
                                possiblePasswords.add(curPwd);
                            }
                        }
                    }
                }
            }
        }
        return possiblePasswords;
    }

    private static List<String> createLowerAndUpperCasePasswordList() {
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

        // Liste moeglicher Passwoerter erstellen
        List<String> possiblePasswords = new ArrayList<>();
        char[] temp = new char[4];
        String curPwd;

        for (Character character : all) {
            for (Character character1 : all) {
                for (Character character2 : all) {
                    for (Character character3 : all) {
                        for (Character character4 : all) {
                            temp[0] = character;
                            temp[1] = character1;
                            temp[2] = character2;
                            temp[3] = character3;
                            temp[3] = character4;

                            curPwd = String.copyValueOf(temp);
                            possiblePasswords.add(curPwd);
                        }
                    }
                }
            }
        }
        return possiblePasswords;
    }
}
