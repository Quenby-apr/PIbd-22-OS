public class Page {

    private int processId;
    private int physAddress;
    private int virtAddress;

    private boolean modif;
    private boolean presence;
    public Page(int processId,int virtAddress) {
        this.processId = processId;
        this.physAddress = -1;
        this.virtAddress = virtAddress;
        this.modif = false;
        this.presence = false;
    }

    public boolean isModif() {
        return modif;
    }

    public boolean isPresent() {
        return presence;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

    public void setModif(boolean modif) {
        this.modif = modif;
    }

    public int getPhysAddress() {
        return physAddress;
    }

    public void setPhysAddress(int physAddress) {
        this.physAddress = physAddress;
    }

    public int getVirtAddress() {
        return virtAddress;
    }

    public void setVirtAddress(int virtAddress) {
        this.virtAddress = virtAddress;
    }

    public int getProcessId() {
        return processId;
    }
}