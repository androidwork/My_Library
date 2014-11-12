package library.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void fling(int velocityY) {
		// TODO Auto-generated method stub
		super.fling(velocityY*2);
	}
	
	
	
}
