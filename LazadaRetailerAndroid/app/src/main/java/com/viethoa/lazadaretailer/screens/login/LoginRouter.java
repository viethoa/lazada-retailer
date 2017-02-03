package com.viethoa.lazadaretailer.screens.login;

import android.app.Activity;
import android.content.Intent;

import com.viethoa.lazadaretailer.screens.home.HomeActivity;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class LoginRouter {

    void navigateToHomeActivity(Activity activity) {
        Intent intent = HomeActivity.newInstance(activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
