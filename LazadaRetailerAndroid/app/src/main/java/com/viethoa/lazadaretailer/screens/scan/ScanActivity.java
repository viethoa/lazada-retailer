package com.viethoa.lazadaretailer.screens.scan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.screens.baseviews.BaseSnackBarActivity;
import com.viethoa.lazadaretailer.screens.store.StoreActivity;

import butterknife.Bind;

public class ScanActivity extends BaseSnackBarActivity {
    private static final String EXTRACT_STORE = "extract-store";
    private static final String EXTRACT_TITLE = "extract-title";
    private Store store;
    private boolean isNeedGam;

    @Bind(R.id.toolbar_title)
    TextView tvTitle;
    @Bind(R.id.toolbar_icon)
    ImageView imgToolbarIcon;

    public static Intent newInstance(Context context, Store store, int titleRes) {
        Intent intent = new Intent(context, StoreActivity.class);
        intent.putExtra(EXTRACT_TITLE, titleRes);
        intent.putExtra(EXTRACT_STORE, store);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // Extract data
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        store = (Store) bundle.getSerializable(EXTRACT_STORE);
        if (store == null) {
            finish();
            return;
        }

        // Init toolbar
        int titleRes = bundle.getInt(EXTRACT_TITLE);
        tvTitle.setText(titleRes);
        switch (titleRes) {
            case R.string.store_btn_ready_to_ship:
                imgToolbarIcon.setImageResource(R.mipmap.ic_ready_to_ship);
                break;
            case R.string.store_btn_delivery_fail:
                imgToolbarIcon.setImageResource(R.mipmap.ic_order_cancelled);
                break;
            default:
                imgToolbarIcon.setImageResource(R.mipmap.ic_order_returned);
                break;
        }
    }
}
