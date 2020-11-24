import java.util.ArrayList;


public class ProcessWithInterrupt {
    private int necessaryTime=60;
    private int pID;
    private int value;
    private int sumValue=0;
    ArrayList<ThreadWithInterrupt> arrThreadWithInterrupt =  new ArrayList<>();

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getpID() {
        return pID;
    }

    public int getSumValue() {
        return sumValue;
    }

    public ProcessWithInterrupt(int pID, int value) {
        this.pID=pID;
        this.value=value;
    }

    public void createThread() {
        int value = 6;
        int necessaryTime=10;
        arrThreadWithInterrupt.add(new ThreadWithInterrupt(1,value,necessaryTime,false));
        arrThreadWithInterrupt.add(new ThreadWithInterrupt(2,value,necessaryTime,true));
        arrThreadWithInterrupt.add(new ThreadWithInterrupt(3,value,necessaryTime,false));
        arrThreadWithInterrupt.add(new ThreadWithInterrupt(4,value,necessaryTime,false));
        arrThreadWithInterrupt.add(new ThreadWithInterrupt(5,value,necessaryTime,true));
        arrThreadWithInterrupt.add(new ThreadWithInterrupt(6,value,necessaryTime,false));
        sumValue=36;
    }

    public void start() {
        createThread();
    }
}
//ядро сразу планирует потоки, а не процессы