package processor.pipeline;

public class EX_MA_LatchType {
	
	// Flags for control
	boolean MA_enable;
	boolean ISWRITEBACK;
	public boolean ma_busy;
	// ALU result and destination register
	int ALU_RESULT;
	int DESTINATION;
	String OPCODE;
	int rs1;
	

	int EX_MA_instruction_in_integer = -134217728; 

	public void set_EX_MA_instruction_in_integer(int x){
		EX_MA_instruction_in_integer = x;
	}

	public boolean IsMA_busy(){
		return ma_busy;
	}
	public void  set_MA_busy(boolean value){
		 ma_busy = value;
	}
	public int get_EX_MA_instruction_in_integer(){
		return EX_MA_instruction_in_integer;
	}

	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean MA_enable) {
		this.MA_enable = MA_enable;
	}

	// Setter and getter for OPCODE
	public void setOpCode(String OPCODE){
		this.OPCODE = OPCODE;
	}
	public String getOpCode(){
		return OPCODE;
	}

	// Setter and getter for rs1
	public void setrs1(int rs1){
		this.rs1 = rs1;
	}
	public int getrs1(){
		return rs1;
	}

	// Setter and getter for DESTINATION
	public void setDestination(int DESTINATION){
		this.DESTINATION = DESTINATION;
	}
	public int getDestination(){
		return DESTINATION;
	}

	// Setter and getter for ALU_RESULT
	public void setAluResult(int ALU_RESULT){
		this.ALU_RESULT = ALU_RESULT;
	}
	public int getAluresult(){
		return ALU_RESULT;
	}

	// Setter and getter for ISWRITEBACK
	public void setWriteBack(boolean ISWRITEBACK){
		this.ISWRITEBACK = ISWRITEBACK;
	}
	public boolean getWriteBack(){
		return ISWRITEBACK;
	}
}
