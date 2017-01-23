package com.viethoa.lazadaretailer.screens.home.storefragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.screens.baseviews.BaseSnackBarFragment;

import java.util.List;

import butterknife.Bind;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class StoreFragment extends BaseSnackBarFragment {
    private StoreAdapter storeAdapter;
    private List<Store> stores;

    @Bind(R.id.txt_no_data)
    TextView tvNoStoreData;
    @Bind(R.id.my_recycler_view)
    RecyclerView recyclerView;

    public static StoreFragment newInstance() {
        return new StoreFragment();
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_stores, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView.setHasFixedSize(true);
        initializeView(stores);
    }

    public void initializeView(List<Store> stores) {
        if (recyclerView == null || stores == null) {
            return;
        }

        this.stores = stores;

        // No data text
        if (stores.size() > 0) {
            tvNoStoreData.setVisibility(View.VISIBLE);
        }

        // Setup store recycle view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        storeAdapter = new StoreAdapter(stores);
        recyclerView.setAdapter(storeAdapter);
    }
}
