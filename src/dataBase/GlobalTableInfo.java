package dataBase;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GlobalTableInfo implements java.io.Serializable {
		public Vector tableNames;//仅存放表名 还是存放对象？？？
		
		
		public GlobalTableInfo() {
		//构造函数	
			tableNames = new Vector();
		}
		
		public Vector getTableNames() {
			return tableNames;
		}
		
		public int searchTName(String tname) {
			//根据给定的表名查找是否已经存在，1-已经存在，0-不存在
			int tag = 0;
			if(tableNames == null) {
				return 0;
			}
			for(int i = 0;i<tableNames.size();i++) {
				if(tableNames.get(i) == tname) {
					tag = 1;
					System.out.print("该表已经存在");
					break;
				}
			}
			
			if(tag==1) {
				//返回1表示该表名已经存在
				return 1;
			}
			else {
				return 0;
			}
		}
		
		
		public TableContent getTable(String name) throws Exception {
			//根据表名获取对象
			int tag = 0;
			if(tableNames == null) {
				return null;
//				throw new Error("数据库中暂时没有任何数据表");
			}
			
			for(int i = 0;i<tableNames.size();i++) {
				if(tableNames.get(i) == name) {
					tag = 1;
					System.out.print("该表存在");
				}
			}
			
			if(tag==1) {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name+".db"));
				TableContent tc = (TableContent)ois.readObject(); // 读取对应tableContent对象
				ois.close();
				//打印获取表的信息，检查
				tc.contentPrint();
				return tc;
			}
			else {
				return null;
				//throw new Error("该表不存在");
			}
			
		}

		public void WriteToFile()throws Exception
		{//序列化 生成一个文件
			String filename="globalTableName.db";
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(this);
			oos.close();
			System.out.println("写入完毕");
		}
		
		
}
