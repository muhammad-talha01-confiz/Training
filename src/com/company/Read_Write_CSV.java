package com.company;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

public class Read_Write_CSV implements Runnable{

    Thread myThread;
    String threadName;

    Read_Write_CSV(String name){
        threadName = name;
    }

    public void run() {}

    public void start() {
        System.out.println("Thread Started");
        if (myThread == null) {
            myThread = new Thread(this, threadName);
            myThread.start();
        }
    }

    private static final HashSet<String> allCities = new HashSet<>();

    public static void main(String[] args) {

        String path = "src/com/company/Candidates.csv";
        List<Candidate> candidates = readCandidatesFromCSV(path);

        candidates.sort(Comparator.comparing(Candidate::getCity));

        separateCities(candidates);
    }

    private static List<Candidate> readCandidatesFromCSV(String filePath) {
        List<Candidate> candidates= new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){

            br.readLine();
            String line = br.readLine();

            while (line != null) {
                String[] data = line.split(",");
                allCities.add(data[4]);
                Candidate c = createCandidate(data);
                candidates.add(c);

                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error! File not found.");
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ParseException pe) {
            pe.getCause();
        }

        return candidates;
    }

    private static Candidate createCandidate(String[] dataFromCSVLine) throws ParseException {
        // CSV Format: ID, Name, Gender, Age, City, DOB
        int id = Integer.parseInt(dataFromCSVLine[0]);
        String name = dataFromCSVLine[1];
        char gender = dataFromCSVLine[2].charAt(0);
        int age = Integer.parseInt(dataFromCSVLine[3]);
        String city = dataFromCSVLine[4];
        String dateString = dataFromCSVLine[5];
        Date date = new SimpleDateFormat("dd/MM/yy").parse(dateString);

        return new Candidate(id, name, gender, age, city, date);
    }


    private static void separateCities(List<Candidate> candidates)
    {
        for(String cityName : allCities){
            writeToCSV(candidates, cityName);
        }
    }

    private static void writeToCSV(List<Candidate> candidates, String cityName) {

        List<Candidate> written = new ArrayList<>();

        try (FileWriter csvWriter = new FileWriter(cityName + "-data.csv")) {
            csvWriter.append("id, Name, Gender, Age, City, DOB");
            csvWriter.append("\n");

            for (Candidate c : candidates) {
                if (c.getCity().equals(cityName)) {
                    csvWriter.append(c.toString());
                    csvWriter.append("\n");
                    written.add(c);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        candidates.removeAll(written);
    }
}


