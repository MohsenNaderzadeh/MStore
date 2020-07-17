package ir.developer_boy.mstore.orders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.model.Order;
import ir.developer_boy.mstore.utils.PriceConverter;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    List<Order> orderList = new ArrayList<>();

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bindOrder(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_order_code;
        private TextView tv_order_recipent;
        private TextView tv_order_payable;
        private TextView tv_order_payment_status;
        private TextView tv_order_date;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tv_order_code = itemView.findViewById(R.id.tv_order_code);
            tv_order_recipent = itemView.findViewById(R.id.tv_order_recipent);
            tv_order_payable = itemView.findViewById(R.id.tv_order_payable);
            tv_order_payment_status = itemView.findViewById(R.id.tv_order_payment_status);
            tv_order_date = itemView.findViewById(R.id.tv_order_date);
        }

        public void bindOrder(Order order) {
            tv_order_code.setText(String.valueOf(order.getId()));
            tv_order_recipent.setText(order.getFirstName() + " " + order.getLastName());
            tv_order_payable.setText(PriceConverter.convert(order.getPayable()));
            tv_order_payment_status.setText(order.getPaymentStatus());
            tv_order_date.setText(order.getDate());
        }
    }
}
