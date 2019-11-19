package beispiel1;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {

    /** 
     * Applies SHA-256 algorithm to the given String.
     * 
     * SHA (Secure Hash Algorithm 2) is a set of cryptographic hash functions 
     * designed by the United States National Security Agency (NSA).Cryptographic 
     * hash functions are mathematical operations run on digital data; by com-
     * paring the computed "hash" (the output from execution of the algorithm) 
     * to a known and expected hash value, a person can determine the data's in-
     * tegrity.
     * 
     * @param input Some String.
     * @return  Generated SHA-256 signature.
     **/
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
