package com.example.healthAndFitness.JavaClass;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OcrTest {
    public static void test(String args[]) {
        try{
            ITesseract instance = new Tesseract();
            String imgText = instance.doOCR(new File("C://Users//vaibhav jangid//Desktop//2040304248.jpeg"));
            imgText = imgText.toLowerCase();
//            imgText = imgText.replaceAll("mq\\/dl","");
//            imgText = imgText.replaceAll("q\\/dl","");
//            imgText = imgText.replaceAll("mg\\/dl","");
//            imgText = imgText.replaceAll("u\\/l","");


            imgText = imgText.replaceAll("\\—","-");
            String obj="haemoglobin";
            String p1 = "\\s*\\b"+obj+"\\b\\s*[0-9]*\\s*\\.*[0-9]*\\s*\\%*";
            String p2 = "\\s*\\b"+obj+"\\b\\s*[0-9]*\\s*[0-9]*\\s*\\%*";
            Pattern p = Pattern.compile((p1));
            Matcher m = p.matcher(imgText);
//            Pattern range = Pattern.compile(("[0-9]{2}\\.?[0-9]*\\-[0-9]*\\.?[0-9]*"));
//            Matcher matchRange = range.matcher(imgText);
            System.out.println(imgText);
            System.out.println("OUTPUT");
            if(m.find())
            {
                System.out.println("1");
                System.out.println(m.group());
            }
            else if(Pattern.compile(p2).matcher(imgText).find())
            {
                System.out.println("2");
                System.out.println(Pattern.compile(p2).matcher(imgText).group());
            }
            else
            {
                System.out.println("NO");
            }


        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
