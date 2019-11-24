/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Daniel Fischer
 */
public class Parallel {

    public static void main(String[] args) throws IOException {
        final String fileName = "numbers0";
        final int amountOfThreads = 4;
        List<String> lines;
        int subArraySize = 0;
        List<Integer> allNumbers = new ArrayList<>();

        // Zeilen aus Datei einlesen
        lines = Files.readAllLines(Paths.get(fileName));

        // Groesse der subArrays bestimmen
        subArraySize = Integer.valueOf(lines.get(0));

        // Zeilen in eine Integer List zusammenfassen
        for (int i = 1; i < lines.size(); i++) {
            List<Integer> lineNumbers = Arrays.stream(lines.get(i).split(" ")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            allNumbers.addAll(lineNumbers);
        }

        // Integer List aller Zahlne auf Callables aufteilen
        List<Callable<Integer>> callables = new ArrayList<>();
        int partionSize = allNumbers.size() / amountOfThreads;
        if (partionSize < subArraySize) {
            callables.add(new Worker(subArraySize, allNumbers));
        } 
        else {
            for (int i = 0; i < allNumbers.size(); i += partionSize) {
                /*
                Wenn die Anzahl der uebrig bleibenden Zahlen der Gesamtliste kleiner als Groesse
                einer Partition ist wird ein letztes Callables mit kleinerer Partion hinzugefuegt
                */
                if (allNumbers.size() - (i + partionSize) < subArraySize) {
                    callables.add(new Worker(subArraySize, allNumbers.subList(i, allNumbers.size())));
                    break;
                }
                callables.add(new Worker(subArraySize, allNumbers.subList(i, i + partionSize)));
            }
        }

        // Liste der Callables mit ExecutorService ausfuehren
        ExecutorService executor = Executors.newFixedThreadPool(amountOfThreads);
        List<Future<Integer>> results = new ArrayList<>();
        try {
            results = executor.invokeAll(callables);
        } catch (InterruptedException ex) {
            Logger.getLogger(Parallel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // maximale Anzahl an unterschiedlichen Zahlen von subLists ausgeben
        int maxUniqueNumbers = 0;
        for (Future<Integer> result : results) {
            try {
                int temp = result.get();
                if (temp > maxUniqueNumbers) {
                    maxUniqueNumbers = temp;
                }
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Parallel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        executor.shutdown();
        System.out.println("Die maximale Anzahl an unterschiedlichen Nummbern ist " + maxUniqueNumbers);
    }
}
