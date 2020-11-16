package dataBase;

import java.util.*;
import staticContent.*;
//可以直接保存一个文件  writeToFile  序列化文件
public class Expression02 implements java.io.Serializable {
	private Vector elements = new Vector();
	private String expstr = "";
	
	public void addOperator(Operator op) {
		//添加操作符
		elements.add(op);
	}
	
	public void addElement(Object ob) {
		if (ob instanceof Operand || ob instanceof Operator || ob instanceof Function || ob instanceof Variable) {
			elements.add(ob);
		}else {
			throw new Error("无法识别的元素不能够被加入到表达式中:"+ob.getClass());
		}
	}
	
	public Object elementAt(int n) {
		return elements.get(n);//返回给定索引位置的对象
	}
	
	public void setElement(int n,Object ob) {
		elements.set(n, ob); //将位置n的对象修改为ob
	}
	
	public Vector getElements() {
		return elements; //返回整个表达式对象
	}
	
	public void printElements() {
		Object ob;
		for (int i=0;i<elements.size();i++) {
			ob = elements.get(i);
			System.out.println(ob.getClass().getName() + " ");
			if (ob instanceof Operand)
				System.out.print(((Operand) ob).getString()+" ");
			else if (ob instanceof Operator)
				System.out.print(((Operator)ob).stringRepresentation());
			
			System.out.println();
		}
		//打印整个表达式
	}
	
	public void mergerExpression(Expression exp2) {
		int n = exp2.getElements().size();
		for(int i = 0;i<n;i++) {
			this.elements.add(exp2.elementAt(i));
		}
	}
	//???expstr
	public void setExpStr(String str) {
		expstr = str;
	}
	
	public String getString() {
		return expstr;
	}
}
