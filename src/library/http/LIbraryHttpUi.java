package library.http;

import java.io.InputStream;
import java.util.ArrayList;
import library.pullxml.BaseObj;
import library.pullxml.LibraryPullResolveXml;
import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

/**
 * Action£º
 * @author android_vip@sina.com
 * @create 2014-9-5
 */
public class LIbraryHttpUi implements Callback
{
    private Thread thread;
    private Handler libraryHandler=new Handler(this);
    private RequestCallBack callBack;
    private Context context;
    
    public LIbraryHttpUi(Context context,RequestCallBack callBack)
    {
        this.callBack=callBack;
        this.context=context;
    }
    public  void requestHttp(final String url,final BaseObj obj)
    {
        if (thread!=null && thread.isAlive())
        {
            callBack.onRuning(true);
            return;
        }
        thread=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String error="";
                try
                {
                    InputStream is=new LibraryHttp().getHttpInputStreamToGet(context, url);
                    if (is==null)
                    {
                        libraryHandler.sendMessage(libraryHandler.obtainMessage(0,"net error"));
                        return;
                    }
                    ArrayList<? extends BaseObj> array=(ArrayList<? extends BaseObj>) new LibraryPullResolveXml().getList(is, obj);
                    libraryHandler.sendMessage(libraryHandler.obtainMessage(1, array));
                    return;
                } catch (Exception e)
                {
                    e.printStackTrace();
                    error=e.toString();
                }
                libraryHandler.sendMessage(libraryHandler.obtainMessage(0,error));
            }
        });
        thread.start();
    }
    @Override
    public boolean handleMessage(Message msg)
    {
        switch (msg.what)
        {
            case 0://ÍøÂç´íÎó
                callBack.onError(msg.obj.toString());
                break;
            case 1://³É¹¦
                callBack.onSuccess(msg.obj);
                break;
                
            default:
                break;
        }
        return false;
    }
}

