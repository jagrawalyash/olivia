package com.example.healthAndFitness.JavaClass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class ForDisease {
    public static void test(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.malacards.org/categories/bone_disease_list").timeout(5000).get();
        Element e1 = doc.select(".search-results").first();
        String base="https://www.malacards.org";
        List<Element> e2 = e1.select("tbody tr");
        for(Element e3 : e2)
        {
            try {
                Element e4 = e3.getElementsByTag("td").get(3).getElementsByTag("a").first();
                String url =base+ e4.attr("href");
                Document doc2 = Jsoup.connect(url).timeout(5000).get();

                Element eName = doc2.select(".main-name b").first();
                String name = eName.text();

                Element eSummary = doc2.getElementById("Summary");
                String description = eSummary.text();

                List<Element> eSymptoms = doc2.select("#HPO_Symptoms-table tbody tr");
                String symptoms = "";
                for (Element e : eSymptoms) {
                    symptoms = symptoms + e.text() + "\n";
                }
                System.out.println(name);
                System.out.println(description);
                System.out.println(symptoms);
                break;

            }
            catch (Exception exception)
            {
                continue;
            }
        }
    }
}
