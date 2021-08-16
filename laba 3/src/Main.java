import java.util.Random;

public class  Main {
    public static void main (String[] args) {
        Random random = new Random();
        MemoryManagment mm = new MemoryManagment(10,2);
        for (int i = 0; i < 3+ random.nextInt(4); i++) {
            mm.addProcess(i,2+random.nextInt(5));
        }
        mm.work();
    }
}