import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Wordle {

    private String answer;
    private String[] dictionary;
    private String[] answerList;
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * Wordle Constructor -- creates a new Wordle game
     * @param file1 - list of valid guesses, used to create dictionary
     * @param file2 - list of possible answers, used to create answerList
     * @throws IOException - if files are not found
     */
    public Wordle(File file1, File file2) throws IOException {
        dictionary = createPossibleWords(file1);
        answerList = createPossibleWords(file2);
        int maxIndex = answerList.length;
        int randomIndex = (int) (Math.random() * (maxIndex + 1));

        answer = answerList[randomIndex];
    }

    //binary search

    /**
     * findWord --
     * uses binary search method to see if word exists in dictionary
     * @param word - word to be found
     * @return int - position of word in dictionary, -1 if not found
     */
    public int findWord(String word) {
        int low = 0;
        int high = dictionary.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (dictionary[mid].equals(word)) {
                return mid;
            }

            if (dictionary[mid].compareTo(word) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    /**
     * createPossibleWords --
     * creates an array of words from a File that lists each word
     * @param file - File to be made into a list of words
     * @return String[] - array of words that were listed in File parameter
     * @throws IOException - if there is no line for the BufferedReader to read
     */
    private String[] createPossibleWords(File file) throws IOException {
        List<String> listOfWords = new ArrayList<String>();
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line = bf.readLine();

        while (line != null) {
            listOfWords.add(line);
            line = bf.readLine();
        }

        bf.close();

        String[] arrayOfWords = listOfWords.toArray(new String[0]);

        return arrayOfWords;
    }

    /**
     * checkCorrect
     * @param word - String to be checked, current guess (input by user)
     * @return bool - true if the word input is the same as the answer, otherwise false
     */
    public boolean checkCorrect(String word) {
        return word.equals(answer);
    }

    /**
     * giveFeedback --
     * prints the current guess in the console, displaying letters that are in the correct position as green, letters
     * that appear in the answer but in a different position as yellow, and any letters not in the answer as default
     *
     * achieved by creating arrays of the current guess (word parameter) and answer and comparing the
     * characters at each index - affects a newly created output array which is read to correctly display
     * the guess as described above
     * @param word - String to be checked, current guess (input by user)
     */
    public void giveFeedback(String word) {
        word = word.toLowerCase();
        char[] wordArr = initializeArray(word);
        char[] answerArr = initializeArray(answer);
        char[] output = initializeOutput();

        output = checkGreen(wordArr, answerArr, output);
        output = checkYellow(wordArr, answerArr, output);

        printFeedback(wordArr, output);
    }

    /**
     * initializeArray --
     * creates an array from the String parameter, which holds the characters of the word, each index corresponding
     * to the character's original position in the word
     * @param word - String to be turned into array
     * @return char[] - array of characters from word parameter in the order in which they appear
     */
    private char[] initializeArray(String word) {
        char[] arr = new char[word.length()];

        for (int i = 0; i < word.length(); i++) {
            arr[i] = word.charAt(i);
        }

        return arr;
    }

    /**
     * initializeOutput --
     * creates an array of ANSWER_LENGTH (as defined in WordleDriverClass), with each item being the empty
     * space character
     * @return char[] - "empty" array, holding ' ' at each position
     */
    private char[] initializeOutput() {
        char[] arr = new char[answer.length()];

        for (int i = 0; i < answer.length(); i++) {
            arr[i] = ' ';
        }

        return arr;
    }

    /**
     * checkGreen --
     * loops through arrays - if the character at index i in the word array matches the character at index i in the
     * answer array, this character is placed at index i in the output array in upper case
     *
     * @param word - array holding the characters of the current guess in order
     * @param answer - array holding the characters of the answer in order
     * @param output - "empty" array (holding ' ' characters)
     * @return char[] - array holding only "green" characters (those in the correct position in the current guess),
     * other positions hold empty space character (' ')
     */
    private char[] checkGreen(char[] word, char[] answer, char[] output) {
        for (int i = 0; i < answer.length; i++) {
            if (word[i] == answer[i]) {
                output[i] = Character.toUpperCase(word[i]);
            }
        }

        return output;
    }

    /**
     * checkYellow --
     * loops through arrays - if the character at index i in the word array matches any character in the answer array
     * at some index j (provided that the output array at either position has not yet been designated)
     * this character is placed at index i in the output array in lowercase
     *
     * @param word - array holding the characters of the current guess in order
     * @param answer - array holding the characters of the answer in order
     * @param output - array holding ' ' characters and "green" letters
     * @return char[] - array holding "green" and "yellow" (characters who appear in the answer but in a different
     * position than the current guess) characters, other positions hold empty space character (' ')
     */
    private char[] checkYellow(char[] word, char[] answer, char[] output) {
        for (int i = 0; i < answer.length; i++) {

            // checks that this character is not green at the current spot
            if (output[i] == ' ') {
                for (int j = 0; j < answer.length; j++) {

                    // checks that characters are the same and that this character is not green at the other spot
                    if (word[i] == answer[j] && output[j] == ' ') {
                        output[i] = word[i];
                    }
                }
            }
        }

        return output;
    }

    /**
     * printFeedback --
     * loops through output array - if char at index i is uppercase, prints in green
     * if char at index i is lowercase, prints in yellow
     * if char at index i is empty (' '), prints the corresponding char from the original word array in default color
     *
     * @param word - array holding characters of the current guess
     * @param output - array whose characters have been modified to reflect if they appear in the answer
     */
    private void printFeedback(char[] word, char[] output) {
        for (int i = 0; i < word.length; i++) {
            char c = output[i];

            if (c == ' ') {
                System.out.print(word[i]);
            } else if (Character.isUpperCase(c)) {
                System.out.print(ANSI_GREEN + Character.toLowerCase(c) + ANSI_RESET);
            } else {
                System.out.print(ANSI_YELLOW + c + ANSI_RESET);
            }
        }

        System.out.println();
    }

}
