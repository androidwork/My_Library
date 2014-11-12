package library.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.my_library.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * �������� �ṩ����ˢ�µ�listview
 * 
 * @author ף־��:2014-5-22
 */
@SuppressLint("NewApi")
public class MyListView extends ListView implements OnScrollListener
{

	private final static int RELEASE_To_REFRESH = 0;
	private final static int PULL_To_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;

	private boolean isJiaZai=true;
	
	// ʵ�ʵ�padding�ľ����������ƫ�ƾ���ı���
	private final static int RATIO = 3;

	private LayoutInflater inflater;

	private LinearLayout headView;

	private TextView tipsTextview;
	private TextView lastUpdatedTextView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;

	//��ȡ�������ݵ�view
	private View btmView;
	private TextView btmTextView;
	
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;

	// ���ڱ�֤startY��ֵ��һ��������touch�¼���ֻ����¼һ��
	private boolean isRecored;

	private int headContentWidth;
	private int headContentHeight;

	private int startY;
	private int firstItemIndex;

	private int state;

	private boolean isBack;

	private OnRefreshListener refreshListener;
	private OnBottomMoreListener bottomMoreListener;
	private boolean isRefreshable;
	private Context context;
	
	
	
	public MyListView(Context context)
	{
		super(context);
		// ���������ʼ���ĺ���
		this.context=context;
		init(context);
	}

	public MyListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// ���������ʼ���ĺ���
		this.context=context;
		init(context);
	}

	private void init(Context context)
	{

		// ���LayoutInflater�Ӹ����������ġ�
		inflater = LayoutInflater.from(context);

		// ʵ��������XML�ļ�ת������Ӧ����ͼ����
		headView = (LinearLayout) inflater.inflate(
				R.layout.tool_mylistview_top_refreshview, null);

		arrowImageView = (ImageView) headView
				.findViewById(R.id.head_arrowImageView);
		// ������С��� �͸߶�
		arrowImageView.setMinimumWidth(70);
		arrowImageView.setMinimumHeight(50);
		// ʵ��������XML�ļ�ת������Ӧ����ͼ����
		progressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView
				.findViewById(R.id.head_lastUpdatedTextView);

		// ��������ˢ�µķ���
		measureView(headView);
		// d�õ�ԭʼ�߶ȺͿ��
		headContentHeight = headView.getMeasuredHeight();
		headContentWidth = headView.getMeasuredWidth();

		// ������䡣��ͼ������ӵĿռ�Ҫ����ʾ������,��ȡ���ڷ���֪���ȵĹ�����
		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		// ��ǩ����ʶ��һ����־��Ϣ����Դ����ʶ�����־���÷���
		// Log.v("size", "width:" + headContentWidth + " height:"
		// + headContentHeight);

		// ���һ���̶���ͼ�������б�Ķ���
		addHeaderView(headView, null, false);
		// ���ü����¼�
		setOnScrollListener(this);

		// ����Ч��ʵ���������ɿ�ʱ��ͼƬ�� 180�� ��ת ע��0, -180,������������� -180, 0,
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		// ���ü��ٶ�����Ϊ���������Ĭ��ֵΪһ�����Բ�ֵ��
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(300);
		// ���fillAfter�����,ת��,�ö���ִ�����ʱ���������ȥ
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		// ���ü��ٶ�����Ϊ���������Ĭ��ֵΪһ�����Բ�ֵ��
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(300);
		// ���fillAfter�����,ת��,�ö���ִ�����ʱ���������ȥ
		reverseAnimation.setFillAfter(true);
		// ����״̬
		state = DONE;
		// ���ò���ˢ��״̬
		isRefreshable = false;
	}

	// �ص�����ʱҪ���õ��б�������Ѿ��������⽫���֮����õĹ�������
	public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2,
			int arg3)
	{
		firstItemIndex = firstVisiableItem;
	}

	/*
	 * �ص��������ö��б���ͼ��������ͼ�������� ��������ͼ������,�����ô˷����ڽ�������һ�ֻ���ĳ��� *
	 */
	public void onScrollStateChanged(AbsListView arg0, int scrollState)
	{
		switch (scrollState)
		{
		// ��������ʱ
			case OnScrollListener.SCROLL_STATE_IDLE:
				// �жϹ������ײ�
				if (!isJiaZai && firstItemIndex!=0 && this.getLastVisiblePosition() == (this.getCount() - 1))
				{
					onBottomMore();
				}
				break;
		}
	}

	// �����¼�����
	public boolean onTouchEvent(MotionEvent event)
	{

		// �ж��Ƿ����ˢ��
		if (isRefreshable)
		{
			// ���ݶ�����Ӧ��ͬ�ķ���
			switch (event.getAction())
			{

			// ����ס��Ļ��������Ļ��ʱ��
				case MotionEvent.ACTION_DOWN:
					if (firstItemIndex == 0 && !isRecored)
					{
						isRecored = true;
						startY = (int) event.getY();
						// Log.v(TAG, "��������ʱ���¼��ǰλ�á�");
					}
					break;

				// ����ס��Ļ��������Ļ��ʱ��
				case MotionEvent.ACTION_UP:

					if (state != REFRESHING && state != LOADING)
					{
						if (state == DONE)
						{

						}
						if (state == PULL_To_REFRESH)
						{
							state = DONE;
							changeHeaderViewByState(state);

							// Log.v(TAG, "������ˢ��״̬����done״̬");
						}
						if (state == RELEASE_To_REFRESH)
						{
							state = REFRESHING;
							changeHeaderViewByState(state);
							onRefresh();

							// Log.v(TAG, "���ɿ�ˢ��״̬����done״̬");
						}
					}

					isRecored = false;
					isBack = false;

					break;

				// ����ס��Ļ�ƶ�ʱ��
				case MotionEvent.ACTION_MOVE:
					int tempY = (int) event.getY();

					if (!isRecored && firstItemIndex == 0)
					{
						// Log.v(TAG, "��moveʱ���¼��λ��");
						isRecored = true;
						startY = tempY;
					}

					if (state != REFRESHING && isRecored && state != LOADING)
					{

						/***
						 * �� ��ǰ��λ��һֱ����head������������б�����Ļ�Ļ��� �������Ƶ�ʱ���б��ͬʱ���й���
						 */
						// ��������ȥˢ����
						if (state == RELEASE_To_REFRESH)
						{

							setSelection(0);

							// �������ˣ��Ƶ�����Ļ�㹻�ڸ�head�ĳ̶ȣ����ǻ�û���Ƶ�ȫ���ڸǵĵز�
							if (((tempY - startY) / RATIO < headContentHeight)
									&& (tempY - startY) > 0)
							{
								state = PULL_To_REFRESH;
								changeHeaderViewByState(state);

								// Log.v(TAG, "���ɿ�ˢ��״̬ת�䵽����ˢ��״̬");
							}
							// һ�����Ƶ�����
							else if (tempY - startY <= 0)
							{
								state = DONE;
								// ���øı�ʱ��ķ���������UI
								changeHeaderViewByState(state);

								// Log.v(TAG, "���ɿ�ˢ��״̬ת�䵽done״̬");
							} else
							{
							}
						}
						// ��û�е�����ʾ�ɿ�ˢ�µ�ʱ��
						if (state == PULL_To_REFRESH)
						{

							setSelection(0);

							// ���������Խ���RELEASE_TO_REFRESH��״̬
							if ((tempY - startY) / RATIO >= headContentHeight)
							{
								state = RELEASE_To_REFRESH;
								isBack = true;
								// ���øı�ʱ��ķ���������UI
								changeHeaderViewByState(state);

								// Log.v(TAG, "��done��������ˢ��״̬ת�䵽�ɿ�ˢ��");
							}
							// ���Ƶ�����
							else if (tempY - startY <= 0)
							{
								state = DONE;
								// ���øı�ʱ��ķ���������UI
								changeHeaderViewByState(state);

								// Log.v(TAG, "��DOne��������ˢ��״̬ת�䵽done״̬");
							}
						}

						// done״̬��
						if (state == DONE)
						{
							if (tempY - startY > 0)
							{
								state = PULL_To_REFRESH;
								// ���øı�ʱ��ķ���������UI
								changeHeaderViewByState(state);
							}
						}

						// ����headView��size
						if (state == PULL_To_REFRESH)
						{
							headView.setPadding(0, -1 * headContentHeight
									+ (tempY - startY) / RATIO, 0, 0);

						}

						// ����headView��paddingTop
						if (state == RELEASE_To_REFRESH)
						{
							headView.setPadding(0, (tempY - startY) / RATIO
									- headContentHeight, 0, 0);
						}
					}

					break;
			}
		}

		return super.onTouchEvent(event);
	}

	// ��״̬�ı�ʱ�򣬵��ø÷������Ը��½���
	private void changeHeaderViewByState(int state)
	{
		// ���ݵ�ǰ��״̬�����ж�
		switch (state)
		{

		// ����ʱ���ɿ��ȿ�ˢ��
			case RELEASE_To_REFRESH:
				// ������ͼ VISIBLE �ɼ� ��GONE ���ɼ�
				arrowImageView.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				tipsTextview.setVisibility(View.VISIBLE);
				lastUpdatedTextView.setVisibility(View.VISIBLE);

				// ���ڿ�ʼָ���Ķ�����
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(animation);

				tipsTextview.setText("�ͷ�ˢ��");
				// Log.v(TAG, "��ǰ״̬���ɿ�����ˢ��");
				break;

			// ��ʼʱ������ˢ��
			case PULL_To_REFRESH:
				// ������ͼ VISIBLE �ɼ� ��GONE ���ɼ�
				progressBar.setVisibility(View.GONE);
				tipsTextview.setVisibility(View.VISIBLE);
				lastUpdatedTextView.setVisibility(View.VISIBLE);
				// ���ڿ�ʼָ���Ķ�����
				arrowImageView.clearAnimation();
				arrowImageView.setVisibility(View.VISIBLE);

				if (isBack)
				{
					isBack = false;
					// ���ڿ�ʼָ���Ķ�����
					arrowImageView.clearAnimation();
					arrowImageView.startAnimation(reverseAnimation);

					tipsTextview.setText("����ˢ��");
				} else
				{
					tipsTextview.setText("����ˢ��");
				}
				// Log.v(TAG, "��ǰ״̬������ˢ��");
				break;

			case REFRESHING:
				headView.setPadding(0, 0, 0, 0);
				// ������ͼ VISIBLE �ɼ� ��GONE ���ɼ�
				progressBar.setVisibility(View.VISIBLE);
				// ���ڿ�ʼָ���Ķ�����
				arrowImageView.clearAnimation();
				arrowImageView.setVisibility(View.GONE);
				tipsTextview.setText("����ˢ��...");
				lastUpdatedTextView.setVisibility(View.VISIBLE);
				// Log.v(TAG, "��ǰ״̬,����ˢ��...");
				break;
			case DONE:
				// ������䡣��ͼ������ӵĿռ�Ҫ����ʾ������
				headView.setPadding(0, -1 * headContentHeight, 0, 0);
				// ������ͼ VISIBLE �ɼ� ��GONE ���ɼ�
				progressBar.setVisibility(View.GONE);
				// ���ڿ�ʼָ���Ķ�����
				arrowImageView.clearAnimation();
				arrowImageView
						.setImageResource(R.drawable.tool_mylistview_refresh);
				tipsTextview.setText("����ˢ��");
				lastUpdatedTextView.setVisibility(View.VISIBLE);
				// Log.v(TAG, "��ǰ״̬");
				break;
		}
	}

	/**
	 * ����������ˢ��
	 */
	public void setOnDesRefresh()
	{
		this.isRefreshable = false;
	}

	/**
	 * ��������ˢ��
	 */
	public void setOnEnRefresh()
	{
		this.isRefreshable = true;
	}

	public void setonRefreshListener(OnRefreshListener refreshListener)
	{
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}
	public void setonBottomMoreListener(OnBottomMoreListener bottomMoreListener)
	{
		isJiaZai=false;
		this.bottomMoreListener=bottomMoreListener;
		if (btmView==null)
		{
			btmView=LayoutInflater.from(context).inflate(R.layout.pull_to_bottom_view, null);
			btmTextView=(TextView)btmView.findViewById(R.id.pull_bottom_text);
			addFooterView(btmView, null, false);	
		}
	}

	public interface OnRefreshListener
	{
		public void onRefresh();
	}
	public interface OnBottomMoreListener
	{
		public void onBottomMore();
	}
	

	
	/**listview ��ȡ�����������
	 * @param type 0������listview��adapter��1����������ʧ�� adaper��text�ɲ���ֵ��2��û�и���������adapter������text����֧��
	 * @param adapter
	 * @param text
	 */
	public  void onBottomMoreComplete(int type,BaseAdapter adapter,String text)
	{
		switch (type)
		{
			case 0:
				onBottomMoreCompleteRefresh(adapter);
				break;
			case 1:
				onBottomMoreCompleteError(text);
				break;
			case 2:
				onBottomMoreCompleteOver(text);
				break;
			default:
				break;
		}
	}
	
	
	/**��ȡ���ݳɹ�
	 * @param adapter ����adapter
	 */
	public synchronized void onBottomMoreCompleteRefresh(Object adapter)
	{
		isJiaZai=false;
		if (adapter!=null)
		{
			((BaseAdapter)adapter).notifyDataSetChanged();
		}
	}
	
	/**��ȡ����ʧ��
	 * @param adapter
	 */
	public synchronized void onBottomMoreCompleteError(String text)
	{
		isJiaZai=false;
		setSelected(true);
		if (firstItemIndex>0)
		{
			setSelectionFromTop(firstItemIndex-1, -30);
		}
		if (text.length()>0)
        {
		    StaticTel.ToastShow(context, text);
        }
		
	}
	public synchronized void onBottomMoreCompleteOver(String text)
	{
		isJiaZai=true;
		if (text!=null)
		{
			btmTextView.setText(text);
		}
	}
	/**
	 * ˢ�����
	 */
	public void onRefreshComplete()
	{
		if (btmView!=null)
		{
			isJiaZai=false;
		}
		state = DONE;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String date = format.format(new Date());
		lastUpdatedTextView.setText("�ϴθ���:" + date);
		changeHeaderViewByState(state);
	}

	/**
	 * ˢ������
	 */
	public void onRefresh()
	{
		if (refreshListener != null)
		{
			state = 2;
			changeHeaderViewByState(2);
			refreshListener.onRefresh();
		}
	}
	/**
	 * ��ȡ��������
	 */
	public void onBottomMore()
	{
		if (bottomMoreListener!=null)
		{
			isJiaZai=true;
			btmTextView.setText("���ڻ�ȡ��������...");
			bottomMoreListener.onBottomMore();
		}
	}
	
	

	// ����ˢ�µ�
	private void measureView(View child)
	{
		// v���鲼�ֲ�����Ⱥ͸߶�
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null)
		{
			// ����һ�����鲼�ֲ���ָ���Ŀ��(���)�͸߶ȣ���������
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		// d�õ�childWidthSpec(�߶Ȼ���)������ͼ
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0)
		{
			// ����һ�������淶�������ṩ�Ĵ�С��ģʽ
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else
		{
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		// �ҳ�һ����ͼӦ�ö�󡣸���ӦԼ����Ϣ�ڿ�Ⱥ͸߶Ȳ���
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void setAdapter(BaseAdapter adapter)
	{
		// �������ˢ�µ�ʱ��
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String date = format.format(new Date());
		lastUpdatedTextView.setText("�ϴθ���:" + date);
		super.setAdapter(adapter);
	}

}