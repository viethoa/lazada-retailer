package com.viethoa.lazadaretailer.screens.store;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.screens.baseviews.BaseSnackBarActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class StoreActivity extends BaseSnackBarActivity {
    private static final String EXTRACT_STORE = "extract-store";
    private Store store;

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;

    public static Intent newInstance(Context context, Store store) {
        Intent intent = new Intent(context, StoreActivity.class);
        intent.putExtra(EXTRACT_STORE, store);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

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

        // Set title name
        if (!TextUtils.isEmpty(store.getName())) {
            toolbarTitle.setText(store.getName());
        }
    }

    @OnClick(R.id.btn_ready_to_ship)
    public void btnReadyToShipClicked() {

    }

    @OnClick(R.id.btn_delivery_fail)
    public void btnDeliverryFailCliked() {

    }

    @OnClick(R.id.btn_return)
    public void btnReturnClicked() {

    }
}
