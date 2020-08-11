package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.forgetAccountActivity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;

import butterknife.BindView;

public class CheckEmailActivity extends BaseActivity {


    public static final String EMAIL = "Email";
    @BindView(R.id.text2)
    TextView email_Text;


    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email);

        try {
            initView();

            Bundle bundle = getIntent().getExtras();
            assert bundle != null;
            String email = bundle.getString(EMAIL);


            email_Text.setText("We've sent an email to " + email + ".\n Click the link in the email to reset password");


            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            Drawable navIcon = mToolbar.getNavigationIcon();
            navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }


}
