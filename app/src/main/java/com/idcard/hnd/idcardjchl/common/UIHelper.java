package com.idcard.hnd.idcardjchl.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.idcard.hnd.idcardjchl.AppManager;
import com.idcard.hnd.idcardjchl.R;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * Created by Administrator on 2019/6/17.
 */

public class UIHelper {
    private final static String TAG = "UIHelper";

    /**
     * 弹出Toast消息
     *
     * @param msg
     */
    public static void ToastMessage(Context cont, String msg) {
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, int msg) {
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, String msg, int time) {
        Toast.makeText(cont, msg, time).show();
    }

    /**
     * 发送App异常崩溃报告
     *
     * @param cont
     * @param crashReport
     */
    public static void sendAppCrashReport(final Context cont,
                                          final String crashReport) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cont);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(R.string.app_error);
        builder.setMessage(R.string.app_error_message);
        builder.setPositiveButton(R.string.submit_report,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 发送异常报告
                        Intent i = new Intent(Intent.ACTION_SEND);
                        // i.setType("text/plain"); //模拟器
                        i.setType("message/rfc822"); // 真机
                        i.putExtra(Intent.EXTRA_EMAIL,
                                new String[] { "jxsmallmouse@163.com" });
                        i.putExtra(Intent.EXTRA_SUBJECT,
                                "开源中国Android客户端 - 错误报告");
                        i.putExtra(Intent.EXTRA_TEXT, crashReport);
                        cont.startActivity(Intent.createChooser(i, "发送错误报告"));
                        // 退出
                        AppManager.getAppManager().AppExit(cont);
                    }
                });
        builder.setNegativeButton(R.string.sure,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 退出
                        AppManager.getAppManager().AppExit(cont);
                    }
                });
        builder.show();
    }

//    /**
//     * 清除app缓存
//     *
//     * @param activity
//     */
//    public static void clearAppCache(Activity activity) {
//        final AppContext ac = (AppContext) activity.getApplication();
//        final Handler handler = new Handler() {
//            public void handleMessage(Message msg) {
//                if (msg.what == 1) {
//                    ToastMessage(ac, "缓存清除成功");
//                } else {
//                    ToastMessage(ac, "缓存清除失败");
//                }
//            }
//        };
//        new Thread() {
//            public void run() {
//                Message msg = new Message();
//                try {
//                    ac.clearAppCache();
//                    msg.what = 1;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    msg.what = -1;
//                }
//                handler.sendMessage(msg);
//            }
//        }.start();
//    }

}
