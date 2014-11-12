package library.activitys;

import java.util.ArrayList;

import library.tool.StaticTel;

import com.my_library.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * �������� ��������е�activity��ջ
 * 
 * @author ף־��:2014-5-22
 */
@SuppressLint("NewApi")
public class LibraryActivityStack
{

	public static ArrayList<Activity> activityStack = new ArrayList<Activity>();

	/**
	 * �˳�����ĶԻ���
	 * 
	 * @param context
	 */
	public static void exitApp(Activity activity)
	{
		activity.startActivityForResult(new Intent(activity, ExitAppActivity.class),44);
	}

	/**
	 * �ڼ��������activity
	 * 
	 * @param activity
	 */
	public static void addActivity(Activity activity)
	{
		activityStack.add(activity);
	}

	/**��ȡ����activity����
	 * @param activityName
	 * @return
	 */
	public static Activity getActivity(String activityName)
	{
		for (Activity ac : activityStack)
		{
			String name = ac.getClass().getName();
			if (name.indexOf(activityName) >= 0)
			{
				return ac;
			}
		}
		return null;
	}
	/**
	 * ���activity��ջ���˳����
	 */
	public static void destoryActivitys()
	{
		for (int i = 0; i < activityStack.size(); i++)
		{
			activityStack.get(i).finish();
		}
	}
}
