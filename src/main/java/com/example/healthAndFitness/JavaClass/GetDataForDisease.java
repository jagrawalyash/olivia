package com.example.healthAndFitness.JavaClass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class GetDataForDisease {
    public static void test(String[] args) throws IOException
    {
        int i=0;
        FileInputStream fis=new FileInputStream("C:\\Users\\vaibhav jangid\\Desktop\\Health and fitness data from git\\blood_diseases.txt");
        Scanner sc=new Scanner(fis);
        while (sc.hasNextLine())
        {
            String txt = sc.nextLine().toLowerCase();
            try {
                String url="https://www.malacards.org/card/"+PrepareString.PrepareText(txt)+"?search=Blood%20diseases";
                System.out.println("URL : " + url);
                Document doc = Jsoup.connect(url).get();
                Element eName = doc.select(".main-name b").first();
                String name = eName.text();
                System.out.println("NAME : " + name);

                Element eSummary = doc.getElementById("Summary");
                String description = eSummary.text();
                System.out.println(description);

                List<Element> eSymptoms = doc.select("#HPO_Symptoms-table tbody tr");
                String symptoms="";
                for(Element e : eSymptoms)
                {
                    symptoms = symptoms + e.text() + "\n";
                }
                System.out.println("Su : " + symptoms);
                break;
            }
            catch (Exception e)
            {
                System.out.println("ERROR : ");
                break;
            }
        }
        System.out.println("DONE");
    }
}
