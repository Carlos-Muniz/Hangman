import java.util.Scanner;

class Game {
    public static int startInput() {
        System.out.println("Welcome to Hangman!");
        Scanner objectInput = new Scanner(System.in);
        System.out.println("Think of a word and enter the number of letters in that word");

        String wordSizeString = objectInput.nextLine();
        int wordSize = Integer.parseInt(wordSizeString);
        System.out.println("Word Size is: " + wordSize);

        objectInput.close();

        return wordSize;
    }

    public static void constrainDatabase(int wordSize) {
        
    }

    public static void main(String [] args) {
        int wordSize = startInput();
    }
}