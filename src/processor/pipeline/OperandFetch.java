package processor.pipeline;

import processor.Processor;
import java.util.HashMap;
import java.util.Map;
import java.lang.Integer;
import processor.pipeline.Variables;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;

	// Included
	// Processor containingProcessor;
	// OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	IF_EnableLatchType IF_EnableLatch;
	MA_RW_LatchType MA_RW_Latch;

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

	public static int binaryToInt(String BinaryString) {
		// Returns same if number positive
		if (BinaryString.substring(0, 1).equals("0"))
			return (int) Long.parseLong(BinaryString, 2);
		else {
			int VAL = (int) Long.parseLong(BinaryString, 2);
			VAL = VAL - (int) Math.pow((double) 2, (double) BinaryString.length());
			return VAL;
		}
	}

	public static String binaryofint(int IntegerValue) {
		String BinaryString = Long.toBinaryString(Integer.toUnsignedLong(IntegerValue) | 0x100000000L).substring(1);
		return BinaryString;
	}

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch,
			EX_MA_LatchType EX_MA_Latch, MA_RW_LatchType MA_RW_Latch) {
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = EX_MA_Latch;
		this.MA_RW_Latch = MA_RW_Latch;
	}

	String add = "00000"; // Opcode
	String addi = "00001"; // Opcode
	String sub = "00010"; // Opcode
	String subi = "00011"; // Opcode
	String mul = "00100"; // Opcode
	String muli = "00101"; // Opcode
	String div = "00110"; // Opcode
	String divi = "00111"; // Opcode
	String andOp = "01000"; // Opcode
	String andi = "01001"; // Opcode
	String orOp = "01010"; // Opcode
	String ori = "01011"; // Opcode
	String xorOp = "01100"; // Opcode
	String xori = "01101"; // Opcode
	String slt = "01110"; // Opcode
	String slti = "01111"; // Opcode
	String sll = "10000"; // Opcode
	String slli = "10001"; // Opcode
	String srl = "10010"; // Opcode
	String srli = "10011"; // Opcode
	String sra = "10100"; // Opcode
	String srai = "10101"; // Opcode
	String load = "10110"; // Opcode
	String store = "10111"; // Opcode
	String jmp = "11000"; // Opcode
	String beq = "11001"; // Opcode
	String bne = "11010"; // Opcode
	String blt = "11011"; // Opcode
	String bgt = "11100"; // Opcode
	String end = "11101"; // Opcode

	public boolean checkConflict(int a, int b) {
		return false;
		
	}

	public void performOF() {

		

		if (IF_OF_Latch.isOF_enable()) {

			System.out.println("In OF");
			System.out.println("Instruction in OF is " + IF_OF_Latch.IF_OF_instruction_in_integer);

			if (OF_EX_Latch.isEX_busy()) {

				System.out.println("setting of to busy");

				IF_OF_Latch.setOF_busy(true);
				return;
			} else
				IF_OF_Latch.setOF_busy(false);

			System.out.println("***** PRINTING CONFLICT OF - EX  " + this
					.checkConflict(IF_OF_Latch.IF_OF_instruction_in_integer, OF_EX_Latch.OF_EX_instruction_in_integer));

			System.out.println("****** PRINTING CONFLICT OF - MA: " + this
					.checkConflict(IF_OF_Latch.IF_OF_instruction_in_integer, MA_RW_Latch.MA_RW_instruction_in_integer));

			// System.out.println("****** PRINTING CONFLICT EX - MA: " +
			// this.checkConflict(IF_OF_Latch.IF_OF_instruction_in_integer,
			// EX_MA_Latch.EX_MA_instruction_in_integer));
			System.out.println("\nIF OF " + IF_OF_Latch.IF_OF_instruction_in_integer);

			System.out.println("\nOF EX " + OF_EX_Latch.OF_EX_instruction_in_integer);

			System.out.println("\nEX MA " + EX_MA_Latch.EX_MA_instruction_in_integer);

			// *******************

			// ********************************
			int PC = containingProcessor.getRegisterFile().getProgramCounter();
			String INSTRUCTION = binaryofint(IF_OF_Latch.getInstruction());

			// Control unit
			String OPCODE = INSTRUCTION.substring(0, 5);

			System.out.println("PC in OF " + PC);

			if ((this.checkConflict(IF_OF_Latch.IF_OF_instruction_in_integer, OF_EX_Latch.OF_EX_instruction_in_integer))
					||
					this.checkConflict(IF_OF_Latch.IF_OF_instruction_in_integer,
							MA_RW_Latch.MA_RW_instruction_in_integer)

			) {

				Variables.DATA_CLASHES += 1;
				System.out.println("Uuuuu Conflict ****");
				Variables.fetch_again = true;

				System.out.println("Will store this for future use ****" + PC + " Ins : "
						+ containingProcessor.getMainMemory().getWord(PC - 1) + " and it has to be actually "
						+ IF_OF_Latch.IF_OF_instruction_in_integer);
				Variables.CONFLICT_PC_OF = PC - 1;
				Variables.fetch_again_pc = 	Variables.CONFLICT_PC_OF ;

				Variables.set_next_time = true;
				IF_OF_Latch.null_and_void_if_of();
				OF_EX_Latch.null_and_void_ex_of();

				IF_OF_LatchType.check = true;

				return;
			}

			OF_EX_Latch.setOpCode(OPCODE);

			OF_EX_Latch.OF_EX_instruction_in_integer = IF_OF_Latch.getInstruction();
			// Immediate
			int immediate17 = binaryToInt(INSTRUCTION.substring(15));

			OF_EX_Latch.setImmediate(immediate17);

			// Writing branchTarget
			int OFFSET;

			// Check if the instruction opcode matches "11000"
			if (INSTRUCTION.substring(0, 5).equals("11000")) {
				// Extract the destination register index from the instruction
				int rdIndex = (int) Long.parseLong(INSTRUCTION.substring(5, 10), 2);

				// Get the value of the destination register from the register file
				int rdValue = containingProcessor.getRegisterFile().getValue(rdIndex);

				// Calculate OFFSET by adding the value of the destination register
				OFFSET = rdValue + binaryToInt(INSTRUCTION.substring(10));
			} else {
				// Use the immediate17 value as OFFSET
				OFFSET = immediate17;
			}

			// Calculate the branch target
			int branchTarget = OFFSET + PC - 1;
			System.out.println("\nPC is  " + PC);
			System.out.println("Offset is  " + OFFSET);

			System.out.println("Branch target  " + branchTarget + "\n");
			OF_EX_Latch.setBranchTarget(branchTarget);

			// Set op1
			int sourceRegIndex1 = (int) Long.parseLong(INSTRUCTION.substring(5, 10), 2);
			int op1 = containingProcessor.getRegisterFile().getValue(sourceRegIndex1);
			OF_EX_Latch.setOp1(op1);

			int destRegIndex = 0;
			int op2;
			int rd;

			// Check the instruction type
			if (INSTRUCTION_TYPE.get(OPCODE) == 3) {
				// R3 type instruction
				int sourceRegIndex2 = (int) Long.parseLong(INSTRUCTION.substring(10, 15), 2);
				destRegIndex = (int) Long.parseLong(INSTRUCTION.substring(15, 20), 2);
				rd = containingProcessor.getRegisterFile().getValue(destRegIndex);
				op2 = containingProcessor.getRegisterFile().getValue(sourceRegIndex2);

			} else if (OPCODE.equals("10111")) {
				// Store instruction
				// The order is reversed: [rd + imm] = rs1
				rd = containingProcessor.getRegisterFile().getValue(
						(int) Long.parseLong(INSTRUCTION.substring(10, 15), 2));
				int sourceRegIndexRs1 = (int) Long.parseLong(INSTRUCTION.substring(5, 10), 2);
				op2 = containingProcessor.getRegisterFile().getValue(sourceRegIndexRs1);

			} else {
				// Other instructions
				op2 = 0;
				destRegIndex = (int) Long.parseLong(INSTRUCTION.substring(10, 15), 2);
				rd = containingProcessor.getRegisterFile().getValue(destRegIndex);
			}

			OF_EX_Latch.setOp2(op2);
			OF_EX_Latch.setRd(rd);
			OF_EX_Latch.setDestination(destRegIndex);

			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}

		
	}

	
}
