package ir.developer_boy.mstore.main;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.model.Banner;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    private List<Banner> bannerList=new ArrayList<>();
    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView=new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setAdjustViewBounds(true);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        holder.BindBanner(bannerList.get(position));
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }
    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
        notifyDataSetChanged();
    }
    public class BannerViewHolder extends RecyclerView.ViewHolder{
        private ImageView Bannerimg;
        public BannerViewHolder(View itemView) {
            super(itemView);
            Bannerimg= (ImageView) itemView;
        }

        public void BindBanner(Banner banner)
        {
            Picasso.get().load(banner.getImage()).into(Bannerimg);
        }
    }
}
