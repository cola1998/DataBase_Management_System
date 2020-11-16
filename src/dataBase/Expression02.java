package dataBase;

import java.util.*;
import staticContent.*;
//����ֱ�ӱ���һ���ļ�  writeToFile  ���л��ļ�
public class Expression02 implements java.io.Serializable {
	private Vector elements = new Vector();
	private String expstr = "";
	
	public void addOperator(Operator op) {
		//��Ӳ�����
		elements.add(op);
	}
	
	public void addElement(Object ob) {
		if (ob instanceof Operand || ob instanceof Operator || ob instanceof Function || ob instanceof Variable) {
			elements.add(ob);
		}else {
			throw new Error("�޷�ʶ���Ԫ�ز��ܹ������뵽���ʽ��:"+ob.getClass());
		}
	}
	
	public Object elementAt(int n) {
		return elements.get(n);//���ظ�������λ�õĶ���
	}
	
	public void setElement(int n,Object ob) {
		elements.set(n, ob); //��λ��n�Ķ����޸�Ϊob
	}
	
	public Vector getElements() {
		return elements; //�����������ʽ����
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
		//��ӡ�������ʽ
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
