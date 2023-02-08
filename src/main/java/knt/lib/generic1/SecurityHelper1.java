package knt.lib.generic1;
  
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

public class SecurityHelper1 {
    private static String PASSCODESHA2048 = "$W%Ld0os$3";
    private String passCode = null;
    private String content = null;
    private String contentEn = "";
    private PublicKey publicKey = null;
    private PrivateKey privateKey = null;

    public String getPassCode() { return passCode; }

    public void setPassCode(String passCode) { this.passCode = passCode; }

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

    public SecurityHelper1(String content_, String passCode_) {
        passCode = passCode_ == null || passCode_.length() == 0 ? PASSCODESHA2048 : passCode_;
        content = content_ == null || content_.length() == 0 ? "" : content_;
    }

    /**
     * This is a WIP Code
     * @param s
     * @return
     */
    public PublicKey deserializeV1(String s) {
//        String []Parts = s.split("\\|");
//        PublicKey outputPublicKey = null;
//        PrivateKey outputPrivateKey = null;
//        // pair.getPrivate()
//
//        try {
//            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(
//                    new BigInteger(Parts[0]),
//                    new BigInteger(Parts[1]));
//            outputPublicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
//        } catch (Exception e) {
//
//        }
//        return outputPublicKey;
        return null;
    }

    public KeyPair generateStoreKeys(String algorithm) {
        //Getting the public key from the key pair
//        PublicKey publicKey = pair.getPublic();
//        PrivateKey privateKey = pair.getPrivate();
//        String publicKeyString = javax.xml.bind.DatatypeConverter.printBase64Binary(publicKey.getEncoded());
//        String privateKeyString = javax.xml.bind.DatatypeConverter.printBase64Binary(privateKey.getEncoded());
        KeyPair keyPair = null;
        algorithm = algorithm == null || algorithm.isEmpty() ? "RSA" : algorithm; // DSA, RSA, or DH
        byte[] privateKeyBytes = null;
        byte[] publicKeyBytes = null;
        PrivateKey privateKey;
        try {
            // Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
            keyGen.initialize(2048);
            keyPair = keyGen.genKeyPair();
            privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            privateKeyBytes = privateKey.getEncoded();
            publicKeyBytes = publicKey.getEncoded();

        } catch(Exception e) {
            System.out.println("serialize:Error:" + e);
            keyPair = null;
        }

        return keyPair;
    }

    public PrivateKey deSerialize(String algorithm, byte[] privateKeyBytes, byte[] publicKeyBytes) {
        PrivateKey privateKey = null;
//        PublicKey publicKey = pair.getPublic();
//        PrivateKey privateKey = pair.getPrivate();
//        String publicKeyString = javax.xml.bind.DatatypeConverter.printBase64Binary(publicKey.getEncoded());
//        String privateKeyString = javax.xml.bind.DatatypeConverter.printBase64Binary(privateKey.getEncoded());

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            privateKey = keyFactory.generatePrivate(privateKeySpec);
            if(publicKeyBytes != null) {
                EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                PublicKey publicKey2 = keyFactory.generatePublic(publicKeySpec);
            }
//            // The orginal and new keys are the same
//            boolean same = privateKey.equals(privateKey2);
//            System.out.println("==================================");
//            same = publicKey.equals(publicKey2);
        } catch (Exception e) {
            System.out.println("serialize:Error:" + e);
            e.printStackTrace();
            System.out.println("==================================");
            privateKey = null;
        }
        return privateKey;
    }

    // Note:Not Tested
    public KeyPair prepareKeyPair() {
        final String METHOD_NAME = "prepareKeyPair:";
        KeyPair pair = null;
        try {
            //Creating KeyPair generator object
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            //Initializing the key pair generator
            keyPairGen.initialize(2048);

            //Generate the pair of keys
            pair = keyPairGen.generateKeyPair();

            //Getting the public key from the key pair
            PublicKey publicKey = pair.getPublic();

            PrivateKey privateKey = pair.getPrivate();
        } catch (Exception e) {
            System.out.println(METHOD_NAME + "ExceptionExceptionException");
            System.out.println(METHOD_NAME + "ExceptionExceptionException e:" + e.toString());
            System.out.println(METHOD_NAME + "ExceptionExceptionException");
        }
        return pair;
    }
    public void getUniformNumberString (ArrayList<Integer> inputArray) {
        ArrayList<String> array = new ArrayList<> ();
        array.add("1");
        String numStr;

        for (int num:inputArray) {
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

//        this.contentEn = array.toString();
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
     *
     */
    public void codesEn() {
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
            System.out.println("contentAsArray[i]: " + value1 + " # passcodeLocal.chatAt(passOffset): " +  value2);
            int calAscii = value1 + value2;
            System.out.println("calAscii: " + calAscii);
            result.add(calAscii);
        }
        System.out.println("result Array: " + result);
        /*
        Input:content:  TestInput
        content.charCodeAt(i):  84  # passcodeLocal.charCodeAt(passOffset):  36
        calAscii:  120
         */
        getUniformNumberString(result);
        System.out.println("contentEn: " + this.getContentEn());
    }
/*
// Original python code
    exports.decryptCodes = (codesArr, passcode = '') => {
        let result = []; let str = '';
        // let codesArr = content.split('-');
        let passcodeLocal = passcode === '' ? PASSCODESHA2048 : passcode;

        let passLen = passcodeLocal.length;
        for (let i = 0; i < codesArr.length; i++) {
            let passOffset = i % passLen;
            let calAscii = (codesArr[i] - passcodeLocal.charCodeAt(passOffset));
            result.push(calAscii);
        }
        for (let i = 0; i < result.length; i++) {
            let ch = String.fromCharCode(result[i]); str += ch;
        }
        return str;
    }*/
    public void codesDe() {

//        System.out.println("Input:contentEn : " + this.contentEn);
//        System.out.println("Input:passCode: " + this.passCode);
        ArrayList<Character> characterArrayList = getNumberArrayFrom(this.contentEn);
//        System.out.println("characterArrayList: " + characterArrayList);
        String passcodeLocal = this.passCode == "" ? PASSCODESHA2048 : this.passCode;
        StringBuffer stringBuffer = new StringBuffer();
        String str = "";

        int passLen = passcodeLocal.length();
        for (int i = 0; i < characterArrayList.size(); i++) {
            int  passOffset = i % passLen;
            Character character = characterArrayList.get(i);
            int calAscii = ((int) character - passcodeLocal.charAt(passOffset));
            stringBuffer.append((char) calAscii);
//            System.out.println("(char) calAscii: " + (char) calAscii);
        }
        this.content = stringBuffer.toString();
    }
}
