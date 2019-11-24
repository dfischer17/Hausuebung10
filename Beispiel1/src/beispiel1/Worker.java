/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beispiel1;

import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Daniel Fischer
 */
public class Worker implements Callable<String> {

    private List<String> possiblePasswords;
    private String searchedPassword;

    public Worker(List<String> possiblePasswords, String searchedPassword) {
        this.possiblePasswords = possiblePasswords;
        this.searchedPassword = searchedPassword;
    }
        
    @Override
    public String call() throws Exception {
        // Passwoerter testen
        for (String currentPassword : possiblePasswords) {
            if (StringUtil.applySha256(currentPassword).equals(searchedPassword)) {
                return currentPassword;
            }
        }
        return null;
    }
}
