package com.example.healthAndFitness.JavaClass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class ForDoctor {
    public static void test(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.medifee.com/best-doctors/urologist-in-jaipur/").timeout(5000).get();
        List<Element> ll = doc.getElementsByClass("col-lg-9").first().getElementsByClass("col-lg-12");
        for(Element e : ll)
        {
            Element a = e.getElementsByClass("panel panel-default").first();
            Element b = a.getElementsByClass("panel-body").first();
            String name = b.select("h2").text();
            String experience = b.select("p").text();
            String review = b.text();
            review = review.substring(review.indexOf(name)+name.length());
            review = review.substring(review.indexOf(experience)+experience.length());
            System.out.println("NAME : " + name);
            System.out.println("Experience : " + experience);
            System.out.println("REVIEW : " + review);
        }
    }
}
