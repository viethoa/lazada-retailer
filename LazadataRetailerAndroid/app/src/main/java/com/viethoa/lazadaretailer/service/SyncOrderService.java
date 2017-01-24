package com.viethoa.lazadaretailer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.viethoa.lazadaretailer.Application;
import com.viethoa.lazadaretailer.loggers.Logger;
import com.viethoa.lazadaretailer.models.Order;
import com.viethoa.lazadaretailer.network.responses.NetworkResponse;
import com.viethoa.lazadaretailer.network.services.orderservice.OrderService;
import com.viethoa.lazadaretailer.screens.BriefObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import rx.schedulers.Schedulers;

/**
 * Created by VietHoa on 24/01/2017.
 */

public class SyncOrderService extends Service {
    private static final String NAME = SyncOrderService.class.getSimpleName();
    private static final String EXTRACT_ORDERS = "extract-orders";
    private SyncOrderEventBus syncOrderEventBus;
    private LinkedList<Order> orders;

    @Inject
    OrderService orderservice;

    public static Intent newInstance(Context context, ArrayList<Order> orders) {
        Intent intent = new Intent(context, SyncOrderService.class);
        intent.putExtra(EXTRACT_ORDERS, orders);
        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(NAME, "onCreate");

        // Injection
        Application application = (Application) getApplication();
        application.getComponent().inject(this);

        // EventBus
        syncOrderEventBus = SyncOrderEventBus.newInstance();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(NAME, "onStartCommand");
        if (intent == null) {
            stopSelf();
            return START_NOT_STICKY;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            stopSelf();
            return START_NOT_STICKY;
        }

        ArrayList<Order> orderArray = (ArrayList<Order>) bundle.getSerializable(EXTRACT_ORDERS);
        if (orderArray == null || orderArray.size() <= 0) {
            stopSelf();
            return START_NOT_STICKY;
        }

        orders = convertToLinkedList(orderArray);
        syncOrderWithQueue();
        return START_NOT_STICKY;
    }

    private void syncOrderWithQueue() {
        if (orders == null || orders.size() <= 0) {
            Logger.d(NAME, "syncDBBookingWithQueue no data to sync/sync is done");
            syncOrderEventBus.emitSyncOrderIsDone();
            stopSelf();
            return;
        }

        Logger.d(NAME, "Orders must be sync: " + orders.size());
        submitOrder(orders.pop());
    }

    private void submitOrder(final Order order) {
        if (order == null) {
            Logger.e(NAME, "submitOrder have null input data");
            syncOrderWithQueue();
            return;
        }

        Logger.d(NAME, "Sync order: " + order.getOrderNo());
        orderservice.pushOrder(order)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new BriefObserver<NetworkResponse<Order>>() {
                    @Override
                    public void onNext(NetworkResponse<Order> response) {
                        Logger.d(NAME, String.format("synced order: %s", order.getOrderNo()));
                        syncOrderEventBus.emitOrderHaveSynced(response.getData());
                        syncOrderWithQueue();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (throwable != null) {
                            syncOrderEventBus.emitOrderHaveSyncError(new Exception(throwable.getMessage()));
                        }
                        syncOrderWithQueue();
                    }
                });
    }

    private LinkedList<Order> convertToLinkedList(List<Order> orders) {
        if (orders == null || orders.size() <= 0) {
            return new LinkedList<>();
        }

        LinkedList<Order> linkedList = new LinkedList<>();
        for (Order order : orders) {
            linkedList.add(order);
        }

        return linkedList;
    }
}
