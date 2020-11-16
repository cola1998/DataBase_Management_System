package dataBase;

import java.util.*;
//insert into  tableName values ()
// insert into tableName (columnNames,) values (operand,)
//getfildValue  负数整数 浮点数 true false null 字符串
	//检查表明是否存在
	//列名是否存在
	//主键列不能为空，且唯一  遍历content的vdata
	//值类型是否符合 外键插入值是否存在
//check约束 unique

public class InsertIntoInfo implements java.io.Serializable{
	public String tName;
	public Vector column;
//	public Vector values;
	
	public InsertIntoInfo() {
		tName = "";
		column = new Vector();
//		values = new Vector();
	}
}
