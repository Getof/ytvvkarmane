package ru.getof.ytvvkarmane.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.getof.ytvvkarmane.Components.VideoData.NewsList;
import ru.getof.ytvvkarmane.R;

public class VideoGalleryAdapter extends RecyclerView.Adapter<VideoGalleryAdapter.VidViewHolder> {

    private Context vContext;
    private List<NewsList> vGData;
    private OnItemClickListener listener;

    public VideoGalleryAdapter(Context vContext, List<NewsList> vGData) {
        this.vContext = vContext;
        this.vGData = vGData;
    }

    @NonNull
    @Override
    public VidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(vContext).inflate(R.layout.item_newvideo_player,parent,false);
        return new VidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VidViewHolder holder, int position) {
        holder.vTitle.setText(vGData.get(position).getTitle_news());
        holder.vDate.setText(vGData.get(position).getData_news());
        holder.vImage.setImageResource(R.drawable.vg_tumb_list);
    }

    @Override
    public int getItemCount() {
        return vGData.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



    public class VidViewHolder extends RecyclerView.ViewHolder {

        TextView vTitle, vDate;
        RelativeLayout vAction;
        ImageView vImage;

        public VidViewHolder(@NonNull final View itemView) {
            super(itemView);
            vTitle = itemView.findViewById(R.id.title_pr);
            vDate = itemView.findViewById(R.id.date_pr);
            vAction = itemView.findViewById(R.id.lv_action);
            vImage = itemView.findViewById(R.id.imageTumb);


            vAction.setOnClickListener(new View.OnClickListener() {
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
