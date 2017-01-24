package com.viethoa.lazadaretailer.screens.home.scanbarcodefragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.zxing.Result;
import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.models.Order;
import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.screens.baseviews.BaseSnackBarFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class ScannerFragment extends BaseSnackBarFragment implements
        ZXingScannerView.ResultHandler,
        ScannerAdapterListener {

    private ScannerFragmentListener listener;
    private ZXingScannerView scannerView;
    private ScannerAdapter scannerAdapter;
    private List<Order> orders;
    private Store store;

    @Bind(R.id.ls_orders)
    ListView listViewOrder;
    @Bind(R.id.scanner_content)
    FrameLayout contentFrame;

    public static ScannerFragment newInstance() {
        return new ScannerFragment();
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_scanner, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ScannerFragmentListener) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
        scannerView.setFlash(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    //----------------------------------------------------------------------------------------------
    // Init View
    //----------------------------------------------------------------------------------------------

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Init scanner
        scannerView = new ZXingScannerView(getContext());
        contentFrame.removeAllViews();
        contentFrame.addView(scannerView);

        // Init listView order
        orders = new ArrayList<>();
        scannerAdapter = new ScannerAdapter(getContext(), orders, this);
        listViewOrder.setAdapter(scannerAdapter);

        initializeView(store);
    }

    public void initializeView(Store store) {
        this.store = store;
    }

    //----------------------------------------------------------------------------------------------
    // Events
    //----------------------------------------------------------------------------------------------

    @OnClick(R.id.btn_push_order)
    public void btnPushOrderToServerClicked() {

    }

    @Override
    public void handleResult(Result result) {
        if (store == null) {
            showTopErrorMessage(R.string.scanner_error_store_null);
            resumeScanner();
            return;
        }
        String barcode = result.getBarcodeFormat().toString();
        if (TextUtils.isEmpty(barcode)) {
            showTopErrorMessage(R.string.scanner_error_barcode_null);
            resumeScanner();
            return;
        }
        Order order = new Order(barcode, store.getId());
        if (orders.contains(order)) {
            showTopErrorMessage(R.string.scanner_error_order_already_exist);
            resumeScanner();
            return;
        }

        orders.add(order);
        scannerAdapter.refreshData(orders);
        resumeScanner();
    }

    private void resumeScanner() {
        Handler handler = new Handler();
        handler.postDelayed(() -> scannerView.resumeCameraPreview(ScannerFragment.this), 1000);
    }

    @Override
    public void onOrderItemClicked(Order order) {
        if (order == null) {
            showTopErrorMessage(R.string.scanner_error_delete_order_null);
            return;
        }
        if (!orders.contains(order)) {
            showTopErrorMessage(R.string.scanner_error_delete_order_not_exist);
            return;
        }

        orders.remove(order);
        scannerAdapter.refreshData(orders);
    }
}
