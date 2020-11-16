package dataBase;
import java.util.*;
import dataBase.*;

import java.io.*;


public class TableContent implements java.io.Serializable{ //要实现序列化必须要实现接口
	private static final long serialVersionUID= 1L; // 还需要写一个版本号
	private String tName;//表名
	private Vector vConstraint; //表约束  每一个元素为一个TableConstraint对象
	private Vector vColumn; //列 每一个元素为一个ColumnDef对象
	private Vector vData;  //数据

	//构造函数
	public TableContent() {
		tName = "";
		vConstraint = new Vector();
		vColumn = new Vector();
		vData = new Vector();
	}
	
	public String getName() {
		return tName;
	}
	
	public void setName(String tname) {
		this.tName = tname;
	}
	
	public Vector getvConstraint() {
		return vConstraint;
	}
	
	public Vector getvColumn() {
		return this.vColumn;
	}
	
	public Vector getvData() {
		return vData;
	}
	
	public void addElement(ColumnDef cof) {
		vColumn.add(cof);
	}
	
	public GlobalTableInfo getGlobalTableInfo() throws Exception {//获取全局表对象
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("globalTableInfo.db"));
		GlobalTableInfo gt = (GlobalTableInfo)ois.readObject(); // 创建一个全局表的对象
		ois.close();
		
		Vector nameVector = gt.getTableNames();
		System.out.printf("当前数据库中共有%d个数据表",nameVector.size());
		for(int i =0;i<nameVector.size();i++) {
			System.out.println(nameVector.get(i));
		}
		return gt;
	}
	
	public void contentPrint() throws Exception{
		System.out.println("表名：" + tName);
		System.out.printf("共有%d列：",vColumn.size());
		for(int i=0;i<vColumn.size();i++) {
			System.out.printf("%d",i);
			System.out.println(((ColumnDef) vColumn.get(i)).getDescribe());
		}
		System.out.printf("共有%d个约束：",vConstraint.size());
		for(int i=0;i<vConstraint.size();i++) {
			System.out.printf("%d",i);
			((TableConstraint) vConstraint.get(i)).printDescribe();
		}
		
	}
	
	public int searchColName(Vector cname,String cn) {
		int tag = 0;//0-未找到 1-找到了
		if(cname.size()==0) {
			return tag;
		}
		for(int i=0;i<cname.size();i++) {
			if(cname.get(i) == cn) {
				tag = 1;
				return tag;
			}
		}
		return tag;
	}
	
	
	public void printDescribe() throws Exception {
		int pkNumber=0;
		
		GlobalTableInfo gt = getGlobalTableInfo(); // 获取全局表对象
		Vector cName = new Vector();//存储所有列的名称  不能够重复
		
		System.out.println("表名：" + tName);
		if(gt.searchTName(tName)==0)  //表名不重复
		{

			System.out.println("列："+vColumn.size());
			
			//针对每一列进行处理 
			for(int i=0;i<vColumn.size();i++) {
				
				//将所有的columnName存放到cName中
				if(searchColName(cName,((ColumnDef) vColumn.get(i)).getColName())==0) {
					cName.addElement(((ColumnDef) vColumn.get(i)).getColName());
				}else {
					System.out.println("列名重复，请仔细检查"+((ColumnDef) vColumn.get(i)).getColName());
//					throw new Error("列名重复!");
				}
				
				//检查是否有列被设置为primary key  将其加入到constraint中
				if(((ColumnDef) vColumn.get(i)).isPrimaryKey() == true) {
					TableConstraint tc = new TableConstraint();
					tc.setTcType(1);
			        tc.addElement(((ColumnDef) vColumn.get(i)).getColName());
			        ((ColumnDef) vColumn.get(i)).setUnique(true);
			        ((ColumnDef) vColumn.get(i)).setNotNull(true);
			        vConstraint.add(tc);
				}
				
				System.out.println(((ColumnDef) vColumn.get(i)).getDescribe());
			}
			
			
			System.out.println("约束："+vConstraint.size());
			//输出该表全部的约束
			for(int i=0;i<vConstraint.size();i++) {
				((TableConstraint) vConstraint.get(i)).printDescribe();
				
				//检查主键约束
				if(((TableConstraint) vConstraint.get(i)).getTcType()==1) {
					pkNumber += 1;
					if(pkNumber>1) {
						System.out.println("主键设置超过1次，请重新检查"+pkNumber);
						break;
//						throw new Error("主键设置超过一次");
					}
					Vector temp = ((TableConstraint) vConstraint.get(i)).gettcV();
					for(int j=0;j<temp.size();j++) {
						int t2 = cName.indexOf(temp.get(j));
						if(t2 == -1) {
							//表示该列不存在
							System.out.println("该列不存在，请检查后重新创建!"+temp.get(j));
//							throw new Error("该列不存在，请检查后重新创建!"+temp.get(j));
						}else {
							((ColumnDef) vColumn.get(t2)).setUnique(true);
					        ((ColumnDef) vColumn.get(t2)).setNotNull(true);
					        ((ColumnDef) vColumn.get(t2)).setPrimaryKey(true);
						}
					}
				}
				
				
				//检查外键约束
//				foreign key 部分 [字段名   关联表名    关联的字段名  delete update  ]
				if(((TableConstraint) vConstraint.get(i)).getTcType()==2) {
					String cname="",tname="",ctname="";
					Vector temp = ((TableConstraint) vConstraint.get(i)).gettcV();
					
					for(int j=0;j<temp.size();j++) {
						if(j==0) {
							cname = (String) temp.get(j);
						}
						else if(j==1) {
							tname = (String) temp.get(j);
						}else {
							ctname = (String) temp.get(j);
						}
					}
					
					if(cName.indexOf(cname) == -1) {
						//表示该列不存在
						System.out.println("该列不存在，请检查后重新创建!"+cname);
//						throw new Error("该列不存在，请检查后重新创建!");
					}
					
					if(gt.searchTName(tname)==0) {
						System.out.println("参考表不存在，请仔细检查!"+tname);
						//throw new Error("参考表不存在，请仔细检查!");
					}
					
					//获取关联表信息  检查列在不在
					TableContent temp_tc = new TableContent();
					temp_tc = gt.getTable(tname);
					int t3tag = 0;
					if(temp_tc != null) {
						Vector v = temp_tc.getvColumn();
						for(int s=0;s<v.size();s++) {
							if(ctname == ((ColumnDef) v.get(s)).getColName()) {
//								((ColumnDef) v.get(s)).setNotNull(true);
								t3tag = 1;
							}
						}
					}
					if(t3tag==0) {
						System.out.println("该列不存在,请重新设置");
//						throw new Error("该列不存在,请重新设置");
					}
				}
				
				//检查check约束  check (sage>18)  check (name is not null)
				if(((TableConstraint) vConstraint.get(i)).getTcType()==3) {
					//输出表达式  需要检查列是否存在
					Vector tct = ((TableConstraint) vConstraint.get(i)).gettcV();
					
					for(int q=0;q<tct.size();q++) {
						System.out.println(((Expression) tct.get(q)).printExpression());
						Vector e = ((Expression) tct.get(q)).getElements();
						if(cName.indexOf(e.get(0)) == -1) {
							//该列不存在
							System.out.println("该列不存在"+e.get(0));
							//throw new Error("该列不存在"+e.get(0));
						}
					}
				}
			}
			
			
			gt.getTableNames().addElement(tName);
			
		}
		else {
			System.out.println("该表已经存在，请仔细检查！");
//			throw new Error("该表已经存在，请仔细检查");
		}
		
	}
	
	
	public void WriteToFile()throws Exception
	{//序列化 生成一个文件
		String filename=this.getName()+".db";
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
		oos.writeObject(this);
		oos.close();
		System.out.println("写入完毕");
	}
	
	public TableContent ReadFromFile(String tablename) throws Exception
	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tablename+".db"));
		TableContent tc=(TableContent)ois.readObject();
		ois.close();
		return tc;
	}
	
	
	public void AppendToFile(String info) throws Exception{
		String filename = "globalTableName.db";
		File file = new File(filename);
		try {
			if(!file.exists()) {
				file.createNewFile();
			}else {
				FileWriter fileWriter = new FileWriter(file,true);
				info = info + System.getProperty("line.separator");
				fileWriter.write(info);
				fileWriter.flush();
				fileWriter.close();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void executeSQL(){
		//执行这个sql文件
		
	}
	
//addRowData(){
	
//}
	
	
}
/*
 * 
 * 缺省值 要画个图 判断有没有回路  -- 能否计算出来
 * 或者缺省值只能是常数  或者不允许设置缺省值
 */