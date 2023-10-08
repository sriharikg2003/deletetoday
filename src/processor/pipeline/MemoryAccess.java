package processor.pipeline;

import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable()){

			System.out.println("In MA SS");



			int ALU_RESULT = EX_MA_Latch.getAluresult();
			System.out.println("MA has ALU RESULT " + ALU_RESULT);


			int DESTINATION = EX_MA_Latch.getDestination();
			boolean IS_WRITE_BACK = EX_MA_Latch.getWriteBack();
			String OPCODE = EX_MA_Latch.getOpCode();
			int rs1 = EX_MA_Latch.getrs1();
			int LOAD_RESULT = 0;

			MA_RW_Latch.MA_RW_instruction_in_integer  = EX_MA_Latch.EX_MA_instruction_in_integer;

// LOAD 
			if(OPCODE.equals("10110")){
				LOAD_RESULT= containingProcessor.getMainMemory().getWord(ALU_RESULT);
				System.out.println("Loaded " + LOAD_RESULT);

			}


// STORE 
			else if(OPCODE.equals("10111")){
				System.out.println("storing in mem "+ALU_RESULT+" rs1 "+rs1);
				containingProcessor.getMainMemory().setWord(ALU_RESULT, rs1);
				System.out.println("Stored " + ALU_RESULT);

			}

			MA_RW_Latch.setAluResult(ALU_RESULT);
			MA_RW_Latch.setLoadResult(LOAD_RESULT);
			MA_RW_Latch.setWriteBack(IS_WRITE_BACK);
			MA_RW_Latch.setDestination(DESTINATION);
			MA_RW_Latch.setOpCode(OPCODE);

			MA_RW_Latch.setRW_enable(true);
			// EX_MA_Latch.setMA_enable(false);
		}
	}

}
