package com.viethoa.lazadaretailer.utilities;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by VietHoa on 08/06/16.
 */
public class KeyBoardUtils {

    public interface softKeyBoardListener {
        void onSoftKeyBoardHidden();

        void onSoftKeyBoardShowing();
    }

    public static void detectSoftKeyBoard(final View rootView, final softKeyBoardListener listener) {

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //rect will be populated with the coordinates of your view that area still visible.
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);

                int heightDiff = rootView.getRootView().getHeight() - (rect.bottom - rect.top);
                if (heightDiff > 300) // if more than 300 pixels, its probably a keyboard
                    listener.onSoftKeyBoardShowing(); // keyboard is showing

                else listener.onSoftKeyBoardHidden();  // keyboard is hidden
            }
        });
    }

}
