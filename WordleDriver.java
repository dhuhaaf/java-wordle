import java.util.Scanner;

public class WordleDriver {
    public static final int ANSWER_LENGTH = 5;
    public static final int MAX_GUESSES = 6;

    /**
     * runProgram
     * @param game - current Wordle game being played
     * @param input - the user's current guess
     * pre: user has entered a String
     * post: runs through game until 6 guesses have been made or word is correctly guessed
     */
    public static void runProgram(Wordle game, Scanner input) {
        int guess = 1;
        while(guess <= MAX_GUESSES) {
            System.out.print("Enter a " + ANSWER_LENGTH + " letter word: ");
            String currentGuess = input.next();

            if (validInput(game, currentGuess)) {
                game.giveFeedback(currentGuess);
                int guessesLeft = MAX_GUESSES - guess;
                System.out.println("\nYou have " + guessesLeft + " guesses left.\n");
                guess++;
            }

            if(game.checkCorrect(currentGuess)) {
                System.out.println("Congratulations! Word found.");
                break;
            }
        }

        if (guess > MAX_GUESSES) {
            System.out.println("No guesses left. You lose.");
        }
    }

    /**
     * checkInputLength
     * @param word - String to be checked, current guess (input by user)
     * @return bool - true if word parameter's length is equal to ANSWER_LENGTH, otherwise false
     */
    private static boolean checkInputLength(String word) {
        if(word.length() == ANSWER_LENGTH) {
            return true;
        }

        return false;
    }

    /**
     * checkInputExists
     * @param game - current Wordle game
     * @param word - String to be checked, current guess (input by user)
     * @return bool - true if word parameter exists in the valid words dictionary, otherwise false
     */
    private static boolean checkInputExists(Wordle game, String word) {
        if (game.findWord(word) == -1) {
            return false;
        }

        return true;
    }

    /**
     * validInput
     * @param game - current Wordle game
     * @param word - String to be checked, current guess (input by user)
     * @return bool - true if word parameter is the correct length and exists in the dictionary, otherwise false
     */
    private static boolean validInput(Wordle game, String word) {
        if (!checkInputExists(game, word)) {
            System.out.println("This is not a valid word in our dictionary.\n");
            return false;
        }

        if (!checkInputLength(word)) {
            System.out.println("Word is incorrect length.\n");
            return false;
        }

        return true;
    }

}
