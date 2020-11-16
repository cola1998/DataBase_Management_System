package staticContent;
import java.util.Vector;
import java.util.Stack;
import dataBase.Operator;
import dataBase.*;
public class StaticMethod {
	public static Operand calExpression(Vector v) throws Exception
	{
//		for(int i=0;i<v.size();i++) {
//			System.out.println(v.get(i).getClass());
//		}
		//计算表达式  分开两个栈
		if (v.size() == 0)
			throw new Exception("无法计算");
		Stack opstack = new Stack();
		Stack odstack = new Stack(); //操作数
			
		Operand result = new Operand();
		Object o;
		Object opstr,opstacktopstr;
		Operand od1,od2,od3 = new Operand();
		
		for(int i=0;i<v.size();i++) {
			//遍历表达式中每一个元素
			o = v.get(i);
			if (o instanceof Operand) {
				odstack.push(o);//操作数直接压栈
			}
			else {
				if (o instanceof Function) {
					od3 = ((Function) o).eval();
//					System.out.println(od3.getString());
					odstack.push(od3);
					continue;
				}
				
				if(0 == opstack.size()) {
					opstack.add(o);
					continue;
				}
				
				opstacktopstr = opstack.peek();
				opstr = o;
		
				//当栈顶操作符优先级小于当前优先级压入栈中，或者是左括号
				if(((Operator) opstr).precedence() > ((Operator) opstacktopstr).precedence() &&  ")" != ((Operator) opstr).stringRepresentation() || "(" == ((Operator) opstr).stringRepresentation()) {
					opstack.push(opstr);
					continue;
				}
				if (")" == ((Operator) opstr).stringRepresentation()){
					while(true) {
						opstacktopstr = opstack.peek();
						if("(" == ((Operator) opstacktopstr).stringRepresentation()) {
							opstack.pop();
							break;
						}
						
						od2 = (Operand) odstack.pop();
						od1 = (Operand) odstack.pop();
						opstacktopstr = opstack.pop();
						odstack.push(cal0d(od1,od2,opstacktopstr));
					}
				}
				while(true) {
					if  (")" == ((Operator) opstr).stringRepresentation()) {
						break;
					}
					
					if (0 == opstack.size() ) {
						opstack.push(opstr);
						break;
					}
					
					opstacktopstr = opstack.peek();
					if(((Operator) opstr).precedence() >((Operator) opstacktopstr).precedence()) {
						opstack.push(opstr);
						break;
					}
					
					od2 = (Operand) odstack.pop();
					od1 = (Operand) odstack.pop();
					opstacktopstr = opstack.pop();
					odstack.push(cal0d(od1,od2,opstacktopstr));
				}
			}
		}
		
		while(0 < opstack.size())
		{
			od2 = (Operand) odstack.pop();
			od1 = (Operand) odstack.pop();
			opstacktopstr = opstack.pop();
			odstack.push(cal0d(od1,od2,opstacktopstr));
		}
		
		return (Operand)odstack.peek();
	}
	
	public static Operand cal0d(Operand od1,Operand od2,Object opstacktopstr) throws Exception
	{
//		switch ( ((Operator) opstacktopstr).stringRepresentation() ) {
//		case "+":
//			return  od2.OperatorAdd(od1);
//		case "-":
//			return od1.OperatorSub(od2);
//		case "*":
//			return od1.OperatorMul(od2);
//		case "/":
//			return od1.OperatorFDiv(od2);
//		case "%":
//			return od1.OperatorMod(od2);
//		case ">":
//			return od1.OperatorGreat(od2);
//		case "<":
//			return od1.OperatorLess(od2);
//		case "=":
//			return (od1.OperatorEqual(od2));
//		case "and":
//			return (od1.OperatorAnd(od2));
//		case "or":
//			return (od1.OperatorOr(od2));
//		case "not":
//			return (od1.OperatorNot(od2));
//		default:
//			System.out.println("不支持的计算符号：" + ((Operator) opstacktopstr).stringRepresentation() );
//			throw new Exception("不支持");
//		}
		return ((Operator) opstacktopstr).eval(od1,od2);
	}
	
	
	public static Operand calObject(Object ob) throws Exception
	{
		Expression exp;
		Function f;
		int i=0;
		if(ob instanceof Operand)
			return (Operand)ob;
		else if(ob instanceof Expression)
		{
			exp=(Expression) ob;
			return calExpression(exp.getElements());
		}
		else if(ob instanceof Function)
		{
			f=((Function) ob).get(((Function) ob).name);
			return f.eval();
		}
		else 
			throw new Exception("无法计算的元素");
					
	}
	
//	public static void main(String args[]) {
//		Vector v = new Vector();
//		Operand i1 = new Operand(1);
//		v.addElement(i1);
//		Operator s1 = new Operator("+");
//		v.addElement(s1);
//		Operand i2 = new Operand(2);
//		v.addElement(i2);
//		Operand od = new Operand();
//		try {
//			od = calExpression(v);
//		} catch (Exception e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//	}
}


