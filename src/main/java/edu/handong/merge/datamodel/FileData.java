package edu.handong.merge.datamodel;

import org.apache.poi.ss.usermodel.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class FileData {
    private ArrayList<Object[]> data;

    public void saveRowData(InputStream is) {
        //ArrayList<String> values = new ArrayList<String>();
        data = new ArrayList<Object[]>();

        try (InputStream inp = is) {

            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            for(int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++){
                Row row = sheet.getRow(i);
                Object [] datas = new Object[row.getLastCellNum()];
                System.out.println(row.getLastCellNum());
                for(int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++){
                    Cell cell = row.getCell(j);
                    System.out.println(cell.getStringCellValue());
                    datas[j] = cell.getStringCellValue();
                }
                data.add(datas);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public ArrayList<Object[]> getData(){
        return data;
    }
}
