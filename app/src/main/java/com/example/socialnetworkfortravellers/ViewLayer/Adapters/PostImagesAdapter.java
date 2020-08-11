package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.DisplayImageDialog;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PostImagesAdapter extends PagerAdapter {


    public @BindView(R.id.postImage)
    ImageView postImage;
    public @BindView(R.id.number_of_image)
    TextView number_of_image;
    private ArrayList<String> paths;
    private Context context;
    private int count;
    private DisplayImageDialog mDisplayImageDialog;


    public PostImagesAdapter(Context context, ArrayList<String> paths) {
        this.paths = paths;
        this.context = context;
        mDisplayImageDialog = new DisplayImageDialog(context);
        count = this.paths.size();
    }


    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view;
        try {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            view = layoutInflater.inflate(R.layout.item_images_post_viewpager, container, false);
            ButterKnife.bind(this, view);
            Glide.with(Objects.requireNonNull(Objects.requireNonNull(context))).load(paths.get(position)).into(postImage);

            if (position == 0) {
                number_of_image.setText("1 / " + count);
            }
            postImage.setOnClickListener(view1 -> mDisplayImageDialog.ImageDialog(paths.get(position)));


            container.addView(view);
            return view;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        try {
            container.removeView((RelativeLayout) object);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
