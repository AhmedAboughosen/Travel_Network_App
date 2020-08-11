package com.example.socialnetworkfortravellers.ViewLayer.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.utilLayer.FlexibleColorUtli;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.Objects;

public class DisplayImageDialog extends BaseDialog {

    private Context context;


    public DisplayImageDialog(Context context) {
        super(context);
        this.context = context;
    }


    /**
     * when user click on any IMAGE , system should display Dialog contain IMAGE.
     *
     * @param ImageURL
     */
    public void ImageDialog(String ImageURL) {
        try {
            //create Dialog
            final Dialog mdialog = dialog(R.layout.dailog_image_post);
            //create instance

            mdialog.show();

            mdialog.setCancelable(true);
            PhotoView imageView = mdialog.findViewById(R.id.Image_Post);
            ImageView close_dialog = mdialog.findViewById(R.id.close_dialog);
            ImageView downloadImage = mdialog.findViewById(R.id.down_load_image);

            /*
            set views
             */
            Glide.with(Objects.requireNonNull(Objects.requireNonNull(context))).load(ImageURL).into(imageView);
            RelativeLayout flexible_color = mdialog.findViewById(R.id.flexible_color);

            FlexibleColorUtli.setFlexible_color(ImageURL, flexible_color, context);

            close_dialog.setOnClickListener(view -> mdialog.dismiss());


           /* downloadImage.setOnClickListener(view -> {
                DownLoadImageServices downLoadImageServices = new DownLoadImageServices(context);
                downLoadImageServices.downLoadImage(ImageURL);
            });*/


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
