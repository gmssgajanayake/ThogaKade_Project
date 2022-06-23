package lk.ijse.pos.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class SecurityConfig {
    private static SecretKeySpec secretKey;
    private static byte[] key;
    public final static String holdingSecretKey="abcdefghijklmnopqrstuvwxyz";

    public static String encrypt(final String plainPassword,final String secret){
        try {
            setKey(secret);
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(plainPassword.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(final String plainPassword,final String secret){
        try {
            setKey(secret);
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            return new String(
              cipher.doFinal(
                      Base64.getDecoder().decode(plainPassword)
              )
            );
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }


    public static void setKey(final String importedKey){
        MessageDigest sha=null;
        try {
            key=importedKey.getBytes(StandardCharsets.UTF_8);
            sha=MessageDigest.getInstance("SHA-1");
            key=sha.digest(key);
            key= Arrays.copyOf(key,16);
            secretKey=new SecretKeySpec(key,"AES");

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
