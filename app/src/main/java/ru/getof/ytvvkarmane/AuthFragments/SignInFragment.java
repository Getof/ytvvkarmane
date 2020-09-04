package ru.getof.ytvvkarmane.AuthFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import ru.getof.ytvvkarmane.AppInterfaces.SwitchAuthFragments;
import ru.getof.ytvvkarmane.R;

public class SignInFragment extends Fragment {

    private SwitchAuthFragments switchAuthFragments;
    private EditText editLogin, editPassword;
    private TextInputLayout layoutLogin, layoutPass;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_sign_in, container, false);

        Button btnSignIn = v.findViewById(R.id.buttonLogin);
        Button btnGetAcc = v.findViewById(R.id.buttonGetAcc);
        editLogin = v.findViewById(R.id.main_editEmail);
        editPassword = v.findViewById(R.id.main_editPassword);
        layoutLogin = v.findViewById(R.id.layoutEmail);
        layoutPass = v.findViewById(R.id.layoutPassword);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidForm()){
                    switchAuthFragments.PressButtonSignIn(editLogin.getText().toString(),editPassword.getText().toString());
                }
            }
        });

        btnGetAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchAuthFragments.PressButtonGetAcc();
            }
        });
        return v;
    }

    private Boolean isValidForm(){
        boolean isValid = true;

        String loginUser = editLogin.getText().toString();
        String password = editPassword.getText().toString();

        if (loginUser.isEmpty()){
            layoutLogin.setErrorEnabled(true);
            layoutLogin.setError("Введите E-mail");
            isValid = false;
        } else {
            layoutLogin.setErrorEnabled(false);
        }

        if (password.isEmpty()){
            layoutPass.setErrorEnabled(true);
            layoutPass.setError("Укажите пароль");
            isValid = false;
        } else {
            layoutPass.setErrorEnabled(false);
        }

        return isValid;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SwitchAuthFragments) {
            switchAuthFragments = (SwitchAuthFragments) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }
}
