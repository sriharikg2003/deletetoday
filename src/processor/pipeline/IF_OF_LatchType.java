package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	int INSTRUCTION;
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


}
