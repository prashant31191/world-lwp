package com.clickygame.utils;

import com.clickygame.db.DLocationModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prashant.patel on 10/31/2017.
 */
public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public ArrayList<DLocationModel> read() throws Exception {
        ArrayList<DLocationModel> resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
         //   int i=0;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                DLocationModel dLocationModel = new DLocationModel();
                dLocationModel.Image_URL = row[1];
                resultList.add(dLocationModel);
              //  i++;
            }
        } finally {
            try {
                inputStream.close();
            }
            catch (Exception e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return resultList;
    }
}