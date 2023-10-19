package generic;

import java.io.PrintWriter;

import processor.pipeline.Variables;

public class Statistics {
	
	// TODO add your statistics here
	static int numberOfInstructions;
	static int numberOfCycles;
	static int numberOfReadInstructions;

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			writer.println("Number of static instructions = "+ numberOfReadInstructions);
			// writer.println("Number of dynamic instructions executed = " + numberOfInstructions);
			// writer.println("Number of cycles taken = " + numberOfCycles);

			// public static int NO_OF_CYCLES = 0;
			// public static int BRANCH_LOCKED = 0;
			// public static int DATA_CLASHES = 0;
			// public static int NO_OF_INSTRUCTIONS_FETCHED = 0;
			
			System.out.println("Number of cycles = " + Variables.NO_OF_CYCLES);


			System.out.println("Number of instructions fetched = "+ Variables.NO_OF_INSTRUCTIONS_FETCHED);
			// System.out.println("IPC "+  (Variables.NO_OF_INSTRUCTIONS_FETCHED/Variables.NO_OF_CYCLES));

			writer.println("Number of cycles = " + Variables.NO_OF_CYCLES);


			writer.println("Number of instructions fetched = "+ Variables.NO_OF_INSTRUCTIONS_FETCHED);
			// writer.println("IPC "+ (Variables.NO_OF_INSTRUCTIONS_FETCHED/Variables.NO_OF_CYCLES));



			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics
	public void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}

	public void setNumberOfReadInstructions(int numberOfReadInstructions) {
		Statistics.numberOfReadInstructions = numberOfReadInstructions;
	}
}
