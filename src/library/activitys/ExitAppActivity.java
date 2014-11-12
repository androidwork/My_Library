package library.activitys;

import library.tool.StaticTel;

import com.my_library.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * ÀàÃèÊö£º
 *
 * @author ×£Ö¾¹ú:2014-5-29
 */
public class ExitAppActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exitapp);
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		int width = mDisplayMetrics.widthPixels-260;
		Button btn =(Button)findViewById(R.id.exitapp_qd);
		btn.setWidth(width);
		
		btn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ExitAppActivity.this.setResult(44, new Intent());
				finish();
			}
		});
		findViewById(R.id.exitapp_qx).setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		finish();
		return super.onTouchEvent(event);
	}
}
