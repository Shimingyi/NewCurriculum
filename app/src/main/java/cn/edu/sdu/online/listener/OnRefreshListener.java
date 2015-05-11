package cn.edu.sdu.online.listener;

public interface OnRefreshListener {

	/**
	 * ����ˢ��ִ�е�ˢ������, ʹ��ʱ, 
	 * ��ˢ�����֮��, ��Ҫ�ֶ��ĵ���onRefreshFinish(), ȥ����ͷ����
	 */
	public void onRefresh();
	
	/**
	 * �����ظ���ʱ�ص�
	 * �����ظ������֮��, ��Ҫ�ֶ��ĵ���onRefreshFinish(), ȥ���ؽŲ���
	 */
	public void onLoadMoring();
}