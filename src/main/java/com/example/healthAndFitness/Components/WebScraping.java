package com.example.healthAndFitness.Components;

import com.example.healthAndFitness.Entity.*;
import com.example.healthAndFitness.JavaClass.PrepareString;
import com.example.healthAndFitness.Repo.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class WebScraping {
    @Autowired
    DrugRepo drugRepo;
    @Autowired
    DiseaseRepo diseaseRepo;
    @Autowired
    NearTreatmentCenterRepo nearTreatmentCenterRepo;
    @Autowired
    DoctorRepo doctorRepo;
    @Autowired
    CommonDiseaseRepo commonDiseaseRepo;

    public void InsertDrug1() throws IOException {
        int i = 0;
        FileInputStream fis = new FileInputStream("C:\\Users\\vaibhav jangid\\Desktop\\new_drug.txt");
        Scanner sc = new Scanner(fis);

        while (sc.hasNextLine()) {
            i++;
            String finalString = sc.nextLine();
            //String encodedString = PrepareString.PrepareText(finalString);
            try {
                String url = "https://www.drugs.com/" + finalString + ".html";
                Document doc = Jsoup.connect(url).get();
                //Element ans = doc.getElementsByClass("contentBox").tagName("p").get(24);
                Element ans = doc.select("h2#what-to-avoid.r > p").first();

                System.out.println(ans.text());
            } catch (Exception e) {
                System.out.println("ERROR IN " + i + " : " + finalString);
            }
        }
        sc.close();
    }
    List<DrugEntity>drugEntities=new ArrayList();
    public void GetDrug2() throws IOException {
        CompletableFuture.runAsync(() -> {
            List<Integer> ll = new ArrayList<>();
            int i = 0;
            Scanner sc;
            try (FileInputStream fis = new FileInputStream("C:\\Users\\vaibhav jangid\\Desktop\\Spring Boot\\Fitness_Project_data\\drug_links.txt")) {
                sc = new Scanner(fis);
                while (sc.hasNextLine()) {
                    i++;
                    final int localI = i;
                    String url = sc.nextLine();
                    CompletableFuture.runAsync(() -> {
                        log.info("Fetching i:{} {}", localI, url);
                        try {
                            Document doc = Jsoup.connect(url).timeout(70000).get();
                            Element e = doc.getElementsByClass("monograph-content").first();
                            Element name = doc.getElementsByClass("drug-name").first();
                            DrugEntity de = DrugEntity.builder()
                                    .useOfDrug(e.text())
                                    .drugName(name.text())
                                    .build();
                            saveDrug(de);
                            log.info("Inserted i:{}", localI);
                        } catch (Exception e) {
                            log.error("ERROR in : " + localI + " : " + url);
                            ll.add(localI);
                        }
                    });
                }
                log.info("DONE");
                for (int x : ll) {
                    log.info("{}", x);
                }
            } catch (IOException e) {
                log.error("Error", e);
            }
        });
    }

    public synchronized void saveDrug(DrugEntity drugEntity) {
        drugEntities.add(drugEntity);
        if (drugEntities.size() > 100) {
            drugRepo.saveAll(drugEntities);
            drugEntities.clear();
        }
    }


    public void InsertDisease() throws IOException
    {
        int i=0;
        List<Integer> ll = new ArrayList<>();
        FileInputStream fis=new FileInputStream("C:\\Users\\vaibhav jangid\\Desktop\\Spring Boot\\Fitness_Project_data\\remain.txt");
        Scanner sc=new Scanner(fis);
        while (sc.hasNextLine())
        {
            i++;
            String txt = sc.nextLine().toLowerCase();
            try {
                int finalI = i;
                CompletableFuture.runAsync(() -> {
                    final int localI= finalI;
                    String url = "https://www.malacards.org/card/" + PrepareString.PrepareText(txt) + "?search=Blood%20diseases";
                    log.info("Fetching {} : {}",localI,url);
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(url).get();
                    } catch (IOException e) {
                        log.error("ERROR in : {}",localI);
                    }
                    Element eName = doc.select(".main-name b").first();
                    String name = eName.text();


                    Element eSummary = doc.getElementById("Summary");
                    String description = eSummary.text();


                    List<Element> eSymptoms = doc.select("#HPO_Symptoms-table tbody tr");
                    String symptoms = "";
                    for (Element e : eSymptoms) {
                        symptoms = symptoms + e.text() + "\n";
                    }


                    DiseaseEntity de = DiseaseEntity.builder()
                            .symptoms(symptoms)
                            .description(description)
                            .diseaseName(name)
                            .build();
                    diseaseRepo.save(de);
                    log.info("Inserted {}",finalI);

                });
            }
            catch (Exception e)
            {
                log.error("ERROR in : {}",i);
                ll.add(i);
            }
        }
        System.out.println("DONE");
        System.out.println("ERRORS in ");
        for(int x : ll)
        {
            System.out.println(x);
        }
    }



    public void DrugFilter1()
    {
        int i=0;
        List<DrugEntity> ll = drugRepo.findAll();
        for(DrugEntity d : ll)
        {
            i++;
            int x=i;
            CompletableFuture.runAsync(()->{
                final int v = x;
                try {
                    DrugEntity temp = d;
                    drugRepo.deleteByDrugName(d.getDrugName());
                    String s = temp.getDrugName();
                    s = s.replaceAll("%", "");
                    temp.setDrugName(s);
                    drugRepo.save(temp);
                    System.out.println("Done : " + v);
                }
                catch (Exception e)
                {
                    System.out.println("ERROR in : " + v);
                    System.out.println(e.getMessage());
                }
            });

        }
    }
    public void DrugFilter2() throws IOException
    {
        List<DrugEntity>ll = drugRepo.findAll();
        List<String> drugName = new ArrayList<>();
        for(DrugEntity d : ll)
        {
            drugName.add(d.getDrugName());
        }
        FileReader fr = new FileReader("C:\\Users\\vaibhav jangid\\Desktop\\Spring Boot\\Fitness_Project_data\\links.txt");
        Scanner sc = new Scanner(fr);
        String url="";
        int count=0;
        while(sc.hasNextLine())
        {
            try {
                url = sc.nextLine();
                Document doc = Jsoup.connect(url).timeout(5000).get();
                Element e = doc.getElementById("section-1");
                String name = doc.getElementsByClass("with-also").first().text();
                String desc = e.select("p").first().text();
                int ct=0;
                for(String s : drugName)
                {
                    if(s.equalsIgnoreCase(name))
                    {
                        ct=1;
                        System.out.println("not inserted : " + name);
                        break;
                    }
                }
                if(ct==0)
                {
                    count++;
                    DrugEntity de = DrugEntity.builder()
                            .drugName(name)
                            .useOfDrug(desc)
                            .build();
                    drugRepo.save(de);
                    System.out.println("Inserted : " + count);
                }
            }
            catch (Exception e)
            {
                System.out.println("Error in : " + url) ;
            }
        }
    }

    public void InsertCenter() throws IOException
    {
        //Jodhpur , Jaipur , kota
        Document doc = Jsoup.connect("https://www.medifee.com/hospitals-in-jaipur").timeout(5000).get();
        Element mainClass = doc.getElementsByClass("table table-bordered table-striped").first();
        List<Element> e = mainClass.select("tbody tr");
        int i=0;
        for(Element x : e)
        {
            i++;
            int temp=i;
            CompletableFuture.runAsync(()->{
                final int iFinal = temp;
                try {
                    String name = x.select("td a").text();
                    String no = x.getElementsByTag("td").get(1).text();
                    String address = x.select("td").text();
                    address = address.substring(address.indexOf(name)+name.length());
                    address = address.substring(0,address.indexOf(no));
                    NearTreatmentCenterEntity ne = NearTreatmentCenterEntity.builder()
                            .centerName(name)
                            .contactNumber(no)
                            .centerLocation(address)
                            .city("jaipur")
                            .build();
                    nearTreatmentCenterRepo.save(ne);
                    log.info("Inserted : {}",iFinal);
                }
                catch (Exception exception)
                {
                    log.error("Erron in :{}",iFinal);
                }
            });

        }
        log.info("Completed");
    }

    public void InsertDoctor() throws IOException
    {
        //Jaipur, Banglore , chandigarh , chennai
        int i=0;
        String base = "https://www.medifee.com";
        FileReader fr = new FileReader("C:\\Users\\vaibhav jangid\\Desktop\\dlink.txt");
        Scanner sc = new Scanner(fr);
        while(sc.hasNextLine())
        {
            try {
                String url = base + sc.nextLine();
                Document doc = Jsoup.connect(url).timeout(5000).get();
                List<Element> ll = doc.getElementsByClass("col-lg-9").first().getElementsByClass("col-lg-12");
                for (Element e : ll) {
                    i++;
                    try {
                        Element a = e.getElementsByClass("panel panel-default").first();
                        Element b = a.getElementsByClass("panel-body").first();
                        String name = b.select("h2").text();
                        String experience = b.select("p").text();
                        String review = b.text();
                        review = review.substring(review.indexOf(name) + name.length());
                        review = review.substring(review.indexOf(experience) + experience.length());
                        DoctorEntity de = DoctorEntity.builder()
                                .doctorName(name)
                                .doctorLocation("chennai")
                                .doctorExperience(experience)
                                .doctorDescription(review)
                                .build();
                        doctorRepo.save(de);
                        log.info("Inserted : {}", i);
                    } catch (Exception exception) {
                        log.error("Error in {}", i);
                    }
                }
            }
            catch (Exception exception)
            {
                log.error("Error in {}", i);
            }
        }
        log.info("Finished");
    }

    public void SetDiseaseType()
    {
        int i=0;
        List<DiseaseEntity> ll = diseaseRepo.findAll();
        for(DiseaseEntity d : ll)
        {
            i++;
            d.setDiseaseType("blood disease");
            diseaseRepo.save(d);
            System.out.println("Done : "+i);
        }
        System.out.println("Finish");
    }


    public void InsertMoreDisease() throws IOException
    {
            int i=0;
            Document doc = Jsoup.connect("https://www.malacards.org/categories/cancer_disease_list").timeout(5000).get();
            String dType = "cancer disease";
            Element e1 = doc.select(".search-results").first();
            String base="https://www.malacards.org";
            List<Element> e2 = e1.select("tbody tr");
            for(Element e3 : e2) {
                i++;
                Element e4 = null;
                try {
                    e4 = e3.getElementsByTag("td").get(3).getElementsByTag("a").first();
                }
                catch (Exception exception)
                {
                    continue;
                }
                String url="";
                url = base + e4.attr("href");
                final String fUrl=url;
                final int finalI = i;
                CompletableFuture.runAsync(()-> {
                    try {
                        Document doc2 = Jsoup.connect(fUrl).timeout(5000).get();

                        Element eName = doc2.select(".main-name b").first();
                        String name = eName.text();

                        Element eSummary = doc2.getElementById("Summary");
                        String description = eSummary.text();

                        List<Element> eSymptoms = doc2.select("#HPO_Symptoms-table tbody tr");
                        String symptoms = "";
                        for (Element e : eSymptoms) {
                            symptoms = symptoms + e.text() + "\n";
                        }
                        DiseaseEntity de = DiseaseEntity.builder()
                                .diseaseName(name)
                                .description(description)
                                .symptoms(symptoms)
                                .diseaseType(dType)
                                .build();
                        diseaseRepo.save(de);
                        log.info("Inserted : {} : {}",finalI,fUrl);
                    }
                    catch (Exception exception)
                    {
                        log.error("Error in : {}",finalI);
                        try {
                            FileWriter fw = new FileWriter("C:\\Users\\vaibhav jangid\\Desktop\\error.txt",true);
                            fw.write(fUrl+"\n");
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            log.info("WebScrapping Completed");
    }

    public void JodhpurDoctor() throws IOException
    {
        //page 1,2,3,4,5
        int i=0;
        Document doc = Jsoup.connect("https://www.practo.com/jodhpur/general-physician?page=5").timeout(10000).get();
        List<Element> e1 = doc.getElementsByClass("u-border-general--bottom");
        String base="https://www.practo.com";
        for(Element x : e1)
        {
            i++;
            try {
                Element e2 = x.getElementsByClass("listing-doctor-card").first();
                Element e3 = e2.select(".u-d-flex .info-section a").first();
                Document doc2 = Jsoup.connect(base + e3.attr("href")).timeout(10000).get();
                Element e4 = doc2.getElementsByClass("u-d-flex flex-jc-space-between").first();
                String name = e4.select("div h1").first().text();

                String expr = doc2.getElementsByClass("c-profile--qualification").first().text();

                Element e5 = doc2.getElementsByClass("pure-u-20-24").first();

                String desc = e5.getElementsByClass("c-profile__description").first().text();

                DoctorEntity d = DoctorEntity.builder()
                        .doctorName(name)
                        .doctorLocation("jodhpur")
                        .doctorDescription(desc)
                        .doctorExperience(expr)
                        .build();
                doctorRepo.save(d);
                log.info("Inserted : {} , name : {}",i,name);

            }
            catch (Exception exception)
            {
                log.error("Error : {}",i);
            }
        }
    }
    @PostConstruct
    public void InsertCommonDisease()
    {
        CommonDiseaseEntity ce = null;
        ce = CommonDiseaseEntity.builder()
                .diseaseName("headache")
                .drugName("ibuprofen / aspirin")
                .build();
        commonDiseaseRepo.save(ce);

        ce = CommonDiseaseEntity.builder()
                .diseaseName("fever and flu")
                .drugName("acetaminophen")
                .build();
        commonDiseaseRepo.save(ce);

        ce = CommonDiseaseEntity.builder()
                .diseaseName("cold and cough")
                .drugName("Nasal decongestants / Cough suppressants")
                .build();
        commonDiseaseRepo.save(ce);

        ce = CommonDiseaseEntity.builder()
                .diseaseName("Diarrhea")
                .drugName("Imodium A-D Imodium A-D")
                .build();
        commonDiseaseRepo.save(ce);

        ce = CommonDiseaseEntity.builder()
                .diseaseName("Mononucleosis")
                .drugName("acyclovir / desciclovir / ganciclovir")
                .build();
        commonDiseaseRepo.save(ce);

        ce = CommonDiseaseEntity.builder()
                .diseaseName("stomach pain")
                .drugName("Kaopectate / Pepto-Bismol")
                .build();
        commonDiseaseRepo.save(ce);

        ce = CommonDiseaseEntity.builder()
                .diseaseName("all type of pain")
                .drugName("acetaminophen (Tylenol)")
                .build();
        commonDiseaseRepo.save(ce);
    }

}
