package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if (MA_RW_Latch.isRW_enable())
		{
			String OPCODE = MA_RW_Latch.getOpCode();
	
			if (OPCODE.equals("11101"))
			{
				Simulator.setSimulationComplete(true);
			}
	
			int destination = MA_RW_Latch.getDestination();
			int ALU_RESULT = MA_RW_Latch.getAluResult();
			boolean IS_WRITE_BACK = MA_RW_Latch.getWriteBack();
			int LOAD_RESULT = MA_RW_Latch.getLoadResult();
	
			if (IS_WRITE_BACK)
			{
				if (OPCODE.equals("10110"))
				{
					containingProcessor.getRegisterFile().setValue(destination, LOAD_RESULT);
				}
				else
				{
					containingProcessor.getRegisterFile().setValue(destination, ALU_RESULT);
				}
			}
	
	
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}
}
