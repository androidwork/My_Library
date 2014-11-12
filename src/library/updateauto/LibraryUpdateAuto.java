package library.updateauto;

import library.tool.StaticTel;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;

/**
 * ��������ʵ���Զ�����
 * 
 * @author ף־��:2014-6-17
 */
public class LibraryUpdateAuto
{
	private Context context;
	private UpdateAppListener updateAppListener;
	
	public LibraryUpdateAuto(Context context,UpdateAppListener updateAppListener)
	{
		this.context = context;
		this.updateAppListener=updateAppListener;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateApp(String url)
	{
		FinalHttp fh = new FinalHttp();
		fh.get(url, new AjaxCallBack()
		{
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg)
			{
				super.onFailure(t, errorNo, strMsg);
				updateAppListener.updateAppListener("���´���");
			}

			@Override
			public void onSuccess(Object t)
			{
				super.onSuccess(t);
				if (t!=null)
				{
					String str=t.toString();
					if (str.startsWith("http://"))
					{
						    context.startActivity(new Intent(context,UpdateAppActivity.class).putExtra("info", str));
					}else
					{
					        updateAppListener.updateAppListener("�������°汾");
					}
				}else
				{
				    updateAppListener.updateAppListener("���´���");
				}
			}
			
		});
	}
}
