import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Would you like (1) Act 1 Scene 1, (2) Act 1, or (3) Twelfth Night FULL?");
        Scanner scan = new Scanner(System.in);
        int userInput = scan.nextInt();
        int length = 0;
        String path = "";

        if (userInput == 1) {
            length = 997;
            path = "src/TwelfthNightActOneScene1.txt";
        } else if (userInput == 2) {
            length = 1499;
            path = "src/TwelfthNightActOne.txt";
        } else if (userInput == 3) {
            length = 4000;
            path = "src/TwelfthNightFULL.txt";
        } else {
            System.out.println("You put an invalid number and therefore hath been kick'st out of the theatre. Bye");
            System.exit(0);
        }

        System.out.println("Would you like to input the table size? y\\n");
        String userLengthChoice = scan.next().toLowerCase();

        if(userLengthChoice.equals("y")) {
            System.out.print("What size? ");
            length = scan.nextInt();
        }

        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            String[] wordArr = new String[(int) myObj.length()];

            int i = 0;
            while (myReader.hasNext()) {
                String fileWord = myReader.next();
                wordArr[i] = fileWord;
                //System.out.println( fileWord );
                i++;
            }
            myReader.close();

            Hash word = new Hash(wordArr, length);
            word.hash();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("----------------------------------------------------");
        System.out.println("NODES AVALIABLE: {A, J, M, R, K, S, I, N, T, D}");
        System.out.println("Easter egg: See what you get when you print out the path from D to A ;)");
        System.out.println("Do you want to input\n\t(1) a source and destination node\n\t(2) a source node");
        int userDijInput = scan.nextInt();
        Dijkstra findShortestPath = null;

        System.out.println("What is the source node?");
        String source = scan.next().toUpperCase();

        if(userDijInput == 1) {
            System.out.println("What is the destination node?");
            String destination = scan.next().toUpperCase();

            findShortestPath = new Dijkstra(source, destination);
            findShortestPath.printShortestPath();
        } else if(userDijInput == 2){
            String[] NAMES = {"A", "J", "M", "R", "K", "S", "I", "N", "T", "D"};

            for(int i = 0; i < NAMES.length; i++){
                String dest = NAMES[i];
                findShortestPath = new Dijkstra(source, dest);
                findShortestPath.printShortestPath();
                System.out.println("----------------------");
            }
        } else {
            System.out.println("Wow........ I can't believe you did that.......");
            System.exit(0);
        }
    }
}