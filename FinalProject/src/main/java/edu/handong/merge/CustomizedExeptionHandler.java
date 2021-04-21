package edu.handong.merge;

public class CustomizedExeptionHandler extends Exception{
    public CustomizedExeptionHandler(String message){
        super("The file path '" + message + "' does not exist. Please check your CLI argument!");
    }

}