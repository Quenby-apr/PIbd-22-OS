import java.util.ArrayList;


public class ProcessWithoutInterrupt {
    private int necessaryTime=60;
    private int pID;
    private int value;
    private int sumValue=0;
    ArrayList<ThreadWithoutInterrupt> arrThreadWithoutInterrupt =  new ArrayList<>();

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

    public ProcessWithoutInterrupt(int pID, int value) {
        this.pID=pID;
        this.value=value;
    }

    public void createThread() {
        int value = 6;
        int necessaryTime=10;
        arrThreadWithoutInterrupt.add(new ThreadWithoutInterrupt(1,value,necessaryTime,false));
        arrThreadWithoutInterrupt.add(new ThreadWithoutInterrupt(2,value,necessaryTime,true));
        arrThreadWithoutInterrupt.add(new ThreadWithoutInterrupt(3,value,necessaryTime,false));
        arrThreadWithoutInterrupt.add(new ThreadWithoutInterrupt(4,value,necessaryTime,false));
        arrThreadWithoutInterrupt.add(new ThreadWithoutInterrupt(5,value,necessaryTime,true));
        arrThreadWithoutInterrupt.add(new ThreadWithoutInterrupt(6,value,necessaryTime,false));
        sumValue=36;
    }

    public void start() {
        createThread();
    }
}
//ядро сразу планирует потоки, а не процессы