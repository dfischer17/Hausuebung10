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
public class WorkerPwd1 implements Callable<String> {

    static final String pwd1 = "6cd2017cdafb4b2d6412eb50c7a8e457dac6e5c5a5a528d03231462e5d774589";
    private int firstAsciiCode;
    private int lastAsciiCode;

    public WorkerPwd1(int firstAsciiCode, int lastAsciiCode) {
        this.firstAsciiCode = firstAsciiCode;
        this.lastAsciiCode = lastAsciiCode;
    }
            
    // 65 - 90
    @Override
    public String call() throws Exception {
        // Liste moeglicher Passwoerter erstellen
        char[] temp = new char[6];
        
        for (int i = firstAsciiCode; i <= lastAsciiCode; i++) {
            for (int j = 65; j <= 90; j++) {
                for (int k = 65; k <= 90; k++) {
                    for (int l = 65; l <= 90; l++) {
                        for (int m = 65; m <= 90; m++) {
                            for (int n = 65; n <= 90; n++) {
                                temp[0] = (char) i;
                                temp[1] = (char) j;
                                temp[2] = (char) k;
                                temp[3] = (char) l;
                                temp[4] = (char) m;
                                temp[5] = (char) n;

                                String curPwd = String.copyValueOf(temp);
                                if (StringUtil.applySha256(curPwd).equals(pwd1)) {
                                    return curPwd;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
