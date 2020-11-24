public class ThreadWithInterrupt {
    private int tID;
    private int necessaryTime;
    private int value;
    private boolean deviceNecessary;
    private int pID;

    public ThreadWithInterrupt(int tID, int value, int necessaryTime, boolean deviceNecessary){
        this.tID=tID;
        this.value=value;
        this.necessaryTime=necessaryTime;
        this.deviceNecessary=deviceNecessary;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public int getValue() {
        return value;
    }

    public int gettID() {
        return tID;
    }

    public int getpID() {
        return pID;
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
