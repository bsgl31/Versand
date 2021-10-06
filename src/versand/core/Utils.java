package versand.core;

import com.google.gson.JsonPrimitive;

import javax.swing.*;
import java.time.LocalDate;

public class Utils {

    public static void message(String msg) {
        new Thread(() -> JOptionPane.showMessageDialog(null, msg)).start();
    }

    public static String localDateToString(LocalDate localDate) {
        return localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth();
    }

    public static LocalDate localDateFromString(String s) {
        String[] split = s.split("-");
        return LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

}
