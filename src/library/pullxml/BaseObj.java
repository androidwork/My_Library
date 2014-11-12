package library.pullxml;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * �������� �����ʵ���ಢ�Ҹ�����������ֵ
 *
 * @author ף־��:2014-5-22
 */
public abstract class BaseObj {
	
/**xml�Ľڵ�����
 * @return
 */
public abstract ArrayList<String> getNodes();	
/**xml�ڵ���Ŀ������
 * @return
 */
public abstract String getRootNodes();



/**����ʵ���������ֵ
 * @param nodeTag
 * @param value
 */
@SuppressWarnings("rawtypes")
public void setPreamValue(String nodeTag,Object value)
{
	try {
		Field field=getClass().getDeclaredField(nodeTag);
		field.setAccessible(true);
		Class c=field.getType();
		if (c.getName().indexOf("int")!=-1) 
		{
			value=Integer.parseInt((String) value);
		}
		field.set(this, value);
	 	
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	
public ArrayList<String> setClassNotes(Object value)
{
	 ArrayList<String> list=new ArrayList<String>();
	 Field[] field = value.getClass().getDeclaredFields();
	 for (int i = 0; i < field.length; i++) 
	 {
		 list.add(field[i].getName());
	 }
	 return list;
}


}
