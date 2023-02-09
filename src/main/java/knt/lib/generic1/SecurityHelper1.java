package knt.lib.generic1;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

import javax.crypto.Cipher;

public class SecurityHelper1 {

    private static String PASSCODESHA2048 = "$W%Ld0os$3";
    public static final int KEYPAIR_GEN_CONFIG_2048 = 2048;
    public static final String DEFAULT_ALGORITHM = "RSA";
    public static final String DEFAULT_CIPHER_CONFIG_STR = "RSA/ECB/PKCS1Padding";

    private String passCode = null;
    private String content = null;
    private String contentEn = "";
    // V2 member variables
    private PublicKey publicKey = null;
    private PrivateKey privateKey = null;
    private String algorithm = null;
    private String cipherConfigStr = null;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public String getCipherConfigStr() {
        return cipherConfigStr;
    }

    public void setCipherConfigStr(String cipherConfigStr) {
        this.cipherConfigStr = cipherConfigStr;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentEn() {
        return contentEn;
    }

    public void setContentEn(String contentEn) {
        this.contentEn = contentEn;
    }
    private void initialize() {
        this.passCode = PASSCODESHA2048;
        this.algorithm = DEFAULT_ALGORITHM;
        this.cipherConfigStr = DEFAULT_CIPHER_CONFIG_STR;
    }
    public SecurityHelper1() {
        initialize();
    }
    public SecurityHelper1(String _content) {
        initialize();
        this.content = _content;
    }
    public SecurityHelper1(String _content, String _passCode) {
        initialize();
        this.passCode = _passCode == null || _passCode.length() == 0 ? PASSCODESHA2048 : _passCode;
        this.content = _content == null || _content.length() == 0 ? "" : _content;
    }

    public SecurityHelper1(String _algorithm, String _cipherConfigStr, byte[] _publicKeyBytes,
            byte[] _privateKeyBytes) {
        initialize();
        // V2 member variables
        algorithm = _algorithm == null || _algorithm.length() == 0 ? DEFAULT_ALGORITHM : _algorithm;
        cipherConfigStr = _cipherConfigStr == null || _cipherConfigStr.length() == 0 ? DEFAULT_CIPHER_CONFIG_STR
                : _cipherConfigStr;
        if (_publicKeyBytes != null)
            deSerializePubKe(_publicKeyBytes);
        if (_privateKeyBytes != null)
            deSerializePriKe(_privateKeyBytes);
    }

    /**
     * codeEnV2 version 2 This method encrypts input string based on inputs
     * 
     * @param inputStr
     * @param algorithm       RSA
     * @param cipherConfigStr RSA/ECB/PKCS1Padding
     * @return UTF8 encoded String
     */
    public byte[] codeEn(String inputStr) {
        KeyPair pair = null;
        byte[] cipherText = null;
        // String cipherConfigStr = null;
        try {
            // (String args[]) throws Exception
            // Creating a Signature object
            // Signature sign = Signature.getInstance("SHA256withRSA");

            // // Creating KeyPair generator object
            // KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // // Initializing the key pair generator
            // keyPairGen.initialize(2048);
            // // Generate the pair of keys
            // pair = keyPairGen.generateKeyPair();
            // Getting the public key from the key pair
            // commented to test RSAPublicKey
            // PublicKey publicKeyOri = pair.getPublic();
            this.algorithm = this.algorithm == null ? DEFAULT_ALGORITHM : this.algorithm;
            pair = prepareKeyPair(this.algorithm);

            // Approach changes / TODOs:
            // Create a KeyPair on first signin or use
            // serialize and store it as key values for future use
            // Each SMS is then encrypted and saved.
            // While consumption and segregation use decrypt and use

            // Logic source :
            // https://stackoverflow.com/questions/21606428/serialize-and-deserialize-an-rsa-public-key
            RSAPublicKey publicKey = (RSAPublicKey) pair.getPublic();

            // Creating a Cipher object

            this.cipherConfigStr = this.cipherConfigStr == null ? DEFAULT_CIPHER_CONFIG_STR : this.cipherConfigStr;
            Cipher cipher = Cipher.getInstance(this.cipherConfigStr);

            // Initializing a Cipher object
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // Add data to the cipher
            byte[] input = inputStr.getBytes();
            cipher.update(input);

            // encrypting the data
            cipherText = cipher.doFinal();
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return cipherText;
        }
    }

    /**
     * 
     * Inputs:
     * algorithm, privateKey object
     * @param inputUTF8EncryptedStr
     * @return
     */
    public String codeDe(byte[] inputEncryptedBytes) {
        final String METHOD_NAME = ":codeDe:";
        String outputStr = null;
        try {
            // Generate the pair of keys
            // this.privateKey = deSerializePriKe(null);
            System.out.println(METHOD_NAME + "Milestone:3: inputEncryptedBytes: " + inputEncryptedBytes);
            // Creating a Cipher object
            Cipher cipher = Cipher.getInstance(cipherConfigStr);

            // Initializing the same cipher for decryption
            cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
            // Decrypting the text
            byte[] decipheredText = cipher.doFinal(inputEncryptedBytes);

            outputStr = new String(decipheredText, StandardCharsets.UTF_8);
            System.out.println(METHOD_NAME + "Milestone:5: outputStr: " + outputStr);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(METHOD_NAME + "Milestone:6: ERROR:e.message: " + e.getMessage());
        }
        System.out.println(METHOD_NAME + "Milestone:7: Leaving");
        return outputStr;
    }

    /**
     * This is a WIP Code
     * 
     * @param s
     * @return
     */
    public PublicKey deserializeV1(String s) {
        // String []Parts = s.split("\\|");
        // PublicKey outputPublicKey = null;
        // PrivateKey outputPrivateKey = null;
        // // pair.getPrivate()
        //
        // try {
        // PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(
        // new BigInteger(Parts[0]),
        // new BigInteger(Parts[1]));
        // outputPublicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
        // } catch (Exception e) {
        //
        // }
        // return outputPublicKey;
        return null;
    }

    /**
     * This method is transient code not useful in LIB
     * 
     * @param algorithm
     * @return
     */
    private KeyPair generateStoreKeys(String algorithm) {
        // Getting the public key from the key pair
        // PublicKey publicKey = pair.getPublic();
        // PrivateKey privateKey = pair.getPrivate();
        // String publicKeyString =
        // javax.xml.bind.DatatypeConverter.printBase64Binary(publicKey.getEncoded());
        // String privateKeyString =
        // javax.xml.bind.DatatypeConverter.printBase64Binary(privateKey.getEncoded());
        KeyPair keyPair = null;
        algorithm = algorithm == null || algorithm.isEmpty() ? "RSA" : algorithm; // DSA, RSA, or DH
        byte[] privateKeyBytes = null;
        byte[] publicKeyBytes = null;
        PrivateKey privateKey;
        try {
            // Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
            keyGen.initialize(KEYPAIR_GEN_CONFIG_2048);
            keyPair = keyGen.genKeyPair();
            privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            privateKeyBytes = privateKey.getEncoded();
            publicKeyBytes = publicKey.getEncoded();

        } catch (Exception e) {
            System.out.println("serialize:Error:" + e);
            keyPair = null;
        }

        return keyPair;
    }

    public PrivateKey deSerialize(String _algorithm, byte[] privateKeyBytes, byte[] publicKeyBytes) {
        this.algorithm = _algorithm == null && this.algorithm != null ? this.algorithm : _algorithm;
        if(privateKeyBytes == null) return null;
        if(publicKeyBytes == null) return null;
        if(this.algorithm == null) return null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            this.privateKey = keyFactory.generatePrivate(privateKeySpec);
            if (publicKeyBytes != null) {
                EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                this.publicKey = keyFactory.generatePublic(publicKeySpec);
            }
            // // The orginal and new keys are the same
            // boolean same = privateKey.equals(privateKey2);
            // System.out.println("==================================");
            // same = publicKey.equals(publicKey2);
        } catch (Exception e) {
            System.out.println("serialize:Error:" + e);
            e.printStackTrace();
            System.out.println("==================================");
            this.privateKey = null;
        }
        return this.privateKey;
    }

    public PublicKey deSerializePubKe(byte[] publicKeyBytes) {
        this.publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
            // EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            // privateKey = keyFactory.generatePrivate(privateKeySpec);
            if (publicKeyBytes != null) {
                EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                publicKey = keyFactory.generatePublic(publicKeySpec);
            }
        } catch (Exception e) {
            System.out.println("serialize:Error:" + e);
            e.printStackTrace();
            System.out.println("==================================");
        }
        return this.publicKey;
    }

    public PrivateKey deSerializePriKe(byte[] privateKeyBytes) {
        this.privateKey = null;

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            this.privateKey = keyFactory.generatePrivate(privateKeySpec);
        } catch (Exception e) {
            System.out.println("serialize:Error:" + e);
            e.printStackTrace();
            System.out.println("==================================");
        }
        return this.privateKey;
    }

    // Note:Not Tested
    public KeyPair prepareKeyPair(String algorithm) {
        final String METHOD_NAME = "prepareKeyPair:";
        KeyPair pair = null;
        try {
            algorithm = algorithm == null ? "RSA" : algorithm;
            // Creating KeyPair generator object
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);

            // Initializing the key pair generator
            keyPairGen.initialize(KEYPAIR_GEN_CONFIG_2048);

            // Generate the pair of keys
            pair = keyPairGen.generateKeyPair();

            // Getting the public key from the key pair
            this.publicKey = pair.getPublic();
            this.privateKey = pair.getPrivate();
        } catch (Exception e) {
            System.out.println(METHOD_NAME + "ExceptionExceptionException");
            System.out.println(METHOD_NAME + "ExceptionExceptionException e:" + e.toString());
            System.out.println(METHOD_NAME + "ExceptionExceptionException");
        }
        return pair;
    }

    public void getUniformNumberString(ArrayList<Integer> inputArray) {
        ArrayList<String> array = new ArrayList<>();
        array.add("1");
        String numStr;

        for (int num : inputArray) {
            if (num < 10) {
                numStr = "00" + num;
            } else if (num < 100) {
                numStr = "0" + num;
            } else {
                numStr = "" + num;
            }
            System.out.println("Input:number: " + num + " # numStr: " + numStr + " # number type: ");
            array.add(numStr);
        }

        // this.contentEn = array.toString();
        array.forEach(str -> this.contentEn += str);
        System.out.println("this.contentEn: " + this.contentEn);
    }

    public ArrayList<Character> getNumberArrayFrom(String inputStr) {
        // console.log("inputStr:", inputStr);
        ArrayList<Character> outputArr = new ArrayList<Character>();
        String tmp = "";
        int count = 0;

        for (int i = 1; i < inputStr.length(); i++) {
            tmp += inputStr.charAt(i);
            count++;

            if (count == 3) {
                outputArr.add((char) Integer.parseInt(tmp));
                count = 0;
                tmp = "";
            }
        }
        return outputArr;
    }

    /**
     * custom algorithm functional code
     */
    public void codesEnV1() {
        System.out.println("Input:content: " + this.content);
        System.out.println("Input:passCode: " + this.passCode);
        String passcodeLocal = this.passCode == "" ? PASSCODESHA2048 : this.passCode;
        ArrayList<Integer> result = new ArrayList<Integer>();
        int passLen = passcodeLocal.length();
        char[] contentAsArray = this.content.toCharArray();
        for (int i = 0; i < this.content.length(); i++) {
            int passOffset = i % passLen;
            int value1 = contentAsArray[i];
            int value2 = passcodeLocal.charAt(passOffset);
            System.out.println("contentAsArray[i]: " + value1 + " # passcodeLocal.chatAt(passOffset): " + value2);
            int calAscii = value1 + value2;
            System.out.println("calAscii: " + calAscii);
            result.add(calAscii);
        }
        System.out.println("result Array: " + result);
        /*
         * Input:content: TestInput
         * content.charCodeAt(i): 84 # passcodeLocal.charCodeAt(passOffset): 36
         * calAscii: 120
         */
        getUniformNumberString(result);
        System.out.println("contentEn: " + this.getContentEn());
    }

    /*
     * // Original python code
     * exports.decryptCodes = (codesArr, passcode = '') => {
     * let result = []; let str = '';
     * // let codesArr = content.split('-');
     * let passcodeLocal = passcode === '' ? PASSCODESHA2048 : passcode;
     * 
     * let passLen = passcodeLocal.length;
     * for (let i = 0; i < codesArr.length; i++) {
     * let passOffset = i % passLen;
     * let calAscii = (codesArr[i] - passcodeLocal.charCodeAt(passOffset));
     * result.push(calAscii);
     * }
     * for (let i = 0; i < result.length; i++) {
     * let ch = String.fromCharCode(result[i]); str += ch;
     * }
     * return str;
     * }
     */
    /**
     * Functional decrypt code
     */
    public void codesDeV1() {

        // System.out.println("Input:contentEn : " + this.contentEn);
        // System.out.println("Input:passCode: " + this.passCode);
        ArrayList<Character> characterArrayList = getNumberArrayFrom(this.contentEn);
        // System.out.println("characterArrayList: " + characterArrayList);
        String passcodeLocal = this.passCode == "" ? PASSCODESHA2048 : this.passCode;
        StringBuffer stringBuffer = new StringBuffer();
        String str = "";

        int passLen = passcodeLocal.length();
        for (int i = 0; i < characterArrayList.size(); i++) {
            int passOffset = i % passLen;
            Character character = characterArrayList.get(i);
            int calAscii = ((int) character - passcodeLocal.charAt(passOffset));
            stringBuffer.append((char) calAscii);
            // System.out.println("(char) calAscii: " + (char) calAscii);
        }
        this.content = stringBuffer.toString();
    }
}
