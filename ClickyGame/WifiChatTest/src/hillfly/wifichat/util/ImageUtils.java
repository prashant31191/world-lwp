package hillfly.wifichat.util;

import hillfly.wifichat.BaseApplication;
import hillfly.wifichat.R;
import hillfly.wifichat.R.drawable;
import hillfly.wifichat.activity.imagefactory.ImageFactoryActivity;
import hillfly.wifichat.activity.imagefactory.ImageFactoryFliter.FilterType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @fileName PhotoUtils.java
 * @description å›¾ç‰‡å·¥å…·ç±»
 */
public class ImageUtils {

    /** ç›¸å†Œçš„RequestCode **/
    public static final int INTENT_REQUEST_CODE_ALBUM = 0;
    /** ç›¸æœºçš„RequestCode **/
    public static final int INTENT_REQUEST_CODE_CAMERA = 1;
    /** è£�å‰ªç…§ç‰‡çš„RequestCode **/
    public static final int INTENT_REQUEST_CODE_CROP = 2;
    /** æ»¤é•œå›¾ç‰‡çš„RequestCode **/
    public static final int INTENT_REQUEST_CODE_FLITER = 3;

    /**
     * é€šè¿‡æ‰‹æœºç›¸å†ŒèŽ·å�–å›¾ç‰‡
     * 
     * @param activity
     */
    public static void selectPhoto(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_ALBUM);
    }

    /**
     * é€šè¿‡æ‰‹æœºç…§ç›¸èŽ·å�–å›¾ç‰‡
     * 
     * @param activity
     * @return ç…§ç›¸å�Žå›¾ç‰‡çš„è·¯å¾„
     */
    public static String takePicture(Activity activity) {
        FileUtils.createDirFile(BaseApplication.CAMERA_IMAGE_PATH);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String path = BaseApplication.CAMERA_IMAGE_PATH + UUID.randomUUID().toString() + "jpg";
        File file = FileUtils.createNewFile(path);
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_CAMERA);
        return path;
    }

    /**
     * è£�å‰ªå›¾ç‰‡
     * 
     * @param context
     * @param activity
     * @param path
     *            éœ€è¦�è£�å‰ªçš„å›¾ç‰‡è·¯å¾„
     */
    public static void cropPhoto(Context context, Activity activity, String path) {
        Intent intent = new Intent(context, ImageFactoryActivity.class);
        if (path != null) {
            intent.putExtra("path", path);
            intent.putExtra(ImageFactoryActivity.TYPE, ImageFactoryActivity.CROP);
        }
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_CROP);
    }

    /**
     * æ»¤é•œå›¾ç‰‡
     * 
     * @param context
     * @param activity
     * @param path
     *            éœ€è¦�æ»¤é•œçš„å›¾ç‰‡è·¯å¾„
     */
    public static void fliterPhoto(Context context, Activity activity, String path) {
        Intent intent = new Intent(context, ImageFactoryActivity.class);
        if (path != null) {
            intent.putExtra("path", path);
            intent.putExtra(ImageFactoryActivity.TYPE, ImageFactoryActivity.FLITER);
        }
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_FLITER);
    }

    /**
     * ä»Žæ–‡ä»¶ä¸­èŽ·å�–å›¾ç‰‡
     * 
     * @param path
     *            å›¾ç‰‡çš„è·¯å¾„
     * @return
     */
    public static Bitmap getBitmapFromPath(String path) {
        return BitmapFactory.decodeFile(path);
    }

    public static Bitmap getBitmapFromID(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // èŽ·å�–èµ„æº�å›¾ç‰‡
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * èŽ·å�–drawbleä¸­æŒ‡å®šæ–‡ä»¶å��çš„ID
     * 
     * @param æ–‡ä»¶å��
     * @return æ–‡ä»¶å¯¹åº”ID
     * 
     */
    public static int getImageID(String imgName) {
        Class<drawable> draw = R.drawable.class;
        try {
            Field field = draw.getDeclaredField(imgName);
            return field.getInt(imgName);
        }
        catch (SecurityException e) {
            return R.drawable.app_icon;
        }
        catch (NoSuchFieldException e) {
            return R.drawable.app_icon;
        }
        catch (IllegalArgumentException e) {
            return R.drawable.app_icon;
        }
        catch (IllegalAccessException e) {
            return R.drawable.app_icon;
        }
    }

    /**
     * åˆ›å»ºå›¾ç‰‡ç¼©ç•¥å›¾
     * 
     * @param path
     *            å›¾ç‰‡è·¯å¾„
     * @param w
     *            å®½åº¦
     * @param h
     *            é«˜åº¦
     */
    public static void createThumbnail(Context context, String path, String dir) {
        int imagePX = dp2px(context, 100);
        savePhotoToSDCard(decodedBitmapFromPath(path, imagePX, imagePX), dir,
                FileUtils.getNameByPath(path));
    }

    /**
     * ç¼©æ”¾å›¾ç‰‡
     * 
     * @param path
     *            å›¾ç‰‡çš„è·¯å¾„
     * @param w
     *            å®½åº¦
     * @param h
     *            é•¿åº¦
     * @return
     */
    public static Bitmap decodedBitmapFromPath(String path, int reqWidth, int reqHeight) {
        try {

            // é¦–å…ˆè®¾ç½® inJustDecodeBounds=true æ�¥èŽ·å�–å›¾ç‰‡å°ºå¯¸
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            // è®¡ç®— inSampleSize çš„å€¼
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // æ ¹æ�®è®¡ç®—å‡ºçš„ inSampleSize æ�¥è§£ç �å›¾ç‰‡ç”Ÿæˆ�Bitmap
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(path, options);
        }
        catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    /**
     * è®¡ç®—ç¼©æ”¾ç³»æ•°
     * 
     * @param options
     * @param reqWidth
     *            ç›®æ ‡å®½åº¦
     * @param reqHeight
     *            ç›®æ ‡é«˜åº¦
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
            int reqHeight) {
        // åŽŸå§‹å›¾ç‰‡çš„å®½é«˜
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // åœ¨ä¿�è¯�è§£æž�å‡ºçš„bitmapå®½é«˜åˆ†åˆ«å¤§äºŽç›®æ ‡å°ºå¯¸å®½é«˜çš„å‰�æ��ä¸‹ï¼Œå�–å�¯èƒ½çš„inSampleSizeçš„æœ€å¤§å€¼
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * èŽ·å�–å›¾ç‰‡çš„é•¿åº¦å’Œå®½åº¦
     * 
     * @param bitmap
     *            å›¾ç‰‡bitmapå¯¹è±¡
     * @return
     */
    public static Bundle getBitmapWidthAndHeight(Bitmap bitmap) {
        Bundle bundle = null;
        if (bitmap != null) {
            bundle = new Bundle();
            bundle.putInt("width", bitmap.getWidth());
            bundle.putInt("height", bitmap.getHeight());
            return bundle;
        }
        return null;
    }

    /**
     * åˆ¤æ–­å›¾ç‰‡é«˜åº¦å’Œå®½åº¦æ˜¯å�¦è¿‡å¤§
     * 
     * @param bitmap
     *            å›¾ç‰‡bitmapå¯¹è±¡
     * @return
     */
    public static boolean bitmapIsLarge(Bitmap bitmap) {
        final int MAX_WIDTH = 60;
        final int MAX_HEIGHT = 60;
        Bundle bundle = getBitmapWidthAndHeight(bitmap);
        if (bundle != null) {
            int width = bundle.getInt("width");
            int height = bundle.getInt("height");
            if (width > MAX_WIDTH && height > MAX_HEIGHT) {
                return true;
            }
        }
        return false;
    }

    /**
     * æ ¹æ�®æ¯”ä¾‹ç¼©æ”¾å›¾ç‰‡
     * 
     * @param screenWidth
     *            æ‰‹æœºå±�å¹•çš„å®½åº¦
     * @param filePath
     *            å›¾ç‰‡çš„è·¯å¾„
     * @param ratio
     *            ç¼©æ”¾æ¯”ä¾‹
     * @return
     */
    public static Bitmap CompressionPhoto(float screenWidth, String filePath, int ratio) {
        Bitmap bitmap = ImageUtils.getBitmapFromPath(filePath);
        Bitmap compressionBitmap = null;
        float scaleWidth = screenWidth / (bitmap.getWidth() * ratio);
        float scaleHeight = screenWidth / (bitmap.getHeight() * ratio);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        try {
            compressionBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        catch (Exception e) {
            return bitmap;
        }
        return compressionBitmap;
    }

    /**
     * ä¿�å­˜å›¾ç‰‡åˆ°SDå�¡
     * 
     * @param bitmap
     *            å›¾ç‰‡çš„bitmapå¯¹è±¡
     */
    public static String savePhotoToSDCard(Bitmap bitmap, String filedir, String paramFilename) {

        if (!FileUtils.isSdcardExist()) {
            return null;
        }
        FileUtils.createDirFile(filedir);
        int quality = 60; // ç¼©ç•¥å›¾æ²¡å¿…è¦�é«˜è´¨é‡�
        String filename = paramFilename;

        if (TextUtils.isEmpty(paramFilename)) {
            filename = UUID.randomUUID().toString() + ".jpg";
            quality = 100;
        }

        String newFilePath = filedir + filename;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(newFilePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        catch (IOException e1) {
            return null;
        }
        return newFilePath;
    }

    /**
     * æ ¹æ�®æ»¤é•œç±»åž‹èŽ·å�–å›¾ç‰‡
     * 
     * @param filterType
     *            æ»¤é•œç±»åž‹
     * @param defaultBitmap
     *            é»˜è®¤å›¾ç‰‡
     * @return
     */
    public static Bitmap getFilter(FilterType filterType, Bitmap defaultBitmap) {
        Bitmap returnBitmap = null;
        switch (filterType) {
            //case é»˜è®¤:
        case é:
                returnBitmap = defaultBitmap;
                break;

            case LOMO:
                returnBitmap = lomoFilter(defaultBitmap);
                break;
        }
        return returnBitmap;
    }

    /**
     * æ»¤é•œæ•ˆæžœ--LOMO
     * 
     * @param bitmap
     * @return
     */
    public static Bitmap lomoFilter(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int dst[] = new int[width * height];
        bitmap.getPixels(dst, 0, width, 0, 0, width, height);

        int ratio = width > height ? height * 32768 / width : width * 32768 / height;
        int cx = width >> 1;
        int cy = height >> 1;
        int max = cx * cx + cy * cy;
        int min = (int) (max * (1 - 0.8f));
        int diff = max - min;

        int ri, gi, bi;
        int dx, dy, distSq, v;

        int R, G, B;

        int value;
        int pos, pixColor;
        int newR, newG, newB;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pos = y * width + x;
                pixColor = dst[pos];
                R = Color.red(pixColor);
                G = Color.green(pixColor);
                B = Color.blue(pixColor);

                value = R < 128 ? R : 256 - R;
                newR = (value * value * value) / 64 / 256;
                newR = (R < 128 ? newR : 255 - newR);

                value = G < 128 ? G : 256 - G;
                newG = (value * value) / 128;
                newG = (G < 128 ? newG : 255 - newG);

                newB = B / 2 + 0x25;

                // ==========è¾¹ç¼˜é»‘æš—==============//
                dx = cx - x;
                dy = cy - y;
                if (width > height)
                    dx = (dx * ratio) >> 15;
                else
                    dy = (dy * ratio) >> 15;

                distSq = dx * dx + dy * dy;
                if (distSq > min) {
                    v = ((max - distSq) << 8) / diff;
                    v *= v;

                    ri = (int) (newR * v) >> 16;
                    gi = (int) (newG * v) >> 16;
                    bi = (int) (newB * v) >> 16;

                    newR = ri > 255 ? 255 : (ri < 0 ? 0 : ri);
                    newG = gi > 255 ? 255 : (gi < 0 ? 0 : gi);
                    newB = bi > 255 ? 255 : (bi < 0 ? 0 : bi);
                }
                // ==========è¾¹ç¼˜é»‘æš—end==============//

                dst[pos] = Color.rgb(newR, newG, newB);
            }
        }

        Bitmap acrossFlushBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        acrossFlushBitmap.setPixels(dst, 0, width, 0, 0, width, height);
        return acrossFlushBitmap;
    }

    /**
     * èŽ·å�–åœ†è§’å›¾ç‰‡
     * 
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap
                .createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * èŽ·å�–é¢œè‰²çš„åœ†è§’bitmap
     * 
     * @param context
     * @param color
     * @return
     */
    public static Bitmap getRoundBitmap(Context context, int color) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12.0f,
                metrics));
        int height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4.0f,
                metrics));
        int round = Math.round(TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.0f, metrics));
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawRoundRect(new RectF(0.0F, 0.0F, width, height), round, round, paint);
        return bitmap;
    }

    /**
     * æ ¹æ�®æ‰‹æœºçš„åˆ†è¾¨çŽ‡ä»Ž dp çš„å�•ä½� è½¬æˆ�ä¸º px(åƒ�ç´ )
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * æ ¹æ�®æ‰‹æœºçš„åˆ†è¾¨çŽ‡ä»Ž px(åƒ�ç´ ) çš„å�•ä½� è½¬æˆ�ä¸º dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
