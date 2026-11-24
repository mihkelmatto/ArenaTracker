package utils.exceptions;
public class ArenaException extends Exception{
    private final String message;

    public ArenaException(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
