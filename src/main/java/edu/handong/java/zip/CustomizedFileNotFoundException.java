package edu.handong.java.zip;

public class CustomizedFileNotFoundException extends Exception{
    public CustomizedFileNotFoundException(String message){
        super("The file path '" + message + "' does not exist. Please check your CLI argument!");
    }
}
