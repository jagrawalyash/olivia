package com.example.healthAndFitness.JavaClass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Test2 {
    public static void test(String[] args) throws IOException {
//        int i=0;
//        FileWriter fr = new FileWriter("C:\\Users\\vaibhav jangid\\Desktop\\links.txt");
//        for(char a='a';a<='z';a++)
//        {
//            try{
//            String url = "https://druginfo.nlm.nih.gov/drugportal/drug/names/"+a;
//            Document doc = Jsoup.connect(url).get();
//            Element e = doc.select("table tbody tr td table tbody").get(1);
//            List<Element> e1 = e.select("tr td a");
//            for(Element x : e1)
//            {
//                i++;
//                String base = "https://druginfo.nlm.nih.gov";
//                base += x.attr("href");
//                try {
//                    System.out.println(base);
//                    Document doc1 = Jsoup.connect(base).get();
//                    Element inE1 = doc1.getElementsByClass("links-lists").get(0).selectFirst("ul li");
//                    Element inE12 = inE1.select("a").get(1);
//                    fr.write(inE12.attr("href") + "\n");
//                    System.out.println("DONE : " + i);
//                }
//                catch (Exception ea)
//                {
//                    System.out.println("ERROR in : "+base);
//                }
//            }
//        } catch (Exception q) { continue; }
//        }
//        fr.close();
//        System.out.println("FINISH");
















    }
}
