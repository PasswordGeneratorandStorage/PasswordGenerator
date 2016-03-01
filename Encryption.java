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
    public String encode(String original, int key)
    {
        String outputString = "";
        for (int i = 0; i < original.length(); i++)
        {
            char current = original.charAt(i);
            current += key;
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
    public String decode(String encrypted, int key)
    {
        String outputString = "";
        for (int i = 0; i < encrypted.length(); i++)
        {
            char current = encrypted.charAt(i);
            current -= key;
            outputString += current;
        }
        return outputString;
    }

}