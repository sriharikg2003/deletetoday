package processor.pipeline;

import processor.Processor;
import java.util.HashMap;
import java.util.Map;
import generic.Simulator;
import processor.Clock;
import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.Event.EventType;
import generic.ExecutionCompleteEvent;


import javax.sound.midi.SysexMessage;

public class Execute implements Element {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	IF_EnableLatchType IF_EnableLatch;
	public static Map<String, Integer> INSTRUCTION_TYPE = new HashMap<String, Integer>() {
		{
			put("00000", 3);
			put("00001", 2);
			put("00010", 3);
			put("00011", 2);
			put("00100", 3);
			put("00101", 2);
			put("00110", 3);
			put("00111", 2);
			put("01000", 3);
			put("01001", 2);
			put("01010", 3);
			put("01011", 2);
			put("01100", 3);
			put("01101", 2);
			put("01110", 3);
			put("01111", 2);
			put("10000", 3);
			put("10001", 2);
			put("10010", 3);
			put("10011", 2);
			put("10100", 3);
			put("10101", 2);
			put("10110", 2);
			put("10111", 2);
			put("11000", 1);
			put("11001", 6);
			put("11010", 6);
			put("11011", 6);
			put("11100", 6);
			put("11101", 0);
		}
	};


	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch,
			EX_IF_LatchType eX_IF_Latch) {
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	
	

	public void performEX() {
		if (OF_EX_Latch.isEX_enable()) {

			System.out.println("Instruction in EX is " + OF_EX_Latch.OF_EX_instruction_in_integer);

			if (OF_EX_Latch.isEX_busy()) {
				System.out.println("EX IS BUSY BYE BYE " );

				return;
			}


			// Simulator.getEventQueue().addEvent(
			// 	new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency,this,containingProcessor.getMainMemory() , containingProcessor.getRegisterFile().getProgramCounter())
			// );


			System.out.println("In EX");

			String OPCODE = OF_EX_Latch.getOpCode();
	

			int BRANCH_TARGET = OF_EX_Latch.getBranchTarget();
			System.out.println("In EX " +OPCODE + "\n" +  containingProcessor.getRegisterFile().getProgramCounter() + " BC " + BRANCH_TARGET );
			int rs1 = OF_EX_Latch.getOp1();
			int rs2 = OF_EX_Latch.getOp2();
			int rdval = OF_EX_Latch.getRd();
			int DESTINATION = OF_EX_Latch.getDestination();
			int ALU_RESULT = 0;
			int IMMEDIATE = OF_EX_Latch.getImmediate();

			int latency = Configuration.ALU_latency;


			EX_MA_Latch.EX_MA_instruction_in_integer = OF_EX_Latch.OF_EX_instruction_in_integer;



			

			boolean IS_WRITE_BACK = false;
			boolean local_branch_taken = false;
			// beg, bne, blt, bgt and not jump
			if (INSTRUCTION_TYPE.get(OPCODE) == 6) {




				System.out.println("OPCDE   "+ OPCODE + "   rs1 " + rs1 + " rdval " + rdval );
				int PC = containingProcessor.getRegisterFile().getProgramCounter();

				if (OPCODE.equals("11001")) { // beq
					if (rs1 == rdval) {
						PC = BRANCH_TARGET;
						local_branch_taken = true;
					}
				} else if (OPCODE.equals("11010")) { // bne
					if (rs1 != rdval) {
						PC = BRANCH_TARGET;

						local_branch_taken = true;


					}
				} else if (OPCODE.equals("11011")) { // blt
					if (rs1 < rdval) {
						PC = BRANCH_TARGET;
						local_branch_taken = true;



					}
				} else if (OPCODE.equals("11100")) { // bgt
					if (rs1 > rdval) {
						PC = BRANCH_TARGET;
						local_branch_taken = true;



					}
				}

			

				containingProcessor.getRegisterFile().setProgramCounter(PC);
				System.out.println("PC IS SET TO IN EX"  + containingProcessor.getRegisterFile().getProgramCounter());
			}

			else if (INSTRUCTION_TYPE.get(OPCODE) == 2) {
				IS_WRITE_BACK = true;
				if (OPCODE.equals("00001")) { // addi
					ALU_RESULT = rs1 + IMMEDIATE;
					System.out.println(" adding immediate rs1 " + rs1 + " imm " + IMMEDIATE + " results in " + ALU_RESULT );
				} else if (OPCODE.equals("00011")) { // subi
					ALU_RESULT = rs1 - IMMEDIATE;
				} else if (OPCODE.equals("00101")) { // muli
					ALU_RESULT = rs1 * IMMEDIATE;
					latency = Configuration.multiplier_latency;

				} else if (OPCODE.equals("00111")) { // divi
					ALU_RESULT = rs1 / IMMEDIATE;
					latency = Configuration.divider_latency;

					containingProcessor.getRegisterFile().setValue(31, rs1 % IMMEDIATE);
				} else if (OPCODE.equals("01001")) { // andi
					ALU_RESULT = rs1 & IMMEDIATE;
				} else if (OPCODE.equals("01011")) { // ori
					ALU_RESULT = rs1 | IMMEDIATE;
				} else if (OPCODE.equals("01101")) { // xori
					ALU_RESULT = rs1 ^ IMMEDIATE;
				} else if (OPCODE.equals("01111")) { // slt
					ALU_RESULT = (rs1 < IMMEDIATE) ? 1 : 0;
				} else if (OPCODE.equals("10001")) { // sll
					ALU_RESULT = rs1 << IMMEDIATE;
				} else if (OPCODE.equals("10011")) { // rsli
					ALU_RESULT = rs1 >>> IMMEDIATE;
				} else if (OPCODE.equals("10101")) { // rsai
					ALU_RESULT = rs1 >> IMMEDIATE;
				} else if (OPCODE.equals("10110")) { // load
					ALU_RESULT = rs1 + IMMEDIATE;
				} else if (OPCODE.equals("10111")) { // store
					ALU_RESULT = rdval + IMMEDIATE;
					IS_WRITE_BACK = false;
				}

			}

			else if (INSTRUCTION_TYPE.get(OPCODE) == 3) {

				IS_WRITE_BACK = true;
				if (OPCODE.equals("00000")) { // add
					ALU_RESULT = rs1 + rs2;
				} else if (OPCODE.equals("00010")) { // sub
					ALU_RESULT = rs1 - rs2;
				} else if (OPCODE.equals("00100")) { // mul
					latency = Configuration.multiplier_latency;

					ALU_RESULT = rs1 * rs2;
				} else if (OPCODE.equals("00110")) { // div
					latency = Configuration.divider_latency;

					ALU_RESULT = (int) (rs1 / rs2);
					containingProcessor.getRegisterFile().setValue(31, rs1 % rs2);
				} else if (OPCODE.equals("01000")) { // and
					ALU_RESULT = rs1 & rs2;
				} else if (OPCODE.equals("01010")) { // or
					ALU_RESULT = rs1 | rs2;
				} else if (OPCODE.equals("01100")) { // xor
					ALU_RESULT = rs1 ^ rs2;
				} else if (OPCODE.equals("01110")) { // slt
					ALU_RESULT = rs1 < rs2 ? 1 : 0;
				} else if (OPCODE.equals("10000")) { // sll
					ALU_RESULT = rs1 << rs2;
				} else if (OPCODE.equals("10010")) { // srl
					ALU_RESULT = rs1 >>> rs2;
				} else if (OPCODE.equals("10100")) { // sra
					ALU_RESULT = rs1 >> rs2;
				}

			}

			// jmp / jump
			else if (INSTRUCTION_TYPE.get(OPCODE) == 1) {
				int PC = BRANCH_TARGET;
				System.out.println("COMPULSORY BRANCH TAKE and branch target is " + BRANCH_TARGET);
				int oldPC = containingProcessor.getRegisterFile().getProgramCounter();
				containingProcessor.getRegisterFile().setProgramCounter(PC);
				System.out.println("PC IS SET FROM " + oldPC + " TO IN EX "  + containingProcessor.getRegisterFile().getProgramCounter());
				local_branch_taken = true;


			}


			if (local_branch_taken){

				Variables.ignore_instruction = true;
				Variables.bcos_ignore_new_pc = BRANCH_TARGET;
System.out.println("PLEASE ignore_instruction and consider PC at " + BRANCH_TARGET);
// Variables.BRANCH_LOCKED += 1;
// Simulator.getEventQueue().addEvent();
// OF_EX_Latch.null_and_void_ex_of();

}

			ExecutionCompleteEvent exevent  = new ExecutionCompleteEvent(Clock.getCurrentTime() + latency, this, this, ALU_RESULT, DESTINATION, rs1, BRANCH_TARGET, OPCODE, IS_WRITE_BACK, local_branch_taken);
			Simulator.getEventQueue().addEvent(
				exevent
			);


			System.out.println( " ADDING EX EVENT " + exevent );
			OF_EX_Latch.setEX_busy(true);

			

			


			// set all the things in the next latch.
			
			OF_EX_Latch.setEX_enable(false);
		}

	}

	@Override
	public void handleEvent(Event e) {

		System.out.println("I CAME IN EVENT HANDLE OF EX  BRO");

		if(EX_MA_Latch.IsMA_busy()){
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else if(e.getEventType() == EventType.ExecutionComplete){
			System.out.println("I CAME IN EVENT HANDLE else code  EX  BRO");


			ExecutionCompleteEvent event = (ExecutionCompleteEvent) e;
			String OPCODE = event.getOpCode();
			int ALU_RESULT = event.getAluresult();
			int DESTINATION = event.getDestination();
			boolean IS_WRITE_BACK = event.getIsWriteBack();
			int rs1 = event.getrs1();
			int branchTarget = event.getBranchTarget();
			Boolean local_branch_taken = event.getIsBranchTaken();


			EX_MA_Latch.setOpCode(OPCODE);
			EX_MA_Latch.setAluResult(ALU_RESULT);
			EX_MA_Latch.setDestination(DESTINATION);
			EX_MA_Latch.setrs1(rs1);
			EX_MA_Latch.setWriteBack(IS_WRITE_BACK);

			EX_MA_Latch.setMA_enable(true);

			  Variables.branch_taken_global_variable = 	local_branch_taken;

			  if (local_branch_taken){

				System.out.println("YES BRANCH TAKEN  ************************");
				Variables.BRANCH_LOCKED += 1;
				OF_EX_Latch.null_and_void_ex_of();
			  }





			OF_EX_Latch.setEX_enable(false);

			OF_EX_Latch.setDestination(32);

			//setiing pc if branch taken
			// if(isBranchTaken){
			// 	containingProcessor.getRegisterFile().setProgramCounter(branchTarget);
			// 	OF_EX_Latch.set_BranchTaken(true);
			// }

			OF_EX_Latch.setEX_busy(false);
			
		}
	}
}
