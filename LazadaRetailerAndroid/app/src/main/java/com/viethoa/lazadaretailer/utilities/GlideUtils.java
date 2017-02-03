package com.viethoa.lazadaretailer.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.loggers.Logger;

/**
 * Created by VietHoa on 21/07/2016.
 */

public class GlideUtils {

    public static void loadCircleUserImage(Context context, String url, ImageView imageView) {
        loadCircleUserImage(context, url, imageView, R.drawable.placeholder_user);
    }

    public static void loadCircleUserImage(Context context, String url, ImageView imageView, @DrawableRes int holder) {
        if (context == null || imageView == null) {
            return;
        }
        if (!(context instanceof Activity) || ((Activity)context).isFinishing()) {
            return;
        }

        try {
            Glide.with(context).load(url)
                    .transform(new CircleTransform(context))
                    .placeholder(holder)
                    .into(imageView);
        } catch (Exception ex) {
            Logger.e("loadCircleUserImage", ex);
        }
    }

    private static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }
}
