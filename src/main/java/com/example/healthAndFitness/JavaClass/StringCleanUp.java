package com.example.healthAndFitness.JavaClass;

import com.example.healthAndFitness.Entity.DiseaseEntity;
import com.example.healthAndFitness.Entity.DrugEntity;
import com.example.healthAndFitness.Repo.DiseaseRepo;
import com.example.healthAndFitness.Repo.DrugRepo;
import com.sun.xml.internal.ws.util.CompletedFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class StringCleanUp {
    @Autowired
    DrugRepo drugRepo;
    @Autowired
    DiseaseRepo diseaseRepo;
    //@PostConstruct
    public void UpdateDrug() throws IOException {
        List<String> ans = new ArrayList<>();
        FileReader fr = new FileReader("C:\\Users\\vaibhav jangid\\Desktop\\remove_string.txt");
        Scanner sc = new Scanner(fr);

        while(sc.hasNextLine())
        {
            ans.add(sc.nextLine());
        }
        //System.out.println(ans);

        //FOR NEW DRUG

        //String str[] = {"medication","this","is","used","to","common","causes","include","and","certain","medications","product","can","could","may","might","shall","should","will","would","must","of","with","at","from","into","during","including","until","against","among","throughout","despite","towards","upon","concerning","to","in","for","on","by","about","like","through","over","before","between","after","since","without","under","within","along","following","across","behind","beyond","plus","except","but","up","out","around","down","off","above","near","all","another","any","anybody","anyone","anything","as","aught","both","each","each other","either","enough","everybody","everyone","everything","few","he","her","hers","herself","him","himself","his","I","idem","it","its","itself","many","me","mine","most","my","myself","naught","neither","no one","nobody","none","nothing","nought","one","one another","other","others","ought","our","ours","ourself","ourselves","several","she","some","somebody","someone","something","somewhat","such","suchlike","that","thee","their","theirs","theirself","theirselves","them","themself","themselves","there","these","they","thine","this","those","thou","thy","thyself","us","we","what","whatever","whatnot","whatsoever","whence","where","whereby","wherefrom","wherein","whereinto","whereof","whereon","wherever","wheresoever","whereto","whereunto","wherewith","wherewithal","whether","which","whichever","whichsoever","who","whoever","whom","whomever","whomso","whomsoever","whose","whosever","whosesoever","whoso","whosoever","ye","yon","yonder","you","your","yours","yourself","yourselves","1","2","3","4","5","6","7","8","9","0","\n"};
//        List<DrugEntity> ll = drugRepo.findAll();
//        int i=0;
//        for(DrugEntity de : ll)
//        {
//            i++;
//            final int li=i;
//            CompletableFuture.runAsync(() -> {
//                String desc = de.getUseOfDrug();
//                desc = desc.toLowerCase();
//                log.info("Before Length : {}",desc.length());
//                //System.out.println(desc);
//                for(String s :ans)
//                {
//                    desc = desc.replaceAll("\\s*\\b"+s.toLowerCase()+"\\b\\s*"," ");
//                }
//                desc = desc.replaceAll("\\s*,"," ");
//                desc = desc.replaceAll("\\s*\n"," ");
//                desc = desc.replaceAll("\\s*/"," ");
//                //desc = desc.replaceAll("\\s*."," ");
//                desc = desc.replaceAll("  "," ");
//                desc = desc.replaceAll("   "," ");
//                log.info("After cleanUp : {}",desc.length());
//                de.setForSearch(desc);
//                drugRepo.save(de);
//                log.info("Complete : {}",li);
//            });
//        }
//        log.info("Process Complete");


        //FOR NEW DISEASE

        List<DiseaseEntity> ll = diseaseRepo.findAll();
        int i=0;
        for(DiseaseEntity de : ll)
        {
            i++;
            final int li=i;
            CompletableFuture.runAsync(() -> {
                String desc = de.getDescription();
                desc = desc.toLowerCase();
                log.info("Before Length : {}",desc.length());
                //System.out.println(desc);
                for(String s :ans)
                {
                    desc = desc.replaceAll("\\s*\\b"+s.toLowerCase()+"\\b\\s*"," ");
                }
                desc = desc.replaceAll("\\s*,"," ");
                desc = desc.replaceAll("\\s*\n"," ");
                desc = desc.replaceAll("\\s*/"," ");
                desc = desc.replaceAll("\\s*\\bomim® : 57\\b\\s*"," ");
                //desc = desc.replaceAll("\\s*."," ");
                desc = desc.replaceAll("  "," ");
                desc = desc.replaceAll("   "," ");
                log.info("After cleanUp : {}",desc.length());
                diseaseRepo.save(de);
                log.info("Complete : {}",li);
            });
        }
        log.info("Process Complete");


    }
}
