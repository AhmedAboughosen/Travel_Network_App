package com.example.socialnetworkfortravellers.utilLayer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.socialnetworkfortravellers.R;

public class UpdateFragmentUtil {


    private static FragmentTransaction update(Fragment fragment, FragmentManager manager, int layout) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(layout, fragment);

        return ft;
    }


    //change Fragment
    public static void loadFragment(Fragment fragment, FragmentManager manager, int layout) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            ft.replace(layout, fragment);
          //  ft.addToBackStack(null);
            ft.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //change Fragment
    public static void loadFragment(Fragment fragment, int layout, FragmentManager fm) {
        try {
            // create main_logo FragmentTransaction to begin the transaction and replace the Fragment
            FragmentTransaction fragmentTransaction = update(fragment, fm, layout);
            // replace the FrameLayout with new Fragment
            fragmentTransaction.commit(); // save the changes
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
