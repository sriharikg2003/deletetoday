package processor.pipeline;

public class OF_EX_LatchType {
	
	// Flag for control
	boolean EX_enable;
	int BRANCH_TARGET;
	String OPCODE;
	int IMMEDIATE;
	int op1, op2;
	int rd;
	int DESTINATION;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean EX_enable) {
		this.EX_enable = EX_enable;
	}

	// Setter and getter for OPCODE
	public void setOpCode(String OPCODE){
		this.OPCODE = OPCODE;
	}
	public String getOpCode(){
		return OPCODE;
	}

	// Setter and getter for IMMEDIATE
	public void setImmediate(int IMMEDIATE){
		this.IMMEDIATE = IMMEDIATE;
	}
	public int getImmediate(){
		return IMMEDIATE;
	}

	// Setter and getter for BRANCH_TARGET
	public int getBranchTarget(){
		return BRANCH_TARGET;
	}
	public void setBranchTarget(int BRANCH_TARGET){
		this.BRANCH_TARGET = BRANCH_TARGET;
	}

	// Setter and getter for op1
	public int getOp1(){
		return op1;
	}
	public void setOp1(int op1){
		this.op1 = op1;
	}

	// Setter and getter for op2
	public int getOp2(){
		return op2;
	}
	public void setOp2(int op2){
		this.op2 = op2;
	}

	// Setter and getter for rd
	public int getRd(){
		return rd;
	}
	public void setRd(int rd){
		this.rd = rd;
	}

	// Setter and getter for DESTINATION
	public void setDestination(int DESTINATION){
		this.DESTINATION = DESTINATION;
	}
	public int getDestination(){
		return DESTINATION;
	}
}
