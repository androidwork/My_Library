package library.pullxml;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import library.tool.StaticTel;

import org.ksoap2.serialization.SoapObject;

/**
 * Action：解析webservice返回的soapobject对象
 * @author android_vip@sina.com
 * @create 2014-8-28
 */
public class LibraryPullWebServiceSoapObject
{
    public LibraryPullWebServiceSoapObject()
    {
        // TODO Auto-generated constructor stub
    }
    public <T extends BaseObj> List<T> getSoapObjValue(SoapObject soapObject,T object,int position)
    {
        List<T> list=new ArrayList<T>();
        try
        {
            Constructor<T> constructor = (Constructor<T>) object.getClass().getConstructor();
            ArrayList<String> xmlNodes=object.getNodes();
            soapObject=(SoapObject) soapObject.getProperty(position);
            for (int i = 0; i < soapObject.getPropertyCount(); i++)
            {
                SoapObject soap=(SoapObject) soapObject.getProperty(i);
                object = constructor.newInstance();
                int z=0;
                for (int j = 0; j < xmlNodes.size(); j++)
                {
                    String pream=xmlNodes.get(j);
                    if (null!=soap.getProperty(pream))
                    {
                        object.setPreamValue(pream, soap.getProperty(pream).toString().trim());
                    }else
                    {
                        z++;
                    }
                }
                if (z!=xmlNodes.size())
                {
                    list.add(object);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
}
