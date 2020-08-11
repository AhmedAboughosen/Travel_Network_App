package com.example.socialnetworkfortravellers.utilLayer;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ConvertTimeUtil {


    public static String toTimeStamp(String currentdate) {
        try {

            long now = System.currentTimeMillis(); // See note below
            long then = Long.parseLong(currentdate);
            Timestamp stamp = new Timestamp(then);


            long minutes = TimeUnit.MILLISECONDS.toMinutes(now - then);
            long hours = TimeUnit.MILLISECONDS.toHours(now - then);
            long days = TimeUnit.MILLISECONDS.toDays(now - then);
            Date date = new Date(stamp.getTime());


            String current_date = (minutes <= 60) ? minutes + " mins" : (hours <= 60) ? hours + " hr" : (days <= 7) ? days + " day" : (days <= 35) ? (days / 7) + " wk" : date.getTime() + "";

            return current_date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long toMinutes(String currentdate) {
        try {

            long now = System.currentTimeMillis(); // See note below
            long then = Long.parseLong(currentdate);


            long minutes = TimeUnit.MILLISECONDS.toMinutes(now - then);

            return minutes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long toTimeStampToSeconds(String currentdate) {
        try {

            long now = System.currentTimeMillis(); // See note below
            long then = Long.parseLong(currentdate);
            Timestamp stamp = new Timestamp(then);


            long seconds = TimeUnit.MILLISECONDS.toSeconds(now - then);
            return seconds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String fromTimestampToMonth(String currentdate) {
        try {
            // timestamp to Date
            long timestamp = Long.parseLong(currentdate); //Example -> in ms
            Date d = new Date(timestamp);


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            String dateString = format.format(d);

            SimpleDateFormat month_date = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);

            Date date = format.parse(dateString);

            assert date != null;
            String month_name = month_date.format(date);

            return month_name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
