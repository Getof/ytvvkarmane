package ru.getof.ytvvkarmane.MainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.getof.ytvvkarmane.Adapters.RunAdapter;
import ru.getof.ytvvkarmane.Components.RunData.RunData;
import ru.getof.ytvvkarmane.Components.RunList;
import ru.getof.ytvvkarmane.R;
import ru.getof.ytvvkarmane.RunActivity;
import ru.getof.ytvvkarmane.Utils.RetrofitClient;

public class FragmentRun extends Fragment {

    private RecyclerView rvRun;
    private FloatingActionButton fabAddRun;
    private RunAdapter runAdapter;
    private ProgressBar progressBarRun;
    private List<RunData> runDataList = new ArrayList<>();


    public static FragmentRun newInstance() {
        FragmentRun fragmentRun = new FragmentRun();
        return fragmentRun;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run, container, false);
        LoadRuns();
        rvRun = view.findViewById(R.id.rv_run);
        fabAddRun = view.findViewById(R.id.fab_add_run);
        progressBarRun = view.findViewById(R.id.progressBarRun);

        fabAddRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RunActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void LoadRuns() {
        if (progressBarRun != null) progressBarRun.setVisibility(View.VISIBLE);

        RetrofitClient.getInstance()
                .getApi()
                .getRunListData()
                .enqueue(new Callback<RunList>() {
                    @Override
                    public void onResponse(Call<RunList> call, Response<RunList> response) {
                        if (response.isSuccessful()){
                            RunReady(response.body());
                            progressBarRun.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<RunList> call, Throwable t) {

                    }
                });
    }

    private void RunReady(RunList body) {
        runAdapter = new RunAdapter(getActivity(), body.getRunList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvRun.setLayoutManager(layoutManager);
        rvRun.setAdapter(runAdapter);
    }
}
