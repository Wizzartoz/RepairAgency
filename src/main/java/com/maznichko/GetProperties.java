package com.maznichko;

import java.io.*;
import java.util.Properties;

public class GetProperties {
    public static Properties getProp(String path){
        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream(path);
            property.load(fis);
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
        return property;
    }
}
