package com.example.socialnetworkfortravellers.utilLayer;

public class StringUtil {


    public static boolean isEmptyString(String str){


        try {
            if(str == null)
                return true;

            return  str.isEmpty() || str.replace(" " , "").length() == 0;
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return false;
    }


}
