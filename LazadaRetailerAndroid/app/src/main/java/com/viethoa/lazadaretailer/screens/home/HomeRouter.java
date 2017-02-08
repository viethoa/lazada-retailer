package com.viethoa.lazadaretailer.screens.home;

import android.content.Context;
import android.content.Intent;

import com.viethoa.lazadaretailer.loggers.Logger;
import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.screens.store.StoreActivity;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class HomeRouter {

    private static final String TAG = HomeRouter.class.getSimpleName();

    void navigateToStoreActivity(Context context, Store store) {
        if (context == null) {
            Logger.e(TAG, "navigateToStoreActivity: null context");
            return;
        }
        Intent intent = StoreActivity.newInstance(context, store);
        context.startActivity(intent);
    }
}
