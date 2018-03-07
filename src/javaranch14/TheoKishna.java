/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaranch14;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 *
 * @author Piet
 */
public class TheoKishna {

}

class DateFormatTest {

    public static void main(String[] args) {

        for (FormatStyle c : FormatStyle.values()) {
            System.out.println(c);
        }

        LocalDate date = LocalDate.of(2020, Month.JANUARY, 31);
        LocalTime time = LocalTime.of(11, 58, 59);
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        DateTimeFormatter shortF = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT);
        DateTimeFormatter longF = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM);

        System.out.println(shortF.format(dateTime));
        System.out.println(longF.format(dateTime));
    }//main
}//class
