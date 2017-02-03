package com.viethoa.lazadaretailer.service;

import com.viethoa.lazadaretailer.models.Order;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by VietHoa on 24/01/2017.
 */

public class SyncOrderEventBus {

    private static SyncOrderEventBus mInstance = null;

    public static SyncOrderEventBus newInstance() {
        if (mInstance == null) {
            mInstance = new SyncOrderEventBus();
        }
        return mInstance;
    }

    private final PublishSubject<Order> syncedOrderSuccessSub = PublishSubject.create();
    private final PublishSubject<Exception> syncOrderErrorSub = PublishSubject.create();
    private final PublishSubject<Void> syncOrderHaveDoneSub = PublishSubject.create();

    public Observable<Order> onOrderSynced() {
        return syncedOrderSuccessSub.asObservable();
    }

    public void emitOrderHaveSynced(Order order) {
        syncedOrderSuccessSub.onNext(order);
    }

    public Observable<Exception> onOrderSyncError() {
        return syncOrderErrorSub.asObservable();
    }

    public void emitOrderHaveSyncError(Exception ex) {
        syncOrderErrorSub.onNext(ex);
    }

    public Observable<Void> onSyncOrderHaveDone() {
        return syncOrderHaveDoneSub.asObservable();
    }

    public void emitSyncOrderIsDone() {
        syncOrderHaveDoneSub.onNext(null);
    }
}
