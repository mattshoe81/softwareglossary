package controller;

import java.util.Comparator;

/**
 * Class whose only purpose is to perform the coomparison operation for the
 * Glossary class. Compares 2 strings to report which occurs first
 * alphabetically
 *
 * @author Matthew Shoemaker
 *
 */
public class AlphabeticComparator implements Comparator<String> {

    /**
     * Compares {@code str1} and {@code str2} to report whether {@code str1} is
     * greater than, less than, or equal to {@code str2} with regards to
     * alphabetization.
     *
     * @param str1
     *            string being checked
     * @param str2
     *            string being checked against *
     *
     * @return -1, 0, or 1 indicatings whether {@code str1} is less than, equal
     *         to, or greater than, respectively, to {@code str2}
     *
     * @ensures [value of -1, 0, or 1 is returned, indicating whether
     *          {@code str1} is less than, equal to, or greater than,
     *          respectively, to {@code str2}] and [empty string is considered
     *          to occur first alphabetically when compared to any string that
     *          is not empty]
     */
    @Override
    public int compare(String str1, String str2) {
        // Initialize to 0 to save a conditional statement
        int result = 0;
        // Only perform sorting if both strings are not empty
        if (str1.length() > 0 && str2.length() > 0) {
            // Convert Strings to lowercase to avoid unicode comparison issues
            String str1Temp = str1.toLowerCase();
            String str2Temp = str2.toLowerCase();
            if (str1Temp.charAt(0) < str2Temp.charAt(0)) {
                result = -1;
            } else if (str1Temp.charAt(0) > str2Temp.charAt(0)) {
                result = 1;
            } else if (str1Temp.charAt(0) == str2Temp.charAt(0)) {
                result = this.compare(str1Temp.substring(1),
                        str2Temp.substring(1));
            }
        }
        // If str1 is empty and str2 is not, then str1 is less than str2
        else if (str1.length() == 0 && str2.length() > 0) {
            result = -1;
        }
        // If str2 is empty and str1 is not, then str1 is greater than str2
        else if (str1.length() > 0 && str2.length() == 0) {
            result = 1;
        }

        return result;
    }

}
