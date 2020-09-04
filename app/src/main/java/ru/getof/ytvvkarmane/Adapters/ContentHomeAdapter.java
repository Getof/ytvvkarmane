package ru.getof.ytvvkarmane.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ru.getof.ytvvkarmane.Components.VideoData.NewsList;
import ru.getof.ytvvkarmane.R;
import ru.getof.ytvvkarmane.Utils.SquareLayout;

public class ContentHomeAdapter extends RecyclerView.Adapter<ContentHomeAdapter.CViewHolder> {

    private Context nContext;
    private List<NewsList> nCData;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ContentHomeAdapter(Context nContext, List<NewsList> nCData) {
        this.nContext = nContext;
        this.nCData = nCData;
    }

    @NonNull
    @Override
    public CViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(nContext).inflate(R.layout.item_newvideo,parent,false);
        return new CViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CViewHolder holder, int position) {

        Glide
                .with(nContext)
                .load(nCData.get(position).getTumb_news())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.nImage);
        holder.nTitle.setText(nCData.get(position).getTitle_news());
        holder.nDate.setText("от: "+nCData.get(position).getData_news());

    }

    @Override
    public int getItemCount() {
        return nCData.size();
    }

    public class CViewHolder extends RecyclerView.ViewHolder{

        ImageView nImage;
        TextView nTitle, nDate;
        CardView nCardView;

        public CViewHolder(@NonNull final View itemView) {
            super(itemView);
            nImage = itemView.findViewById(R.id.tumbVideo);
            nTitle = itemView.findViewById(R.id.titleVideo);
            nDate = itemView.findViewById(R.id.dateVideo);
            nCardView = itemView.findViewById(R.id.itemVideo);

            nCardView.setOnClickListener(new View.OnClickListener() {
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
