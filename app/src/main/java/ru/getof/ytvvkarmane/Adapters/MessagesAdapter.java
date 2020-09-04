package ru.getof.ytvvkarmane.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ru.getof.ytvvkarmane.Components.Messages;
import ru.getof.ytvvkarmane.R;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessViewHolder> {

    private Context mContext;
    private ArrayList<Messages> mediaObjects;
    private OnItemClickListener listener;


    @Override
    public int getItemCount() {
        return mediaObjects.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MessagesAdapter(Context mContext, ArrayList<Messages> mediaObjects) {
        this.mContext = mContext;
        this.mediaObjects = mediaObjects;
    }

    @NonNull
    @Override
    public MessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_messages,parent,false);
        return new MessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessViewHolder holder, int position) {
        holder.tvName.setText(mediaObjects.get(position).getNameUser());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY", Locale.getDefault());
        holder.tvDate.setText(sdf.format(mediaObjects.get(position).getDateMessage()));
        Glide
                .with(mContext)
                .load(mediaObjects.get(position).getThumb())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imThumb);
    }

    public class MessViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDate;
        ImageView imThumb;

        public MessViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textViewName);
            tvDate = itemView.findViewById(R.id.textViewDate);
            imThumb = itemView.findViewById(R.id.ivMediaCoverImage);

            imThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }
}
