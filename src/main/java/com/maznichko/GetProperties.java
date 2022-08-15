package com.maznichko;

import java.io.*;
import java.util.Properties;
@Deprecated
public class GetProperties {
    public static Properties getProp(String path){
        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream(path);
            property.load(fis);
        } catch (IOException e) {
            System.err.println("File didn't find");
        }
        return property;
    }
}
