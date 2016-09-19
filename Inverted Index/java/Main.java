/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package indexing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import org.tartarus.martin.Stemmer;

/**
 *
 * @author Abdullah
 */
public class Main {

   public static int generic_Count;
    //Hashes to be declared
    static public final Hashtable < String , Hashtable < String , Integer >> index_Hash =
                                                 new Hashtable < String , Hashtable < String , Integer>> ();
    static public final Hashtable < String , Integer > useless_Words =
                                        new  Hashtable < String , Integer >();

    public static void populateStopWords() throws FileNotFoundException, IOException{

        File sw = new File ("StopWords.txt");
        BufferedReader bf = new BufferedReader(new FileReader(sw));
        String word;
        while( (word = bf.readLine()) != null ){
            useless_Words.put( word , 0 );
        }
    }


     public static void populateInvertedIndex(File d) throws FileNotFoundException, IOException{

         File[] files = d.listFiles();
         if(files != null){
             for (File file : files) {
                 if (file.isDirectory()) {
                     //Recursively call function Again
                     populateInvertedIndex(file);
                 }
                 else{
                     File tempFile = new File(file.getPath());
                     BufferedReader reader = new BufferedReader(new FileReader(tempFile));
                     String tempText;
                     while ((tempText = reader.readLine()) != null) {
                         for (String abc : tempText.split("[^a-zA-Z0-9]+")) {
                             if(!(useless_Words.containsKey(abc.toLowerCase()))){
                                  Stemmer s = new Stemmer();
                                  s.add(abc.toLowerCase().toCharArray(), abc.length());
                                  s.stem();
                                  String tempTerm = s.toString();
                                  if(index_Hash.containsKey(tempTerm)){

                                      if (((Hashtable) index_Hash.get(tempTerm)).containsKey(file.getName())) {
                                        //  double value = (double) ((Hashtable) index_Hash.get(tempTerm)
                                        int numb = (Integer) ((Hashtable) index_Hash.get(tempTerm)).get(file.getName());
                                        numb ++;
                                    ((Hashtable) index_Hash.get(tempTerm)).put(d.getName() + "/" + file.getName(), numb);
                                } else {
                                          try{
                                    ((Hashtable) index_Hash.get(tempTerm)).put(d.getName() + "/" + file.getName(), new Integer(1));
                                    generic_Count++;
                                          }catch(Exception e){
                                          }
                                }

                                  }
                                  else{
                                      index_Hash.put(tempTerm,new Hashtable());
                                      ((Hashtable) index_Hash.get(tempTerm)).put(d.getName() + "/" + file.getName(), new Integer(1));
                                  }
                             }

                         }

                     }

                 }
             }
         }

     }


    public static void main(String[] args) throws FileNotFoundException, IOException {

        populateStopWords();
        // Counter for storing the total number of tokens we have in our corpus
        generic_Count=0;
        // The path where all our files are placed
        File parent = new File("corpus");
        System.out.println("Processing");
        populateInvertedIndex(parent);
        System.out.println("Total Tokens = "+generic_Count);

        // Get a set of all the entries (key - value pairs) contained in the Hashtable
        Set entrySet = index_Hash.entrySet();
        // Obtain an Iterator for the entries Set
        Iterator it = entrySet.iterator();
        // Iterate through Hashtable entries
        System.out.println("Hashtable entries : ");
        int i=0;
        while (it.hasNext()) {
             System.out.print(++i+"--> ");
            System.out.println(it.next());
        }

}


    }


