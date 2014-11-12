package library.tool;

public interface MyInterface {
	/**
	 * 初始化控件、实例化对象
	 */
	public void init();
	/**刷新activity局部View
	 * @param pream 附带参数
	 */
	public void onRefreshView(Object...pream);
	
}
