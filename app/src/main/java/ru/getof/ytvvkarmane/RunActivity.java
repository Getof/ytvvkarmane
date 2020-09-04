package ru.getof.ytvvkarmane;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ru.getof.ytvvkarmane.AppInterfaces.SwitchRunFragments;
import ru.getof.ytvvkarmane.DialogFragments.HelpRunDialog;

public class RunActivity extends AppCompatActivity implements SwitchRunFragments {

    private Toolbar runToolbar;
    private NavController navRunController;
    private Menu runMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        runToolbar = findViewById(R.id.runToolbar);
        runToolbar.setTitle("Подать объявление");
        setSupportActionBar(runToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        navRunController = Navigation.findNavController(this, R.id.nav_host_run_frag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.runMenu = menu;
        getMenuInflater().inflate(R.menu.run_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (navRunController.getCurrentDestination().getId() == R.id.addRunFragment){
                    onBackPressed();
                } else {
                    //runToolbar.setTitle("Подать объявление");
                    //navRunController.popBackStack();
                    finish();
                }
                return true;
            case R.id.action_help:
                HelpRunDialog helpRunDialog = HelpRunDialog.newInstance();
                helpRunDialog.show(getSupportFragmentManager(), null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void PressPayment(String idRun, String urlRun) {
        runToolbar.setTitle("Оплата");
        Bundle bundle = new Bundle();
        bundle.putString("idRun", idRun);
        bundle.putString("urlRun", urlRun);
        navRunController.navigate(R.id.paymentFragment, bundle);
    }
}
