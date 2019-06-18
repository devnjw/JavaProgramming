package edu.handong.merge;

import java.util.ArrayList;
import java.util.Map;

class ThreadHandler extends Thread {
    private String path;
    ArrayList<Object[]> summary;
    ArrayList<Object[]> chartAndImage;
    private int id;

    public ThreadHandler(String path, int id){
        this.path = path;
        this.id = id;
    }

    public void run() {
        ZipReader zipReader = new ZipReader();
        zipReader.readFileInZip2(id, path);
        summary = zipReader.getFileDatas().get(0).getData();
        chartAndImage = zipReader.getFileDatas().get(1).getData();
    }

    public ArrayList<Object[]> getSummary() {
        return summary;
    }

    public ArrayList<Object[]> getChartAndImage() {
        return chartAndImage;
    }
}