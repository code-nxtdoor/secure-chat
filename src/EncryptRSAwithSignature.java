
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class EncryptRSAwithSignature {
    private byte[] cipherKey;
    private byte[] signature;

    public EncryptRSAwithSignature(byte[] plainText, PublicKey publicKey, PrivateKey privateKey) throws Exception{
        // get an RSA cipher object
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

        // encrypt the plaintext using the public key
        System.out.println( "\nStart encryption" );
        cipher.init(Cipher.ENCRYPT_MODE, publicKey, new OAEPParameterSpec(
            "SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
        byte[] cipherText = cipher.doFinal(plainText);
        System.out.println( "Finish encryption: " );
        System.out.println( new String(cipherText, StandardCharsets.UTF_8) );

        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(privateKey);                         //Initializes the Signature object.
        sig.update(cipherText);                                  // Calculates the signature with a plaintext string.
        byte[] signature = sig.sign();
        System.out.println( "\nSignature:" );
        System.out.println( new String(signature, StandardCharsets.UTF_8) );
        this.signature = signature;
        this.cipherKey = cipherText;

        System.out.println("Finish encryption: ");
    }

    public byte[] getCipherKey() {
        return cipherKey;
    }

    public byte[] getSignature() {
        return signature;
    }
}
