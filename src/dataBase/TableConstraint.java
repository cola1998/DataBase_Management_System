..package dataBase;
import java.util.*;
import dataBase.*;
public class TableConstraint implements java.io.Serializable{
	
	private String tcName;//Լ����
	private int tcType;//Լ������
	/*
	 * 1-Primary key
	 * 2-foreign key
	 * 3-check
	 * */
	private Vector tcV;  
	
	//���췽��
	public TableConstraint() {
		tcName = "";
		tcType = 1;
		tcV = new Vector();
	}

	public void setTcName(String cname)
	{
		this.tcName = cname;
	}
	
	public String getTcName()
	{
		return this.tcName;
	}
	
	public void setTcType(int t) {
		this.tcType = t;
	}
		
	public int getTcType() {
		return this.tcType;
	}
	
	public Vector gettcV() 
	{
		return this.tcV;
	}
	
	public void setDeleteType(int t)
	{
	//  noaction��1 Ĭ��  setnull��2   cascade��3
		if (tcV.size() == 3) {
			tcV.add(t);;
		}
		else {
			System.out.println("�����Ƿ�ȱʧ���룡");
			tcV.add(t);
		}
	}
	
	public void setUpdateType(int t)
	{
	//  noaction��1 Ĭ��  setnull��2   cascade��3
		if (tcV.size() == 4) {
			tcV.add(t);;
		}
		else {
			tcV.add(1);
			tcV.add(t);
		}
	}
	
	public void printDescribe() {
		if(tcName != "") {
			System.out.println("Լ�����ƣ�" + tcName);
		}
		
		if (tcType == 1) {
			System.out.println("Primary key:");
			
			for(int i=0;i<tcV.size();i++)
			{
				System.out.println(tcV.get(i));
				
			}
		}
		else if(tcType == 2) {
			System.out.println("Foreign key:");
			if(tcV.size() == 4) {
				setUpdateType(1);
			}
			for(int i=0;i<tcV.size();i++)
			{
				if(i==0) {
					System.out.println("������"+tcV.get(i));
				}
				else if(i==1) {
					System.out.print("�ο�������"+tcV.get(i));
				}
				else if(i==2) {
					System.out.println("�ο�������"+tcV.get(i));
				}
				else if(i==3) {
					System.out.println("delete�������(noaction��1   setnull��2   cascade��3)��"+tcV.get(i));
				}
				else if(i==4) {
					System.out.println("update�������(noaction��1  setnull��2   cascade��3)��"+tcV.get(i));
				}
			}
		}else {if(tcType == 3)
			System.out.print("Check:");
		}
		
		
		
	}
	
	public void addElement(Expression exp) {
		tcV.add(exp);//��Ҫ�������ʽ
	}
	public void addElement(String str) {
		tcV.add(str); // primary key ���� foreign key���õ�
	}
	//  noaction��1 Ĭ��  setnull��2   cascade��3
	//foreign key ���� [�ֶ���   ��������    �������ֶ���  delete update  ]
	public void setElement(int position,int value) {
		if(position == 3) {
			if(value == 1) {
				setDeleteType(1);
			}
			else if(value == 2) {
				setDeleteType(2);
			}
			else if(value == 3) {
				setDeleteType(3);
			}
		}
		else if(position == 4) {
			if(value == 1) {
				setUpdateType(1);
			}
			else if(value == 2) {
				setUpdateType(2);
			}else if(value == 3) {
				
				setUpdateType(3);
			}
		}
	}
}
