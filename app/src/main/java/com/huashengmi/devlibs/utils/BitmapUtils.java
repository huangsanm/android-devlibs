package com.huashengmi.devlibs.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: huangsanm@foxmail.com
 * Date: 2016-03-16
 * Time: 15:50
 */
public class BitmapUtils {

    /**
     * 保存bitmap，并返回路径
     * @param bitmap
     * @return 路径
     */
    public static String saveBitmapToPath(Bitmap bitmap) {
        File f = new File(SDCardUtils.getImageDir()
                + System.currentTimeMillis() + ".jpg");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getAbsolutePath();
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    public static ByteArrayOutputStream comppressImage2(String path) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            // options.inJustDecodeBounds = true;
            Bitmap src = BitmapFactory.decodeFile(path, options);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            src.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int optionsnum = 100;
            int times = 0;
            Globals.log("==begin-zip-len:" + baos.size());
            while (baos.toByteArray().length / 1024 > 200 && times < 10) { // 循环判断如果压缩后图片是否大于300kb,大于继续压缩
                baos.reset();// 重置baos即清空baos
                src.compress(Bitmap.CompressFormat.JPEG, optionsnum, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                optionsnum -= 10;// 每次都减少10
                times++;
            }
            if (times >= 10) {
                throw new RuntimeException("压缩图片失败");
            }
            Globals.log("==end-zip-len:" + baos.size());
            return baos;
			/*
			 * ByteArrayInputStream isBm = new
			 * ByteArrayInputStream();//把压缩后的数据baos存放到ByteArrayInputStream中
			 * return BitmapFactory.decodeStream(isBm, null,
			 * null);//把ByteArrayInputStream数据生成图片
			 *//*
				 * FileOutputStream fos = new FileOutputStream(newpath);
				 * bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				 * fos.flush(); fos.close();
				 */
        } catch (Exception e) {
            throw new RuntimeException("压缩图片出错", e);
        }
    }

    public static String compressImage(String path, String newPath) {
        Bitmap newBitmap = compressImage(path, 1024);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(newPath);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        recycle(newBitmap);
        return newPath;
    }

    public static Bitmap compressImage(String path, int maxSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int inSampleSize = 1;
        if (options.outWidth > maxSize || options.outHeight > maxSize) {
            int widthScale = (int) Math.ceil(options.outWidth * 1.0 / maxSize);
            int heightScale = (int) Math
                    .ceil(options.outHeight * 1.0 / maxSize);
            inSampleSize = Math.max(widthScale, heightScale);
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int newW = w;
        int newH = h;
        if (w > maxSize || h > maxSize) {
            if (w > h) {
                newW = maxSize;
                newH = (int) (newW * h * 1.0 / w);
            } else {
                newH = maxSize;
                newW = (int) (newH * w * 1.0 / h);
            }
        }
        Bitmap bm = Bitmap.createScaledBitmap(bitmap, newW, newH, false);

        return reviewPicRotate(bm,path);
		/*
		 * FileOutputStream outputStream = null; try { outputStream = new
		 * FileOutputStream(newPath);
		 * newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream); }
		 * catch (FileNotFoundException e) { e.printStackTrace(); } finally {
		 * try { if (outputStream != null) { outputStream.close(); } } catch
		 * (IOException e) { e.printStackTrace(); }
		 *
		 * } recycle(newBitmap); recycle(bitmap); return newPath;
		 */
    }


    public static Bitmap comImage(String path, int maxSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int inSampleSize = 1;
        if (options.outWidth > maxSize ) {
            int height = options.outHeight * maxSize / options.outWidth;
            options.outWidth = maxSize;
            options.outHeight = height;
            inSampleSize= options.outWidth / maxSize;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int newW = w;
        int newH = h;
        if (w > maxSize ) {
                newW = maxSize;
        }
        Bitmap bm = Bitmap.createScaledBitmap(bitmap, newW, newH, false);
      return   reviewPicRotate(bm,path);



    }
    /**
     * 回收垃圾 recycle
     *
     * @throws
     */
    public static void recycle(Bitmap bitmap) {
        // 先判断是否已经回收
        if (bitmap != null && !bitmap.isRecycled()) {
            // 回收并且置为null
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }

    /**
     * 获取图片文件的信息，是否旋转了90度，如果是则反转
     * @param bitmap 需要旋转的图片
     * @param path   图片的路径
     */
    public static Bitmap reviewPicRotate(Bitmap bitmap,String path){
        int degree = getPicRotate(path);
        if(degree!=0){
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(degree); // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,m, true);// 从新生成图片
        }
        return bitmap;
    }

    /**
     * 读取图片文件旋转的角度
     * @param path 图片绝对路径
     * @return 图片旋转的角度
     */
    public static int getPicRotate(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

}
