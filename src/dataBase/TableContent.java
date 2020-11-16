package dataBase;
import java.util.*;
import dataBase.*;

import java.io.*;


public class TableContent implements java.io.Serializable{ //Ҫʵ�����л�����Ҫʵ�ֽӿ�
	private static final long serialVersionUID= 1L; // ����Ҫдһ���汾��
	private String tName;//����
	private Vector vConstraint; //��Լ��  ÿһ��Ԫ��Ϊһ��TableConstraint����
	private Vector vColumn; //�� ÿһ��Ԫ��Ϊһ��ColumnDef����
	private Vector vData;  //����

	//���캯��
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
	
	public GlobalTableInfo getGlobalTableInfo() throws Exception {//��ȡȫ�ֱ����
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("globalTableInfo.db"));
		GlobalTableInfo gt = (GlobalTableInfo)ois.readObject(); // ����һ��ȫ�ֱ�Ķ���
		ois.close();
		
		Vector nameVector = gt.getTableNames();
		System.out.printf("��ǰ���ݿ��й���%d�����ݱ�",nameVector.size());
		for(int i =0;i<nameVector.size();i++) {
			System.out.println(nameVector.get(i));
		}
		return gt;
	}
	
	public void contentPrint() throws Exception{
		System.out.println("������" + tName);
		System.out.printf("����%d�У�",vColumn.size());
		for(int i=0;i<vColumn.size();i++) {
			System.out.printf("%d",i);
			System.out.println(((ColumnDef) vColumn.get(i)).getDescribe());
		}
		System.out.printf("����%d��Լ����",vConstraint.size());
		for(int i=0;i<vConstraint.size();i++) {
			System.out.printf("%d",i);
			((TableConstraint) vConstraint.get(i)).printDescribe();
		}
		
	}
	
	public int searchColName(Vector cname,String cn) {
		int tag = 0;//0-δ�ҵ� 1-�ҵ���
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
		
		GlobalTableInfo gt = getGlobalTableInfo(); // ��ȡȫ�ֱ����
		Vector cName = new Vector();//�洢�����е�����  ���ܹ��ظ�
		
		System.out.println("������" + tName);
		if(gt.searchTName(tName)==0)  //�������ظ�
		{

			System.out.println("�У�"+vColumn.size());
			
			//���ÿһ�н��д��� 
			for(int i=0;i<vColumn.size();i++) {
				
				//�����е�columnName��ŵ�cName��
				if(searchColName(cName,((ColumnDef) vColumn.get(i)).getColName())==0) {
					cName.addElement(((ColumnDef) vColumn.get(i)).getColName());
				}else {
					System.out.println("�����ظ�������ϸ���"+((ColumnDef) vColumn.get(i)).getColName());
//					throw new Error("�����ظ�!");
				}
				
				//����Ƿ����б�����Ϊprimary key  ������뵽constraint��
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
			
			
			System.out.println("Լ����"+vConstraint.size());
			//����ñ�ȫ����Լ��
			for(int i=0;i<vConstraint.size();i++) {
				((TableConstraint) vConstraint.get(i)).printDescribe();
				
				//�������Լ��
				if(((TableConstraint) vConstraint.get(i)).getTcType()==1) {
					pkNumber += 1;
					if(pkNumber>1) {
						System.out.println("�������ó���1�Σ������¼��"+pkNumber);
						break;
//						throw new Error("�������ó���һ��");
					}
					Vector temp = ((TableConstraint) vConstraint.get(i)).gettcV();
					for(int j=0;j<temp.size();j++) {
						int t2 = cName.indexOf(temp.get(j));
						if(t2 == -1) {
							//��ʾ���в�����
							System.out.println("���в����ڣ���������´���!"+temp.get(j));
//							throw new Error("���в����ڣ���������´���!"+temp.get(j));
						}else {
							((ColumnDef) vColumn.get(t2)).setUnique(true);
					        ((ColumnDef) vColumn.get(t2)).setNotNull(true);
					        ((ColumnDef) vColumn.get(t2)).setPrimaryKey(true);
						}
					}
				}
				
				
				//������Լ��
//				foreign key ���� [�ֶ���   ��������    �������ֶ���  delete update  ]
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
						//��ʾ���в�����
						System.out.println("���в����ڣ���������´���!"+cname);
//						throw new Error("���в����ڣ���������´���!");
					}
					
					if(gt.searchTName(tname)==0) {
						System.out.println("�ο������ڣ�����ϸ���!"+tname);
						//throw new Error("�ο������ڣ�����ϸ���!");
					}
					
					//��ȡ��������Ϣ  ������ڲ���
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
						System.out.println("���в�����,����������");
//						throw new Error("���в�����,����������");
					}
				}
				
				//���checkԼ��  check (sage>18)  check (name is not null)
				if(((TableConstraint) vConstraint.get(i)).getTcType()==3) {
					//������ʽ  ��Ҫ������Ƿ����
					Vector tct = ((TableConstraint) vConstraint.get(i)).gettcV();
					
					for(int q=0;q<tct.size();q++) {
						System.out.println(((Expression) tct.get(q)).printExpression());
						Vector e = ((Expression) tct.get(q)).getElements();
						if(cName.indexOf(e.get(0)) == -1) {
							//���в�����
							System.out.println("���в�����"+e.get(0));
							//throw new Error("���в�����"+e.get(0));
						}
					}
				}
			}
			
			
			gt.getTableNames().addElement(tName);
			
		}
		else {
			System.out.println("�ñ��Ѿ����ڣ�����ϸ��飡");
//			throw new Error("�ñ��Ѿ����ڣ�����ϸ���");
		}
		
	}
	
	
	public void WriteToFile()throws Exception
	{//���л� ����һ���ļ�
		String filename=this.getName()+".db";
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
		oos.writeObject(this);
		oos.close();
		System.out.println("д�����");
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
		//ִ�����sql�ļ�
		
	}
	
//addRowData(){
	
//}
	
	
}
/*
 * 
 * ȱʡֵ Ҫ����ͼ �ж���û�л�·  -- �ܷ�������
 * ����ȱʡֵֻ���ǳ���  ���߲���������ȱʡֵ
 */