package knt.lib.generic1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

import javax.crypto.Cipher;

import org.junit.jupiter.api.Test;

public class TestSecurityHelper1 {
    // TestSecurityHelper1#testgetRandomNumberValid
    @Test
    public void testgetRandomNumberValid() {
        int outputVar;
        int minInt = 1;
        int maxInt = 129;
        outputVar = GenericUtility1.getRandomNumber(minInt, maxInt);
        System.out.println("**************************");
        System.out.println("securityHelper1:getRandomArray: " + outputVar);
        System.out.println("**************************");
        assertTrue(outputVar > minInt && outputVar < maxInt);
    }

    @Test
    public void testGetRandomArray() {
        ArrayList<Integer> outputVar;
        outputVar = GenericUtility1.getRandomArray(1, 129, 6);
        System.out.println("securityHelper1:getRandomArray: " + outputVar);
        assertTrue(outputVar != null);
    }

    public void testGetUniformNumberString() {
        SecurityHelper1 securityHelper1 = new SecurityHelper1("content1", "passCode1");
        // ArrayList<Integer> inputArray2 = new int[]{10, 1, 9, 122, 292, 8};
        ArrayList<Integer> inputArray = new ArrayList<>();
        for (int inputItem : new int[] { 10, 1, 9, 122, 292, 8 }) {
            inputArray.add(inputItem);
        }
        securityHelper1.getUniformNumberString(inputArray);
        String outputVar = securityHelper1.getContentEn();
        System.out.println("securityHelper1:contentEn: " + outputVar);
        assertTrue(outputVar != null);
    }

    public void testCodesEnV1() {
        String content = "TestInput";
        System.out.println("securityHelper1:testCodesEn:content: " + content);
        SecurityHelper1 securityHelper1 = new SecurityHelper1(content, null);
        securityHelper1.codesEnV1();
        System.out.println("securityHelper1:testCodesEn:contentEn: " + securityHelper1.getContentEn());
        assertTrue(securityHelper1.getContentEn() != null);
        /*
         * Input:content: TestInput
         * content.charCodeAt(i): 84 # passcodeLocal.charCodeAt(passOffset): 36
         * calAscii: 120
         */
    }

    public void testCodesDeV1() {
        String content = "TestInput";
        System.out.println("securityHelper1:testCodesEn:content: " + content);
        SecurityHelper1 securityHelper1 = new SecurityHelper1(content, null);
        securityHelper1.codesEnV1();
        System.out.println("securityHelper1:testCodesEn:contentEn: " + securityHelper1.getContentEn());
        securityHelper1.setContent(null);
        securityHelper1.codesDeV1();
        System.out.println("securityHelper1:testCodes:contentDe: " + securityHelper1.getContent());
        assertTrue(securityHelper1.getContent() != null);

    }

    public void testCodesEnDe_ORIGINAL() {
        try {
            // (String args[]) throws Exception
            // Creating a Signature object
            Signature sign = Signature.getInstance("SHA256withRSA");

            // Creating KeyPair generator object
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            // Initializing the key pair generator
            keyPairGen.initialize(2048);

            // Generate the pair of keys
            KeyPair pair = keyPairGen.generateKeyPair();

            // Getting the public key from the key pair
            PublicKey publicKey = pair.getPublic();

            // Creating a Cipher object
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            // Initializing a Cipher object
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // Add data to the cipher
            byte[] input = "Welcome to Tutorialspoint".getBytes();
            cipher.update(input);

            // encrypting the data
            byte[] cipherText = cipher.doFinal();
            System.out.println(new String(cipherText, "UTF8"));

            // Initializing the same cipher for decryption
            cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());

            // Decrypting the text
            byte[] decipheredText = cipher.doFinal(cipherText);
            System.out.println(new String(decipheredText));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test_generateStoreKeys1() {
        // generateStoreKeys
        final String METHOD_NAME = "test_generateStoreKeys1:";
        String content = "TestInput";
        String algorithm = "RSA";
        System.out.println(
                METHOD_NAME + "securityHelper1:testCodesEn:content: " + content + " # algorithm: " + algorithm);
        SecurityHelper1 securityHelper1 = new SecurityHelper1(content, null);
        KeyPair keyPair = securityHelper1.generateStoreKeys(algorithm);
        byte[] privateKeyBytes;
        byte[] publicKeyBytes;

        try {
            PublicKey publicKey = keyPair.getPublic();
            // Creating a Cipher object
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            // Initializing a Cipher object
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // Add data to the cipher
            byte[] input = securityHelper1.getContent().getBytes();
            cipher.update(input);

            // encrypting the data
            byte[] cipherText = cipher.doFinal();
            System.out.println(METHOD_NAME + "cipherText: " + new String(cipherText, "UTF8"));

            privateKeyBytes = keyPair.getPrivate().getEncoded();
            PrivateKey privateKey = securityHelper1.deSerialize(algorithm, privateKeyBytes, null);

            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // Initializing the same cipher for decryption
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // Decrypting the text
            byte[] decipheredText = cipher.doFinal(cipherText);
            System.out.println(METHOD_NAME + "decipheredText: " + new String(decipheredText));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testSecurityHelper1() {
        String _algorithm = "RSA";
        String _cipherConfigStr;
        // byte[] _publicKeyBytes,
        // byte[] _privateKeyBytes
        SecurityHelper1 securityHelper1 = SecurityHelper1(String _algorithm, String _cipherConfigStr, byte[] _publicKeyBytes,
        byte[] _privateKeyBytes);

    }
}