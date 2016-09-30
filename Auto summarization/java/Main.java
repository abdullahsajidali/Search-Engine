
package autosummarizertextrank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static String systemSummary1=null;
    public static String systemSummary2=null;
    public static String systemSummary3=null;


    public static int find_Min(int arr[]) {
        if (arr.length == 0) {
            return -1;
        }
        int small = arr[0];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < small) {
                small = arr[i];
                index = i;
            }
        }
        return index;
    }

    public static String compute_Summary(BufferedReader in, String title) throws IOException {

        String Summary = null;
        String line = in.readLine();

        Levenshtein obj = null;
        StringBuilder paragraph = new StringBuilder();
        while (true) {
            if (line == null || line.trim().length() == 0) {

                String allSentences[] = paragraph.toString().split("(?<=[a-z])\\.\\s+");
                int scores[] = new int[allSentences.length];

                for (int i = 0; i < allSentences.length; i++) {

                    scores[i] = obj.distance(title, allSentences[i]); //Specifies k title aur is sentence ka dist kya hai
                }
                paragraph.setLength(0);
                //Now we have finished reading & processing whole paragraph
                int x = find_Min(scores);
                if (Summary == null) {
                    Summary = allSentences[x];
                } else {
                    Summary = new StringBuilder().append(Summary).append(allSentences[x]).toString();
                }
                // Siterator++;
                if (line == null) {
                    break;
                }
            } else {
                paragraph.append(" ");
                paragraph.append(line);
            }
            line = in.readLine();
        }
        in.close();

        Summary = Summary.replace("."," ");
        Summary = Summary.toLowerCase();
        return Summary;
    }

    public static List<String> BIgrams(int n, String str) {
        List<String> BI_grams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            BI_grams.add(concat(words, i, i+n));
        return BI_grams;
    }

    public static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        BufferedReader in;
        String name;
        PrintWriter out;
        //part a of the assignment
        System.out.println("---------->>>>    PART ONE    <<<<----------- ");
        in = new BufferedReader(new FileReader("DOC1/document.txt"));
        name = in.readLine();
        name = name.trim();
        systemSummary1 = compute_Summary(in,name);
        out = new PrintWriter("DOC1/SystemSummary/systemAnswer.txt");
        out.println(systemSummary1);
        out.close();
        System.out.println("Document1 Summary = "+systemSummary1);

        in = new BufferedReader(new FileReader("DOC2/document.txt"));
        name = in.readLine();
        name = name.trim();
        systemSummary2 = compute_Summary(in,name);
        out = new PrintWriter("DOC2/SystemSummary/systemAnswer.txt");
        out.println(systemSummary2);
        out.close();
        System.out.println("Document2 Summary = "+systemSummary2);

        in = new BufferedReader(new FileReader("DOC3/document.txt"));
        name = in.readLine();
        name = name.trim();
        systemSummary3 = compute_Summary(in,name);
        out = new PrintWriter("DOC3/SystemSummary/systemAnswer.txt");
        out.println(systemSummary3);
        out.close();
        System.out.println("Document3 Summary = "+systemSummary3);


        // Calculation of TextRank Starts Using ROUGE-2
        System.out.println("---------->>>>    PART TWO    <<<<----------- ");
        //For 1st Document
        String systemSummary = new Scanner(new File("DOC1/SystemSummary/systemAnswer.txt")).useDelimiter("\\Z").next();
        systemSummary = systemSummary.replace("."," ");
        systemSummary = systemSummary.toLowerCase();
        String refSummary1 = new Scanner(new File("DOC1/HumanSummaries/human1.txt")).useDelimiter("\\Z").next();
        String refSummary2 = new Scanner(new File("DOC1/HumanSummaries/human2.txt")).useDelimiter("\\Z").next();
        String refSummary3 = new Scanner(new File("DOC1/HumanSummaries/human3.txt")).useDelimiter("\\Z").next();
        
        refSummary1 = refSummary1.replace("."," ");
        refSummary1 = refSummary1.toLowerCase();
        refSummary2 = refSummary2.replace("."," ");
        refSummary2 = refSummary2.toLowerCase();
        refSummary3 = refSummary3.replace("."," ");
        refSummary3 = refSummary3.toLowerCase();

        List<String> systemBigrams;
        List<String> refBigrams;

        systemBigrams = BIgrams(2,systemSummary);
        int numerator=0;
        int denominator=0;

        refBigrams = BIgrams(2,refSummary1);
        for (String temp : refBigrams){
                  if(systemBigrams.contains(temp)){
                      numerator++;
                  }
                  denominator++;
              }

        refBigrams = BIgrams(2,refSummary2);
        for (String temp : refBigrams){
                  if(systemBigrams.contains(temp)){
                      numerator++;
                  }
                  denominator++;
              }

        refBigrams = BIgrams(2,refSummary3);
        for (String temp : refBigrams){
                  if(systemBigrams.contains(temp)){
                      numerator++;
                  }
                  denominator++;
              }
        System.out.println("ROUGE-2 value of textRank for our system in doc 1 is = "+numerator+"/"+denominator);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
         //For 2nd Document
        systemSummary = new Scanner(new File("DOC2/SystemSummary/systemAnswer.txt")).useDelimiter("\\Z").next();
        systemSummary = systemSummary.replace("."," ");
        systemSummary = systemSummary.toLowerCase();
        refSummary1 = new Scanner(new File("DOC2/HumanSummaries/human1.txt")).useDelimiter("\\Z").next();
        refSummary2 = new Scanner(new File("DOC2/HumanSummaries/human2.txt")).useDelimiter("\\Z").next();
        refSummary3 = new Scanner(new File("DOC2/HumanSummaries/human3.txt")).useDelimiter("\\Z").next();

        refSummary1 = refSummary1.replace("."," ");
        refSummary1 = refSummary1.toLowerCase();
        refSummary2 = refSummary2.replace("."," ");
        refSummary2 = refSummary2.toLowerCase();
        refSummary3 = refSummary3.replace("."," ");
        refSummary3 = refSummary3.toLowerCase();



        systemBigrams = BIgrams(2,systemSummary);
        numerator=0;
        denominator=0;

        refBigrams = BIgrams(2,refSummary1);
        for (String temp : refBigrams){
                  if(systemBigrams.contains(temp)){
                      numerator++;
                  }
                  denominator++;
              }

        refBigrams = BIgrams(2,refSummary2);
        for (String temp : refBigrams){
                  if(systemBigrams.contains(temp)){
                      numerator++;
                  }
                  denominator++;
              }

        refBigrams = BIgrams(2,refSummary3);
        for (String temp : refBigrams){
                  if(systemBigrams.contains(temp)){
                      numerator++;
                  }
                  denominator++;
              }
        System.out.println("ROUGE-2 value of textRank for our system in doc 2 is = "+numerator+"/"+denominator);
//////////////////////////////////////////////////////////////////////////////////////////////////
        //For 3rd Document
        systemSummary = new Scanner(new File("DOC3/SystemSummary/systemAnswer.txt")).useDelimiter("\\Z").next();
        systemSummary = systemSummary.replace("."," ");
        systemSummary = systemSummary.toLowerCase();
        refSummary1 = new Scanner(new File("DOC3/HumanSummaries/human1.txt")).useDelimiter("\\Z").next();
        refSummary2 = new Scanner(new File("DOC3/HumanSummaries/human2.txt")).useDelimiter("\\Z").next();
        refSummary3 = new Scanner(new File("DOC3/HumanSummaries/human3.txt")).useDelimiter("\\Z").next();

        refSummary1 = refSummary1.replace("."," ");
        refSummary1 = refSummary1.toLowerCase();
        refSummary2 = refSummary2.replace("."," ");
        refSummary2 = refSummary2.toLowerCase();
        refSummary3 = refSummary3.replace("."," ");
        refSummary3 = refSummary3.toLowerCase();



        systemBigrams = BIgrams(2,systemSummary);
        numerator=0;
        denominator=0;

        refBigrams = BIgrams(2,refSummary1);
        for (String temp : refBigrams){
                  if(systemBigrams.contains(temp)){
                      numerator++;
                  }
                  denominator++;
              }

        refBigrams = BIgrams(2,refSummary2);
        for (String temp : refBigrams){
                  if(systemBigrams.contains(temp)){
                      numerator++;
                  }
                  denominator++;
              }

        refBigrams = BIgrams(2,refSummary3);
        for (String temp : refBigrams){
                  if(systemBigrams.contains(temp)){
                      numerator++;
                  }
                  denominator++;
              }
        System.out.println("ROUGE-2 value of textRank for our system in doc 2 is = "+numerator+"/"+denominator);
 
    }

}
