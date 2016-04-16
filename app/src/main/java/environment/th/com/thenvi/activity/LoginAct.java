package environment.th.com.thenvi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.services.BDLocationService;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.SharedPreferencesUtil;
import environment.th.com.thenvi.utils.ToastUtils;

/**
 * Created by Administrator on 2016/3/9.
 */
public class LoginAct  extends AppCompatActivity implements View.OnClickListener {

    public BDLocationService locationService;
    public Fragment frg;
    public View selectBtn=null;
    private int selectPos=0;
    private HttpHandler handler;
    private EditText psdEdt, nameEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferencesUtil.setString(this, ConstantUtil.AreaInfo, SharedPreferencesUtil.FAILURE_STRING);
        initHandler();
        initView();
    }

    private void initHandler() {
        handler=new HttpHandler(this, new CallBack(this){
            @Override
            public void doSuccess(String method, String jsonData) {
                startActivity(new Intent(LoginAct.this, MainMenuAct.class));
                finish();
            }
        });
    }

    private void initView() {
        psdEdt=(EditText)findViewById(R.id.psdEdt);
        nameEdt=(EditText)findViewById(R.id.nameEdt);
        psdEdt.setText("123123");
        nameEdt.setText("cetest");
        findViewById(R.id.loginBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                String name=nameEdt.getText().toString().trim();
                String psw=psdEdt.getText().toString().trim();
                if(name.length()==0)
                    ToastUtils.displayTextShort(LoginAct.this, "请输入用户名");
                if(psw.length()==0)
                    ToastUtils.displayTextShort(LoginAct.this, "请输入密码");
                else
                    handler.userLogin(name, psw);
                break;
            case R.id.menu_btn2:
                break;
        }
    }
}
