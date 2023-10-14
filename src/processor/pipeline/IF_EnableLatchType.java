package processor.pipeline;

public class IF_EnableLatchType {
	
	public static boolean IF_enable;

	int IF_Ena_instruction_in_integer = -134217728; 



	public void set_IF_Ena_instruction_in_integer(int x){
		IF_Ena_instruction_in_integer = x;
	}


	public int get_IF_Ena_instruction_in_integer(){
		return IF_Ena_instruction_in_integer;
	}

	public IF_EnableLatchType()
	{
		IF_enable = true;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}

	public void setIF_enable(boolean IF_enable) {
		this.IF_enable = IF_enable;
	}

}
