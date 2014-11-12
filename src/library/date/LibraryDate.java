package library.date;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类描述：格式化时间工具类
 *
 * @author 祝志国:2014-7-4
 */
public class LibraryDate
{
 
    /**获取当前时间
     * @param style时间格式
     * @return 获取当前时间
     * yyyy年MM月dd日 HH:mm:ss
     */
    public  String getThisTime(String style)
    {
        String str="0";
        try
        {
            str=new SimpleDateFormat(style).format(new Date(System.currentTimeMillis()));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return str;
    }
    public long getThisTimeFormentToLong(String style)
    {
        long lon=0;
        try
        {
            lon=getStringdateToLong(getThisTime(style), style);
        } catch (Exception e)
        {
            // TODO: handle exception
        }
        return lon;
    }
    /**获取格式化后的时间
     * @param style  yyyy年MM月dd日 HH:mm:ss
     * @return
     */
    public  String getThisTimeToEncodeUtf8(String style)
    {
        String time="";
        try
        {
            time=URLEncoder.encode(new SimpleDateFormat(style).format(new Date(System.currentTimeMillis())), "utf-8");
        } catch (Exception e)
        {
            e.printStackTrace();
        }   
        return time;
    }
    
    
    /**long to String
     * @param style 时间格式yyyy年MM月dd日 HH:mm:ss
     * @param date 要格式化的long数据
     * @return
     */
    public  String getLongdateToString(String style,long date)
    {
        String str="0";
         try
        {
             str= new SimpleDateFormat(style).format(new Date(date));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
         return str;
    }
    /**String to long
     * @param strdate 日期
     * @param style  类型
     * @return
     */
    public long getStringdateToLong(String strdate,String style)
    {
        long ong=0;
        try
        {
            ong=new SimpleDateFormat(style).parse(strdate).getTime();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return ong;
    } 
    
}
