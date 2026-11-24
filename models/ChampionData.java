package models;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.Filereader;
import utils.exceptions.ArenaException;

public class ChampionData{
    private List<Champion> champions;

    public ChampionData(){
        this.champions = Filereader.readChampionData();
        Collections.sort(this.champions);
    }

    public void addChampion(String name) throws ArenaException{
        if(name.isBlank()) throw new ArenaException("Name can't be blank");
        
        Champion existing = findChampion(name);
        if(existing != null) throw new ArenaException("Champion already exists");
    
        Champion champion = new Champion(name, 0);

        this.champions.add(champion);
        Collections.sort(this.champions);

        System.out.printf("Champion added: %s\n", champion.getName());
    }

    public void removeChampion(String name) throws ArenaException{
        Champion existing = findChampion(name);
        if(existing == null) throw new ArenaException("Champion not found");
        
        this.champions.remove(existing);

        System.out.printf("Champion removed: %s\n", existing.getName());
    }

    public void setNickname(String name, String nickname) throws ArenaException{
        Champion existing = findChampion(name);
        if(existing == null) throw new ArenaException("Champion not found");
        if(nickname.isBlank()) throw new ArenaException("Nickname can't be blank");
        if(findChampion(nickname) != null) throw new ArenaException("Nickname already exists");
        
        existing.setNickname(nickname);

        System.out.printf("%s's nickname changed to %s.\n", existing.getName(), nickname);
    }

    public void clearNickname(String name) throws ArenaException{
        Champion existing = findChampion(name);
        if(existing == null) throw new ArenaException("Champion not found");

        existing.setNickname(name);

        System.out.printf("%s's nickname cleared\n", existing.getName());
    }

    public void setLvl(String name, int lvl) throws ArenaException{
        Champion existing = findChampion(name);
        if(existing == null) throw new ArenaException("Champion not found");
        if(lvl < 0 || lvl > 3) throw new ArenaException("Level must be between 0-3");

        existing.setLvl(lvl);
        Collections.sort(this.champions);

        System.out.printf("%s's level set to %s\n", existing.getName(), lvl);

    }

    public Champion findChampion(String name){
        for(Champion c : this.champions){
            if(name.equalsIgnoreCase(c.getName()) || name.equalsIgnoreCase(c.getNickname())){
                return c;
            }
        }
        
        return null;
    }

    public List<Champion> findChampions(String ... input){
        ArrayList<Champion> champions = new ArrayList<>();
        for(String name : input){
            Champion champion = findChampion(name);
            if(champion != null){
                champions.add(champion);
            }
        }
        
        Collections.sort(champions);
        return champions;
    }

    public List<Champion> findByLevel(int lvl){
        ArrayList<Champion> filtered = new ArrayList<>();

        for(Champion c : this.champions){
            if(c.getLvl() == lvl){
                filtered.add(c);
            }
        }

        return filtered;
    }

    public void sort(){
        Collections.sort(this.champions);
    }

    public List<Champion> getChampions(){
        return this.champions;
    }
}
