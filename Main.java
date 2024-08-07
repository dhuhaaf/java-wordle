import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    /**
     * main
     * pre: user is prepared to enter data at the terminal
     * post: creates Wordle game and calls runProgram from driver class
     * @throws IOException - if files are not found
     */
    public static void main(String[] args) throws IOException {
        System.out.println("\nWELCOME TO JORDLE (a jail version of Wordle): \nThe same rules as Wordle but " +
                "without the UI design to make it so easy! No keyboard to help you.\n");

        File validWords = new File("valid-wordle-words.txt");
        File validAnswers = new File("wordlist.txt");
        Wordle newGame = new Wordle(validWords, validAnswers);

        Scanner input = new Scanner(System.in);

        WordleDriver.runProgram(newGame, input);
    }

}
