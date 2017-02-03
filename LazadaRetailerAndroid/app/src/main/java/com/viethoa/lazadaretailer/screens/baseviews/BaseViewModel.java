package com.viethoa.lazadaretailer.screens.baseviews;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseViewModel<T extends BaseViewModel.Listener> {

    private CompositeSubscription mCompositeSubscription;
    protected T listener;

    public void destroy() {
        onDestroy();
    }

    public void initialize(T listener) {
        this.listener = listener;
    }

    protected void onDestroy() {
        listener = null;
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription.clear();
            mCompositeSubscription = null;
        }
    }

    public synchronized Subscription manageSubscription(Subscription subscription) {
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
        return subscription;
    }

    public interface Listener {

        void onError(Throwable e);
    }
}