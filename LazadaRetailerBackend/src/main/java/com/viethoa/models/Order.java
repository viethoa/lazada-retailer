package com.viethoa.models;

/**
 * Created by VietHoa on 16/01/2017.
 */
public class Order {

    private long id;
    private String orderNo;
    @OrderState
    private String state;
    private long storeID;
    private long insertAt;
    private long updateAt;

    public Order(String orderNo, long storeID) {
        this.orderNo = orderNo;
        this.storeID = storeID;
        this.state = OrderState.Delivered;
        this.insertAt = System.currentTimeMillis();
        this.updateAt = insertAt;
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

}
