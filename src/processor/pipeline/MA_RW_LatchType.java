package processor.pipeline;

public class MA_RW_LatchType {
	
	// Flag for control
	boolean RW_enable;
	String OPCODE;
	int ALU_RESULT;
	int LOAD_RESULT;
	boolean IS_WRITE_BACK;
	int DESTINATION;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean RW_enable) {
		this.RW_enable = RW_enable;
	}

	// Setter and getter for OPCODE
	public void setOpCode(String OPCODE){
		this.OPCODE = OPCODE;
	}
	public String getOpCode(){
		return OPCODE;
	}

	// Setter and getter for ALU_RESULT
	public void setAluResult(int ALU_RESULT){
		this.ALU_RESULT = ALU_RESULT;
	}
	public int getAluResult(){
		return ALU_RESULT;
	}

	// Setter and getter for DESTINATION
	public void setDestination(int DESTINATION){
		this.DESTINATION = DESTINATION;
	}
	public int getDestination(){
		return DESTINATION;
	}

	// Setter and getter for LOAD_RESULT
	public void setLoadResult(int LOAD_RESULT){
		this.LOAD_RESULT = LOAD_RESULT;
	}
	public int getLoadResult(){
		return LOAD_RESULT;
	}

	// Setter and getter for IS_WRITE_BACK
	public void setWriteBack(boolean IS_WRITE_BACK){
		this.IS_WRITE_BACK = IS_WRITE_BACK;
	}
	public boolean getWriteBack(){
		return IS_WRITE_BACK;
	}
}
