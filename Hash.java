import java.util.Hashtable;
public class Hash {
    private String[] wordArr;
    private String[] hashedWords;
    private int[] hashArr;
    private int length = 0;

    // constructs the word array
    public Hash(String[] arr, int l){
        length = l;
        wordArr = new String[arr.length];
        hashedWords = new String[length];
        hashArr = new int[length];

        for(int i = 0; i < arr.length; i++) {
            wordArr[i] = arr[i];
            //System.out.println( "at " + i + " word is " + wordArr[i] );
        }
        for(int i = 0; i < hashedWords.length; i++){
            hashedWords[i] = "-1";
        }
    }

    // launcher
    public void hash(){
        for(int i = 0; i < wordArr.length; i++){
            if(wordArr[i] != null){
                wordArr[i] = wordArr[i].replaceAll("(?!['-])\\p{Punct}", "");
                //System.out.println( wordArr[i] );
                hashThis(wordArr[i]);
            }

        }

        printHash();
        System.out.println("***************************");
        calculateLoadFactor();
        System.out.println("***************************");
        findLongestEmptyArea();
        System.out.println("***************************");
        findLongestCluster();
        System.out.println("***************************");
        findMaxDistinctHash();
        System.out.println("***************************");
        findFarthestWord();
    }

    // hashes the word and stores the hashed word into the hashedWords array & hashArray = code
    public void hashThis(String word) {
        //if (word != null) {
            int h = code(word);

            int startIndex = h; // Store the initial index
            boolean foundEmptySpot = false;

            while(!foundEmptySpot) {
                if(hashedWords[startIndex].equals("-1")){ // if there's an empty spot
                    hashArr[startIndex] = h;
                    hashedWords[startIndex] = word;
                    foundEmptySpot = true;
                } else if (hashedWords[startIndex].equals(word)){ // if the word is already there, avoid duplicates
                    foundEmptySpot = true;
                } else { // else need to wrap around and look
                    startIndex++;
                    if(startIndex == length){
                        startIndex = 0;
                    }
//                    if(h == startIndex){ // if you looped through the entire thing and got back to where you were
//                        foundEmptySpot = true;
//                    }
                }
            }
        //}
    }

    private int code(String word){
        int h = 0;
        for(int i = 0; i < word.length(); i++){
            h = (h * 123 + (int)(word.charAt(i))) % length;
        }
        return h;
    }

    private void printHash(){
        for(int i = 0; i < hashedWords.length; i++){
            if(hashedWords[i] == "-1"){
                System.out.println(i + "\t" + hashedWords[i]);
            } else {
                System.out.println(i + "\t" + hashedWords[i] + "\t" + hashArr[i]);
            }
        }
    }

    // a. Count non-empty addresses and calculate load factor
    public void calculateLoadFactor(){
        int nonEmptyCount = 0;
        for(String word : hashedWords){
            if(!word.equals("-1")){
                nonEmptyCount++;
            }
        }
        double loadFactor = (double) nonEmptyCount / length;
        System.out.println("Number of non-empty addresses: " + nonEmptyCount);
        System.out.println("Load Factor (α): " + loadFactor);
    }

    // b. Find the longest empty area in the table
    public void findLongestEmptyArea(){
        int maxEmptyLength = 0;
        int emptyLength = 0;
        int maxEmptyStartIndex = 0;

        for(int i = 0; i < length; i++){
            if(hashedWords[i].equals("-1")){
                emptyLength++;
            } else {
                if(emptyLength > maxEmptyLength){
                    maxEmptyLength = emptyLength;
                    maxEmptyStartIndex = i - emptyLength;
                }
                emptyLength = 0;
            }
        }

        System.out.println("Longest empty area starts at index " + maxEmptyStartIndex + " with a length of " + maxEmptyLength);
    }

    // c. find the longest cluster
    public void findLongestCluster(){
        int maxClusterLength = 0;
        int clusterLength = 0;
        int maxClusterStartIndex = 0;

        for(int i = 0; i < length; i++){
            if(!hashedWords[i].equals("-1")){
                clusterLength++;
            } else {
                if(clusterLength > maxClusterLength){
                    maxClusterLength = clusterLength;
                    maxClusterStartIndex = i - clusterLength;
                }
                clusterLength = 0;
            }
        }

        System.out.println("Longest cluster starts at index " + maxClusterStartIndex + " with a length of " + maxClusterLength);
    }

    // d. Find the hash value with the greatest number of distinct words
    public void findMaxDistinctHash(){
        int[] distinctHashCounts = new int[length]; // Array to store the counts of distinct words for each hash value

        for(String word : hashedWords){
            if(!word.equals("-1")){
                int hash = code(word);
                distinctHashCounts[hash]++;
            }
        }

        int maxHashValue = 0;
        int maxCount = 0;

        for(int i = 0; i < distinctHashCounts.length; i++){
            if(distinctHashCounts[i] > maxCount){
                maxCount = distinctHashCounts[i];
                maxHashValue = i;
            }
        }

        System.out.println("Hash value with the greatest number of distinct words: " + maxHashValue);
        System.out.println("Number of words with this hash value: " + maxCount);
    }

    // e. farthest word from their hash value
    public void findFarthestWord(){
        int maxDistance = 0;
        String farthestWord = "";

        for(int i = 0; i < length; i++){
            String word = hashedWords[i];
            if(!word.equals("-1")){
                int actualHash = code(word);
                int distance = Math.abs(actualHash - i);
                if(distance > maxDistance){
                    maxDistance = distance;
                    farthestWord = word;
                }
            }
        }

        System.out.println("Farthest word from its actual hash value: " + farthestWord);
        System.out.println("Distance from its actual hash value: " + maxDistance);
    }
}
