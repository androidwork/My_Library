package library.http;
/**
 * Action��
 * @author android_vip@sina.com
 * @create 2014-9-5
 */
public interface RequestCallBack
{
    public void onRuning(boolean isRun);
    public void onError(String error);
    public void onSuccess(Object object);
    
}
