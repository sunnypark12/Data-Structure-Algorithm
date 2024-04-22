package HW07;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author Sunho Park
 * @version 1.0
 * @userid spark901
 * @GTID 903795870
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     *
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch and shifting down by 1.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> bruteForce(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null or of length 0.");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comparator cannot be null.");
        }

        List<Integer> matches = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();

        for (int t = 0; t <= n - m; t++) {
            int i = 0;
            while (i <= m - 1) {
                if (comparator.compare(text.charAt(t + i), pattern.charAt(i)) == 0) {
                    i++;
                } else {
                    break;
                }
            }
            if (i == m) {
                matches.add(t);
            }
        }
        return matches;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("Null pattern or null comparator "
                    + "cannot be used to build a failure table.");
        }
        if (pattern.length() == 0) {
            return new int[0];
        }
        int i = 0;
        int j = i + 1;
        int[] arr = new int[pattern.length()];
        arr[0] = 0;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                arr[j] = i + 1;
                i++;
                j++;
            } else {
                if (i == 0) {
                    arr[j] = 0;
                    j++;
                } else {
                    i = arr[i - 1];
                }
            }
        }
        return arr;
    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatch will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Null or empty pattern cannot be used.");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comparator is null!");
        }
        List<Integer> result = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return result;
        }
        int index = 0;
        int patternIndex = 0;
        int[] arr = buildFailureTable(pattern, comparator);
        while (index + pattern.length() <= text.length()) {
            while (patternIndex < pattern.length() && comparator.compare(pattern.charAt(patternIndex),
                    text.charAt(index + patternIndex)) == 0) {
                patternIndex++;
            }
            if (patternIndex == 0) {
                index++;
            } else {
                if (pattern.length() == patternIndex) {
                    result.add(index);
                }
                index += patternIndex - arr[patternIndex - 1];
                patternIndex = arr[patternIndex - 1];
            }
        }
        return result;

    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Null pattern cannot be used to build last table.");
        }
        Map<Character, Integer> lastTable = new HashMap<>();
        for (int i = 0;  i < pattern.length(); i++) {
            lastTable.put(pattern.charAt(i), i);
        }
        return lastTable;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *

     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Null pattern or a pattern of "
                    + "length 0 cannot be built in table");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or Comparator cannot be null.");
        }
        if (pattern.length() > text.length()) {
            return result;
        }
        List<Integer> result = new ArrayList<>();
        Map<Character, Integer> t = buildLastTable(pattern);
        int i = 0;
        int j;
        while (i <= text.length() - pattern.length()) {
            j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(pattern.charAt(j),
                    text.charAt(i + j)) == 0) {
                j--;
            }
            if (j == -1) {
                result.add(i);
                i++;
            } else {
                char text1 = text.charAt(i + j);
                int shifted = t.getOrDefault(text1, -1);
                if (shifted < j) {
                    i = i + j - shifted;
                } else {
                    i++;
                }
            }
        }
        return result;
    }

    /**
     * This method is OPTIONAL and for extra credit only.
     *
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     *
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                                    CharSequence text,
                                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Null pattern or a pattern of "
                    + "length 0 cannot be built in table");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or Comparator cannot be null.");
        }

        List<Integer> result = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return result;
        }
        Map<Character, Integer> t = buildLastTable(pattern);
        int[] arr = buildFailureTable(pattern, comparator);
        int k = pattern.length() - arr[pattern.length() - 1];
        int i = 0;
        int m = 0;
        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;
            while (j >= m && comparator.compare(text.charAt(i + j),
                    pattern.charAt(j)) == 0) {
                j--;
            }
            if (j < m) {
                result.add(i);
                m = pattern.length() - k;
                i += k;
            } else {
                m = 0;
                int shifted = t.getOrDefault(text.charAt(i + j), -1);
                if (j > shifted) {
                    i = i + j - shifted;
                } else {
                    i++;
                }
            }
        }
        return result;
    }
}
