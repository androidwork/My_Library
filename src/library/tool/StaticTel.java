package library.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 类描述： 常用静态方法合集
 * 
 * @author 祝志国:2014-5-22
 */
public class StaticTel
{
    
    /**
     * 获取urlencode过的数据
     * @param str
     * @return
     */
    public static String getStringToUrlEncode(String str)
    {
        try
        {
            str = URLEncoder.encode(str, "utf-8");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return str;
    }
    
    /**
     * Toast文字
     * @param context 上下文对象
     * @param text显示内容
     */
    public static void ToastShow(Context context, String text)
    {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    
    /**
     * toast 位置
     * @param context
     * @param text
     * @param position 0：中间 1：上方 -1：下方
     */
    public static void ToastShow(Context context, String text, int position)
    {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        int po = position;
        int x = 0;
        switch (position)
        {
            case 0:
                po = Gravity.CENTER;
                break;
            case 1:
                po = Gravity.TOP;
                x = 10;
                break;
            case -1:
                po = Gravity.BOTTOM;
                break;
            default:
                po = Gravity.CENTER;
                break;
        }
        toast.setGravity(po, 0, x);
        toast.show();
    }
    
    /**
     * 获取string资源
     * @param context
     * @param id
     * @return
     */
    public static String getStringResources(Context context, int id)
    {
        return context.getResources().getString(id);
    }
    
    /**
     * 自动登录传入集合
     * @param user用户名
     * @param pwd密码
     * @param activityActivity标志
     * @return
     */
    public static Bundle setLoginBund(String user, String pwd, String activity)
    {
        Bundle bund = new Bundle();
        bund.putString("user", user);
        bund.putString("pwd", pwd);
        bund.putString("activity", activity);
        return bund;
    }
    
    /**
     * debug信息
     * @param msg
     */
    public static void Debug(String msg)
    {
        Log.d("okok", msg);
    }
    
    /**输出错误信息
     * @param msg
     */
    public static void DebugError(String msg)
    {
        Log.e("error", msg);
    }
    
    /**
     * @param name
     * @return
     */
    public static boolean isChineseName(String name)
    {
        Pattern pattern = Pattern.compile("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]){1,4}$");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find())
        {
            return true;
        }
        return false;
    }
}
