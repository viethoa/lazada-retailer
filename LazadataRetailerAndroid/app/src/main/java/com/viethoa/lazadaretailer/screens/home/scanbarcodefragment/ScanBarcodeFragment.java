package com.viethoa.lazadaretailer.screens.home.scanbarcodefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.screens.baseviews.BaseSnackBarFragment;

import java.util.List;

import butterknife.Bind;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class ScanBarcodeFragment extends BaseSnackBarFragment {

    @Bind(R.id.ls_orders)
    ListView listViewOrder;

    public static ScanBarcodeFragment newInstance() {
        return new ScanBarcodeFragment();
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_scan_barcode, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    public void initializeView(Store store) {
        if (listViewOrder == null || store == null) {
            return;
        }


    }

}
