import java.io.*;
import java.security.MessageDigest;

/**
 * This class is provided for encryption on the project
 */

public class Encryption
{
    /**
     * This method returns a string containing the ROT47 equivalent of the input
     *
     * @param original The string that is to be encrypted
     * @return the input in ROT47
     */
    public static String encode(String original, int key)
    {
        String outputString = "";
        for (int i = 0; i < original.length(); i++)
        {
            char current = original.charAt(i);
            if (i % 2 == 0)
                current += key;
            else
                current -= key;
            outputString += current;
        }
        return outputString;

    }

    /**
     * This method returns the English equivalent of a ROT47 String
     *
     * @param encrypted ROT47 text
     * @return The input in english
     */
    public static String decode(String encrypted, int key)
    {
        String outputString = "";
        for (int i = 0; i < encrypted.length(); i++)
        {
            char current = encrypted.charAt(i);
            if (i % 2 == 0)
                current -= key;
            else
                current += key;
            outputString += current;
        }
        return outputString;
    }

    /**
     * This method uses an algorithm to generate a key from the users password
     *
     * @param password The users password
     * @return An integer with the key
     */
    public static int getKey(String password)
    {
        int counter = 1;
        int total = 0;
        for (char a : password.toCharArray())
        {
            total += a;
            counter += 1;
        }
        return (total / counter) / 2;
    }

    public static void savePassword(String username, String password)
    {
        try
        {
            FileWriter writer = new FileWriter("src\\AppData\\users\\users.db", true);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(getHash(username) + "," + getHash(password));
            printWriter.close();
        } catch (Exception e)
        {
            System.out.println("Database not found");
        }
    }

    public static String getHash(String base)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++)
            {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
}