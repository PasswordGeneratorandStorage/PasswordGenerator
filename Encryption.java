import java.security.MessageDigest;

/**
 * This class is provided for encryption on the project
 *
 * @author Ian Hoegen
 */

public class Encryption
{
    /**
     * This method returns a string containing the encrypted equivalent of the input
     * For every other letter in the string, it will add a number that is generated from the
     * key method. It then returns this value.
     *
     * @param original The string that is to be encrypted
     * @return the input in encrypted form
     */
    public static String encode(String original, String pass)
    {
        int key = getKey(pass);
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
     * This method returns the English equivalent of the encrypted String
     * For every other letter in the encrypted string, it will add or subtract a to the character.
     *
     * @param encrypted The encrypted string
     * @return The input in english
     */
    public static String decode(String encrypted, String pass)
    {
        int key = getKey(pass);
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

    /**
     * This method generates a hash by means of SHA-256, to be used as a way to store the master username and
     * password
     *
     * @param base The string to be hashed
     * @return The hashed String
     */
    public static String getHash(String base)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

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