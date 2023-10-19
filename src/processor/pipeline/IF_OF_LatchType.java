package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	int INSTRUCTION;

	int IF_OF_instruction_in_integer = -134217728 ;
	public  boolean OF_busy;
	public static boolean check ;


	public void set_IF_OF_instruction_in_integer(int x){
		IF_OF_instruction_in_integer = x;
	}


	public int get_IF_OF_instruction_in_integer(){
		return IF_OF_instruction_in_integer;
	}


	boolean IF_OF_Later;
	int ctr = 0;
	public IF_OF_LatchType()
	{
		OF_enable = false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public int getInstruction() {
		return INSTRUCTION;
	}

	public void setInstruction(int INSTRUCTION) {
		this.INSTRUCTION = INSTRUCTION;
	}

	public void enable_IF_OF_Later(){

		if (ctr ==1){
			this.setOF_enable(true);
			ctr = 0;
		}
		ctr = 1;
	}

	public void null_and_void_if_of(){
		 OF_enable = true;
		INSTRUCTION  = 0;
		IF_OF_instruction_in_integer = 0;
		
	}


	public boolean isOF_busy() {
		return OF_busy;
	}

	public void setOF_busy(boolean value) {
		OF_busy = value;
	}
}
