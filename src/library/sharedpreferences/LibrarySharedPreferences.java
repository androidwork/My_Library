package library.sharedpreferences;

import java.util.HashMap;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * �������� ��������ʹ�ã�XML�洢
 * 
 * @author ף־��:2014-5-23
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
	 * �洢����
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
	 * ��ȡ������ɳ���е�xml����
	 * 
	 * @param valueKey
	 *            ��ȡ���ݵ�key����
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
	/**���xml����
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
