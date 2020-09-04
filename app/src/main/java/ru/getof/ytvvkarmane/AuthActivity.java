package ru.getof.ytvvkarmane;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ru.getof.ytvvkarmane.AppInterfaces.SwitchAuthFragments;
import ru.getof.ytvvkarmane.Utils.AuthUser;

public class AuthActivity extends AppCompatActivity implements SwitchAuthFragments {

    private NavController navController;
    private Toolbar toolbarAuth;
    private AuthUser authUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        authUser = AuthUser.getInstance();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbarAuth = findViewById(R.id.toolbarAuth);
        toolbarAuth.setTitle("Войти в аккаунт");
        setSupportActionBar(toolbarAuth);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        navController = Navigation.findNavController(this,R.id.nav_host_fragment);

    }

    @Override
    public void PressButtonSignIn(final String userEmail, final String userPass) {

        mAuth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            authUser.setUser(true);
                            Intent intent = new Intent();
                            intent.putExtra("login", userEmail);
                            intent.putExtra("password", userPass);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Ошибка авторизации!",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void PressButtonGetAcc() {
        toolbarAuth.setTitle("Регистрация");
        navController.navigate(R.id.signUpFragment);
    }

    @Override
    public void PressButtonRegAcc(final String userName, String userEmail, String userPass) {

        mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> userUp = new HashMap<>();
                            userUp.put("Name", userName);
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                db.collection("users")
                                        .document(user.getUid())
                                        .set(userUp)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(),"Успешная регистрация!", Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                            toolbarAuth.setTitle("Войти в аккаунт");
                            navController.popBackStack();
                        } else {
                            Toast.makeText(getApplicationContext(),"Такой пользователь существует!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (navController.getCurrentDestination().getId() == R.id.signInFragment){
                    onBackPressed();
                } else {
                    toolbarAuth.setTitle("Войти в аккаунт");
                    navController.popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
