package library.sharedpreferences;

import java.util.HashMap;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 类描述： 保存数据使用，XML存储
 * 
 * @author 祝志国:2014-5-23
 */
public class LibrarySharedPreferences
{
	private SharedPreferences sharedPreferences;
	private static LibrarySharedPreferences instance = null;
	
	private LibrarySharedPreferences()
	{
	}

	private static synchronized void syncInit()
	{
		if (instance == null)
		{
			instance = new LibrarySharedPreferences();
		}
	}

	public static LibrarySharedPreferences getInstance()
	{
		if (instance == null)
		{
			syncInit();
		}
		return instance;
	}

	/**
	 * 存储数据
	 * 
	 * @param dateMap
	 */
	@SuppressWarnings("rawtypes")
	public void saveSharedPreferencesXml(Context context,HashMap<String, String> dateMap)
	{
		if (sharedPreferences==null)
		{
			sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
		}
		java.util.Iterator it = dateMap.entrySet().iterator();
		Editor editor = sharedPreferences.edit();
		while (it.hasNext())
		{
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			editor.putString(entry.getKey().toString(), entry.getValue()
					.toString());
		}
		editor.commit();
	}

	/**
	 * 读取保存在沙盒中的xml数据
	 * 
	 * @param valueKey
	 *            获取数据的key数组
	 * @return HashMap
	 */
	public HashMap<String, String> getSharedPreferencesXml(Context context,String valueKey[])
	{
		if (sharedPreferences==null)
		{
			sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
		}
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (int i = 0; i < valueKey.length; i++)
		{
			String key = valueKey[i];
			if (sharedPreferences.contains(key))
			{
				hashMap.put(key, sharedPreferences.getString(key, ""));
			}
		}
		return hashMap;
	}
	/**清除xml数据
	 * @param context
	 */
	public void clearSharedPreferences(Context context)
	{
		if (sharedPreferences==null)
		{
			sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
		}
		sharedPreferences.edit().clear().commit();
	}
	
	
}
