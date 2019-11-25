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
import java.util.stream.Collectors;

/**
 *
 * @author dfischer17
 */
public class Serial {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String fileName = "numbers1";
        List<String> lines;
        int subArraySize = 0;
        List<Integer> allNumbers = new ArrayList<>();
        List<List<Integer>> subLists = new ArrayList<>();

        // Zeilen aus Datei einlesen
        lines = Files.readAllLines(Paths.get(fileName));

        // Groesse der subArrays bestimmen
        subArraySize = Integer.valueOf(lines.get(0));

        // Zeilen in int List umwandeln        
        for (int i = 1; i < lines.size(); i++) {
            List<Integer> lineNumbers = Arrays.stream(lines.get(i).split(" ")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            allNumbers.addAll(lineNumbers);
        }

        // int List in subLists aufteilen        
        for (int i = subArraySize; i <= allNumbers.size(); i++) {
            subLists.add(allNumbers.subList(i - subArraySize, i));
        }

        // maximale Anzahl an unterschiedlichen Zahlen von subLists bestimmen
        int maxUniqueNumbers = 0;

        // subLists durchlaufen
        for (List<Integer> subList : subLists) {

            // Zahlen der subLists durchlaufen
            List<Integer> uniqueNumbers = new ArrayList<>();
            for (Integer curNumber : subList) {
                // wenn aktuelle Zahl noch nicht vorgekommen hinzufugen
                if (!uniqueNumbers.contains(curNumber)) {
                    uniqueNumbers.add(curNumber);
                }
            }

            // wenn aktuelle subList die meisten verschiedenen Zahlen hat maxUniqueNumbers erhoehen
            if (uniqueNumbers.size() > maxUniqueNumbers) {
                maxUniqueNumbers = uniqueNumbers.size();
            }
        }

        // maximale Anzahl an unterschiedlichen Zahlen von subLists ausgeben
        System.out.println("Die maximale Anzahl an unterschiedlichen Nummbern ist " + maxUniqueNumbers);
    }
}
