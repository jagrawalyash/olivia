package com.example.healthAndFitness.JavaClass;

import java.util.ArrayList;
import java.util.List;

public class PrepareString {
    public static String PrepareText(String s)
    {
        String finalString="";
        for(String x : s.split(" "))
        {
            finalString = finalString + x + "_";
        }
        finalString = finalString.substring(0,finalString.length()-1);

       return finalString;
    }

}
