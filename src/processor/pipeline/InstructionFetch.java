package processor.pipeline;

import processor.Processor;
import processor.pipeline.Variables;

public class InstructionFetch {

	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;

	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch,
			IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch) {
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	public void performIF() {

		// System.out.println("BC TAKEN " + Variables.branch_taken_global_variable);

		if (Variables.branch_taken_global_variable) {

			IF_OF_Latch.null_and_void_if_of();
			Variables.branch_taken_global_variable = false;
			return;
		}

		if (IF_EnableLatch.isIF_enable()) {

			if (IF_OF_LatchType.check) {

				if (Variables.set_next_time) {
					Variables.set_next_time = false;
					System.out.println("Will set next time ");
					return;
				}

				int currentPC = Variables.CONFLICT_PC_OF;
				int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);

				System.out.println("Because of Conflict " + currentPC + " "
						+ newInstruction);

				IF_OF_Latch.setInstruction(newInstruction);
				IF_OF_Latch.set_IF_OF_instruction_in_integer(newInstruction);


					containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
				
				IF_OF_LatchType.check = false;

				return;
			}

			System.out.println("In IF");
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);

			if (newInstruction != 0) {
				System.out.println(currentPC + " lll " + containingProcessor.getMainMemory().getWord(currentPC));


				Variables.NO_OF_INSTRUCTIONS_FETCHED +=1;
				IF_OF_Latch.setInstruction(newInstruction);
				IF_OF_Latch.set_IF_OF_instruction_in_integer(newInstruction);

				// Variables.final_PC = currentPC;
				if (newInstruction==  -402653184){
					Variables.final_PC  = currentPC+1;
				}


				

					containingProcessor.getRegisterFile().setProgramCounter(currentPC +1 );


				

				IF_OF_Latch.setOF_enable(true);
			}

			else {
				System.out.println("0 ins in IF");
				return;
			}
		}
	}

}
