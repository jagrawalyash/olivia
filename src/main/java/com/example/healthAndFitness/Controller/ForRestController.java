package com.example.healthAndFitness.Controller;

import com.example.healthAndFitness.Entity.BloodReportEntity;
import com.example.healthAndFitness.Entity.CommonDiseaseEntity;
import com.example.healthAndFitness.Entity.DiseaseEntity;
import com.example.healthAndFitness.Entity.DrugEntity;
import com.example.healthAndFitness.JavaClass.InputFilter;
import com.example.healthAndFitness.Repo.BloodReportRepo;
import com.example.healthAndFitness.Repo.CommonDiseaseRepo;
import com.example.healthAndFitness.Repo.DiseaseRepo;
import com.example.healthAndFitness.Repo.DrugRepo;
import com.example.healthAndFitness.UploadFile.StorageService;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.jsoup.Jsoup;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class ForRestController {
    @Autowired
    DiseaseRepo diseaseRepo;
    @Autowired
    DrugRepo drugRepo;
    @Autowired
    BloodReportRepo bloodReportRepo;
    @Autowired
    CommonDiseaseRepo commonDiseaseRepo;

    private final StorageService storageService;
    @Autowired
    public ForRestController(StorageService storageService) {
        this.storageService = storageService;
    }

    //@PostMapping(value = "/getAnswer")
    public String GetResult(@RequestParam("obj") String obj)
    {
        try {
            List<String> ll = new ArrayList<>();
            int start=0;
            for(int i=0;i<obj.length();i++)
            {
                if(obj.charAt(i)==' ')
                {
                    String t = obj.substring(start,i);
                    ll.add(t);
                    start=i+1;
                }
            }
            ll.add(obj.substring(start));
            String obj1=ll.get(0);
            for (int i=1;i<ll.size();i++)
            {
                obj1 = obj1 +"+"+ ll.get(i);
            }
            System.out.println(obj1);
            String url = "https://www.google.com/search?q=" + obj1;
            Document doc = Jsoup.connect(url).get();

            Element ans = doc.getElementsByClass("aCOpRe").first();
            int i=0;
            while(ans.text().length()<=150 && i<ans.getAllElements().lastIndexOf("aCOpRe"))
            {
                ans=doc.getElementsByClass("aCOpRe").get(i);
                i++;
            }
            //String s = ans.text().substring(0,100);

           return ans.text();

        }
        catch (Exception e)
        {
            System.out.println("ERROR = "+e);
        }
        return "";
    }



    @PostMapping(value = "/getAnswer")
    public String AnswerFromDatabaseForDisease(@RequestParam("obj") String obj)
    {
        try {
            //for Common disease
            String mainString = InputFilter.InputFilter(obj);
            List<String> forCommon = new ArrayList<>();
            int lastSpace=mainString.lastIndexOf(' ');
            if(lastSpace==-1)
            {
                List<CommonDiseaseEntity> ce = commonDiseaseRepo.searchForGeneral(mainString);
                if (ce != null) {
                    forCommon.add(ce.get(0).getDiseaseName());
                    forCommon.add(ce.get(0).getDrugName());
                }
            }
            else
            {
                while (lastSpace != -1) {
                    mainString = mainString.replaceAll(" ", "%");
                    List<CommonDiseaseEntity> ce = commonDiseaseRepo.searchForGeneral(mainString);
                    if (ce.size()!=0) {
                        forCommon.add(ce.get(0).getDiseaseName());
                        forCommon.add(ce.get(0).getDrugName());
                        break;
                    }
                    else {
                        mainString = mainString.substring(0, lastSpace);
                        lastSpace = mainString.lastIndexOf(' ');
                    }
                }
            }
            // end common disease

            // for disease
            mainString = InputFilter.InputFilter(obj);
            lastSpace=mainString.lastIndexOf(' ');
            String diseaseName;
            List<DiseaseEntity> resultantListDisease = null;
            if(lastSpace==-1)
            {
                resultantListDisease = diseaseRepo.searchDisease(mainString);
                Collections.shuffle(resultantListDisease);
                if(resultantListDisease.size()!=0)
                {
                    diseaseName = resultantListDisease.get(0).getDiseaseName();
                }
                else
                {
                    return "Invalid input";
                }
            }
            else {
                while (lastSpace != -1) {
                    mainString = mainString.replaceAll(" ", "%");
                    resultantListDisease = diseaseRepo.searchDisease(mainString);
                    if (resultantListDisease.size() > 0) {
                        break;
                    }
                    else {
                        mainString = mainString.substring(0, lastSpace);
                        lastSpace = mainString.lastIndexOf(' ');
                    }
                }

                if (resultantListDisease.size() == 0) {
                    resultantListDisease = diseaseRepo.searchDisease(mainString);
                    if (resultantListDisease.size() == 0) {
                        return "Invalid input";
                    }
                }
                Collections.shuffle(resultantListDisease);
                diseaseName = resultantListDisease.get(0).getDiseaseName();
            }
            String ans = "You may have "+diseaseName + " disease";

            // end drug
            //****For Disease ****
            mainString = InputFilter.InputFilter(obj);
            lastSpace=mainString.lastIndexOf(' ');
            List<DrugEntity>resultantListDrug = null;
            String drugName = null;
            if(lastSpace==-1)
            {
                resultantListDrug = drugRepo.searchDrug(mainString);
                Collections.shuffle(resultantListDrug);
                if(resultantListDrug.size()!=0)
                {
                    drugName = resultantListDrug.get(0).getDrugName();
                }
            }
            else {
                while (lastSpace != -1) {
                    mainString = mainString.replaceAll(" ", "%");
                    resultantListDrug = drugRepo.searchDrug(mainString);
                    Collections.shuffle(resultantListDrug);
                    if (resultantListDrug.size() > 0) {
                        break;
                    } else {
                        mainString = mainString.substring(0, lastSpace);
                        lastSpace = mainString.lastIndexOf(' ');
                    }
                }
                Collections.shuffle(resultantListDrug);
                if (resultantListDrug.size() == 0) {
                    resultantListDrug = drugRepo.searchDrug(mainString);
                    if (resultantListDrug.size() != 0) {
                        drugName = resultantListDrug.get(0).getDrugName();
                    }
                } else {
                    drugName = resultantListDrug.get(0).getDrugName();
                }
            }
            String commonTreatment="";
            if(forCommon.size()!=0)
            {
                commonTreatment += "If you have normal "+forCommon.get(0);
                commonTreatment += " then we suggest you to take "+forCommon.get(1);
                commonTreatment += " Otherwise <br>";
            }
            String result = "You may be have "+diseaseName + " disease <br>";
            if(drugName!=null)
            {
                result+= "We suggest you to take "+drugName;
            }
            else
            {
                result+= "Please contact to doctor, we unable to suggest best drug for you !!";
            }
            if(commonTreatment=="")
            {
                return result;
            }
            else
            {
                return commonTreatment + result;
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return "Invalid Input";
        }
    }
    @PostMapping("/suggestion")
    public List<String> Suggestions(@RequestParam("obj") String obj) throws IOException
    {
        String url = "http://suggestqueries.google.com/complete/search?output=chrome&q=";
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
        return ll;
    }
    @PostMapping("/uploadReport")
    public String FileUpload(@RequestParam("file") MultipartFile f)
    {
        try {
            int n =1254845+ new Random().nextInt(254621);
            String fname = storageService.store(f, String.valueOf(n));
            return fname;
        }
        catch (Exception e)
        {
            System.out.println("ERROR : " + e.getMessage());
            return "";
        }
    }
    @PostMapping(value = "/getTextFromImg")
    public List<String> getTextFromImg(@RequestParam("component") String path) throws TesseractException {
        File fn = new File(path);
        ITesseract instance = new Tesseract();
        String imgText = instance.doOCR(fn);
        imgText = imgText.toLowerCase();
        List<BloodReportEntity> ll = bloodReportRepo.findAll();
        String result="";
//        System.out.println(imgText);
        List<String> res = new ArrayList<>();
        for(BloodReportEntity be : ll)
        {
            try {
                String obj = be.getComponentName().toLowerCase();
//                System.out.println(obj);
                String regexPattern = "\\s*\\b"+obj+"\\b\\s*[0-9]*\\s*\\.*[0-9]*\\s*\\%*";
                Pattern p = Pattern.compile((regexPattern));
                Matcher m = p.matcher(imgText);
                if (m.find()) {
                    result = m.group() + " , Normal range : " + be.getComponentRange();
                    res.add(result.toUpperCase());
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        return res;
    }

}
