package com.idcard.hnd.idcardjchl.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.idcard.hnd.idcardjchl.HttpApiUtils;
import com.idcard.hnd.idcardjchl.R;
import com.idcard.hnd.idcardjchl.adapter.LocationAdapter;
import com.idcard.hnd.idcardjchl.adapter.RegionAdapter;
import com.idcard.hnd.idcardjchl.adapter.StationAdapter;
import com.idcard.hnd.idcardjchl.bean.Location;
import com.idcard.hnd.idcardjchl.bean.Region;
import com.idcard.hnd.idcardjchl.bean.Station;
import com.idcard.hnd.idcardjchl.bean.URLs;
import com.idcard.hnd.idcardjchl.sqlite.DataBaseUtilssss;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterInforMation extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private TextView tv_jybh, tv_jybh_name, tv_bmdm;
    private Button back_btn;
    private Button btn_dataupdate;
    private Button btn_datacheck;
    private Spinner list_region, list_location, list_station;
    private RegionAdapter adapter_region;
    private LocationAdapter adapter_location;
    private StationAdapter adapter_station;
    List<Region> regionList = new ArrayList<>();
    List<Location> locationList = new ArrayList<>();
    List<Station> stationList = new ArrayList<>();

    private String bmdm;
    private String jwtmm;
    private String jybh;
    private String xm;

    private DbUtils db;
    private boolean isObtainLocation = true;

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
//                    list_region.setAdapter(adapter_region);
//                    list_location.setAdapter(adapter_location);
//                    list_station.setAdapter(adapter_station);
                    refreshdata(regionList,locationList,stationList);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerinfo_layout);
        init();
        Bundle bundle = getIntent().getExtras();
        bmdm = bundle.getString("bmdm");
        xm = bundle.getString("xm");
        jybh = bundle.getString("jybh");
        tv_jybh.setText(jybh);
        tv_jybh_name.setText(xm);
        tv_bmdm.setText(bmdm);
        try {
            List<Region> data1 = new ArrayList<>();
            List<Location> data2 = new ArrayList<>();
            List<Station> data3 = new ArrayList<>();
            regionList = (List<Region>) DataBaseUtilssss.queryDbAll(Region.class);
            locationList = (List<Location>) DataBaseUtilssss.queryDbAll(Location.class);
            stationList = (List<Station>) DataBaseUtilssss.queryDbAll(Station.class);

            if (data1.size() == 0 || data2.size() == 0 || data3.size() == 0 ) {
                requestStationData();
            } else {
                refreshdata(data1, data2,data3);
                Log.d("数据库获取", regionList.toString() + "");
                Log.d("数据库获取", locationList.toString() + "");
                Log.d("数据库获取", stationList.toString() + "");
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

//一级检查站
        list_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String regionid = regionList.get(position).getId();
                Log.d("一级站ID", regionid + "");
                try {
//                    List<StationInfo> stationdata = new ArrayList<>();
                    List<Station> stationdata = (List<Station>) DataBaseUtilssss.queryDbWhere(Station.class, "pid", regionid);
                    // 设置二级下拉列表的选项内容适配器
//                    adapter_station = new StationAdapter(RegisterInforMation.this, stationdata);
//                    list_station.setAdapter(adapter_station);
                    adapter_station.setStaionData(stationdata);
                    Log.d("Station数据", stationList.toString() + "");
                } catch (DbException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //二级站点
        list_station.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String locationid = stationList.get(position).getPid();
                Log.d("二级站ID", locationid + "");
                try {
//                    List<Location> locationdata = new ArrayList<>();
                    List<Location> locationdata = (List<Location>) DataBaseUtilssss.queryDbWhere(Location.class, "sid", locationid);
                    // 设置二级下拉列表的选项内容适配器
//                    adapter_location = new LocationAdapter(RegisterInforMation.this, locationdata);
//                    list_location.setAdapter(adapter_location);
                    adapter_location.setLocationData(locationdata);
                    Log.d("Location数据", locationdata.toString() + "");
                } catch (DbException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //三级站点
        list_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void init() {
        LinearLayout head_title = (LinearLayout) findViewById(R.id.title_include);
        title = (TextView) head_title.findViewById(R.id.title_tv);
        title.setText("重点人员核录系统");
        back_btn = (Button) head_title.findViewById(R.id.title_btn_back);
        back_btn.setOnClickListener(this);
        btn_datacheck = (Button) findViewById(R.id.btn_datacheck);
        btn_datacheck.setOnClickListener(this);
        btn_dataupdate = (Button) findViewById(R.id.btn_dataupdate);
        btn_dataupdate.setOnClickListener(this);
        tv_jybh = (TextView) findViewById(R.id.tv_jybh);
        tv_jybh_name = (TextView) findViewById(R.id.tv_jybh_name);
        tv_bmdm = (TextView) findViewById(R.id.tv_bmdm);
        list_region = (Spinner) findViewById(R.id.list_region);
        list_location = (Spinner) findViewById(R.id.list_location);
        list_station = (Spinner) findViewById(R.id.list_station);


    }

    private void refreshdata(List<Region> data1,List<Location> data2,List<Station> data3){
        adapter_region = new RegionAdapter(this, data1);
        list_region.setAdapter(adapter_region);
        Log.d("适配器数据",data1.toString());

        adapter_location = new LocationAdapter(this, data2);
        list_location.setAdapter(adapter_location);
        Log.d("适配器数据",data2.toString());

        adapter_station = new StationAdapter(this, data3);
        list_station.setAdapter(adapter_station);
        Log.d("适配器数据",data3.toString());

//        adapter_region.notifyDataSetChanged();
//        adapter_location.notifyDataSetChanged();
//        adapter_station.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_datacheck) {
            Intent intent = new Intent(this, CheckIdCardInformation.class);
            startActivity(intent);
        } else if (v == btn_dataupdate) {//数据更行
            requestStationData();
        }
        if (v == back_btn) {
            finish();
        }

    }



    private void requestStationData() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("share_data", Context.MODE_PRIVATE);
                String imei = sp.getString("imei", "");
                String simid = sp.getString("sim", "");
                isObtainLocation = sp.getBoolean("isObtainLocation", true);
                Log.d("设备号shared", "==" + imei + "==" + simid);
                JSONObject json = new JSONObject();
                try {
                    json.put("imei", imei);
                    json.put("jybh", jybh);
                    json.put("sim", simid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    String str = HttpApiUtils.httpApi(URLs.STATION_CHECK_VALIDATE_HTTP, json.toString());
                Log.d("检查站==", str.toString());
                    String code = parseLocationResult(str);
                    Message msg = new Message();
                    msg.what = Integer.parseInt(code);
                    handle.sendMessage(msg);


            }
        });
        t.start();
    }

    private String parseLocationResult(String result) {
        regionList.clear();
        locationList.clear();
        stationList.clear();
        String code = "";
        try {
            if (TextUtils.isEmpty(result)) {
                return code;
            }
            JSONObject dataJson = new JSONObject(result);
            code = dataJson.getString("code");
            String message = dataJson.getString("message");

            JSONObject jsonobject = dataJson.getJSONObject("data");
            JSONArray jsonarray = jsonobject.getJSONArray("region");
            regionList.clear();
            for (int i = 0; i < jsonarray.length(); ++i) {
                JSONObject temp = (JSONObject) jsonarray.get(i);
                Region regionInfo = new Region();
                regionInfo.setId(temp.getString("id"));
                regionInfo.setName(temp.getString("name"));
                regionList.add(regionInfo);
            }

            JSONArray jsonArray2 = jsonobject.getJSONArray("location");
            for (int i = 0; i < jsonArray2.length(); ++i) {
                JSONObject temp = (JSONObject) jsonArray2.get(i);
                Location locationInfo = new Location();
                locationInfo.setId(temp.getString("id"));
                locationInfo.setName(temp.getString("name"));
                locationInfo.setSid(temp.getString("sid"));
                locationList.add(locationInfo);
            }
            JSONArray jsonArray3 = jsonobject.getJSONArray("station");
            for (int i = 0; i < jsonArray3.length(); ++i) {
                JSONObject temp = (JSONObject) jsonArray3.get(i);
                Station stationInfo = new Station();
                stationInfo.setId(temp.getString("id"));
                stationInfo.setName(temp.getString("name"));
                stationInfo.setPid(temp.getString("pid"));
                stationList.add(stationInfo);
            }
//            Log.d("解析的数据", regionList.toString() + "");
//            Log.d("解析的数据", locationList.toString() + "");
//            Log.d("解析的数据", stationList.toString() + "");
//            Log.d("表名称", regionList.get(0).getClass() + "");
            DataBaseUtilssss.saveOrUpdateAll(regionList);
            DataBaseUtilssss.saveOrUpdateAll(locationList);
            DataBaseUtilssss.saveOrUpdateAll(stationList);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return code;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("销毁activity", "123456789");
    }
}
