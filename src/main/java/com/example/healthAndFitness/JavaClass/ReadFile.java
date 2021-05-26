package com.example.healthAndFitness.JavaClass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

public class ReadFile {
    public static void Test(String[] args) throws Exception {
        int x=0;
        try {
            FileWriter myWriter = new FileWriter("C:\\Users\\vaibhav jangid\\Desktop\\new_drug.txt",true);
            for(char i='a';i<='z';i++)
            {
                for(char j='a';j<='z';j++)
                {
                    x++;
                    String url = "https://www.webmd.com/drugs/2/alpha/"+i+ "/"+ i+j;
                    Document doc = Jsoup.connect(url).get();
                    Element linkClass = doc.getElementsByClass("drugs-search-list-conditions").first();
                    if(linkClass.text().length()==0)
                    {
                        continue;
                    }
                    else
                    {
                        System.out.println("Link " + x + " inserted");
                        myWriter.write(url + "\n");
                    }
                }
            }
            myWriter.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
