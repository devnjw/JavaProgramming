package edu.handong.merge.datamodel;

import org.apache.poi.ss.usermodel.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static org.apache.poi.ss.usermodel.CellType.STRING;

//Declare generic type
public class FileData<T> {
    private ArrayList<Object[]> data;
    private T id;

    public void saveRowData(T id, InputStream is) {
        //ArrayList<String> values = new ArrayList<String>();
        data = new ArrayList<Object[]>();
        this.id = id;

        try (InputStream inp = is) {

            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            for(int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++){
                Row row = sheet.getRow(i);
                Object [] datas = new Object[row.getLastCellNum() + 1];
                datas[0] = "000" + id;
                //System.out.println(row.getLastCellNum());
                for(int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++){
                    Cell cell = row.getCell(j);
                    if(cell != null && cell.getCellType()==STRING){
                        //System.out.println(cell.getStringCellValue());
                        datas[j+1] = cell.getStringCellValue();
                    }
                }
                data.add(datas);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Object[]> getData(){
        return data;
    }
}
