package com.example.healthAndFitness.Controller;

import com.example.healthAndFitness.Entity.CommonDiseaseEntity;
import com.example.healthAndFitness.Entity.DoctorEntity;
import com.example.healthAndFitness.Entity.NearTreatmentCenterEntity;
import com.example.healthAndFitness.Repo.CommonDiseaseRepo;
import com.example.healthAndFitness.Repo.DoctorRepo;
import com.example.healthAndFitness.Repo.NearTreatmentCenterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DoctorController {
    @Autowired
    NearTreatmentCenterRepo nearTreatmentCenterRepo;
    @Autowired
    DoctorRepo doctorRepo;
    @PostMapping(value = "/centerSuggestion")
    public List<String> GetCenterSuggestions(@RequestParam("obj") String obj)
    {
        obj = obj.replaceAll(" ","%");
        if(obj.length()>2 && obj.charAt(obj.length()-1)=='%')
        {
            obj = obj.substring(0, obj.length()-2);
        }
        List<NearTreatmentCenterEntity> ll = nearTreatmentCenterRepo.getLocation(obj.toLowerCase());
        List<String>ans = new ArrayList<>();
        for(NearTreatmentCenterEntity ne : ll)
        {
            ans.add(ne.getCenterName());
        }
        return ans;
    }
    @PostMapping(value = "/getCenterDescription")
    public List<String> GetCenterDescription(@RequestParam("") String name)
    {
        List<NearTreatmentCenterEntity> ll = nearTreatmentCenterRepo.findByCenterName(name);
        if(ll.size()==0)
        {
            return null;
        }
        else
        {
            List<String> ans = new ArrayList<>();
            ans.add(ll.get(0).getCenterName());
            ans.add(ll.get(0).getCenterLocation());
            ans.add(ll.get(0).getContactNumber());
            return ans;
        }
    }

    @PostMapping(value = "/doctorSuggestion")
    public List<String> GetDoctorSuggestions(@RequestParam("obj") String obj)
    {
        obj = obj.replaceAll(" ","%");
        if(obj.length()>2 && obj.charAt(obj.length()-1)=='%')
        {
            obj = obj.substring(0, obj.length()-2);
        }
        List<DoctorEntity> ll = doctorRepo.getNames(obj);
        if(ll.size()==0)
        {
            return null;
        }
        else
        {
            List<String> ans = new ArrayList<>();
            for(DoctorEntity de : ll)
            {
                ans.add(de.getDoctorName());
            }
            return ans;
        }
    }
    @PostMapping(value = "/getDoctorDescription")
    public List<String> GetDoctorDescription(@RequestParam("") String name)
    {
        List<DoctorEntity> ll = doctorRepo.findAllByDoctorName(name);
        if(ll.size()==0)
        {
            return null;
        }
        else
        {
            List<String> ans = new ArrayList<>();
            ans.add(ll.get(0).getDoctorName());
            ans.add(ll.get(0).getDoctorLocation());
            ans.add(ll.get(0).getDoctorExperience());
            ans.add(ll.get(0).getDoctorDescription());
            return ans;
        }
    }
}
