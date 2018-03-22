import java.nio.channels.Channel;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A class for training a Markov chain from a body of text
 * and then generating new text using the estimated frequencies.
 */
class TextGenerator
{
   protected ChainingHashMap countTable;
   protected ChainingHashMap totalTable;
   protected int totalWords;
   protected Random rand;

   public TextGenerator()
   {
      rand = new Random();
      countTable = new ChainingHashMap();
      totalTable = new ChainingHashMap();

   }

   /**
    * Trains the Markov chaintotalWords = 0; on the given body of text
    * @param text a String containing the text to train on
    */
   public void train(String text)
   {
      String[] words = text.trim().split("\\s+");
      for(int y = 0;y<words.length-1;y++) {
         totalWords++; //Keep tracks of number of words
         if (totalTable.get(words[y]) == null) {
             //If the word isn't in the hashmap
            totalTable.put(words[y], 1);

         } else {
            int v = (Integer) totalTable.get(words[y]) + 1;
            totalTable.put(words[y], v);
         }

         if (countTable.get(words[y]) != null) {
            ChainingHashMap sm = (ChainingHashMap) countTable.get(words[y]);
            if (sm.get(words[y+1]) != null) {

               int kes = (Integer) sm.get(words[y+1]) + 1; //This Part
               sm.put(words[y+1], kes);
               countTable.put(words[y], sm);
            } else {
               sm.put(words[y+1], 1);
               countTable.put(words[y], sm);
            }
         }  else{
               ChainingHashMap np = new ChainingHashMap();
               np.put(words[y+1], 1);
               countTable.put(words[y], np);
            }
      }

   }

   /**
    * Picks a random word from the given frequencies
    * @param counts a hash map that maps words (String) to the frequency of that word (Integer)
    * @param totalCount the sum of all the frequencies in counts
    * @return the randomly chosen word
    */
   public String sampleWord(ChainingHashMap counts, int totalCount)
   {
      //Pick a random number between 0.0 and 1.0
      double randNum = rand.nextDouble();
      
      double probSum = 0.0;
      for (Entry e : counts)
      {
	 //Add this word's frequency to the cumulative probability so far
	 int num = (Integer) e.value;
	 probSum += ((double)num / (double)totalCount);
	 //If the sum crosses the random number, this is the randomly selected word
	 if (randNum < probSum)
	    return (String) e.key;
      }
      return "";
   }

   /**
    * Uses the Markov chain to generate a sequence of words of the given length
    * (Randomly picks a word, then based on that word picks a next word, and so on)
    * @param numWords the number of words to generate
    * @return a String containing the generated text
    */
   public String generateText(int numWords)
   {

      String text = "";
      String first = sampleWord(totalTable,totalWords);
      text = text + "..." + first;
      int track = 1;
      while(track != numWords) {
         ChainingHashMap mp = (ChainingHashMap)countTable.get(first);
         int val = (Integer)totalTable.get(first);
         first = sampleWord(mp,val);
         text += " " + first;
         track++;

      }
      return text + "...";

   }

   /**
    * Takes a file as a command-line argument, passes the contents
    * to train and then uses generateText to generate and print
    * a 100-word passage.
    */
   public static void main(String [] args)
   {
      if(args.length == 0)
      {
	 System.out.println("Requires a file containing the source text!");
	 System.exit(1);
      }

      //Open the file
      File file = new File(args[0]);

      Scanner scan = null;
      try
      {
	 scan = new Scanner(file);
      }
      catch (FileNotFoundException fnf)
      {
	 System.err.println("Input file not found");
	 System.exit(1);
      }

      //Read the entire file into a String
      scan.useDelimiter("\\Z");
      String text = scan.next();

      TextGenerator t = new TextGenerator();
      t.train(text);
      System.out.println(t.generateText(1000));
   }
}
