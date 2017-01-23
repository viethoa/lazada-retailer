package com.viethoa.lazadaretailer.screens.baseviews;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.viethoa.lazadaretailer.R;

/**
 * Created by VietHoa on 19/05/16.
 */
public class DialogLoading extends Dialog {

    public static DialogLoading newInstance(Context context) {
        return new DialogLoading(context);
    }

    public DialogLoading(Context context) {
        super(context, R.style.FullScreen_DialogStyle);

        // Force Content loading to Bottom.
        getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        getWindow ().setGravity (Gravity.BOTTOM);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_loading_layout);
    }
}
