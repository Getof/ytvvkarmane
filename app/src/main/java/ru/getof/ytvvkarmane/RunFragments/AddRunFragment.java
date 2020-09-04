package ru.getof.ytvvkarmane.RunFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.getof.ytvvkarmane.AppInterfaces.SwitchRunFragments;
import ru.getof.ytvvkarmane.Components.RunId;
import ru.getof.ytvvkarmane.R;
import ru.getof.ytvvkarmane.Utils.RetrofitClient;

public class AddRunFragment extends Fragment {

    private SwitchRunFragments switchRunFragments;
    private Button btnPayment;
    private TextView infoSum, infoDay;
    private EditText infoRun;
    private ImageButton btnUp, btnDown;
    private int id;
    private int kolChar;
    private int Sum = 250;
    private int Days = 1;
    private int Price = 3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_add_run, container, false);
        btnPayment = v.findViewById(R.id.buttonPayment);
        btnUp = v.findViewById(R.id.btn_day_up);
        btnDown = v.findViewById(R.id.btn_day_down);
        infoRun = v.findViewById(R.id.info_run);
        infoSum = v.findViewById(R.id.info_sum);
        infoDay = v.findViewById(R.id.info_day);

        TextWatcher textRunListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getSum();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        infoRun.addTextChangedListener(textRunListener);

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadIdRun();
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonUp();
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonDown();
            }
        });

        return v;
    }

    @SuppressLint("SetTextI18n")
    private void clickButtonDown() {
        if (Days>1) {
            Days = Days - 1;
            Sum=250;
            kolChar = infoRun.getText().length()-70;
            Sum += (kolChar>0) ? kolChar*Price : 0;
            infoDay.setText(Integer.toString(Days));
            Sum = Sum*Days;
            infoSum.setText(Integer.toString(Sum));
        }
    }

    @SuppressLint("SetTextI18n")
    private void clickButtonUp() {
        Days = Days+1;
        Sum = 250;
        kolChar = infoRun.getText().length()-70;
        Sum += (kolChar>0) ? kolChar*Price : 0;
        infoDay.setText(Integer.toString(Days));
        Sum = Sum*Days;
        infoSum.setText(Integer.toString(Sum));
    }

    private void getSum() {
        Sum = 250;
        kolChar = infoRun.getText().length()-70;
        Sum += (kolChar>0) ? kolChar*Price : 0;
        Sum = Sum*Days;
        infoSum.setText(Integer.toString(Sum));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SwitchRunFragments) {
            switchRunFragments = (SwitchRunFragments) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    private void LoadIdRun() {
        RetrofitClient.getInstance()
                .getApi()
                .getRunId(Integer.toString(Sum), infoRun.getText().toString(), infoDay.getText().toString())
                .enqueue(new Callback<RunId>() {
                    @Override
                    public void onResponse(Call<RunId> call, Response<RunId> response) {
                        if (response.isSuccessful()){
                            idRunTask(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<RunId> call, Throwable t) {

                    }
                });
    }

    private void idRunTask(RunId body) {
        infoRun.setText("");
        infoSum.setText("");
        switchRunFragments.PressPayment(body.getIdRun(), body.getUrlRun());
    }
}
