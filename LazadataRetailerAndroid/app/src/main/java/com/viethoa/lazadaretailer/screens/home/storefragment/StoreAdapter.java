package com.viethoa.lazadaretailer.screens.home.storefragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.utilities.FormatUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private Context context;
    private List<Store> dataArray;
    private StoreAdapterListener listener;

    public StoreAdapter(Context context, List<Store> dataArray, StoreAdapterListener listener) {
        this.context = context;
        this.dataArray = dataArray;
        this.listener = listener;
    }

    public void refreshData(List<Store> dataSet) {
        this.dataArray = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(context, dataArray.get(position), listener);
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
        @Bind(R.id.card_view)
        View cardView;
        @Bind(R.id.txt_title)
        TextView txtStoreName;
        @Bind(R.id.txt_created_time)
        TextView txtCreatedTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Context context, Store store, StoreAdapterListener listener) {
            if (store == null) {
                return;
            }

            txtStoreName.setText(store.getName());
            txtCreatedTime.setText(String.format(
                    context.getString(R.string.home_store_created_time), FormatUtils.date(store.getCreatedAt())));

            cardView.setOnClickListener(view -> {
               if (listener != null) {
                   listener.onStoreItemClicked(store);
               }
            });
        }
    }

}
