package dataBase;

public abstract class Operator02 implements java.io.Serializable{
	private String op; //符号的字符串表达方式
	private int precedence;
	
	//构造函数
	protected Operator02(String op,int precedence) {
		this.op = op;
		this.precedence = precedence;
	}
	
	public abstract Operand eval(Operand o1,Operand o2);
	
	public int precedence() {
		return precedence;
	}
	
	String stringRepresentation() {
		return op;
	}
	
	//传给Operator函数一个字符串op，判断是什么op
	public static Operator02 get(String op) {
		if (op.equals("+")) {return add_op;}
		else if (op.equals("-")) { return sub_op; }
		else if (op.equals("*")) { return mul_op; }
		else if (op.equals("/")) { return fdiv_op; }
		else if (op.equals("%")) { return mod_op; }
		else if (op.equals("(")) { return lpara_op; }
		else if (op.equals(")")) { return rpara_op; }
		else if (op.equals(">")) { return great_op; }
		else if (op.equals("<")) { return less_op; }
		else if (op.equals("==")) { return equal_op; }
		else if (op.equals("and")) { return and_op; }
		else if (op.equals("or")) { return or_op; }
		else if (op.equals("not")) { return not_op; }
		throw new Error("无法识别的操作符："+op);
	}
	
	private final static AddOperator add_op = new AddOperator();
	private final static SubOperator sub_op = new SubOperator();
	private final static MulOperator mul_op = new MulOperator();
	private final static FDivOperator fdiv_op = new FDivOperator();
	
	private final static AddOperator mod_op = new AddOperator();
	private final static AddOperator lpara_op = new AddOperator();
	private final static AddOperator rpara_op = new AddOperator();
	private final static AddOperator great_op = new AddOperator();
	private final static AddOperator less_op = new AddOperator();
	private final static AddOperator equal_op = new AddOperator();
	private final static AddOperator and_op = new AddOperator();
	private final static AddOperator or_op = new AddOperator();
	private final static AddOperator not_op = new AddOperator();
	
}

//各操作符及操作定义
class AddOperator extends Operator02{
	public AddOperator() {
		super("+",10);
	}
	
	public Operand eval(Operand o1,Operand o2) {
		Operand rod = new Operand();
		try {
			rod = o1.OperatorAdd(o2);
		}catch (Exception e) {
			System.out.println(e.toString());
		}
		return rod;
	}
}

class SubOperator extends Operator02{
	public SubOperator() {
		super("-",10);
	}
	
	public Operand eval(Operand o1,Operand o2) {
		Operand rod = new Operand();
		try {
			rod = o1.OperatorSub(o2);
		}catch (Exception e) {
			System.out.println(e.toString());
		}
		return rod;
	}
}

class MulOperator extends Operator02{
	public MulOperator() {
		super("*",25);
	}
	
	public Operand eval(Operand o1,Operand o2) {
		Operand rod = new Operand();
		try {
			rod = o1.OperatorMul(o2);
		}catch (Exception e) {
			System.out.println(e.toString());
		}
		return rod;
	}
}

class FDivOperator extends Operator02{
	public FDivOperator() {
		super("/",10);
	}
	
	public Operand eval(Operand o1,Operand o2) {
		Operand rod = new Operand();
		try {
			rod = o1.OperatorFDiv(o2);
		}catch (Exception e) {
			System.out.println(e.toString());
		}
		return rod;
	}
}
