package com.example.healthAndFitness.Controller;

import com.example.healthAndFitness.Entity.DiseaseEntity;
import com.example.healthAndFitness.Entity.DrugEntity;
import com.example.healthAndFitness.Repo.DiseaseRepo;
import com.example.healthAndFitness.Repo.DrugRepo;
import javafx.print.Collation;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class DrugAndDiseaseController {
    @Autowired
    DiseaseRepo diseaseRepo;
    @Autowired
    DrugRepo drugRepo;
//    @PostMapping(value = "/drugSuggestion")
//    public List<String> DrugSuggestion(@RequestParam("obj") String obj)
//    {
//        obj = obj.replaceAll(" ","%");
//        if(obj.length()>2 && obj.charAt(obj.length()-1)=='%')
//        {
//            obj = obj.substring(0, obj.length()-2);
//        }
//        List<String>res = new ArrayList<>();
//        List<DrugEntity> ll= drugRepo.searchForSuggestions(obj.toLowerCase()) ;
//        for(DrugEntity d : ll)
//        {
//            res.add(d.getDrugName());
//        }
//        Collections.sort(res);
//        return res;
//    }
@PostMapping(value = "/drugSuggestion")
public List<Pair<String,Integer>> DrugSuggestion(@RequestParam("obj") String obj)
{
    obj = obj.replaceAll(" ","%");
    if(obj.length()>2 && obj.charAt(obj.length()-1)=='%')
    {
        obj = obj.substring(0, obj.length()-2);
    }
    List<Pair<String, Integer>>res = new ArrayList<>();
    List<DrugEntity> ll= drugRepo.searchForSuggestions(obj.toLowerCase()) ;
    for(DrugEntity d : ll)
    {
        res.add(new Pair<>(d.getDrugName(),d.getDrugId()));
    }
    return res;
}

    @PostMapping(value="/getDrug")
    public Pair<String,String> GetDrug(@RequestParam("dname") int name)
    {
        try {
            DrugEntity de = drugRepo.findById(name).get();
            return new Pair<>(de.getDrugName(),de.getUseOfDrug());
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @PostMapping(value = "/diseaseSuggestion")
    public List<Pair<String,Integer>> DiseaseSuggestion(@RequestParam("obj") String obj)
    {
        obj = obj.replaceAll(" ","%");
        if(obj.length()>2 && obj.charAt(obj.length()-1)=='%')
        {
            obj = obj.substring(0, obj.length()-2);
        }
        List<Pair<String,Integer>>res = new ArrayList<>();
        List<DiseaseEntity> ll= diseaseRepo.searchForSuggestions(obj.toLowerCase()) ;
        for(DiseaseEntity d : ll)
        {
            res.add(new Pair<>(d.getDiseaseName(),d.getId()));
        }
        return res;
    }
    @PostMapping(value="/getDisease")
    public List<String> GetDisease(@RequestParam("dname") int name)
    {
        try {
            DiseaseEntity de = diseaseRepo.findById(name).get();
            List<String>res = new ArrayList<>();
            res.add(de.getDiseaseName());
            res.add(de.getDescription() + "Symptoms : " + de.getSymptoms());
            return res;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}




