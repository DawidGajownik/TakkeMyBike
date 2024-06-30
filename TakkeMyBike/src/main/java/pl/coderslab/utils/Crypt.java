package pl.coderslab.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class Crypt {

    private static SecretKey secretKey;

    static {
        try {
            // Inicjalizacja klucza AES
            secretKey = KeyGenerator.getInstance("AES").generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Szyfruje podaną wiadomość przy użyciu AES.
     * @param message Wiadomość do zaszyfrowania
     * @return Zaszyfrowana wiadomość w postaci Base64
     */
    public static String encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedText = cipher.doFinal(message.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedText);
    }

    /**
     * Deszyfruje podaną zaszyfrowaną wiadomość przy użyciu AES.
     * @param encryptedMessage Zaszyfrowana wiadomość w formacie Base64
     * @return Odszyfrowana wiadomość
     */
    public static String decrypt(String encryptedMessage) throws Exception {
        byte[] decodedEncryptedText = Base64.getDecoder().decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedText = cipher.doFinal(decodedEncryptedText);
        return new String(decryptedText, "UTF-8");
    }
}
