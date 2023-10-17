package processor.memorysystem;

import generic.Element;
import generic.Event;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Simulator;
import generic.Event.EventType;
import processor.Clock;

public class MainMemory implements Element {
	int[] memory;

	public MainMemory() {
		memory = new int[65536];
	}

	public int getWord(int address) {
		return memory[address];
	}

	public void setWord(int address, int value) {
		memory[address] = value;
	}

	public String getContentsAsString(int startingAddress, int endingAddress) {
		if (startingAddress == endingAddress)
			return "";

		StringBuilder sb = new StringBuilder();
		sb.append("\nMain Memory Contents:\n\n");
		for (int i = startingAddress; i <= endingAddress; i++) {
			sb.append(i + "\t\t: " + memory[i] + "\n");
		}
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public void handleEvent(Event e) {


		System.out.println("handling mainmemory event");


		if(e.getEventType() == EventType.MemoryRead){

			
			MemoryReadEvent event  = (MemoryReadEvent)e;
			MemoryResponseEvent memRespEvent  = new MemoryResponseEvent(
				Clock.getCurrentTime(),
				 this,
				event.getRequestingElement(),
				getWord(event.getAddressToReadFrom()));
				System.out.println("LOADING FROM MEMORY " + getWord(event.getAddressToReadFrom()) );

			Simulator.getEventQueue().addEvent(memRespEvent);

			System.out.println("add memresp to memread " + memRespEvent);

		}
		else if(e.getEventType()== EventType.MemoryWrite){

			MemoryWriteEvent event  = (MemoryWriteEvent) e;
			setWord(event.getAddressToWriteTo(), event.getValue());

			MemoryResponseEvent memResEvent =new MemoryResponseEvent(
				Clock.getCurrentTime(),
				this,
				event.getRequestingElement(),
				0
			);
			Simulator.getEventQueue().addEvent(memResEvent);

			System.out.println("add memresp to memwrite "+ memResEvent);


		}
	}
}
