package com.bwie.recyclerview1017;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by eric on 2018/10/17.
 */

// RecyclerView适配器需要继承RecyclerView.Adapter
// Adapter需要有个泛型参数，这个泛型的类型是继承了RecyclerView.ViewHolder的一个类
// 继承自RecyclerView.Adapter需要实现3个方法
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<News.DataBean> list;

    private static final int TYPE_ONE_PIC = 0;
    private static final int TYPE_TWO_PIC = 1;
    private static final int TYPE_THREE_PIC = 2;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface OnLongItemClickListener {
        void onItemLongClick(View itemView, int position);
    }

    private OnItemClickListener clickListener;
    private OnLongItemClickListener longItemClickListener;

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener longItemClickListener) {
        this.longItemClickListener = longItemClickListener;
    }

    public NewsAdapter(Context context, List<News.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    // 创建ViewHolder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_ONE_PIC) {
            View v = View.inflate(context, R.layout.item_news_one, null);
            holder = new ViewHolder1(v);
            return holder;
        } else if (viewType == TYPE_TWO_PIC) {
            View v = View.inflate(context, R.layout.item_news_two, null);
            holder = new ViewHolder2(v);
        } else {
            View v = View.inflate(context, R.layout.item_news_three, null);
            holder = new ViewHolder3(v);
        }

        return holder;

    }

    // 绑定ViewHolder，把视图和数据进行绑定
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_ONE_PIC:
                ViewHolder1 holder1 = (ViewHolder1) holder;
                Glide.with(context).load(list.get(position).getThumbnail01()).into(holder1.imgLogo1);
                holder1.txtTitle.setText(list.get(position).getTitle());
                break;
            case TYPE_TWO_PIC:
                ViewHolder2 holder2 = (ViewHolder2) holder;
                Glide.with(context).load(list.get(position).getThumbnail01()).into(holder2.imgLogo1);
                Glide.with(context).load(list.get(position).getThumbnail02()).into(holder2.imgLogo2);
                holder2.txtTitle.setText(list.get(position).getTitle());
                break;
            case TYPE_THREE_PIC:
                ViewHolder3 holder3 = (ViewHolder3) holder;
                Glide.with(context).load(list.get(position).getThumbnail01()).into(holder3.imgLogo1);
                Glide.with(context).load(list.get(position).getThumbnail02()).into(holder3.imgLogo2);
                Glide.with(context).load(list.get(position).getThumbnail03()).into(holder3.imgLogo3);
                holder3.txtTitle.setText(list.get(position).getTitle());
                break;
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(v, position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longItemClickListener != null) {
                    longItemClickListener.onItemLongClick(v, position);
                }
                return true;
            }
        });
    }

    // 获取条目的数量，类似于BaseAdapter中的getCount方法
    @Override
    public int getItemCount() {
        return list.size();
    }


    // 实现多条目加载只需要实现这一个方法就可以
    @Override
    public int getItemViewType(int position) {
        News.DataBean dataBean = list.get(position);
        // 第三章图片不为空
        if (!TextUtils.isEmpty(dataBean.getThumbnail03())) {
            return TYPE_THREE_PIC;
        } else if (!TextUtils.isEmpty(dataBean.getThumbnail02())) {
            return TYPE_TWO_PIC;
        } else {
            return TYPE_ONE_PIC;
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        private ImageView imgLogo1;
        private TextView txtTitle;

        public ViewHolder1(View itemView) {
            super(itemView);
            // 类似于BaseAdapter中的holder.txtTitle = convertView.findViewById()
            txtTitle = itemView.findViewById(R.id.txt_title);
            imgLogo1 = itemView.findViewById(R.id.img_logo_01);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        private ImageView imgLogo1;
        private ImageView imgLogo2;
        private TextView txtTitle;

        public ViewHolder2(View itemView) {
            super(itemView);
            // 类似于BaseAdapter中的holder.txtTitle = convertView.findViewById()
            txtTitle = itemView.findViewById(R.id.txt_title);
            imgLogo1 = itemView.findViewById(R.id.img_logo_01);
            imgLogo2 = itemView.findViewById(R.id.img_logo_02);
        }
    }

    class ViewHolder3 extends RecyclerView.ViewHolder {
        private ImageView imgLogo1;
        private ImageView imgLogo2;
        private ImageView imgLogo3;
        private TextView txtTitle;

        public ViewHolder3(View itemView) {
            super(itemView);
            // 类似于BaseAdapter中的holder.txtTitle = convertView.findViewById()
            txtTitle = itemView.findViewById(R.id.txt_title);
            imgLogo1 = itemView.findViewById(R.id.img_logo_01);
            imgLogo2 = itemView.findViewById(R.id.img_logo_02);
            imgLogo3 = itemView.findViewById(R.id.img_logo_03);
        }
    }
}
