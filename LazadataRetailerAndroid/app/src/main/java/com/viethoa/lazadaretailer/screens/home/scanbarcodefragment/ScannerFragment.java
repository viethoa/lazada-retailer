package com.viethoa.lazadaretailer.screens.home.scanbarcodefragment;

import android.app.ProgressDialog;
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
import com.viethoa.lazadaretailer.screens.BriefObserver;
import com.viethoa.lazadaretailer.screens.baseviews.BaseSnackBarFragment;
import com.viethoa.lazadaretailer.service.SyncOrderEventBus;
import com.viethoa.lazadaretailer.service.SyncOrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class ScannerFragment extends BaseSnackBarFragment implements
        ZXingScannerView.ResultHandler,
        ScannerAdapterListener {

    private ProgressDialog syncOrderDialog;
    private SyncOrderEventBus syncOrderEventBus;
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
        syncOrderEventBus = SyncOrderEventBus.newInstance();
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

        initializeEventBus();
    }

    public void initializeView(Store store) {
        this.store = store;
    }

    private void initializeEventBus() {
        // Sync order have done
        syncOrderEventBus.onSyncOrderHaveDone()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BriefObserver<Void>() {
                    @Override
                    public void onNext(Void aVoid) {
                        dismissSyncOrderDialog();
                    }
                });

        // Order have synced
        syncOrderEventBus.onOrderSynced()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BriefObserver<Order>() {
                    @Override
                    public void onNext(Order order) {
                        onOrderItemsDeleteButtonClicked(order);
                    }
                });


        // Order sync is error
        syncOrderEventBus.onOrderSyncError()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BriefObserver<Exception>() {
                    @Override
                    public void onNext(Exception exception) {
                        if (exception != null) {
                            showTopErrorMessage(exception.getMessage());
                        }
                    }
                });
    }

    private void dismissSyncOrderDialog() {
        if (getActivity().isFinishing()) {
            return;
        }
        if (syncOrderDialog == null || !syncOrderDialog.isShowing()) {
            return;
        }
        syncOrderDialog.dismiss();
    }

    //----------------------------------------------------------------------------------------------
    // Events
    //----------------------------------------------------------------------------------------------

    public boolean isProcessing() {
        return orders != null && orders.size() > 0;
    }

    @OnClick(R.id.btn_push_order)
    public void btnPushOrderToServerClicked() {
        if (orders == null || orders.size() <= 0) {
            showTopWarningMessage(R.string.scanner_warning_save_order);
            return;
        }

        syncOrderDialog = ProgressDialog.show(
                getActivity(),
                getContext().getString(R.string.scanner_sync_order_title),
                getContext().getString(R.string.scanner_sync_order_message)
        );
        getContext().startService(SyncOrderService.newInstance(getContext(),
                new ArrayList<>(orders)));
    }

    @Override
    public void handleResult(Result result) {
        if (store == null) {
            showTopErrorMessage(R.string.scanner_error_store_null);
            resumeScanner();
            return;
        }
        String barcode = result.getText().toString();
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
        handler.postDelayed(() -> scannerView.resumeCameraPreview(ScannerFragment.this), 1500);
    }

    @Override
    public void onOrderItemsDeleteButtonClicked(Order order) {
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
