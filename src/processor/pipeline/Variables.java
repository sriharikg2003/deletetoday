package processor.pipeline;

import javax.sound.midi.SysexMessage;

public class Variables {
    public static int Branch_counter=0;

    public static boolean set_next_time = false;
    public static int CONFLICT_PC_OF ;
    

    public static boolean fetch_again = false;
    public static int fetch_again_pc ;
    public static int bcos_ignore_new_pc;
public static boolean end_instruction = false;
    public static boolean sim_complete = false;
    public static int final_PC = 0;

    public static int last_sent_pc_for_mem_request ;

    public static boolean branch_taken_global_variable;
    public static void increement_Branch_counter() {
        Branch_counter = (Branch_counter+1)%1;
        System.out.println("BC IS " +Branch_counter);
    }

    public static void setBranch_counter(int number){
        Branch_counter = number;
    }

    public static int getBranch_counter(){
        return Branch_counter;
    }

    public static boolean ignore_instruction = false;


    public static int NO_OF_CYCLES = 0;
    public static int BRANCH_LOCKED = 0;
    public static int DATA_CLASHES = 0;
    public static int NO_OF_INSTRUCTIONS_FETCHED = 0;

}
