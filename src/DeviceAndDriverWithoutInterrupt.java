public class DeviceAndDriverWithoutInterrupt {
    private int workingTime;
    private boolean readiness;

    public boolean isReadiness() {
        return readiness;
    }

    public void setWorkingTime(int workingTime) {
        this.workingTime = workingTime;
    }

    public void working() {
        workingTime--;
        if (workingTime==0) {
            readiness=true;
        }
    }
}
