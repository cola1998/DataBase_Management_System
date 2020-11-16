package dataBase;

import staticContent.*;

//java.io.Serializable序列化接口？？
public class Operand02 implements java.io.Serializable{
	//数组定义时 指定字节数组长度为5
	//byte[]  s = new byte[5];
	private byte[] content;
	
	public Operand02() {
		this.SetNULL();
	}
	
	public void SetNULL() {
		
		if(content == null) {
			content = new byte[1];
		}
		if (1 != content.length) {
			content = null;
			content = new byte[1];
		}
		content[0] = (byte)DataType.NULL;
	}
	
	/*
	 * 构造函数  -- 整数
	 * */
	public Operand02(int i) {
		this.SetINT(i);
	}
	//设置值函数
	public void SetINT(int i) {
		if (content == null) {
			content = new byte[5];
		}
		if (5!= content.length) {
			content = null;
			content = new byte[5];
		}
		content[0] = (byte) DataType.INT;
		content[1] = (byte)((i & 0xff000000) >> 24);
		content[2] = (byte)((i & 0x00ff0000) >> 16);
		content[3] = (byte)((i & 0x0000ff00) >> 8);
		content[4] = (byte)((i & 0x000000ff));
	}
	//取值函数
	public int GetIntValue() throws Exception{
		if (5 != content.length)
			throw new Exception("非法数据类型转换");
		if (content.length > 0) {
			if (DataType.INT != content[0])
				throw new Exception("非法数据类型转换");
		}
		
		return (0xff000000 & (content[1] << 24)) | (0x00ff0000 & (content[2] << 12)) 
				| (0x0000ff00 & (content[3] << 8)) |(0x000000ff & content[4]);
	}
	
	public static void main(String args[]) {
	
		System.out.println("操作数类测试02");
		Operand02 o1 = new Operand02(7);
		try {
			System.out.println(o1.GetIntValue());
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
}
