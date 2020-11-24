public class ThreadWithoutInterrupt {
    private int tID;
    private int necessaryTime;
    private int value;
    private boolean deviceNecessary;

    public ThreadWithoutInterrupt(int tID, int value, int necessaryTime, boolean deviceNecessary){
        this.tID=tID;
        this.value=value;
        this.necessaryTime=necessaryTime;
        this.deviceNecessary=deviceNecessary;
    }

    public int getValue() {
        return value;
    }

    public int gettID() {
        return tID;
    }

    public int getNecessaryTime() {
        return necessaryTime;
    }

    public boolean isDeviceNecessary() {
        return deviceNecessary;
    }

    public void setDeviceNecessary(boolean deviceNecessary) {
        this.deviceNecessary = deviceNecessary;
    }

    public void working() {
        System.out.println("поток "+ tID + " выполнился успешно");
    }
}
