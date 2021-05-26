package com.example.healthAndFitness.JavaClass;

import com.sun.deploy.panel.TreeBuilder;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException, JSONException {
        String url = "http://suggestqueries.google.com/complete/search?output=chrome&q=";
        String obj="how much";
        Document doc = Jsoup.connect(url+obj).get();
        Element el = doc.select("body").first();
        String str = el.text();
        str = str.replaceAll("\\[","");
        str = str.replaceAll("\\]","");
        str = str.replaceAll("\"","");
        str = str.replaceAll("\\d","");
        str = str.replaceAll("\\,,","");
        str = str.substring(0,str.indexOf("{",0));
        List<String> ll = new ArrayList<>();
        for(String s : str.split(","))
        {
            ll.add(s);
        }
    }
}
