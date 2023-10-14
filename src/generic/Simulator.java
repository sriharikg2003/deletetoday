package generic;

import java.io.*;
import java.nio.ByteBuffer;

import processor.Clock;
import processor.Processor;
import processor.pipeline.Variables ;

public class Simulator {

	static Processor processor;
	static boolean simulationComplete;
	public static int LINE_NUMBER;


	public static int Branch_counter = 0 ;

	public static void setupSimulation(String assemblyProgramFile, Processor p) {
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		simulationComplete = false;
	}

	static void loadProgram(String assemblyProgramFile) {
		try {

			InputStream INPUT_FILE = new BufferedInputStream(new FileInputStream(assemblyProgramFile));

			byte[] buffer = new byte[4];
			LINE_NUMBER = 0;
			// Reading the file
			INPUT_FILE.read(buffer);

			processor.getRegisterFile().setProgramCounter(ByteBuffer.wrap(buffer).getInt());

			int bytesRead;
			while ((bytesRead = INPUT_FILE.read(buffer)) >= 4) {
				int content = ByteBuffer.wrap(buffer).getInt();
				processor.getMainMemory().setWord(LINE_NUMBER, content);
				LINE_NUMBER++;
			}

			// Set the value
			processor.getRegisterFile().setValue(0, 0);
			processor.getRegisterFile().setValue(1, 65535);
			processor.getRegisterFile().setValue(2, 65535);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void simulate() {
		int DYNAMIC_INSTRUCTIONS = 0;
		while (simulationComplete == false) {
						System.out.println("CYCLE :"+(DYNAMIC_INSTRUCTIONS+1) );

			// System.out.println("....... getRWUnit");
			processor.getRWUnit().performRW();
			// System.out.println("....... getMAUnit");
			processor.getMAUnit().performMA();
			// System.out.println("....... getEXUnit");
			processor.getEXUnit().performEX();
			// System.out.println("....... getOFUnit");
			processor.getOFUnit().performOF();
			// System.out.println("....... performIF ");
			processor.getIFUnit().performIF();
			// Incrementing for checking the number of dynamic Instructions
			DYNAMIC_INSTRUCTIONS++;
			System.out.println("\n ");

			Variables.NO_OF_CYCLES+=1;
			Clock.incrementClock();
		}

	

		System.out.println( DYNAMIC_INSTRUCTIONS + ": SO MANY" + Variables.final_PC);

		processor.getRegisterFile().setProgramCounter(Variables.final_PC);

		// Statistics

		Statistics stats = new Statistics();

		// Dynamic Instructions
		stats.setNumberOfInstructions(DYNAMIC_INSTRUCTIONS);

		// No Of Cycles
		stats.setNumberOfCycles((int) Clock.getCurrentTime());

		// Static Instructions
		stats.setNumberOfReadInstructions(LINE_NUMBER - 1);

		// Complete the simulation
		setSimulationComplete(true);

	}

	public static void setSimulationComplete(boolean value) {
		simulationComplete = value;
	}
}
