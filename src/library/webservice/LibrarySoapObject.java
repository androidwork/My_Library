package library.webservice;

import java.util.ArrayList;

import library.http.LibraryHttp;
import library.tool.StaticTel;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import android.content.Context;
import android.text.StaticLayout;


/**
 * �������� ����webservice��ȡ�������ݵĶ���
 *
 * @author ף־��:2014-5-22
 */
public class LibrarySoapObject {
	/**
	 * �����ռ�
	 */
	private String targetName;
	/**
	 * ��ַ
	 */
	private String wsdl;
	
	/**ʵ��������
	 * @param targetName�����ռ�
	 * @param wsdl
	 */
	public LibrarySoapObject(String targetName,String wsdl) 
	{
		this.targetName=targetName;
		this.wsdl=wsdl;
	}
	/**��ȡwebservice��soapobject����
	 * @param context�����Ķ���
	 * @param voidName������
	 * @param pream��Ҫ�ϴ��Ĳ�����
	 * @return
	 */
	public  SoapObject getSoapObject(Context context,String voidName,ArrayList<String> pream)
    {
    		SoapObject result=null;
    		try {
    			boolean net=LibraryHttp.isCheckNet(context);
    			if (!net)
    			{
    				return null;
    			}
	    	      	SoapObject soapObject =new SoapObject(targetName, voidName);
	    	      	if (pream!=null) 
	    	      	{
	    	      		for (int i = 0; i < pream.size(); i++) 
		    	      	{
		    	      		soapObject.addProperty("pram", pream.get(i));
		    	      	}
				}
	    	      	SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER10);
	    			envelope.dotNet=true;
	    		    envelope.headerOut=setHeader(context);
	    		    envelope.setOutputSoapObject(soapObject);
	    		    MyAndroidHttpTransport httpTranstation=new MyAndroidHttpTransport(wsdl);
	    			httpTranstation.call(null, envelope);
	    			result=(SoapObject)envelope.bodyIn;
    		    } catch (Exception e) {
				e.printStackTrace();
			}
		return  result;
    }
	
    /**
     * @param context
     * @param voidName������
     * @param pream��Ҫ�ϴ��Ĳ�����
     * @param headerͷ����֤�ļ�
     * @return
     */
    public  SoapObject getSoapObject(Context context,String voidName,ArrayList<String> pream,Element[] header)
    {
            SoapObject result=null;
            try {
                boolean net=LibraryHttp.isCheckNet(context);
                if (!net)
                {
                    return null;
                }
                    SoapObject soapObject =new SoapObject(targetName, voidName);
                    if (pream!=null) 
                    {
                        for (int i = 0; i < pream.size(); i++) 
                        {
                            soapObject.addProperty("pram", pream.get(i));
                        }
                }
                    SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER10);
                    envelope.dotNet=true;
                    envelope.headerOut=header;
                    envelope.setOutputSoapObject(soapObject);
                    MyAndroidHttpTransport httpTranstation=new MyAndroidHttpTransport(wsdl);
                    httpTranstation.call(null, envelope);
                    result=(SoapObject)envelope.bodyIn;
                } catch (Exception e) {
                e.printStackTrace();
            }
        return  result;
    }
    
    
	 public  Element[] setHeader(Context context)
     {
		 Element[] header = new Element[1]; 
		 header[0] = new Element().createElement(targetName, "AuthenticationToken");
         Element userName = new Element().createElement(targetName, "key");
         userName.addChild(Node.TEXT, "8E3A5473C42AC2116B5FE93820DD251A"); 
         header[0].addChild(Node.ELEMENT, userName); 
         return header;
     }
}
