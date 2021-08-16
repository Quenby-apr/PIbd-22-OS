import java.util.ArrayList;


public class Process {
    private int necessaryTime=60;
    private int pID;
    private int value;
    private int sumValue=0;
    ArrayList<Thread> arrThread =  new ArrayList<>();

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

    public Process(int pID, int value) {
        this.pID=pID;
        this.value=value;
    }

    public void createThread() {
        int value = 6;
        int necessaryTime=10;
        arrThread.add(new Thread(1,value,necessaryTime,false));
        arrThread.add(new Thread(2,value,necessaryTime,true));
        arrThread.add(new Thread(3,value,necessaryTime,false));
        arrThread.add(new Thread(4,value,necessaryTime,false));
        arrThread.add(new Thread(5,value,necessaryTime,true));
        arrThread.add(new Thread(6,value,necessaryTime,false));
        sumValue=36;
    }

    public void start() {
        createThread();
    }
}
//ядро сразу планирует потоки, а не процессы