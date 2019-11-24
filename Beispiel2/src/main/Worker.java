/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Daniel Fischer
 */
public class Worker implements Callable<Integer>{
    
    private int subArraySize;
    private List<Integer> numbers;

    public Worker(int subArraySize, List<Integer> numbers) {
        this.subArraySize = subArraySize;
        this.numbers = numbers;
    }
        
    @Override
    public Integer call() throws Exception {
        List<List<Integer>> subLists = new ArrayList<>();
        int maxUniqueNumbers = 0;
        
        // int List in subLists aufteilen        
        for (int i = subArraySize; i <= numbers.size(); i++) {
            subLists.add(numbers.subList(i - subArraySize, i));
        }

        // maximale Anzahl an unterschiedlichen Zahlen von subLists bestimmen        
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
        return maxUniqueNumbers;
    }    
}
