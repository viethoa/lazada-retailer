package com.viethoa.lazadaretailer.screens.store.returnfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.screens.baseviews.BaseSnackBarFragment;

/**
 * Created by VietHoa on 09/02/2017.
 */

public class ReturnFragment extends BaseSnackBarFragment {

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_manage_order, container, false);
    }
}
