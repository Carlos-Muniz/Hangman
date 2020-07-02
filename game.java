import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;


class Game {
    public static int startInput() {
        System.out.println("Welcome to Hangman!");
        Scanner  objectInput = new Scanner(System.in);
        System.out.println("Think of a word and enter the number of letters in that word");

        String wordSizeString = objectInput.nextLine();
        int wordSize = Integer.parseInt(wordSizeString);
        System.out.println("Word Size is: " + wordSize);

        objectInput.close();

        return wordSize;
    }

    public static void loadData(int wordSize) {
        String path = "words.csv";

        File tmpDir = new File(path);
        boolean exists = tmpDir.exists();
        System.out.println("File Exists: " + exists);

        // BufferedReader csvReader = new BufferedReader(new FileReader(path));
        // List<String> words = new ArrayList<String>();
        // String line;
        // while ((line = csvReader.readLine()) != null) {
        //     String[] row_data = line.split(",");
        //     int size = Integer.parseInt(row_data[2]);
        //     if (size == wordSize) {
        //         words.add(row_data[1]);
        //     }
        // }
        // System.out.println("Words List Size is: " + words.size());
        // csvReader.close();
    }

    public static void main(String [] args) {
        int wordSize = startInput();
        loadData(wordSize);
    }
}