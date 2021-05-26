package com.example.healthAndFitness.JavaClass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class ForJodhpurDoctor {
    public static void test(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.practo.com/jodhpur/general-physician?page=1").get();
        List<Element> e1 = doc.getElementsByClass("u-border-general--bottom");
        String base="https://www.practo.com";
        for(Element x : e1)
        {
            try {
                Element e2 = x.getElementsByClass("listing-doctor-card").first();
                Element e3 = e2.select(".u-d-flex .info-section a").first();
                Document doc2 = Jsoup.connect(base + e3.attr("href")).get();
                Element e4 = doc2.getElementsByClass("u-d-flex flex-jc-space-between").first();
                String name = e4.select("div h1").first().text();
                System.out.println(name);
                String expr = doc2.getElementsByClass("c-profile--qualification").first().text();
                System.out.println(expr);
                Element e5 = doc2.getElementsByClass("pure-u-20-24").first();
                Element e6 = e5.getElementsByTag("div").get(5);
                String desc = e5.getElementsByClass("c-profile__description").first().text();
                System.out.println(desc);

            }
            catch (Exception exception)
            {
                continue;
            }
        }
    }
}
