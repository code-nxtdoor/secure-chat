

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;

public class MessageDecryption {
    private String message;

    public MessageDecryption(String message, Key key,String type) throws Exception {
        byte[] envelope = message.getBytes(StandardCharsets.ISO_8859_1);
        if (envelope.length < 28) {
            throw new IllegalArgumentException("Encrypted message is too short");
        }
        byte[] iv = Arrays.copyOfRange(envelope, 0, 12);
        byte[] cipherText = Arrays.copyOfRange(envelope, 12, envelope.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        System.out.println("\nStart decryption");
        final long startTime = System.nanoTime();
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));
        byte[] newPlainText = cipher.doFinal(cipherText);
        final long duration = System.nanoTime() - startTime;
        System.out.println("Finish decryption: ");

        this.message = new String(newPlainText, StandardCharsets.UTF_8);
        System.out.println("It took " + duration + " nanosecond to decrypt the message");
        Launcher.takenTimeD=duration;
        System.out.println("Message length is " + this.message.length());
    }

    public String getMessage() {
        return message;
    }
}
