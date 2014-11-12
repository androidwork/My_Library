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
 * ��������http
 * 
 * @author ף־��:2014-7-4
 */
public class LibraryHttp
{
    
    public LibraryHttp()
    {
    }
    
    /**
     * inputstreamת�ַ�������
     * @param is inputstream
     * @param encodeSet �ַ�����
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
     * ��ȡinputstream���͵���������
     * @param urlPath ���ʵ�url��ַ
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
     * ��ȡ����url�����ص�String�ַ�
     * @param context �����Ķ���
     * @param handler��ȡ���/ʧ����֪ͨ��handler
     * @param url�����ַ
     * @param encode�����ַ��ı����ʽ
     *            msg.what==8080 ʧ�ܷ��ء���
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
     * ��ȡ����url�����ص�String�ַ�
     * @param context �����Ķ���
     * @param streamTostring:HttpStreamToString�ӿ�
     * @param url�����ַ
     * @param encode�����ַ��ı����ʽ
     *            msg.what==8080 ʧ�ܷ��ء���
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
     * �ж������Ƿ����
     * @param context �����Ķ���
     * @return true���ã�false������
     *         ��ҪȨ�ޣ�android.permission.ACCESS_NETWORK_STATE
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
