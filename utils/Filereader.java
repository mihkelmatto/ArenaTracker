package utils;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import models.Champion;
import models.ChampionData;

public class Filereader {
    private static File dataPath = new File("data/data.csv");
    private static File dataTemplate = new File("data/dataTemplate.csv");

    public static List<Champion> readChampionData(){
        ArrayList<Champion> data = new ArrayList<>();

        File path = (dataPath.isFile() ? dataPath : dataTemplate);

        try(Scanner scanner = new Scanner(path)){
            int i = 0;

            while(scanner.hasNextLine()){
                String[] line = scanner.nextLine().strip().split(";");
                if(line.length == 1) continue;
                
                if(line.length > 3){
                    System.out.println(String.format("Invalid file format at line %s", i));
                    return data;
                }

                data.add(new Champion(line[0], line[1], Integer.valueOf(line[2])));
                i++;
            }

            Collections.sort(data);
            System.out.println("Data read successfully");
            return data;
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(1);
            
            return null;
        }
    }

    public static void save(ChampionData data){
        try(PrintWriter writer = new PrintWriter(dataPath)){
            for(Champion c : data.getChampions()){
                writer.println(String.format("%s;%s;%s", c.getName(), c.getNickname(), c.getLvl()));
            }
            System.out.println("Data saved successfully");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
