

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;

public class MessageEncryption {
    private String messageString;

    public MessageEncryption(String message, Key key,String type) throws Exception{
        byte[] plainText = message.getBytes(StandardCharsets.UTF_8);
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        System.out.println("\nStart encryption:");

        final long startTime = System.nanoTime();
        //  Initializes the Cipher object
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));

        // Calculates the ciphertext with a plaintext string.
        byte[] cipherText = cipher.doFinal(plainText);
        byte[] envelope = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, envelope, 0, iv.length);
        System.arraycopy(cipherText, 0, envelope, iv.length, cipherText.length);
        this.messageString = new String(envelope, StandardCharsets.ISO_8859_1);
        final long duration = System.nanoTime() - startTime;
        System.out.println("Finish encryption: ");
        System.out.println("It took " + duration + " nanosecond to encrypt the message using AES-GCM");
        Launcher.takenTimeE=duration;
        System.out.println("Message length is " + message.length());
        if(type.equals("AES"))
        {
            Graph.length_Aes.add((double)message.length());
            Graph.time_Aes.add((double)duration/100000);
        }
        else
        {
             Des_Graph.length_Des.add((double)message.length());
            Des_Graph.time_Des.add((double)duration/100000);
        }
    }

    public String getMessageString() {
        return messageString;
    }
}