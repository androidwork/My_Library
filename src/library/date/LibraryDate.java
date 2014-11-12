package library.date;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ����������ʽ��ʱ�乤����
 *
 * @author ף־��:2014-7-4
 */
public class LibraryDate
{
 
    /**��ȡ��ǰʱ��
     * @param styleʱ���ʽ
     * @return ��ȡ��ǰʱ��
     * yyyy��MM��dd�� HH:mm:ss
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
    /**��ȡ��ʽ�����ʱ��
     * @param style  yyyy��MM��dd�� HH:mm:ss
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
     * @param style ʱ���ʽyyyy��MM��dd�� HH:mm:ss
     * @param date Ҫ��ʽ����long����
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
     * @param strdate ����
     * @param style  ����
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
