package com.company;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.*;

public class Read_Write_CSV {

    private static final HashSet<String> allCities = new HashSet<>();

    public static void main(String[] args) {

        String path = "src/com/company/Candidates.csv";
        List<Candidate> candidates = readCandidatesFromCSV(path);

        candidates.sort(Comparator.comparing(Candidate::getCity));

        try {
            separateCities(candidates);
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
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


    private static void separateCities(List<Candidate> candidates) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(allCities.size());

        for (String City : allCities) {
            Runnable runnableTask = () -> writeToCSV(candidates, City);
            executor.execute(runnableTask);
        }

        executor.shutdown();
    }

    private static void writeToCSV(List<Candidate> candidates, String cityName) {

        try (FileWriter csvWriter = new FileWriter(cityName + "-data.csv")) {
            csvWriter.append("id, Name, Gender, Age, City, DOB");
            csvWriter.append("\n");

            for (Candidate c : candidates) {
                if (c.getCity().equals(cityName)) {
                    csvWriter.append(c.toString());
                    csvWriter.append("\n");
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}


