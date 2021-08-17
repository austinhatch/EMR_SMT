package Solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

public class NameGenerator {
    
    public EnumeratedDistribution<String> male_first;
    public EnumeratedDistribution<String> female_first;
    public EnumeratedDistribution<String> surnames;
    
    public NameGenerator() {
        this.createDistributions();
    }
    
    public void createDistributions() {
        Scanner sc;
        try {
            sc = new Scanner(new File("src/NameDatabases/FirstNames.csv"));
            int male_total = 0;
            int female_total=0;
            ArrayList<Pair<String,Double>> male_freq = new ArrayList<Pair<String,Double>>();
            ArrayList<Pair<String,Double>> female_freq = new ArrayList<Pair<String,Double>>();
    
            
            ArrayList<String[]> rows = new ArrayList<String[]>();
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] line_contents = line.split(",");
                rows.add(line_contents);
            }          
            sc.close();
            rows.remove(0);
            for(String[] row : rows) {
                String gender = row[2];
                Integer count = Integer.parseInt(row[1]);
                if(gender.equals("F")) {
                    female_total += count;
                }
                else {
                    male_total += count;
                }
            }
            for(String[] row : rows) {
                String gender = row[2];
                Integer count = Integer.parseInt(row[1]);
                String name = row[0];
                if(gender.equals("F")) {
                    double freq = (double) count/ female_total;
                    Pair<String, Double> pair = new Pair<String,Double>(name,freq);
                    female_freq.add(pair);
    
                }
                else {
                    double freq = (double) count/ male_total;
                    Pair<String, Double> pair = new Pair<String,Double>(name,freq);
                    male_freq.add(pair);
                }
            }
            male_first = new EnumeratedDistribution<String>(male_freq);
            female_first = new EnumeratedDistribution<String>(female_freq);
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
                
        
        //Read in Surnames
        
        try {
            Scanner sc2 = new Scanner(new File("src/NameDatabases/Surnames.csv"));
            ArrayList<Pair<String, Double>> surname_freq = new ArrayList<Pair<String, Double>>();
            int surname_total = 0;
            
            ArrayList<String[]> rows2 = new ArrayList<String[]>();
            while(sc2.hasNextLine()) {
                String line = sc2.nextLine();
                String[] line_contents = line.split(",");
                rows2.add(line_contents);
            }          
            sc2.close();
            rows2.remove(0);
            
            for(String[] row : rows2) {
                surname_total += Integer.parseInt(row[1]);
            }
            for(String[] row : rows2) {
                String name = row[0];
                int count = Integer.parseInt(row[1]);
                Double freq = (double) count/ surname_total;
                surname_freq.add(new Pair<String, Double>(name, freq));
            }
            
            surnames = new EnumeratedDistribution<String>(surname_freq);
        }
        catch(IOException ioe) {
            
        }
    }
    
    
    
    
    public String getGeneratedMaleName() {
        String name = male_first.sample() + " "+ surnames.sample();
        return name.toUpperCase();   
        
    }
    
    public String getGeneratedFemaleName() {
        String name = female_first.sample() + " "+ surnames.sample();
        return name.toUpperCase();   
    }
    

}
