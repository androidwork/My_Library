package library.notifiation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
/**
 * Action：
 * @author android_vip@sina.com
 * @create 2014-7-10
 */
public class LibraryNotificationManager
{
    NotificationManager  noManager;
    Notification mNotification;
    int id=2;
    
    @SuppressWarnings("deprecation")
    public LibraryNotificationManager(Context context,int icon,String notificationTitle,String contentTitle,String contentText)
    {
        noManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotification = new Notification();
        mNotification.icon=icon;
        mNotification.tickerText=notificationTitle;
        mNotification.when=System.currentTimeMillis();
        mNotification.defaults = Notification.DEFAULT_ALL;  
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(),0 );
        mNotification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
    }
    
    public void show()
    {
        noManager.notify(id, mNotification);
    }
    /**取消通知
     * @param isAll：是否取消全部
     */
    public void cancel(boolean isAll)
    {
        if (isAll)
        {
            noManager.cancelAll();
        }else
        {
            noManager.cancel(id);
        }
    }
    
    
}
