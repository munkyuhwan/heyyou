package com.heyyou.WebviewPack.EtcUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;

public class ImageRotation {

    public ImageRotation() {

    }

    private int getExifOrientation(String filePath) {
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        return 90;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        return 180;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        return 270;
                }
            }
        }

        return 0;
    }

    private Bitmap getRotatedBitmap(Bitmap bitmap, int degree) {
        if (degree != 0 && bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.setRotate(degree, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

            try {
                Bitmap tmpBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                if (bitmap != tmpBitmap) {
                    bitmap.recycle();
                    bitmap = tmpBitmap;
                }
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    public Uri doRotaion(String filePath, Context context) {
        int degree = getExifOrientation(filePath);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        bitmap = getRotatedBitmap(bitmap, degree);
        return saveImage(bitmap, context, "tmp_img");
    }

    private Uri saveImage(Bitmap bitmap, Context context, String image_name) {
        long time= System.currentTimeMillis();

        String savedImageURL = MediaStore.Images.Media.insertImage(
                context.getContentResolver(),
                bitmap,
                "pgba_"+String.valueOf(time),
                "이미지 업로드"
        );

        Uri savedImageURI = Uri.parse(savedImageURL);

        return savedImageURI;

    }

}
