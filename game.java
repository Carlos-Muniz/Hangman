import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;


class Game {
    public static int startInput() {
        System.out.println("Welcome to Hangman!");
        Scanner objectInput = new Scanner(System.in);
        System.out.print("Think of a word and enter the number of letters in that word: ");

        int wordSize = objectInput.nextInt();
        System.out.println("Word Size is: " + wordSize);
        objectInput.nextLine();

        objectInput.close();

        return wordSize;
    }

    public static List<String> loadData(final int wordSize) {
        String path = "words.csv";

        BufferedReader br = null;
        List<String> words = null;

        try {
            br = new BufferedReader(new FileReader(path));
            words = new ArrayList<String>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                int size = Integer.parseInt(rowData[2]);
                if (size == wordSize) {
                    words.add(rowData[1]);
                }
            }
            // System.out.println("Words List Size is: " + words.size());
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            File tmpDir = new File(path);
            boolean exists = tmpDir.exists();
            System.out.println("File Exists: " + exists);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }


    public static char guessLetter(List<String> words) {
        char c = ' ';
        Map<Character,Integer> charCount = new HashMap<Character,Integer>();
        for (int i = 0; i < words.size(); i++) {
            for (int j=0; j < words.get(i).length(); j++) {
                char ch = words.get(i).charAt(j);
                if (!charCount.containsKey(ch)) {
                    charCount.put(ch, 0);
                }
                charCount.put(ch, charCount.get(ch) + 1);
            }
        }
        int max = 0;
        for (char key : charCount.keySet()) {
            int val = charCount.get(key);
            if (val > max) {
                max = val;
                c = key;
            }
        }
        return c;
    }


    public static void guessWords(int wordSize, List<String> words) {
        Scanner ansInput = new Scanner(System.in);
        String mess = "The rules are simple. I'll guess a letter that is in the word. " +
        " If I'm right, you tell me 'yes', and where that letter is. " +
        "If I'm wrong you tell me 'no'. I have 5 guesses to get it right.";
        System.out.println(mess);
        char[] letters = new char[wordSize];
        Arrays.fill(letters, '?');
        String indices = " ";
        String space = "  ";
        for (int i = 0; i < wordSize; i++) {
            if (i+1 > 9) {
                space = "+";
            }
            indices = indices + space + String.valueOf(i+1) + "+";
        }

        int count = 5;
       
        while (count > 0) {
        // 1) Guess letter
            char guess = guessLetter(words);
            System.out.println("My guess: " + guess);
            // 2) Accept input for yes or no
            System.out.println("Did I guess right? (yes or no)");
            String answer = ansInput.nextLine();
            System.out.println(answer);
            
            // if (answer == "yes"){
            //     System.out.println(letters.toString().replace("", "  "));
            //     System.out.println(indices);
            //     System.out.println("Where does the letter go? (Use spaces to separate multiple answers.)");
            //     String places = ansInput.nextLine()
        }
        ansInput.close();

            // 3) If yes, accept input for locations, else continue
            // 4) Constrain dataset.
            // 5) Repeat
    }

    public static void whilePractice() {
        int count = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println("The Count is: " + count);
        while (count < 10) {
            String answer = scan.nextLine();
            System.out.println(answer + count);
            count++;
        }
        scan.close();
    }

    public static void main(final String[] args) {
        // final int wordSize = startInput();
        // final List<String> wordSelection = loadData(wordSize);
        // char guess = guessLetter(wordSelection);
        // System.out.println("Guess: " + guess);
        // guessWords(wordSize, wordSelection);
        
        System.out.println("Welcome to Hangman!");
        Scanner objectInput = new Scanner(System.in);
        System.out.print("Think of a word and enter the number of letters in that word: ");
        int wordSize = objectInput.nextInt();
        objectInput.nextLine();

        final List<String> wordSelection = loadData(wordSize);

        String mess = "The rules are simple. I'll guess a letter that is in the word. " +
        " If I'm right, you tell me 'yes', and where that letter is. " +
        "If I'm wrong you tell me 'no'. I have 5 guesses to get it right.";
        System.out.println(mess);
        char[] letters = new char[wordSize];
        Arrays.fill(letters, '_');
        String indices = " ";
        String space = "  ";
        for (int i = 0; i < wordSize; i++) {
            if (i+1 > 9) {
                space = " ";
            }
            indices = indices + space + String.valueOf(i+1) + " ";
        }

        int count = 5;
        while (count > 0) {
        // 1) Guess letter
            char guess = guessLetter(wordSelection);
            System.out.println("My guess: " + guess);
            
            // 2) Accept input for yes or no
            System.out.println("Did I guess right? (yes or no)");
            String answer = objectInput.nextLine();

            if (answer.equals("yes")){
                System.out.println(String.valueOf(letters).replace("", "   "));
                System.out.println(indices);
                System.out.println("Where does the letter go? (Use spaces to separate multiple answers.)");
                String[] places = objectInput.nextLine().split(" ");
                int[] placeArray = new int[places.length];
                for(int i = 0; i < placeArray.length; i ++) {
                    placeArray[i] = Integer.parseInt(places[i]);
                }

            }
        }
        objectInput.close();

    }
}