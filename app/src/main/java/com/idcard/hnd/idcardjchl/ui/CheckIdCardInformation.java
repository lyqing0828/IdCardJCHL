package com.idcard.hnd.idcardjchl.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idcard.hnd.idcardjchl.AppContext;
import com.idcard.hnd.idcardjchl.HttpApiUtils;
import com.idcard.hnd.idcardjchl.R;
import com.idcard.hnd.idcardjchl.bean.CheckIdCardInfo;
import com.idcard.hnd.idcardjchl.bean.URLs;
import com.idcard.hnd.idcardjchl.common.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class CheckIdCardInformation extends Activity implements View.OnClickListener {
    private TextView title;
    private LinearLayout background_line;
    private LinearLayout show_preson_linear;
    private EditText input_idcard_edt, input_phone_edt;
    private TextView tv_name, tv_gender, tv_nation, tv_date_birth, tv_address;
    private Button back_btn, check_btn;//核查/批次核查
    private Button registration_btn;

    private String imei;
    private String simid;
    private String jybh;
    private String idCard;
    private CheckIdCardInfo checkIdCard = new CheckIdCardInfo();

    Vibrator vibrator;//震动

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //三中情况：绿色通过，黄色怀疑，红色抓捕
            //0非重點人員
            if (msg.what == 1) {
//                if(checkIdCard != null){
//                    tv_name.setText(checkIdCard.getXm().toString().trim());
////                        tv_gender.setText(checkIdCard.().toString().trim());
////                        tv_nation.setText(checkIdCard.getXm().toString().trim());
////                        tv_date_birth.setText(checkIdCard.getXm().toString().trim());
////                        tv_addres.setText(checkIdCard.getXm().toString().trim());
//                }
                show_preson_linear.setVisibility(View.VISIBLE);
                background_line.setBackgroundResource(R.color.green);
            }else if(msg.what == 0){//0重點人員
                background_line.setBackgroundResource(R.color.red);
                show_preson_linear.setVisibility(View.VISIBLE);
                tv_name.setText(checkIdCard.getXm().toString().trim());

                AppContext appContext = (AppContext) getApplication();
                appContext.VibratorTime();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_id_card_information);
        background_line = (LinearLayout) findViewById(R.id.background_check_idcard);
        show_preson_linear = (LinearLayout) findViewById(R.id.show_preson_liner);
        LinearLayout head_title = (LinearLayout) findViewById(R.id.title_include);
        title = (TextView) head_title.findViewById(R.id.title_tv);
        title.setText("人员核查");
        back_btn = (Button) head_title.findViewById(R.id.title_btn_back);
        back_btn.setOnClickListener(this);
        input_idcard_edt = (EditText) findViewById(R.id.input_idcard_edt);
        check_btn = (Button) findViewById(R.id.check_btn);
        check_btn.setOnClickListener(this);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_nation = (TextView) findViewById(R.id.tv_nation);
        tv_date_birth = (TextView) findViewById(R.id.tv_date_of_birth);
        tv_address = (TextView) findViewById(R.id.tv_address);

        registration_btn = (Button) findViewById(R.id.Registration_phone);
        registration_btn.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("share_data", Context.MODE_PRIVATE);
        imei = sp.getString("imei", "");
        simid = sp.getString("sim", "");
        jybh = sp.getString("jybh", "");

    }

    @Override
    public void onClick(View v) {
        if (v == back_btn) {
            finish();
        } else if (v == check_btn) {
            idCard = input_idcard_edt.getText().toString().trim();
            if (!StringUtils.isEmpty(idCard)) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject json = new JSONObject();
                        try {
                            json.put("imei", imei);
                            json.put("jybh", jybh);
                            json.put("sim", simid);
                            json.put("sfzh", idCard);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String str = HttpApiUtils.httpApi(URLs.PERSON_CHECK_VALIDATE_HTTP, json.toString());
                        parsePersonCheck(str);
                    }
                });
                t.start();
            }
        }else if (v == registration_btn){//等級電話

        }
    }

    private void parsePersonCheck(String str) {
        if (StringUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jsonob = new JSONObject(str);
            String code = jsonob.getString("code");
            String message = jsonob.getString("message");
            if (code.equals("0")) {
                JSONObject jsonob2 = jsonob.getJSONObject("data");
                String bz = jsonob2.getString("bz");
                String sfzh = jsonob2.getString("sfzh");
                String xm = jsonob2.getString("xm");
                checkIdCard.setCode(code);
                checkIdCard.setMessage(message);
                checkIdCard.setBz(bz);
                checkIdCard.setSfzh(sfzh);
                checkIdCard.setXm(xm);
            }
            Message msg = new Message();
            msg.what = Integer.parseInt(code);
            handler.sendMessage(msg);


        } catch (JSONException e) {
            e.printStackTrace();


        }

    }
}
