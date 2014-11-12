package library.updateauto;

import com.my_library.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 类描述：更新应用弹出框
 *
 * @author 祝志国:2014-6-18
 */
public class UpdateAppActivity extends Activity  
{
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.library_updateapp);
		UpdateAppActivity.this.setFinishOnTouchOutside(false);
		final String str=getIntent().getStringExtra("info");
		findViewById(R.id.lib_update_gx).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
				startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(str)));
			}
		});
		findViewById(R.id.lib_update_qx).setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	}
}
