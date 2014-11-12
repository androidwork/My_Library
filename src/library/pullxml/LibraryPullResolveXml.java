package library.pullxml;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import library.tool.StaticTel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
/**
 * 类描述： 解析xml
 *
 * @author 祝志国:2014-5-22
 */
public class LibraryPullResolveXml {
	private String inputEncoding="utf-8";
	public LibraryPullResolveXml() {

	}
	/**实例化对象
	 * @param encoding 解析xml的字符集
	 */
	public LibraryPullResolveXml(String encoding)
	{
		this.inputEncoding=encoding;
	}
	public void PullResolveXm()
	{
	}
	/**解析xml并获取list集合
	 * @param inputStream 
	 * @param obj实体类
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
