package library.pullxml;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import library.tool.StaticTel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
/**
 * �������� ����xml
 *
 * @author ף־��:2014-5-22
 */
public class LibraryPullResolveXml {
	private String inputEncoding="utf-8";
	public LibraryPullResolveXml() {

	}
	/**ʵ��������
	 * @param encoding ����xml���ַ���
	 */
	public LibraryPullResolveXml(String encoding)
	{
		this.inputEncoding=encoding;
	}
	public void PullResolveXm()
	{
	}
	/**����xml����ȡlist����
	 * @param inputStream 
	 * @param objʵ����
	 * @return
	 */
	public <T extends BaseObj> List<T> getList(InputStream inputStream,T obj)
	{
		List<T> list=new ArrayList<T>();
		ArrayList<String> xmlNodes=obj.getNodes();
		try {
			XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
			xmlPullParser.setInput(inputStream, inputEncoding);
			int eventType=xmlPullParser.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT)
			{
				String name=xmlPullParser.getName();
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					
					if (null == name || name.equals("")) 
					{
						break;
					}
					if (name.trim().equals(obj.getRootNodes())) 
					{
						@SuppressWarnings("unchecked")
						Constructor<T> constructor = (Constructor<T>) obj.getClass().getConstructor();
						obj = constructor.newInstance();
					}
					if (xmlNodes.contains(name.trim())) 
					{
						String s=xmlPullParser.nextText().toString().trim();
						obj.setPreamValue(name, s);	
					}
				break;
				case XmlPullParser.END_TAG:
					if (name.equals(obj.getRootNodes())) 
					{
						list.add(obj);
					}
					break;
				default:
					break;
				}
				eventType = xmlPullParser.next();
				}
			if (inputStream!=null) 
			{
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
