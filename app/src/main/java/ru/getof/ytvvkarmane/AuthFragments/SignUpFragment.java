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
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import ru.getof.ytvvkarmane.AppInterfaces.SwitchAuthFragments;
import ru.getof.ytvvkarmane.R;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class SignUpFragment extends Fragment {

    private SwitchAuthFragments switchAuthFragments;
    private EditText editEmail, editPassword, editName;
    private TextInputLayout layoutEmail, layoutPass, layoutName;
    private AppCompatCheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_sign_up, container, false);

        Button btnSignUp = v.findViewById(R.id.buttonRegReg);
        editName = v.findViewById(R.id.reg_editName);
        editEmail = v.findViewById(R.id.reg_editEmail);
        editPassword = v.findViewById(R.id.reg_editPassword);
        layoutName = v.findViewById(R.id.layoutRegName);
        layoutEmail = v.findViewById(R.id.layoutRegEmail);
        layoutPass = v.findViewById(R.id.layoutRegPass);
        checkBox = v.findViewById(R.id.checkBox);

        /*MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        mask.setHideHardcodedHead(true);
        FormatWatcher formatWatcher = new MaskFormatWatcher(mask);
        formatWatcher.installOn(editEmail);*/

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidForm()){
                    switchAuthFragments.PressButtonRegAcc(editName.getText().toString(),
                            editEmail.getText().toString(),
                            editPassword.getText().toString());
                }
            }
        });

        return v;
    }

    private Boolean isValidForm(){
        boolean isValid = true;

        String name = editName.getText().toString();
        String phone = editEmail.getText().toString();
        String password = editPassword.getText().toString();


        if (name.isEmpty()){                        //Validate Name
            layoutName.setErrorEnabled(true);
            layoutName.setError("Придумайте имя!");
            isValid = false;
        } else {
            layoutName.setErrorEnabled(false);
        }

        if (!isValidEmail(phone)){                        //Validate Phone
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Введите корректный адрес почты!");
            isValid = false;
        } else {
            layoutEmail.setErrorEnabled(false);
        }

        if (password.isEmpty()){                        //Validate Pass
            layoutPass.setErrorEnabled(true);
            layoutPass.setError("Укажите пароль");
            isValid = false;
        } else {
            layoutPass.setErrorEnabled(false);
        }

        if (!checkBox.isChecked()){
            isValid = false;
        }

        return isValid;
    }

    @NonNull
    private Boolean isValidEmail(String target){
        return target != null && target.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\\b");
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
