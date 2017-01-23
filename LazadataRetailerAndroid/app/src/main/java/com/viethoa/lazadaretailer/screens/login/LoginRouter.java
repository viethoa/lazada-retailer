package com.viethoa.lazadaretailer.screens.login;

import android.app.Activity;

import com.viethoa.lazadaretailer.screens.home.HomeActivity;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class LoginRouter {

    void navigateToHomeActivity(Activity activity) {
        activity.startActivity(HomeActivity.newInstance(activity));
    }
}
