package dataBase;

/*
 * ����
 */
public class Variable implements java.io.Serializable{

	private String name; 	//��������
	
	public Variable(String name)
	{
		this.name=name;
	}
	public String GetName()
	{
		return name;
	}
	
}
