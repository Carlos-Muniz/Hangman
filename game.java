import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


class Game {
    public static int startInput(Scanner objectInput) {

        System.out.println("Welcome to Hangman!");
        System.out.print("Think of a word and enter the number of letters in that word: ");
        final int wordSize = objectInput.nextInt();
        objectInput.nextLine();

        return wordSize;
    }


    public static List<String> loadData(final int wordSize) {
        final String path = "words.csv";

        BufferedReader br = null;
        List<String> words = null;

        try {
            br = new BufferedReader(new FileReader(path));
            words = new ArrayList<String>();
            String line;
            while ((line = br.readLine()) != null) {
                final String[] rowData = line.split(",");
                final int size = Integer.parseInt(rowData[2]);
                if (size == wordSize) {
                    words.add(rowData[1]);
                }
            }
            br.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
            final File tmpDir = new File(path);
            final boolean exists = tmpDir.exists();
            System.out.println("File Exists: " + exists);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return words;
    }


    public static char guessLetter(final List<String> words, final Set<Character> letters) {
        char c = ' ';
        final Map<Character, Integer> charCount = new HashMap<Character, Integer>();
        for (int i = 0; i < words.size(); i++) {
            for (int j = 0; j < words.get(i).length(); j++) {
                final char ch = words.get(i).charAt(j);
                if (!charCount.containsKey(ch)) {
                    charCount.put(ch, 0);
                }
                charCount.put(ch, charCount.get(ch) + 1);
            }
        }
        int max = 0;
        for (final char key : charCount.keySet()) {
            final int val = charCount.get(key);
            if ((val > max) & (!letters.contains(key))) {
                max = val;
                c = key;
            }
        }
        return c;
    }


    public static void constrainDataset(final List<String> words, final String[] places, final char letter,
            final int wordSize) {

        final String[] regexPattern = new String[wordSize];
        final String neg = "[^" + String.valueOf(letter) + "]";
        Arrays.fill(regexPattern, neg);

        for (int i = 0; i < places.length; i++) {
            final int j = Integer.parseInt(places[i]) - 1;
            if (j >= 0) {
                regexPattern[j] = String.valueOf(letter);
            }
        }

        final Pattern pattern = Pattern.compile(String.join("", regexPattern));
        Matcher matcher;
        for (final Iterator<String> iter = words.listIterator(); iter.hasNext();) {
            matcher = pattern.matcher(iter.next());
            if (!matcher.find()) {
                iter.remove();
            }
        }
    }


    public static void guessWords(int wordSize, List<String> wordSelection, Scanner objectInput) {
        final String mess = "The rules are simple. I'll guess a letter that is in the word. "
            + " If I'm right, you tell me 'yes', and where that letter is. "
            + "If I'm wrong you tell me 'no'. I have 5 guesses to get it right.";
        
        System.out.println(mess);
        final char[] letters = new char[wordSize];
        Arrays.fill(letters, '_');
        String indices = " ";
        String space = "  ";
        for (int i = 0; i < wordSize; i++) {
            if (i + 1 > 9) {
                space = " ";
            }
            indices = indices + space + String.valueOf(i + 1) + " ";
        }

        final Set<Character> lettersPicked = new HashSet<Character>();
        for (final char c : letters) {
            lettersPicked.add(c);
        }

        int count = 5;
        boolean wordIncomplete = true;
        while ((count > 0) & (wordIncomplete)) {
            final char guess = guessLetter(wordSelection, lettersPicked);
            System.out.println("My guess: " + guess);

            System.out.println("Did I guess right? (yes or no)");
            final String answer = objectInput.nextLine();
            String[] places = { "0" };
            if (answer.equals("yes")) {
                System.out.println(String.valueOf(letters).replace("", "   "));
                System.out.println(indices);
                System.out.println("Where does the letter go? (Use commas (,) to separate multiple answers.)");
                places = objectInput.nextLine().split(",");
                for (int i = 0; i < places.length; i++) {
                    final int j = Integer.parseInt(places[i]) - 1;
                    letters[j] = guess;
                }
                System.out.println(String.valueOf(letters).replace("", "   "));
                count++;
            }
            constrainDataset(wordSelection, places, guess, wordSize);
            lettersPicked.add(guess);
            count--;
            wordIncomplete = false;
            for (int i=0; i < letters.length; i ++) {
                if (letters[i] == '_') {
                    wordIncomplete = true;
                }
            }
        }

        if (count == 0) {
            System.out.println("I lost...");
        }
        if (!wordIncomplete) {
            System.out.println("I won! The word is: " + String.valueOf(letters));
        }
        System.out.println("Good Game!");
    }


    public static void main(final String[] args) {

        Scanner objectInput = new Scanner(System.in);
        int wordSize = startInput(objectInput);

        final List<String> wordSelection = loadData(wordSize);

        guessWords(wordSize, wordSelection, objectInput);

        objectInput.close();

    }
}