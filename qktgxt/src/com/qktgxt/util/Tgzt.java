package com.qktgxt.util;

//Ͷ��״̬��̬��
public class Tgzt {
	public static final int WTJ=0;	//δ�ύ
	public static final int ZJSP=1;	//������
	public static final int ZJTY=2;	//ר������ͨ��
	public static final int ZJTH=3;	//ר��������ͨ��
	public static final int ZBTY=4;	//��������ͬ��
	public static final int ZBTH=5;	//����������ͨ��
	
	
	public static String showZt(int type){
		String[] arrZt={"δ�ύ","������","ר������ͨ��","ר��������ͨ��","��������ͨ��","����������ͨ��"};
		return arrZt[type];
	}
}
