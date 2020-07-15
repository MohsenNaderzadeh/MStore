package ir.developer_boy.mstore.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ir.developer_boy.mstore.R;

public class CartItemCountChanger extends CardView {
    private View rootview;
    private View add;
    private View minus;
    private TextView counttv;
    private int count = 1;
    private OnCartItemCountChangedListener onCartItemCountChangedListener;


    public CartItemCountChanger(@NonNull Context context) {
        super(context);
        init();
    }

    public CartItemCountChanger(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CartItemCountChanger(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rootview = LayoutInflater.from(getContext()).inflate(R.layout.view_cart_item_count_changer, this, true);
        add = rootview.findViewById(R.id.tv_cart_item_count_changer_add);
        minus = rootview.findViewById(R.id.tv_cart_item_count_changer_minus);
        counttv = rootview.findViewById(R.id.tv_cart_item_count);

        add.setOnClickListener(v -> {
            count += 1;
            OnCountChange();
        });

        minus.setOnClickListener(v -> {
            if (count > 0)
                count -= 1;
            OnCountChange();
        });


    }

    private void OnCountChange() {
        if (count > 0) {
            counttv.setText(String.valueOf(count));
            if (onCartItemCountChangedListener != null) {
                onCartItemCountChangedListener.OnChange(count);
            }
        } else {
            count = 1;
            OnCountChange();
        }

    }

    public void setOnCartItemCountChangedListener(OnCartItemCountChangedListener onCartItemCountChangedListener) {
        this.onCartItemCountChangedListener = onCartItemCountChangedListener;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        counttv.setText(String.valueOf(count));
    }

    public interface OnCartItemCountChangedListener {
        void OnChange(int count);
    }


}
