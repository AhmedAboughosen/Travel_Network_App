package com.example.socialnetworkfortravellers.utilLayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.palette.graphics.Palette;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IFlexibleColorBack;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class FlexibleColorUtli {


    /**
     * set flexible color for background for Any view
     *
     * @param Image
     * @param flexible_color
     */
    public static void setFlexible_color(String Image, ViewGroup flexible_color, Context context, IFlexibleColorBack... flexibleColorBacks) {

        try {
            int imageDimension =
                    (int) context.getResources().getDimension(R.dimen.image_size);

            Picasso.get()
                    .load(Image)
                    .resize(imageDimension, imageDimension)
                    .centerCrop()
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            try {
                                Palette.from(bitmap)
                                        .generate(palette -> {
                                            try {
                                                assert palette != null;
                                                Palette.Swatch textSwatch = palette.getDarkMutedSwatch();
                                                if (textSwatch != null) {
                                                    flexible_color.setBackgroundColor(textSwatch.getRgb());
                                                    if (flexibleColorBacks.length >= 1) {
                                                        flexibleColorBacks[0].setRgb(textSwatch.getRgb());
                                                    }
                                                }

                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        });
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                Toast.makeText(context, "Something Wrong" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }


                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
