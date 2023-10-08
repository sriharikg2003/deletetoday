package processor.pipeline;

public class IF_EnableLatchType {
	
	public static boolean IF_enable;

	public  int IF_Ena_instruction_in_integer ; 

	public IF_EnableLatchType()
	{
		this.IF_enable = true;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}

	public void setIF_enable(boolean IF_enable) {
		this.IF_enable = IF_enable;
	}

}
