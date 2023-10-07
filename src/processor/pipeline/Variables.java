package processor.pipeline;

import javax.sound.midi.SysexMessage;

public class Variables {
    public static int Branch_counter=0;

    public static void increement_Branch_counter() {
        Branch_counter = (Branch_counter+1)%1;
        System.out.println("null");
    }

    public static void setBranch_counter(int number){
        Branch_counter = number;
    }

    public static int getBranch_counter(){
        return Branch_counter;
    }
}
