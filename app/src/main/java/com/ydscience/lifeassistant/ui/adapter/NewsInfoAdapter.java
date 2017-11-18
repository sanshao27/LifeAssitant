package com.ydscience.lifeassistant.ui.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ydscience.lifeassistant.R;
import com.ydscience.lifeassistant.bean.news.NewDetailes;
import com.ydscience.lifeassistant.utils.MyLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ydscience on 2017/6/26.
 */

public class NewsInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private Context mContext;
    private List<NewDetailes> mNewInfos;
    private OnItemOnClickListener mOnItemOnClickListener = null;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    public NewsInfoAdapter(Context context,List<NewDetailes> list) {
        this.mContext = context;
        this.mNewInfos = list;
    }

    public static interface OnItemOnClickListener{
        void onItemClick(View view,int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_new_info,parent,false);
            view.setOnClickListener(this);
            return new ItemViewHolder(view);
        }else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot, parent,
                    false);
            return new FootViewHolder(view);
        }
        return  null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       if (holder instanceof  ItemViewHolder) {
           String imageUrl = mNewInfos.get(position).getThumbnail_pic_s();
           if (!TextUtils.isEmpty(imageUrl)) {
               Glide.with(mContext)
                       .load(imageUrl)
                       .placeholder(R.drawable.test)
                       .into(((ItemViewHolder) holder).imageView);
           }
           ((ItemViewHolder) holder).titleView.setText(mNewInfos.get(position).getTitle());
           ((ItemViewHolder) holder).articleView.setText(mNewInfos.get(position).getAuthour_name());
           ((ItemViewHolder) holder).timeView.setText(mNewInfos.get(position).getDate());
           ((ItemViewHolder) holder).itemView.setTag(position);
       }
    }


    @Override
    public int  getItemCount() {
        return mNewInfos.size() == 0 ?0:mNewInfos.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemOnClickListener != null){
            mOnItemOnClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public void setOnItemOnClickListener(OnItemOnClickListener listener){
        this.mOnItemOnClickListener  = listener;
    }

    class ItemViewHolder  extends RecyclerView.ViewHolder {
        @BindView(R.id.news_icon_imageView) ImageView imageView;
        @BindView(R.id.new_title_view) TextView  titleView;
        @BindView(R.id.new_article) TextView  articleView;
        @BindView(R.id.new_time) TextView timeView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
     class FootViewHolder extends RecyclerView.ViewHolder {

         public FootViewHolder(View itemView) {
             super(itemView);
         }
     }
}
