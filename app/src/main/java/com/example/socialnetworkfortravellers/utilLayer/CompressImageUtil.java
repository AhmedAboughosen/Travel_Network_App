package com.example.socialnetworkfortravellers.utilLayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.socialnetworkfortravellers.utilLayer.interfaces.ICompressImageUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import id.zelory.compressor.Compressor;


/**
 * responsibility of this class is compress Images
 */
public class CompressImageUtil implements ICompressImageUtil {

    private Context mContext;

    public CompressImageUtil(Context context) {
        this.mContext = context;
    }

    /**
     * algorithm used to compress Image before send Image to server
     *
     * @param Image_Url
     * @return
     */
    private Bitmap compressImageFile(Uri Image_Url) {
        File file = new File(Objects.requireNonNull(Image_Url.getPath()));
        try {
            return new Compressor(mContext)
                    .setMaxWidth(250)
                    .setMaxHeight(250)
                    .setQuality(50)
                    .compressToBitmap(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * algorithm used to compress Image before send Image to server
     *
     * @param ImageUri
     * @return byte[]
     */
    @Override
    public byte[] compressImage(Uri ImageUri) {
        byte[] data;
        try {
            Bitmap bitmap_Image = compressImageFile(ImageUri);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            assert bitmap_Image != null;

            bitmap_Image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            data = baos.toByteArray();
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
