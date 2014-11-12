package library.tool;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Action£ºget imei
 * @author android_vip@sina.com
 * @create 2014-7-10 ÉÏÎç8:32:14
 */
public class LibraryIMEI
{
    
    TelephonyManager Imei;
    public LibraryIMEI(Context context)
    {
        Imei = ((TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE));
    }
    public String getIMEI()
    {
        return Imei.getDeviceId();
    }
}
