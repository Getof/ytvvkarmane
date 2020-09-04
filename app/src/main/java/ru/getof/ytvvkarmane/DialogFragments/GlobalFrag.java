package ru.getof.ytvvkarmane.DialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.getof.ytvvkarmane.R;

public class GlobalFrag extends DialogFragment implements View.OnClickListener {

    private static final String BUNDLE_CONTENT = "bundle_content";
    private String content;
    private static final String HELP_CONT = "PressHelp";
    private static final String NO_INTERNET = "NoInternet";

    public static GlobalFrag newInstance(String content) {
        GlobalFrag f = new GlobalFrag();
        Bundle args = new Bundle();
        args.putString(BUNDLE_CONTENT,content);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar);
        if (getArguments() != null && getArguments().containsKey(BUNDLE_CONTENT)) {
            content = getArguments().getString(BUNDLE_CONTENT);
        } else {
            throw new IllegalArgumentException("Ошибка инициализации...");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.frag_global_dialog, null);
        TextView tx = view.findViewById(R.id.textGlobalFrag);
        if (content.equals(HELP_CONT)) {
            tx.setText("В разработке...");
        } else if (content.equals(NO_INTERNET)) {
            tx.setText("Подключите интернет и перезапустите приложение...");
        }
        view.findViewById(R.id.img_btn_close).setOnClickListener(this);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_btn_close) {
            dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(params);
    }
}
