package com.example.healthAndFitness.JavaClass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class HealthType {
    public static void test(String[] args) throws IOException {
        try{
            String url="https://www.webmd.com/hypertension-high-blood-pressure/default.htm";
            Document doc = Jsoup.connect(url).get();
            Element el1= doc.getElementsByClass("article-page active-page").first().getElementsByTag("section").first();
            System.out.println("su : " + el1.getElementsByTag("p").first().text());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
