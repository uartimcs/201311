import java.io.*;
import java.nio.charset.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class AESEncrypt {
    /* debug use
    private static String ToHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }    
    */
    public static void main(String[] args) {
        // default output file name
        String outputFile = "EncryptedData.dat";

        if(args.length < 2 || args.length > 3) {
            System.out.println("Usage: java AESEncrypt key data [outputFile]");
            return;
        }
        else {
            //Assign the data set of input
            String key = args[0];
            String data = args[1];
            if(args.length == 3) {
                outputFile = args[2];
            }
            
            try (OutputStream os = new FileOutputStream(outputFile);
                    InputStream is = new ByteArrayInputStream(data.getBytes()))
            {
                MessageDigest md = MessageDigest.getInstance("MD5");
                //the digest should be 128-bit, equivalent to size of 16 byte size, i.e. digest.length == 16
                byte[] digest = md.digest(key.getBytes(StandardCharsets.UTF_8));
                //System.out.println(ToHex(digest)); debug use
                
                //Set the md5 digest as key, do the AES algorithm for encryption of data
                SecretKeySpec keySpec = new SecretKeySpec(digest, "AES");
                
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                
                //Set the size of block to be encrypted
                int blockSize = cipher.getBlockSize(); //16
                byte[] inBytes = new byte[blockSize];
                
                int outputSize = cipher.getOutputSize(blockSize); //32 
                byte[] outBytes = new byte[outputSize];
                
                //Update the cipher and write the final encrypted msg to the specific file.
                int intLength = 0;
                boolean done = false;
                while (!done) {
                    intLength = is.read(inBytes);
                    if(intLength == blockSize) {
                        int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                        os.write(outBytes, 0, outLength);
                    }
                    else {
                        done = true;
                    }
                }
                
                if (intLength > 0) {
                    outBytes = cipher.doFinal(inBytes, 0, intLength);
                }
                else {
                    outBytes = cipher.doFinal();
                }
                os.write(outBytes);
                                                
                System.out.println("Encrypted data has been saved to " + outputFile);
               
            } 
            catch (NoSuchAlgorithmException nsae) {
                nsae.printStackTrace();
            } 
            catch (GeneralSecurityException gse) {
                gse.printStackTrace();
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
            }
           
            
        }
    }
}
