public class Main {
    public static void main(String[] args) {
        CoreWithoutInterrupt coreWithoutInterrupt = new CoreWithoutInterrupt();
        int totalTimeWithoutInterrupt = coreWithoutInterrupt.startProgram();
        CoreWithInterrupt coreWithInterrupt = new CoreWithInterrupt();
        int totalTimeWithInterrupt = coreWithInterrupt.startProgram();
        System.out.println("Общее время " + totalTimeWithInterrupt+ " с прерываниями");
        System.out.println("Общее время " + totalTimeWithoutInterrupt+ " без прерываний");
    }
}
