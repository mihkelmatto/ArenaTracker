package models;

public class Champion implements Comparable<Champion>{
    private String name;
    private String nickname;
    private int lvl;

    public Champion(String name, int lvl){
        this(name, name, lvl);
    }

    public Champion(String name, String nickname, int lvl){
        this.name = name;
        this.nickname = nickname;
        this.lvl = lvl;
    }

    /*
        Levels desc -> names asc
    */
   
    @Override
    public int compareTo(Champion to){
        int result = Integer.compare(to.lvl, this.lvl);
        
        if(result == 0){
            result = this.name.compareToIgnoreCase(to.name);
        }

        return result;
    }

    @Override
    public String toString(){
        return String.format("%-16s| lvl %s", this.name, this.lvl);
    }

    /*
        Setters
    */

    public void setName(String name){
        this.name = name;
    }

    public void setNickname(String nick){
        this.nickname = nick;
    }

    public void setLvl(int lvl){
        this.lvl = lvl;
    }

    /*
        Getters
    */
    
    public String getName(){
        return this.name;
    }

    public String getNickname(){
        return this.nickname;
    }

    public int getLvl(){
        return this.lvl;
    }
}