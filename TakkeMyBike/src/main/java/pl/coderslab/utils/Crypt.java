package pl.coderslab.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Crypt {

    private static SecretKey getKey(String sender, String receiver){
        char [] senderArray = sender.toCharArray();
        char [] receiverArray = receiver.toCharArray();
        String firstKey = String.valueOf(
                senderArray[3]+receiverArray[7]+
                        senderArray[11]+receiverArray[14]+
                        senderArray[18]+receiverArray[22]+
                        senderArray[26]+receiverArray[29]+
                        senderArray[33]+receiverArray[37]+
                        senderArray[41]+receiverArray[44]+
                        senderArray[48]+receiverArray[52]+
                        senderArray[56]+receiverArray[59]);
        byte[] keyBytes = (firstKey).getBytes(StandardCharsets.UTF_8);
        byte[] keyBytesPadded = new byte[16];
        System.arraycopy(keyBytes, 0, keyBytesPadded, 0, Math.min(keyBytes.length, 16));
        return new SecretKeySpec(keyBytesPadded, "AES");
    }

    public static String encrypt(String message, String sender, String receiver) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getKey(sender, receiver));
        byte[] encryptedText = cipher.doFinal(message.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedText);
    }

    public static String decrypt(String encryptedMessage, String sender, String receiver) throws Exception {
        byte[] decodedEncryptedText = Base64.getDecoder().decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getKey(sender, receiver));
        byte[] decryptedText = cipher.doFinal(decodedEncryptedText);
        return new String(decryptedText, "UTF-8");
    }
}
