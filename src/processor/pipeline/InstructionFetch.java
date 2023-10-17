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

public class InstructionFetch implements Element {

	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;///////////////////////////////////////////////////////
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;

	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch,
			IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch) {
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
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
				System.out.println("UFF ignore_instruction and newpc is " + Variables.bcos_ignore_new_pc);
				// IF_OF_Latch.null_and_void_if_of();
				// return;

				MemoryReadEvent myevent  = new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this,
				containingProcessor.getMainMemory(),
				Variables.bcos_ignore_new_pc);
				Simulator.getEventQueue().addEvent( myevent);


				System.out.println("Sending memory special read event "+myevent+ "request with time " +(Clock.getCurrentTime() + Configuration.mainMemoryLatency));
				IF_EnableLatch.setIF_busy(true);
				
				Variables.ignore_instruction = false;

				return;

			}

			IF_OF_Latch.setInstruction(event.getValue());
			IF_OF_Latch.setOF_enable(true);
			IF_EnableLatch.setIF_busy(false);

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

				Variables.NO_OF_INSTRUCTIONS_FETCHED += 1;
				IF_OF_Latch.setInstruction(newInstruction);
				IF_OF_Latch.set_IF_OF_instruction_in_integer(newInstruction);

				// Variables.final_PC = currentPC;
				if (newInstruction == -402653184) {
					Variables.final_PC = currentPC + 1;
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

		System.out.println("IN IF ");

		if (IF_EnableLatch.isIF_enable()) {

			if (IF_EnableLatch.isIF_busy()) {

				System.out.println("IF enable IS BUSY BRO -------------");
				return;
			}

			else {

				

				MemoryReadEvent myevent  = new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this,
				containingProcessor.getMainMemory(),
				containingProcessor.getRegisterFile().getProgramCounter());
				Simulator.getEventQueue().addEvent( myevent);


				System.out.println("Sending memory read event "+myevent+ "request with time " +(Clock.getCurrentTime() + Configuration.mainMemoryLatency));
				IF_EnableLatch.setIF_busy(true);
			

			}

		}
	}

}
