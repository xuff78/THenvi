package environment.th.com.thenvi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.services.BDLocationService;

/**
 * Created by Administrator on 2016/3/9.
 */
public class LoginAct  extends AppCompatActivity implements View.OnClickListener {

    public BDLocationService locationService;
    public Fragment frg;
    public View selectBtn=null;
    private int selectPos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        findViewById(R.id.loginBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                startActivity(new Intent(LoginAct.this, MainMenuAct.class));
                break;
            case R.id.menu_btn2:
                break;
        }
    }
}
