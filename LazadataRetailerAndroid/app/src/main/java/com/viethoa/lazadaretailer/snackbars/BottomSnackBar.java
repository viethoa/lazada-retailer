package com.viethoa.lazadaretailer.snackbars;

import android.app.Activity;

import com.viethoa.lazadaretailer.R;

/**
 * Created by VietHoa on 21/05/16.
 */
public class BottomSnackBar extends SnackBar {

    public BottomSnackBar(Activity activity) {
        super(activity, R.layout.snackbar_bottom_layout, R.id.snack_bar_content_message,
                R.id.snack_bar_tv_message, R.id.snack_bar_divider, R.id.snack_bar_btn_close, false);
    }
}
