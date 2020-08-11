package com.example.socialnetworkfortravellers.utilLayer;

/**
 * responsibility of this class is to check if string is empty or not.
 */
public class StringEmptyUtil {


    public static boolean isEmptyString(String str) {


        try {
            if (str == null)
                return true;

            return (str.isEmpty() || str.replace(" ", "").length() == 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }


}
