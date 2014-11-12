package library.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import library.tool.StaticTel;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

/**
 * 类描述：http
 * 
 * @author 祝志国:2014-7-4
 */
public class LibraryHttp
{
    
    public LibraryHttp()
    {
    }
    
    /**
     * inputstream转字符串类型
     * @param is inputstream
     * @param encodeSet 字符编码
     * @return
     * 
     */
    public String getHttpInputStreamToString(InputStream is, String encodeSet)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            InputStreamReader inputReader = new InputStreamReader(is, encodeSet);
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
            {
                sb.append(line);
            }
            bufReader.close();
            inputReader.close();
            if (is != null)
            {
                is.close();
            }
        } catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    /**
     * 获取inputstream类型的网络数据
     * @param urlPath 访问的url地址
     * @return
     */
    public InputStream getHttpInputStreamToGet(Context context, String urlPath)
    {
        StaticTel.Debug("Get------->" + urlPath);
        boolean net = isCheckNet(context);
        if (!net)
        {
            return null;
        }
        InputStream inputStream = null;
        HttpURLConnection conn = null;
        try
        {
            for (int i = 0; i < 2; i++)
            {
                URL url = new URL(urlPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(8000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                inputStream = conn.getInputStream();
                if (inputStream != null)
                {
                    return inputStream;
                }
            }
        } catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 获取网络url所返回的String字符
     * @param context 上下文对象
     * @param handler获取完成/失败所通知的handler
     * @param url网络地址
     * @param encode解析字符的编码格式
     *            msg.what==8080 失败返回“”
     */
    public void getHttpUrlToString(final Context context, final Handler handler, final String url, final String encode)
    {
        new Thread(new Runnable()
        {
            
            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                InputStream is = getHttpInputStreamToGet(context, url);
                if (is == null)
                {
                    handler.sendMessage(handler.obtainMessage(8080, ""));
                } else
                {
                    String str = getHttpInputStreamToString(is, encode);
                    handler.sendMessage(handler.obtainMessage(8080, str));
                }
            }
        }).start();
    }
    
    /**
     * 获取网络url所返回的String字符
     * @param context 上下文对象
     * @param streamTostring:HttpStreamToString接口
     * @param url网络地址
     * @param encode解析字符的编码格式
     *            msg.what==8080 失败返回“”
     */
    public void getHttpUrlToString(final Context context, final HttpStreamToString streamTostring, final String url, final String encode)
    {
        new Thread(new Runnable()
        {
            
            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                InputStream is = getHttpInputStreamToGet(context, url);
                streamTostring.httpToString(is == null ? "" : getHttpInputStreamToString(is, encode));
            }
        }).start();
    }
    
    /**
     * @param context
     * @param url
     * @param preamList:list.add(new BasicNameValuePair(name, value));
     * @return
     */
    public InputStream getInputStreamHttpClientToPost(Context context, String url, List<NameValuePair> preamList)
    {
        boolean net = isCheckNet(context);
        if (!net)
        {
            return null;
        }
        InputStream inputstream = null;
        try
        {
            HttpPost httpRequest = new HttpPost(url);
            httpRequest.setEntity(new UrlEncodedFormEntity(preamList, HTTP.UTF_8));
            StaticTel.Debug("Post------->"+httpRequest.getURI().toString());
            for (int i = 0; i < 2; i++)
            {
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputstream = httpEntity.getContent();
                if (inputstream!=null)
                {
                    return inputstream;
                }
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return inputstream;
    }
    
    /**
     * 判断网络是否可用
     * @param context 上下文对象
     * @return true可用；false不可用
     *         需要权限：android.permission.ACCESS_NETWORK_STATE
     */
    public static boolean isCheckNet(Context context)
    {
        try
        {
            ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager == null)
            {
                return false;
            }
            NetworkInfo networkinfo = manager.getActiveNetworkInfo();
            if (networkinfo == null || !networkinfo.isAvailable())
            {
                return false;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }
    
}
