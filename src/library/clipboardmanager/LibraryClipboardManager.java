package library.clipboardmanager;

import java.util.HashMap;

import library.sharedpreferences.LibrarySharedPreferences;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * ��������
 *
 * @author ף־��:2014-6-4
 */
@SuppressLint("NewApi")
public class LibraryClipboardManager
{
	private ClipboardManager cm;
	private static LibraryClipboardManager instance = null;
	
	private LibraryClipboardManager()
	{
	}
	private static synchronized void syncInit()
	{
		if (instance == null)
		{
			instance = new LibraryClipboardManager();
		}
	}

	public static LibraryClipboardManager getInstance()
	{
		if (instance == null)
		{
			syncInit();
		}
		return instance;
	}
	
	/**���ճ��������
	 * @param activity
	 * @param text
	 */
	public boolean setText(Activity activity,String text)
	{
		try
		{
			if (cm==null)
			{
				cm =(ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);	
			}
			cm.setText(text);
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**��ȡճ��������
	 * @param activity
	 * @return
	 */
	public String getString(Activity activity)
	{
		if (cm==null)
		{
			cm =(ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);	
		}
		CharSequence cs=cm.getText();
		if (cs==null)
		{
			cs="";
		}
		return cs.toString();	
	}
	

}
