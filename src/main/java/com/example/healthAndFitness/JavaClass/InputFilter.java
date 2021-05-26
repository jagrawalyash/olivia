package com.example.healthAndFitness.JavaClass;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputFilter {
    public static String InputFilter(String input) throws IOException {
        FileReader fr = new FileReader("C:\\Users\\vaibhav jangid\\Desktop\\Spring " +
                "Boot\\Fitness_Project_data\\input_filter.txt");
        Scanner sc = new Scanner(fr);
        int a=0;
        while(sc.hasNextLine())
        {
            String x = sc.nextLine().toLowerCase();
            input = input.replaceAll("\\b" + x + "\\b\\s*", "");
        }
        input = input.replaceAll("\\d*","");
        input = input.replaceAll("\\?","");
        input = input.replaceAll("\\.","");
        input = input.replaceAll("\\s+"," ");
        List<String> ll = new ArrayList<>();
        for(String s : input.split(" "))
        {
            ll.add(s);
        }
        return input;
    }
}
