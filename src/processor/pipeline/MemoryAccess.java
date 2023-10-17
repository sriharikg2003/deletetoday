package processor.pipeline;

import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Simulator;
import generic.Event.EventType;
import processor.Clock;
import processor.Processor;

public class MemoryAccess implements Element{
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	


	@Override
	public void handleEvent(Event e) {

		System.out.println("I CAME IN EVENT HANDLE OF MA  BRO");

	
		System.out.println(e.getEventType());
		if(e.getEventType() == EventType.MemoryResponse){

			System.out.println("I CAME IN memresponse if  BRO");

			MemoryResponseEvent event = (MemoryResponseEvent)e;

			int aluResult = EX_MA_Latch.getAluresult();
			boolean isWriteback = EX_MA_Latch.getWriteBack();
			int destination = EX_MA_Latch.getDestination();
			String optCode = EX_MA_Latch.getOpCode();
			int loadResult = event.getValue();
			
			MA_RW_Latch.setAluResult(aluResult);
			MA_RW_Latch.setLoadResult(loadResult);
			MA_RW_Latch.setWriteBack(isWriteback);
			MA_RW_Latch.setDestination(destination);
			MA_RW_Latch.setOpCode(optCode);

			MA_RW_Latch.setRW_enable(true);
			EX_MA_Latch.setMA_enable(false);
			EX_MA_Latch.set_MA_busy(false);
			EX_MA_Latch.setDestination(32);
		}

	}

	public void performMA()
	{
		System.out.println("In MEM");

		System.out.println("Instruction in MA is " + EX_MA_Latch.EX_MA_instruction_in_integer);



		if(EX_MA_Latch.isMA_enable()){

			if(EX_MA_Latch.IsMA_busy()){
				System.out.println("MA IS BUSY");
				return;
			}

			System.out.println("In MA NOT BUSY ");



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
				// LOAD_RESULT= containingProcessor.getMainMemory().getWord(ALU_RESULT);
				// System.out.println("Loaded " + LOAD_RESULT);
				System.out.println("will load at time  " + (		Clock.getCurrentTime() + Configuration.mainMemoryLatency));


				Simulator.getEventQueue().addEvent(
						new MemoryReadEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
						 	this,
							containingProcessor.getMainMemory(), 
							EX_MA_Latch.getAluresult()
							)
					);

					EX_MA_Latch.set_MA_busy(true);

			}


// STORE 
			else if(OPCODE.equals("10111")){

				System.out.println("sendig memory store request for time  " + (		Clock.getCurrentTime() + Configuration.mainMemoryLatency));

				// System.out.println("storing in mem "+ALU_RESULT+" rs1 "+rs1);
				// containingProcessor.getMainMemory().setWord(ALU_RESULT, rs1);
				// System.out.println("Stored " + ALU_RESULT);
				Simulator.getEventQueue().addEvent(
						new MemoryWriteEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
							this, 
							containingProcessor.getMainMemory(), 
							EX_MA_Latch.getAluresult(), 
							rs1)						
					);
					EX_MA_Latch.set_MA_busy(true);

			}

			else
			{

				System.out.println("setting MA resuklts to MA_RW_latch");

				MA_RW_Latch.setAluResult(ALU_RESULT);
			MA_RW_Latch.setLoadResult(LOAD_RESULT);
			MA_RW_Latch.setWriteBack(IS_WRITE_BACK);
			MA_RW_Latch.setDestination(DESTINATION);
			MA_RW_Latch.setOpCode(OPCODE);

			MA_RW_Latch.setRW_enable(true);
		}
			// EX_MA_Latch.setMA_enable(false);
		}
	}

}



