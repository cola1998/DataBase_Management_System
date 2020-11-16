package dataBase;
import java.util.*;

import staticContent.DataType;
import staticContent.StaticMethod;


public abstract class Function02 implements java.io.Serializable{
	//��Ա��������������
	public String name;
	public Vector param;
	
	public abstract Operand eval() throws Exception; //�ɲ�����������
	
	public Function02() {
		this.name = "";//��������
		this.param = new Vector();//����
	}
	
	public Function02(String name) {
		this.name = name;
		this.param = new Vector();
	}
	
	public void SetName(String name) {
		this.name = name;
	}
	
	public void SetParam(Vector v) {
		this.param = v;
	}
	
	public void AddParam(Expression exp) {
		this.param.addElement(exp);
	}
	
	public static Function02 get(String fname) {
		if(fname.toLowerCase().equals("max")) {return max_f;}
		else if (fname.toLowerCase().equals("min")) {return min_f;}
		else if (fname.toLowerCase().equals("length")) {return length_f;}
		else throw new Error("�޷�ʶ��ĺ������ͣ�"+fname);
	}
	
	private final static MaxFunction max_f = new MaxFunction();
	private final static MinFunction min_f = new MinFunction();
	private final static LengthFunction length_f = new LengthFunction();
}

class MaxFunction extends Function02{
	//�������ֵ�ú���
	public MaxFunction() {
		super("MAX");
	}
	
	public Operand eval() throws Exception{
		Operand od = new Operand();
		Operand od2;
		
		if(param.size() == 0)
			throw new Exception("����Ϊ��");
		od = StaticMethod.calObject(param.get(0)); //��ȡ��һ������ֵ,�Դ�Ϊ��׼ �ݴ�������ֵ
		for (int i=0;i<param.size();i++) {
			od2 = StaticMethod.calObject(param.get(i));//���λ�ȡ������ÿ��ֵ
			if(od2.getNumericValue() > od.getNumericValue())
				od = od2; //����ȵ�ǰ���ֵ�󣬸��ǵ�ǰ���ֵ
		}
		return od;
	}
};
class MinFunction extends Function02{
	//������Сֵ�ú���
	public MinFunction() {
		super("MIN");
	}
	
	public Operand eval() throws Exception{
		Operand od = new Operand();
		Operand od2;
		
		if(param.size() == 0)
			throw new Exception("����Ϊ��");
		od = StaticMethod.calObject(param.get(0)); //��ȡ��һ������ֵ,�Դ�Ϊ��׼ �ݴ���С����ֵ
		for (int i=0;i<param.size();i++) {
			od2 = StaticMethod.calObject(param.get(i));//���λ�ȡ������ÿ��ֵ
			if(od2.getNumericValue() < od.getNumericValue())
				od = od2; //����ȵ�ǰ��СֵС�����ǵ�ǰ��Сֵ
		}
		return od;
	}
};

class LengthFunction extends Function02{
	public LengthFunction() {
		super("LENGTH");
	}
	public Operand eval() throws Exception{
		Operand od = new Operand();
		Operand ods = new Operand();
		String str;
		
		if(param.size() != 1)
			throw new Exception("������������");
		od = StaticMethod.calObject(param.get(0));
		if(od.getType()!=DataType.STRING)
			throw new Exception("�������ʹ���");
		str = od.GetStringValue();
		ods.SetINT(str.length()-2);
		
		return ods;
	}
}