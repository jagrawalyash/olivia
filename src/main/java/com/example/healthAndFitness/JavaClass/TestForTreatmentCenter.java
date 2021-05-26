package com.example.healthAndFitness.JavaClass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class TestForTreatmentCenter {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.medifee.com/hospitals-in-jodhpur").timeout(5000).get();
        Element mainClass = doc.getElementsByClass("table table-bordered table-striped").first();
        List<Element> e = mainClass.select("tbody tr");
        for(Element x : e)
        {
            String name = x.select("td a").text();
            String no = x.getElementsByTag("td").get(1).text();
            String address = x.select("td").text();
            address = address.replaceAll("\\b"+name+"\\b","");
            System.out.println("Name = " + name);
            System.out.println("no = " + no);
            System.out.println("Address : " + address);
        }
    }
}
