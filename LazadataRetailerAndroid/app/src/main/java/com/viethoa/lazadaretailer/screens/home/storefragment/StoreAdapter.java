package com.viethoa.lazadaretailer.screens.home.storefragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.models.Store;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private List<Store> dataArray;

    public void refreshData(List<Store> dataSet) {
        this.dataArray = dataSet;
        notifyDataSetChanged();
    }

    public StoreAdapter(List<Store> dataArray) {
        this.dataArray = dataArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(dataArray.get(position));
    }

    @Override
    public int getItemCount() {
        if (dataArray == null) {
            return 0;
        } else {
            return dataArray.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Store store) {

        }
    }

}
