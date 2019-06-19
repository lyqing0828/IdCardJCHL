package com.idcard.hnd.idcardjchl;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import com.idcard.hnd.idcardjchl.bean.Location;
import com.idcard.hnd.idcardjchl.bean.Region;
import com.idcard.hnd.idcardjchl.bean.Station;
import com.idcard.hnd.idcardjchl.common.ApiClient;
import com.idcard.hnd.idcardjchl.common.UIHelper;
import com.idcard.hnd.idcardjchl.sqlite.DataBaseUtilssss;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.System.getProperties;

/**
 * Created by Administrator on 2019/6/14.
 */

public class AppContext extends Application {
    private static AppContext instance;
    private DbUtils db;

    private boolean login = false;	//登录状态
    private int loginUid = 0;	//登录用户的id

    private Handler unLoginHandler = new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                UIHelper.ToastMessage(AppContext.this, getString(R.string.msg_login_error));
//                UIHelper.showLoginDialog(AppContext.this);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        //建库
        db = DbUtils.create(this, "location.db");
        Log.d("application", "DbUtils.create +");
        try {
            //建表
            db.createTableIfNotExist(Location.class);
            db.createTableIfNotExist(Region.class);
            db.createTableIfNotExist(Station.class);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DataBaseUtilssss.init(this);
        Log.d("application", "DataBaseUtilssss.init");

    }

    public String getProperty(String key){
        return AppConfig.getAppConfig(this).get(key);
    }

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public DbUtils getDb() {
        return db;
    }

    /**
     * 检测网络是否可用
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    //手机震动
    public void VibratorTime (){
        final Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        long [] pattern = {100,400,100,400}; // 停止 开启 停止 开启
        //第二个参数表示使用pattern第几个参数作为震动时间重复震动，如果是-1就震动一次
//                vibrator.vibrate(pattern,2);
        vibrator.vibrate(pattern,2);
        //每隔1s循环执行run方法
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                vibrator.cancel();
            }
        },5000);//延时5s执行
    }

    public boolean containsProperty(String key){
        Properties props = getProperties();
        return props.containsKey(key);
    }

    /**
     * 清除保存的缓存
     */
    public void cleanCookie()
    {
        removeProperty(AppConfig.CONF_COOKIE);
    }

    public void removeProperty(String...key){
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 用户注销
     */
    public void Logout() {
        ApiClient.cleanCookie();
        this.cleanCookie();
        this.login = false;
        this.loginUid = 0;
    }

    /**
     * 未登录或修改密码后的处理
     */
    public Handler getUnLoginHandler() {
        return this.unLoginHandler;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    /**
     * 获取App安装包信息
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if(info == null) info = new PackageInfo();
        return info;
    }

}
