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
 * 类描述： 管理程序中的activity堆栈
 * 
 * @author 祝志国:2014-5-22
 */
@SuppressLint("NewApi")
public class LibraryActivityStack
{

	public static ArrayList<Activity> activityStack = new ArrayList<Activity>();

	/**
	 * 退出程序的对话框
	 * 
	 * @param context
	 */
	public static void exitApp(Activity activity)
	{
		activity.startActivityForResult(new Intent(activity, ExitAppActivity.class),44);
	}

	/**
	 * 在集合里添加activity
	 * 
	 * @param activity
	 */
	public static void addActivity(Activity activity)
	{
		activityStack.add(activity);
	}

	/**获取单个activity对象
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
	 * 清除activity堆栈，退出软件
	 */
	public static void destoryActivitys()
	{
		for (int i = 0; i < activityStack.size(); i++)
		{
			activityStack.get(i).finish();
		}
	}
}
