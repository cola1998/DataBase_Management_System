..package dataBase;
import java.util.*;
import dataBase.*;
public class TableConstraint implements java.io.Serializable{
	
	private String tcName;//约束名
	private int tcType;//约束类型
	/*
	 * 1-Primary key
	 * 2-foreign key
	 * 3-check
	 * */
	private Vector tcV;  
	
	//构造方法
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
	//  noaction：1 默认  setnull：2   cascade：3
		if (tcV.size() == 3) {
			tcV.add(t);;
		}
		else {
			System.out.println("请检查是否缺失输入！");
			tcV.add(t);
		}
	}
	
	public void setUpdateType(int t)
	{
	//  noaction：1 默认  setnull：2   cascade：3
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
			System.out.println("约束名称：" + tcName);
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
					System.out.println("列名："+tcV.get(i));
				}
				else if(i==1) {
					System.out.print("参考表名："+tcV.get(i));
				}
				else if(i==2) {
					System.out.println("参考列名："+tcV.get(i));
				}
				else if(i==3) {
					System.out.println("delete处理策略(noaction：1   setnull：2   cascade：3)："+tcV.get(i));
				}
				else if(i==4) {
					System.out.println("update处理策略(noaction：1  setnull：2   cascade：3)："+tcV.get(i));
				}
			}
		}else {if(tcType == 3)
			System.out.print("Check:");
		}
		
		
		
	}
	
	public void addElement(Expression exp) {
		tcV.add(exp);//需要解析表达式
	}
	public void addElement(String str) {
		tcV.add(str); // primary key 或者 foreign key会用到
	}
	//  noaction：1 默认  setnull：2   cascade：3
	//foreign key 部分 [字段名   关联表名    关联的字段名  delete update  ]
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
