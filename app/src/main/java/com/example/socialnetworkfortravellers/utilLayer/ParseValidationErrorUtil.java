package com.example.socialnetworkfortravellers.utilLayer;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public class ParseValidationErrorUtil {
    /**
     * this method used to parse ValidationError to list of errors.
     *
     * @param errors
     * @return
     */
    public static void parseValidationError(List<ValidationError> errors, Context context) {

        try {
            for (int i = 0; i < errors.size(); i++) {
                if (errors.get(i).getView() instanceof EditText) {
                    EditText view;
                    view = (EditText) errors.get(i).getView();
                    view.setError(errors.get(i).getCollatedErrorMessage(context));
                } else {
                    Toast.makeText(context, errors.get(i).getCollatedErrorMessage(context), Toast.LENGTH_SHORT).show();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
