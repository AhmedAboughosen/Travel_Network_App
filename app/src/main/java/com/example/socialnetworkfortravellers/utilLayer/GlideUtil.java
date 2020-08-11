package com.example.socialnetworkfortravellers.utilLayer;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class GlideUtil {


    /**
     * load image into ImageView
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadImage(Context context, String path, ImageView imageView) {
        //Bind Image based on image path
        if (path != null && !path.isEmpty()) {
            Glide.with(Objects.requireNonNull(context))
                    .load(path)
                    .into(imageView);
        } else {
            Toast.makeText(context, "we can't display Image, please try later.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void loadImage(Context context, int path, ImageView imageView) {
        //Bind Image based on image path
        Glide.with(Objects.requireNonNull(context))
                .load(path)
                .into(imageView);
    }
}
