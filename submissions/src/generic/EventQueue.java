package generic;

import java.util.Comparator;
import java.util.PriorityQueue;

import processor.Clock;

public class EventQueue {
	
	PriorityQueue<Event> queue;
	
	public EventQueue()
	{
		queue = new PriorityQueue<Event>(new EventComparator());
	}
	
	public void addEvent(Event event)
	{
		queue.add(event);
	}

	public void processEvents()
	{


		System.out.println("in fucntion Process events");
		System.out.println("SIze of quueue "+ queue.size());
		System.out.println("current time " + Clock.getCurrentTime());


		if(queue.isEmpty() == false ){
			System.out.println("top element's time " + queue.peek().getEventTime());
			
		}


		while(queue.isEmpty() == false && queue.peek().getEventTime() <= Clock.getCurrentTime())
		{
			// System.out.println("SIZE OF QUEUE IS  "+ queue.size());

			Event event = queue.poll();
			System.out.println("PROCESSING EVENT "+ event + " with time " + event.getEventTime());
			event.getProcessingElement().handleEvent(event);
			System.out.println("Prcoessed EVENT "+ event + " done");

		}

		// System.out.println(" NOW IS QUE EMPTY ? " + queue.size());

	}
}

class EventComparator implements Comparator<Event>
{
	@Override
    public int compare(Event x, Event y)
    {
		if(x.getEventTime() < y.getEventTime())
		{
			return -1;
		}
		else if(x.getEventTime() > y.getEventTime())
		{
			return 1;
		}
		else
		{
			return 0;
		}
    }
}
