package com.example.healthAndFitness.JavaClass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//
//import com.example.healthAndFitness.Entity.PropertyEntity;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
public class GetDataForDrug {

    public static void main(String[] args) throws IOException
    {
        int i=0;
            FileInputStream fis=new FileInputStream("C:\\Users\\vaibhav jangid\\Desktop\\drug_links.txt");
            Scanner sc=new Scanner(fis);
            while (sc.hasNextLine())
            {
                String url = sc.nextLine();
                try {
                    Document doc = Jsoup.connect(url).get();
                    Element e = doc.getElementsByClass("monograph-content").first();
                    System.out.println(e.text());
                }
                catch (Exception e)
                {
                    System.out.println("ERROR in : " + url);
                }
            }
            System.out.println("DONE");
    }
}

