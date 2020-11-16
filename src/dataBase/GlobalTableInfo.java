package dataBase;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GlobalTableInfo implements java.io.Serializable {
		public Vector tableNames;//����ű��� ���Ǵ�Ŷ��󣿣���
		
		
		public GlobalTableInfo() {
		//���캯��	
			tableNames = new Vector();
		}
		
		public Vector getTableNames() {
			return tableNames;
		}
		
		public int searchTName(String tname) {
			//���ݸ����ı��������Ƿ��Ѿ����ڣ�1-�Ѿ����ڣ�0-������
			int tag = 0;
			if(tableNames == null) {
				return 0;
			}
			for(int i = 0;i<tableNames.size();i++) {
				if(tableNames.get(i) == tname) {
					tag = 1;
					System.out.print("�ñ��Ѿ�����");
					break;
				}
			}
			
			if(tag==1) {
				//����1��ʾ�ñ����Ѿ�����
				return 1;
			}
			else {
				return 0;
			}
		}
		
		
		public TableContent getTable(String name) throws Exception {
			//���ݱ�����ȡ����
			int tag = 0;
			if(tableNames == null) {
				return null;
//				throw new Error("���ݿ�����ʱû���κ����ݱ�");
			}
			
			for(int i = 0;i<tableNames.size();i++) {
				if(tableNames.get(i) == name) {
					tag = 1;
					System.out.print("�ñ����");
				}
			}
			
			if(tag==1) {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name+".db"));
				TableContent tc = (TableContent)ois.readObject(); // ��ȡ��ӦtableContent����
				ois.close();
				//��ӡ��ȡ�����Ϣ�����
				tc.contentPrint();
				return tc;
			}
			else {
				return null;
				//throw new Error("�ñ�����");
			}
			
		}

		public void WriteToFile()throws Exception
		{//���л� ����һ���ļ�
			String filename="globalTableName.db";
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(this);
			oos.close();
			System.out.println("д�����");
		}
		
		
}
