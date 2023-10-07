package processor.pipeline;

import javax.sound.midi.SysexMessage;

public class Variables {
    public static int Branch_counter=0;


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
}
