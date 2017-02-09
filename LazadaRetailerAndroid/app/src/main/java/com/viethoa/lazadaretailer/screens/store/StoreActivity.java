package com.viethoa.lazadaretailer.screens.store;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.TextView;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.screens.baseviews.BaseSnackBarActivity;
import com.viethoa.lazadaretailer.screens.store.deliveryfailfragment.DeliveryFailFragment;
import com.viethoa.lazadaretailer.screens.store.readytoshipfragment.ReadyToShipFragment;
import com.viethoa.lazadaretailer.screens.store.returnfragment.ReturnFragment;

import butterknife.Bind;

public class StoreActivity extends BaseSnackBarActivity {
    private static final String EXTRACT_STORE = "extract-store";
    private static final String READY_TO_SHIP = "READY TO SHIP";
    private static final String DELIVERY_FAIL = "DELIVERY FAIL";
    private static final String RETURN = "RETURN";
    private Store store;

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

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
            toolbarTitle.setText(store.getName().toUpperCase());
        }

        // Init tabs
        StoreViewPagerAdapter adapter = new StoreViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReadyToShipFragment(), READY_TO_SHIP);
        adapter.addFragment(new DeliveryFailFragment(), DELIVERY_FAIL);
        adapter.addFragment(new ReturnFragment(), RETURN);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_ready_to_ship);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_delivery_fail);
        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_returned);
    }
}
