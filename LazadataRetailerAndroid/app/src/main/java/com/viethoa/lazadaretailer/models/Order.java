package com.viethoa.lazadaretailer.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by VietHoa on 24/01/2017.
 */

public class Order implements Serializable {

    @SerializedName("id")
    private long id;
    @SerializedName("order_no")
    private String orderNo;
    @OrderState
    @SerializedName("state")
    private String state;
    @SerializedName("store_id")
    private long storeID;
    @SerializedName("insert_at")
    private long insertAt;
    @SerializedName("update_at")
    private long updateAt;

    public Order(String orderNo, long storeID) {
        this.orderNo = orderNo;
        this.storeID = storeID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getStoreID() {
        return storeID;
    }

    public void setStoreID(long storeID) {
        this.storeID = storeID;
    }

    public long getInsertAt() {
        return insertAt;
    }

    public void setInsertAt(long insertAt) {
        this.insertAt = insertAt;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    //------------------------------------------------------------------------------------------------------------------
    // State
    //------------------------------------------------------------------------------------------------------------------

    public @interface OrderState {
        String Delivered = "delivered";
        String Completed = "completed";
        String Cancelled = "cancelled";
        String Maintaining = "maintaining";
    }

    //------------------------------------------------------------------------------------------------------------------
    // State
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object object) {
        boolean equal = false;

        if (object instanceof Order) {
            Order order = (Order) object;
            equal = order.getOrderNo().equals(this.getOrderNo());
        }

        return equal;
    }
}
