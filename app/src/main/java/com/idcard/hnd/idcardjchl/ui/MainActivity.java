package com.idcard.hnd.idcardjchl.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.idcard.hnd.idcardjchl.AppContext;
import com.idcard.hnd.idcardjchl.HttpApiUtils;
import com.idcard.hnd.idcardjchl.R;
import com.idcard.hnd.idcardjchl.bean.URLs;
import com.idcard.hnd.idcardjchl.common.StringUtils;
import com.idcard.hnd.idcardjchl.common.UIHelper;
import com.idcard.hnd.idcardjchl.common.UpdateManager;
import com.lidroid.xutils.DbUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {
    private EditText edt_jybh, edt_login_password;
    private Button btn_login;
    private String account;
    private String pwd;
    private Handler mHandler;
    private String bmdm;
    private String jwtmm;
    private String jybh;
    private String xm;
    private boolean isFirstin = true;
    private DbUtils db;

    private String vVersion;

    private AppContext appContext;//全局Context

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContext = (AppContext) getApplication();
        //网络链接判断
        if (!appContext.isNetworkConnected()) {
            UIHelper.ToastMessage(this, R.string.network_not_connected);
        }

        edt_jybh = (EditText) findViewById(R.id.edt_jybh);
        edt_login_password = (EditText) findViewById(R.id.edt_login_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = edt_jybh.getText().toString().trim();
                pwd = edt_login_password.getText().toString().trim();
                //判断输入
                if (StringUtils.isEmpty(account)) {
                    UIHelper.ToastMessage(v.getContext(), getString(R.string.login_edit_user_id_hint));
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    UIHelper.ToastMessage(v.getContext(), getString(R.string.login_edit_password_hint));
                    return;
                }
                login(account, pwd);

            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                //获取bundle对象的值
                Bundle b = msg.getData();
                String code = b.getString("code");
                String message = b.getString("message");

                Log.d("返回数据", code + "--" + message);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//                checkupdate();
            }
        };

        SharedPreferences sp = getSharedPreferences("share_data", Context.MODE_PRIVATE);
        isFirstin = sp.getBoolean("isFirstIn", true);
        if (true) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    //注册设备
                    String simId = getSimId();//sim卡号
                    String imei = setPhoneStateManifest();//设备id
                    JSONObject json = new JSONObject();
                    try {
                        json.put("imei", imei);
                        json.put("jybh", "");
                        json.put("sim", simId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("设备注册请求参数=", json.toString());
                    SharedPreferences sp = getSharedPreferences("share_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("imei", imei);
                    editor.putString("sim", simId);
                    String str = HttpApiUtils.httpApi(URLs.DEVICE_REGISTER_VALIDATE_HTTP, json.toString());
                    String code = parseDeviceRegister(str);
                    if (code.equals("0")) {
                        editor.putBoolean("isFirstIn", false);
                    } else {
                        editor.putBoolean("isFirstIn", true);
                    }
                    editor.commit();
//                    try{
//                        String str = HttpApiUtils.HttpRequest(register_url, json.toString());
//
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }

                }
            });
            t.start();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        edt_login_password.setText("");
    }

    //登录验证
    private void login(final String account, final String pwd) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject json = new JSONObject();
                try {
                    json.put("jybh", account);
                    json.put("pwd", pwd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String str = HttpApiUtils.httpApi(URLs.LOGIN_VALIDATE_HTTP, json.toString());
                String code = parseLoginResult(str);
                if (code.equals("0")) {
                    Intent intent = new Intent(MainActivity.this, RegisterInforMation.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("jybh", jybh);
                    bundle.putString("xm", xm);
                    bundle.putString("bmdm", bmdm);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {

                }
            }
        });
        t.start();
    }


    private String parseLoginResult(String result) {
        String code = "";
        try {
            if (TextUtils.isEmpty(result)) {
//                Toast.makeText(this, "返回数据为空", Toast.LENGTH_SHORT).show();
                return code;
            }
            JSONObject dataJson = new JSONObject(result);
            code = dataJson.getString("code");
            String message = dataJson.getString("message");
            if (code == "" || code.equals("1")) {
                Message msg = mHandler.obtainMessage();
                //msg.arg1 = 123;//传递整型数据
                //msg.obj = "asd";传递object类型
                //利用bundle对象来传值
                Bundle b = new Bundle();
                b.putString("code", code);
                b.putString("message", message);
                msg.setData(b);
                msg.sendToTarget();
                return code;
            }
            JSONObject dataJson2 = dataJson.getJSONObject("data");
            bmdm = dataJson2.getString("bmdm");
            jwtmm = dataJson2.getString("jwtmm");
            jybh = dataJson2.getString("jybh");
            xm = dataJson2.getString("xm");
            Log.d("数据", code + "--" + bmdm + "--" + jwtmm + "--" + jybh + "--" + xm + "--" + message);

            SharedPreferences sp = getSharedPreferences("share_data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("jybh", jybh);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    private String parseDeviceRegister(String result) {
        String code = "";
        try {
            if (result == null) {
                return code;
            }
            JSONObject dataJson = new JSONObject(result);
            code = dataJson.getString("code");
            String message = dataJson.getString("message");
            Message msg = mHandler.obtainMessage();
            //msg.arg1 = 123;//传递整型数据
            //msg.obj = "asd";传递object类型
            //利用bundle对象来传值
            Bundle b = new Bundle();
            b.putString("code", code);
            b.putString("message", message);
            msg.setData(b);
            msg.sendToTarget();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    private String setPhoneStateManifest() {
        String IMEI = "";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // toast("需要动态获取权限");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            // toast("不需要动态获取权限");
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            IMEI = tm.getDeviceId();
        }
        return IMEI;
    }

    public String getSimId() {
        String simId = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
        return (simId != null) ? simId : "";
    }

    private void checkupdate() {
//        imei	设备编号
//        jybh	警员编号
//        sim	sim卡id
//        ver	当前版本
        //获取客户端版本信息
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            vVersion = info.versionName;
            int vVersonCode = info.versionCode;
            Log.d("版本信息", "versionName=" + vVersion + "--versionCode=" + vVersonCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        //注册设备
        String simId = getSimId();//sim卡号
        String imei = setPhoneStateManifest();//设备id
        JSONObject json = new JSONObject();
        try {
            json.put("imei", imei);
            json.put("jybh", "123456");
            json.put("sim", simId);
            json.put("version", vVersion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("软件更新接口请求参数=", json.toString());
        UpdateManager.getUpdateManager().checkAppUpdate(this, true, json.toString());
    }


}
