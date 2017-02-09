package com.viethoa.lazadaretailer.screens.scan.scanbarcodefragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.models.Order;
import com.viethoa.lazadaretailer.screens.baseviews.BaseArrayAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by VietHoa on 24/01/2017.
 */

public class ScannerAdapter extends BaseArrayAdapter<Order> {
    private ScannerAdapterListener listener;

    ScannerAdapter(Context context, List<Order> data, ScannerAdapterListener listener) {
        super(context, data);
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_scanner_layout, viewGroup, false);
            HelpViewHolder holder = new HelpViewHolder(convertView);
            convertView.setTag(holder);
        }

        HelpViewHolder helpViewHolder = (HelpViewHolder) convertView.getTag();
        helpViewHolder.bind(getItem(position), listener);

        return convertView;
    }

    static class HelpViewHolder {
        @Bind(R.id.tv_order_no)
        TextView tvOrderNo;
        //@Bind(R.id.btn_delete)
        //View btnDelete;

        HelpViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void bind(Order order, ScannerAdapterListener listener) {
            if (order == null) {
                return;
            }

            tvOrderNo.setText(order.getOrderNo());
//            btnDelete.setOnClickListener(view -> {
//                if (listener != null) {
//                    listener.onOrderItemsDeleteButtonClicked(order);
//                }
//            });
        }
    }
}
