package processor.pipeline;

import generic.Simulator;
import generic.MemoryReadEvent;
import processor.Processor;
import processor.pipeline.Variables;
import configuration.*;
import processor.Clock;
import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.MemoryResponseEvent;
import processor.memorysystem.MainMemory;
import java.util.HashMap;
import java.util.Map;
import java.lang.Integer;

public class InstructionFetch implements Element {

	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;///////////////////////////////////////////////////////
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	OF_EX_LatchType OF_EX_Latch;

	// Included
	// Processor containingProcessor;
	// OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;

	MA_RW_LatchType MA_RW_Latch;

	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch,
			IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch , EX_MA_LatchType eX_MA_Latch , MA_RW_LatchType MA_RW_Latch, OF_EX_LatchType OF_EX_Latch) {
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = MA_RW_Latch;
	}


	@Override
	public void handleEvent(Event e) {

		System.out.println("I CAME IN EVENT HANDLE OF IF  BRO");

		if (IF_OF_Latch.isOF_busy()) {

			System.out.println("YES IN EVENT HANDLE IF IS BUSY");
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);

		} else {
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			System.out.println("EVENT IS "+ event);

			// if (Variables.branch_taken_global_variable) {

			// 	IF_OF_Latch.null_and_void_if_of();
			// 	Variables.branch_taken_global_variable = false;
			// 	return;
			// }

			if (Variables.ignore_instruction){
				// for branch taken types
				System.out.println("UFF ignore_instruction and newpc is " + Variables.bcos_ignore_new_pc);
				IF_OF_Latch.null_and_void_if_of();
				// return;

				MemoryReadEvent myevent  = new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this,
				containingProcessor.getMainMemory(),
				Variables.bcos_ignore_new_pc);
				Simulator.getEventQueue().addEvent( myevent);

				
				Variables.last_sent_pc_for_mem_request = Variables.bcos_ignore_new_pc;
				// containingProcessor.getRegisterFile().setProgramCounter(Variables.bcos_ignore_new_pc);


				System.out.println("Sending memory special read event "+myevent+ "request with time " +(Clock.getCurrentTime() + Configuration.mainMemoryLatency));
				IF_EnableLatch.setIF_busy(true);
				
				Variables.ignore_instruction = false;
				IF_OF_Latch.setOF_enable(true);
				// Variables.NO_OF_INSTRUCTIONS_FETCHED += 1;


				return;

			}



				System.out.println("  CONFLICT hahaha " +  IF_OF_Latch.IF_OF_instruction_in_integer + " with newly fetched "+ event.getValue() );

			if ((this.checkConflict(event.getValue(), IF_OF_Latch.IF_OF_instruction_in_integer))){

				// data conflicts 
				System.out.println("  CONFLICT EXISTS " +  IF_OF_Latch.IF_OF_instruction_in_integer + " with newly fetched "+ event.getValue() + " and new value is obtained from ? " + containingProcessor.getRegisterFile().getProgramCounter() + "  == " + Variables.last_sent_pc_for_mem_request);


				IF_OF_Latch.null_and_void_if_of();
				// return;

				MemoryReadEvent myevent  = new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this,
				containingProcessor.getMainMemory(),
				containingProcessor.getRegisterFile().getProgramCounter());
				Simulator.getEventQueue().addEvent( myevent);

				
				Variables.last_sent_pc_for_mem_request = Variables.bcos_ignore_new_pc;
				// containingProcessor.getRegisterFile().setProgramCounter(Variables.bcos_ignore_new_pc);


				System.out.println("Sending memory special read event "+myevent+ "request with time " +(Clock.getCurrentTime() + Configuration.mainMemoryLatency));
				IF_EnableLatch.setIF_busy(true);
			
				return;

			}
			Variables.NO_OF_INSTRUCTIONS_FETCHED += 1;

			System.out.println("SET INSTRUCTION IN IF OF AS  " + event.getValue());

			IF_OF_Latch.setInstruction(event.getValue());
			IF_OF_Latch.setOF_enable(true);
			IF_EnableLatch.setIF_busy(false);
			IF_OF_Latch.set_IF_OF_instruction_in_integer(event.getValue());
			IF_OF_Latch.setOF_enable(true);


			if (event.getValue() == -402653184) {
				Variables.end_instruction = true;
				Variables.final_PC = containingProcessor.getRegisterFile().getProgramCounter();
				System.out.println("HEY FINAL END IN IF OF AS  GET LOST" + event.getValue());
				return;


			}

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

			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);

			if (newInstruction != 0) {
				System.out.println(currentPC + " lll " + containingProcessor.getMainMemory().getWord(currentPC));

				// Variables.NO_OF_INSTRUCTIONS_FETCHED += 1;
				IF_OF_Latch.setInstruction(newInstruction);
				IF_OF_Latch.set_IF_OF_instruction_in_integer(newInstruction);

				// Variables.final_PC = currentPC;
				if (newInstruction == -402653184) {
					Variables.final_PC = currentPC ;
				}

				containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);

				IF_OF_Latch.setOF_enable(true);
			}

			else {
				IF_EnableLatch.setIF_busy(true);
				return;
			}

		}
	}

	public void performIF() {
		System.out.println("IN IF " );
		if (IF_EnableLatch.isIF_enable()) {
			if (IF_EnableLatch.isIF_busy()) {
				System.out.println("IF enable IS BUSY BRO -------------");
				return;
			}
			else {
				if (Variables.end_instruction){
					System.out.println("NO INS IS REQUESTED  FECTCHD IN IN FUTURE");
					IF_EnableLatch.setIF_enable(false);
					return;
				}
			else
				{MemoryReadEvent myevent  = new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this,
				containingProcessor.getMainMemory(),
				containingProcessor.getRegisterFile().getProgramCounter());
				Simulator.getEventQueue().addEvent( myevent);
				Variables.last_sent_pc_for_mem_request = containingProcessor.getRegisterFile().getProgramCounter();
				System.out.println("Sending memory read event "+myevent+ "request with time " +(Clock.getCurrentTime() + Configuration.mainMemoryLatency));
				IF_EnableLatch.setIF_busy(true);
			}

			}

		}
	}

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
	public static String binaryofint(int IntegerValue) {
		String BinaryString = Long.toBinaryString(Integer.toUnsignedLong(IntegerValue) | 0x100000000L).substring(1);
		return BinaryString;
	}


	public boolean checkConflict(int a, int b) {
		// return false;
		String A = binaryofint(a);
		String B = binaryofint(b);
		String Aopcode = A.substring(0, 5);
		String Bopcode = B.substring(0, 5);

		// // System.out.println("\n"+A +" " +a );
		// // System.out.println(B + " " + b + "\n");

		if (a == 0 || b == 0) {
			return false;
		}
		if (a == -402653184 || b == -402653184) {
			// end ins
			return false;
		}

		if (a == -134217728 || b == -134217728) {
			// default instruction
			// 11111000000 which never comes
			return false;
		}

		int Ars1 = (int) Long.parseLong(A.substring(5, 10), 2);
		int AimmBit = 0;
		int Ars2 = (int) Long.parseLong(A.substring(10, 15), 2);
		;
		int Ard = 31;

		if (INSTRUCTION_TYPE.get(Aopcode) == 3) {
			// R3 type instruction
			Ars2 = (int) Long.parseLong(A.substring(10, 15), 2);
			Ard = (int) Long.parseLong(A.substring(15, 20), 2);
		}

		if (INSTRUCTION_TYPE.get(Aopcode) == 2) {
			// R type instruction
			Ard = (int) Long.parseLong(A.substring(10, 15), 2);
			AimmBit = 1;
			// System.out.println(Ard + " ARD llllllllllllllllllll");
		}

		if (INSTRUCTION_TYPE.get(Aopcode) == 6) {
			// R type instruction
			Ard = (int) Long.parseLong(A.substring(10, 15), 2);
			AimmBit = 1;
			// System.out.println(Ard + " ARD llllllllllllllllllll");
		}

		int Brd = 31;
		int BimmBit = 0;

		if (INSTRUCTION_TYPE.get(Bopcode) == 3) {
			// R3 type instruction
			Brd = (int) Long.parseLong(B.substring(15, 20), 2);
		}

		if (INSTRUCTION_TYPE.get(Bopcode) == 2) {
			// R type instruction
			Brd = (int) Long.parseLong(B.substring(10, 15), 2);
			BimmBit = 1;
		}

		// Check if Aopcode is one of "beq", "bne", "blt", or "bgt"
		if (Aopcode.equals("11001") || Aopcode.equals("11010") || Aopcode.equals("11011") || Aopcode.equals("11100")) {

			// System.out.println(Aopcode + " " + Brd + " " + Ars1 + " " + Ard + " " + "Outside cond");

			if (Brd == Ars1 || Brd == Ard) {

				System.out.println("Inside cond");

				return true;

			}

			// System.out.println("Branch but not checked for dependecy " + a);
			return false;
		}

		// Check if Bopcode is one of "store", "beq", "bne", "blt", or "bgt"
		if (Bopcode.equals("10111") || Bopcode.equals("11001") || Bopcode.equals("11010") || Bopcode.equals("11011")
				|| Bopcode.equals("11100")) {
			return false;
		}

		int src1 = Ars1;
		int src2 = Ars2;

		if (Aopcode.equals("10111")) {
			src2 = Ard;
		}

		int dest = Brd;
		boolean hasSrc2 = true;

		if (!Aopcode.equals("10111")) {
			if (AimmBit == 1) {
				hasSrc2 = false;
			}
		}

		if (src1 == dest) {
			return true;
		} else if (hasSrc2 && src2 == dest) {
			return true;
		}

		return false;
	}
	
}
