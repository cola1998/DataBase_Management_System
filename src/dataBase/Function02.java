package dataBase;
import java.util.*;

import staticContent.DataType;
import staticContent.StaticMethod;


public abstract class Function02 implements java.io.Serializable{
	//成员变量及函数定义
	public String name;
	public Vector param;
	
	public abstract Operand eval() throws Exception; //由参数来计算结果
	
	public Function02() {
		this.name = "";//函数名称
		this.param = new Vector();//参数
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
		else throw new Error("无法识别的函数类型："+fname);
	}
	
	private final static MaxFunction max_f = new MaxFunction();
	private final static MinFunction min_f = new MinFunction();
	private final static LengthFunction length_f = new LengthFunction();
}

class MaxFunction extends Function02{
	//计算最大值得函数
	public MaxFunction() {
		super("MAX");
	}
	
	public Operand eval() throws Exception{
		Operand od = new Operand();
		Operand od2;
		
		if(param.size() == 0)
			throw new Exception("参数为空");
		od = StaticMethod.calObject(param.get(0)); //获取第一个参数值,以此为标准 暂存最大参数值
		for (int i=0;i<param.size();i++) {
			od2 = StaticMethod.calObject(param.get(i));//依次获取参数中每个值
			if(od2.getNumericValue() > od.getNumericValue())
				od = od2; //如果比当前最大值大，覆盖当前最大值
		}
		return od;
	}
};
class MinFunction extends Function02{
	//计算最小值得函数
	public MinFunction() {
		super("MIN");
	}
	
	public Operand eval() throws Exception{
		Operand od = new Operand();
		Operand od2;
		
		if(param.size() == 0)
			throw new Exception("参数为空");
		od = StaticMethod.calObject(param.get(0)); //获取第一个参数值,以此为标准 暂存最小参数值
		for (int i=0;i<param.size();i++) {
			od2 = StaticMethod.calObject(param.get(i));//依次获取参数中每个值
			if(od2.getNumericValue() < od.getNumericValue())
				od = od2; //如果比当前最小值小，覆盖当前最小值
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
			throw new Exception("参数个数错误！");
		od = StaticMethod.calObject(param.get(0));
		if(od.getType()!=DataType.STRING)
			throw new Exception("参数类型错误！");
		str = od.GetStringValue();
		ods.SetINT(str.length()-2);
		
		return ods;
	}
}