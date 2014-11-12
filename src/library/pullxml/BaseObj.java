package library.pullxml;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 类描述： 反射出实体类并且给所属方法赋值
 *
 * @author 祝志国:2014-5-22
 */
public abstract class BaseObj {
	
/**xml的节点名称
 * @return
 */
public abstract ArrayList<String> getNodes();	
/**xml节点条目的名称
 * @return
 */
public abstract String getRootNodes();



/**设置实体类的属性值
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
