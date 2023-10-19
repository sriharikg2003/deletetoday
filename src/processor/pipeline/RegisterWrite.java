package processor.pipeline;

import java.util.FormatterClosedException;
import processor.Clock;

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
		System.out.println("In RW");
		System.out.println("Instruction in RW is " + MA_RW_Latch.MA_RW_instruction_in_integer);


		if (MA_RW_Latch.isRW_enable())
		{
			System.out.println("In RW enable");

			String OPCODE = MA_RW_Latch.getOpCode();
	
			if (OPCODE.equals("11101"))
			{
				// System.out.println("In RW WRITE PC **************************** "+ (Variables.final_PC-1));
				
				// Variables.sim_complete = true;
				// Variables.final_PC = containingProcessor.getRegisterFile().getProgramCounter();
				// Variables.final_PC = Variables.final_PC-1;
				// containingProcessor.getRegisterFile().setProgramCounter(Variables.final_PC);
				Simulator.setSimulationComplete(true);
			}

			// if (Variables.end_instruction){
			// 	Simulator.setSimulationComplete(true);

			// }
	
			int destination = MA_RW_Latch.getDestination();
			int ALU_RESULT = MA_RW_Latch.getAluResult();
			boolean IS_WRITE_BACK = MA_RW_Latch.getWriteBack();
			int LOAD_RESULT = MA_RW_Latch.getLoadResult();
	
			if (IS_WRITE_BACK)
			{
				if (OPCODE.equals("10110"))
				{
					containingProcessor.getRegisterFile().setValue(destination, LOAD_RESULT);
					System.out.println("writing load result from reg "+ destination +" the value " + LOAD_RESULT + " at time " + Clock.getCurrentTime() );

				}
				else
				{
					containingProcessor.getRegisterFile().setValue(destination, ALU_RESULT);
					System.out.println("writing alu result to reg "+ destination + " alu values = " +ALU_RESULT + " at time " + Clock.getCurrentTime() );

				}
			}
	
			// MA_RW_Latch.setWriteBack(false);
			// MA_RW_Latch.setRW_enable(false);

			IF_EnableLatch.setIF_enable(true);

			if (Variables.end_instruction){
				IF_EnableLatch.setIF_enable(false);

			}
		}
	}
}
