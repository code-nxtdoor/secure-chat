
package Client.Encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

public class AESEncryption {
    private String message;

    public AESEncryption(String message, Key key) throws GeneralSecurityException {
        byte[] plainText = message.getBytes(StandardCharsets.UTF_8);
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        System.out.println("\nStart encryption using AES:");

        final long startTime = System.nanoTime();
        //  Initializes the Cipher object
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));

        // Calculates the ciphertext with a plaintext string.
        byte[] cipherText = cipher.doFinal(plainText);
        byte[] envelope = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, envelope, 0, iv.length);
        System.arraycopy(cipherText, 0, envelope, iv.length, cipherText.length);
        this.message = new String(envelope, StandardCharsets.ISO_8859_1);
        final long duration = System.nanoTime() - startTime;
        System.out.println("Finish encryption using AES: ");
        System.out.println("It took " + duration + " nanosecond to encrypt the message \"" + message +"\" using AES");
        System.out.println("Message length is " + message.length());
    }

    public String getMessage() {
        return message;
    }
}
