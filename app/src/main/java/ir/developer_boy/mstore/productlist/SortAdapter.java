package ir.developer_boy.mstore.productlist;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.developer_boy.mstore.R;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.SortViewHolder> {

    private SortItemBtnClick sortItemBtnClick;
    private String[] sorts=new String[]{
            "جدیدترین",
            "پربازدیدترین",
            "قیمت از زیاد به کم",
            "قیمت از کم به زیاد"
    };
    private int defaultselectedsorttype;

    public SortAdapter(int defaultselectedsorttype,SortItemBtnClick sortItemBtnClick) {
        this.defaultselectedsorttype = defaultselectedsorttype;
        this.sortItemBtnClick=sortItemBtnClick;
    }

    @NonNull
    @Override
    public SortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SortViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort_chips,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SortViewHolder holder, int position) {
        if(position==defaultselectedsorttype)
        {
            holder.binddata(sorts[position],true);
        }
        else
        {
            holder.binddata(sorts[position],false);
        }
    }

    @Override
    public int getItemCount() {
        return sorts.length;
    }



    public class SortViewHolder extends RecyclerView.ViewHolder{
        private TextView sortTextView;
        public SortViewHolder(View itemView) {
            super(itemView);
            sortTextView= (TextView) itemView;
        }

        public void binddata(String sortitem, final boolean defaultitemisset)
        {
            sortTextView.setText(sortitem);
            if(!defaultitemisset)
            {

                sortTextView.setBackgroundResource(R.drawable.shape_chips_unselected);
                sortTextView.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.colorAccent));
            }
            else
            {
                sortTextView.setBackgroundResource(R.drawable.shape_chips_selected);
                sortTextView.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.white));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sortItemBtnClick!=null)
                    {
                        if(getAdapterPosition()!=defaultselectedsorttype)
                        {
                            sortItemBtnClick.OnSortBtnClicked(getAdapterPosition());
                            notifyItemChanged(defaultselectedsorttype);
                            defaultselectedsorttype=getAdapterPosition();
                            notifyItemChanged(defaultselectedsorttype);
                        }

                    }
                }
            });

        }


    }

    public interface SortItemBtnClick{
        void OnSortBtnClicked(int selectedsorttype);
    }
}
