package com.example.healthAndFitness.JavaClass;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FilterForDoctor {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("C:\\Users\\vaibhav jangid\\Desktop\\temp.txt");
        FileWriter fw = new FileWriter("C:\\Users\\vaibhav jangid\\Desktop\\dlink.txt");
        Scanner sc = new Scanner(fr);
        while(sc.hasNextLine())
        {
            String s = sc.nextLine();
            s = s.substring(s.indexOf('"')+1);
            s = s.substring(0,s.lastIndexOf('"'));
            fw.write(s+"\n");
        }
        fw.close();
    }
}
