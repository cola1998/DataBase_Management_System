package dataBase;

import java.util.*;
//insert into  tableName values ()
// insert into tableName (columnNames,) values (operand,)
//getfildValue  �������� ������ true false null �ַ���
	//�������Ƿ����
	//�����Ƿ����
	//�����в���Ϊ�գ���Ψһ  ����content��vdata
	//ֵ�����Ƿ���� �������ֵ�Ƿ����
//checkԼ�� unique

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
