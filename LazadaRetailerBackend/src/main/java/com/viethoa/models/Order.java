package com.viethoa.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by VietHoa on 16/01/2017.
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Order {

    @JsonProperty("id")
    private long id;
    @JsonProperty("order_no")
    private String orderNo;
    @OrderState
    @JsonProperty("state")
    private String state;
    @JsonProperty("store_id")
    private long storeID;
    @JsonProperty("insert_at")
    private long insertAt;
    @JsonProperty("update_at")
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
