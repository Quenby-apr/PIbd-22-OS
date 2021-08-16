public class DeviceAndDriver {
    private int workingTime;
    private boolean readiness;

    public boolean isReadiness() {
        return readiness;
    }

    public void setWorkingTime(int workingTime) {
        this.workingTime = workingTime;
        readiness=false;
    }

    public int getWorkingTime() {
        return workingTime;
    }

    public void working(int time) {
        workingTime-=time;
        if (workingTime<=0) {
            readiness=true;
        }
    }
}
