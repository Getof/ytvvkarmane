package ru.getof.ytvvkarmane.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.getof.ytvvkarmane.Components.RunData.RunData;
import ru.getof.ytvvkarmane.Components.RunList;
import ru.getof.ytvvkarmane.R;

public class RunAdapter extends RecyclerView.Adapter<RunAdapter.RunViewHolder> {

    private Context rContext;
    private List<RunData> runList;

    public RunAdapter(Context rContext, List<RunData> runList) {
        this.rContext = rContext;
        this.runList = runList;
    }

    @NonNull
    @Override
    public RunViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(rContext).inflate(R.layout.item_run_list,parent,false);
        return new RunViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RunViewHolder holder, int position) {
        holder.rRun.setText(runList.get(position).getRunText());
        holder.rDate.setText(runList.get(position).getRunStartDate());
    }

    @Override
    public int getItemCount() {
        return runList.size();
    }

    public class RunViewHolder extends RecyclerView.ViewHolder {

        TextView rRun, rDate;

        public RunViewHolder(@NonNull View itemView) {
            super(itemView);
            rRun = itemView.findViewById(R.id.textRunable);
            rDate = itemView.findViewById(R.id.textDatePub);
        }
    }
}
