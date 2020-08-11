package com.example.socialnetworkfortravellers.ViewLayer.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.ConfigurationSharedPref;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.facebook.FacebookSdk;

public class StartUpActivity extends BaseActivity {

    public static final int STARTUP_DELAY = 100;
    public static final int ANIM_ITEM_DURATION = 500;
    public static final int ITEM_DELAY = 100;
    private boolean isSetIntent = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            setTheme(R.style.AppTheme);
            FacebookSdk.sdkInitialize(this);

            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus) {
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }

    private void animate() {
        ImageView logoImageView = findViewById(R.id.img_logo);
        ViewGroup container = findViewById(R.id.container);

        ViewCompat.animate(logoImageView).translationY(-250).setStartDelay(STARTUP_DELAY).setDuration(ANIM_ITEM_DURATION).setInterpolator(new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            viewAnimator = ViewCompat.animate(v)
                    .translationY(50).alpha(1)
                    .setStartDelay((ITEM_DELAY * i) + 500)
                    .setDuration(1000).setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {

                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            if (isSetIntent) {
                                isSetIntent = false;
                                Class<?> className = ConfigurationSharedPref.getInstance().getStartUpActivity(getApplicationContext());
                                startActivity(new Intent(StartUpActivity.this, className));
                                finish();
                            }
                        }

                        @Override
                        public void onAnimationCancel(View view) {

                        }
                    });

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }

    }
}
