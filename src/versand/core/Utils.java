package versand.core;

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

    public static String joinArray(char joinChar, String[] arr, int start, int amount) {
        StringBuilder result = new StringBuilder(arr[start]);
        int len = Math.min(start + amount, arr.length-1);
        for(int i = start+1; i <= len; i++) {
            result.append(joinChar).append(arr[i]);
        }
        return result.toString();
    }

}
