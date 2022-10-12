/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Dell
 */
public class DateTimeHelper {
    public static LocalDate getLocalDate(String date){
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy/MM/dd"); 
        return LocalDate.parse(date, formatter);
    }
    public static Date getDate(String str){
        Date date=Date.valueOf(str);
        return date;
    }
    public static Date getSqlDate(LocalDate localDate){
        return Date.valueOf(localDate);
    }
}
