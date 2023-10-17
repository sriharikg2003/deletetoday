package processor.pipeline;

public class OF_EX_LatchType {

	// Flag for control
	public  boolean EX_enable;

	public boolean EX_busy;
	int OF_EX_instruction_in_integer = -134217728; 

	int INSTRUCTION;

	public void set_OF_EX_instruction_in_integer(int x){
		OF_EX_instruction_in_integer = x;
	}


	public int get_OF_EX_instruction_in_integer(){
		return OF_EX_instruction_in_integer;
	}


	int BRANCH_TARGET;
	String OPCODE;
	int IMMEDIATE;
	int op1, op2;
	int rd;
	int DESTINATION;
	public  boolean isBranchTaken;

	public OF_EX_LatchType() {
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean EX_enable) {
		this.EX_enable = EX_enable;
	}

	// Setter and getter for OPCODE
	public void setOpCode(String OPCODE) {
		this.OPCODE = OPCODE;
	}

	public String getOpCode() {
		return OPCODE;
	}

	// Setter and getter for IMMEDIATE
	public void setImmediate(int IMMEDIATE) {
		this.IMMEDIATE = IMMEDIATE;
	}

	public int getImmediate() {
		return IMMEDIATE;
	}

	// Setter and getter for BRANCH_TARGET
	public int getBranchTarget() {
		return BRANCH_TARGET;
	}

	public void setBranchTarget(int BRANCH_TARGET) {
		this.BRANCH_TARGET = BRANCH_TARGET;
	}

	// Setter and getter for op1
	public int getOp1() {
		return op1;
	}

	public void setOp1(int op1) {
		this.op1 = op1;
	}

	// Setter and getter for op2
	public int getOp2() {
		return op2;
	}

	public void setOp2(int op2) {
		this.op2 = op2;
	}

	// Setter and getter for rd
	public int getRd() {
		return rd;
	}

	public void setRd(int rd) {
		this.rd = rd;
	}

	// Setter and getter for DESTINATION
	public void setDestination(int DESTINATION) {
		this.DESTINATION = DESTINATION;
	}

	public int getDestination() {
		return DESTINATION;
	}

	// Setter and getter for isBranchTaken
	public boolean is_BranchTaken() {
		return isBranchTaken;
	}

	// Setter and getter for isBranchTaken
	public void set_BranchTaken(Boolean boolean_for_branch_taken) {
		this.isBranchTaken = boolean_for_branch_taken;
	}

	public void null_and_void_ex_of(){
		 EX_enable = false;
		 BRANCH_TARGET = 0;
		 OPCODE = "00000";
		 IMMEDIATE = 0;
		 op1 = 0;
		 op2 = 0;
		 rd = 0;
		 DESTINATION = 0;
		 isBranchTaken = false;
		 OF_EX_instruction_in_integer = 0;
	}


	public boolean isEX_busy() {
		return EX_busy;
	}

	public void setEX_busy(boolean value) {
		EX_busy = value;
	}


	public int getInstruction() {
		return INSTRUCTION;
	}

	public void setInstruction(int INSTRUCTION) {
		this.INSTRUCTION = INSTRUCTION;
	}
}
