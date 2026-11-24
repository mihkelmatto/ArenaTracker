import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import models.Champion;
import models.ChampionData;
import utils.Filereader;
import utils.exceptions.ArenaException;

public class ArenaTracker{
    private ChampionData data;

    public ArenaTracker(){
        this.data = new ChampionData();        
    }
    public static void main(String[] args) {
        ArenaTracker tracker = new ArenaTracker();
        tracker.run();
    }

    public void run(){
        try(
            Scanner sc = new Scanner(System.in);
        ){
            showHelp();
            
            while(true){
                String line = sc.nextLine().strip().toLowerCase();

                if(line.isBlank()){
                    System.out.println("Enter a command");
                    continue;
                }

                String[] input = line.split(" ");

                for(int i = 0; i < input.length; i++){
                    input[i] = input[i].replace(",", " ");
                }

                System.out.println();
                processCmd(input);
            }

        }
        catch(Exception e){
            Filereader.save(data);
            e.printStackTrace();
        }
    }

    private void processCmd(String ... input){
        switch (input[0]) {
            case "h" -> showHelp();
            case "q" -> quit();

            case "set" -> set(input);
            case "nick" -> nick(input);

            case "show" -> show(input);
            case "showall" -> showAll(input);

            case "add" -> add(input);
            case "remove" -> remove(input);
            
            default -> System.out.println("ProcessCommand: Invalid command");
        }
    }

    private void showHelp(){
        System.out.println("""
        -----
        Enter a command (use comma "," as space within keywords):

        h                               show this message
        q                               save and exit the program
        
        set {championname} {lvl}        set a champion's level

        nick {championname} {nick}      set a champion's nickname (one per champion, can be used as a name for any command)
        nick {championname} clear       clear a champion's nickname
        nick {championname}             show a champion's nickname
        
        show {championname} {c2} ...    show the champion's level (supports multiple name args)

        showall                         show all champions
        showall {lvl}                   show all champions with the selected level              
 
        add {championname}              adds a new champion with the given level
        remove {championname}           removes a champion
        -----
        """);
    }

    private void quit(){
        Filereader.save(data);
        System.exit(0);
    }

    private void set(String ... input){
        try{
            if(input.length != 3) throw new ArenaException("Invalid command");

            String name = input[1];
            int lvl = Integer.valueOf(input[2]);

            data.setLvl(name, lvl);
        }
        catch(NumberFormatException e){
            System.out.println("Set: Level must be a number");
        }
        catch(ArenaException e){
            System.out.println("Set: " + e.getMessage());
        }
    }

    private void nick(String ... input){
        try{
            // show nickname
            if(input.length == 2){
                Champion champion = data.findChampion(input[1]);
                if(champion == null) throw new ArenaException("Champion not found");

                System.out.printf("%s's nickname is %s\n", champion.getName(), champion.getNickname());
                return;
            }

            if(input.length != 3) throw new ArenaException("Invalid command");
            
            String name = input[1];
            String nick = input[2];

            // clear nickname
            if(nick.equals("clear")){
                data.clearNickname(name);
                return;
            }

            // set nickname
            data.setNickname(name, nick);
        }
        catch(ArenaException e){
            System.out.println("Nick: " + e.getMessage());
        }
    }

    private void show(String ... input){
        try{
            if(input.length < 2) throw new ArenaException("Invalid command");
            List<Champion> champions = data.findChampions(Arrays.copyOfRange(input, 1, input.length));
            print(champions);
        }
        catch(ArenaException e){
            System.out.println("Show: " + e.getMessage());
        }
    }

    private void showAll(String ... input){
        try{
            if(input.length > 2) throw new ArenaException("Invalid command");
            if(input.length == 1){
                print(data.getChampions());
                return;
            }
        
            int lvl = Integer.valueOf(input[1]);

            if(lvl < 0 || lvl > 3) throw new ArenaException("Level must be between 0-3");

            print(data.findByLevel(lvl));
        }
        catch(ArenaException e){
            System.out.println(e.getMessage());
        }
        catch(NumberFormatException e){
            System.out.println("Invalid command");
        }
    }

    private void add(String ... input){
        try{
            if(input.length != 2) throw new ArenaException("Invalid command");
            String name = input[1];
            data.addChampion(name);
        }
        catch(ArenaException e){
            System.out.println("Add: " + e.getMessage());
        }
    }

    private void remove(String ... input){
        try{
            if(input.length != 2) throw new ArenaException("Invalid command");
            String name = input[1];
            data.removeChampion(name);
        }
        catch(ArenaException e){
            System.out.println("remove: " + e.getMessage());
        }
    }

    private void print(List<Champion> champions){
        int[] levelcount = new int[4];
        int total = 0;
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("-----\n");
        sb.append("Champion stats:\n");
        
        for(Champion c : champions){
            sb.append(c.toString() + "\n");
            levelcount[c.getLvl()]++;
            total++;
        }

        if(champions.size() == 0){
            sb.append("No matches\n");
        }

        sb.append(String.format("""

            levels:        0|   1|   2|   3|total
                        %4s|%4s|%4s|%4s|%5s
            -----

            """, levelcount[0], levelcount[1], levelcount[2], levelcount[3], total));

        System.out.println(sb.toString());
    }
}