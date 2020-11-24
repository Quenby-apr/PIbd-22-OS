import java.util.ArrayList;
import java.util.Random;

public class CoreWithoutInterrupt {
    DeviceAndDriverWithoutInterrupt device = new DeviceAndDriverWithoutInterrupt();
    ArrayList<ProcessWithoutInterrupt> arrProcessWithoutInterrupts =new ArrayList<>();
    Random rand = new Random();
    private int deviceTime=23;
    private int totalTime=0;
    private int sumValue=0;
    public void createProcess() {
        for (int i = 0; i < 6 ; i++) {
            int value=1;
            arrProcessWithoutInterrupts.add(new ProcessWithoutInterrupt(i,value));
            arrProcessWithoutInterrupts.get(i).start();
            value= arrProcessWithoutInterrupts.get(i).getSumValue();
            arrProcessWithoutInterrupts.get(i).setValue(value);
            sumValue+=value;
        }
    }
    public int planProcess() {
        while (!arrProcessWithoutInterrupts.isEmpty()) {
            int lotteryResult = rand.nextInt(sumValue);
            for (int i = 0; i < arrProcessWithoutInterrupts.size(); i++) {
                if (lotteryResult - arrProcessWithoutInterrupts.get(i).getValue() > 0) {
                    lotteryResult -= arrProcessWithoutInterrupts.get(i).getValue();
                } else {
                    //int valueThr=0;
                    for (int j = 0; j < arrProcessWithoutInterrupts.get(i).arrThreadWithoutInterrupt.size() ; j++) {
                        if (lotteryResult - arrProcessWithoutInterrupts.get(i).arrThreadWithoutInterrupt.get(j).getValue() > 0) {
                            lotteryResult -= arrProcessWithoutInterrupts.get(i).arrThreadWithoutInterrupt.get(j).getValue();
                        }
                        else {
                            int valueThr= arrProcessWithoutInterrupts.get(i).arrThreadWithoutInterrupt.get(j).getValue();
                            System.out.println("поток " + arrProcessWithoutInterrupts.get(i).arrThreadWithoutInterrupt.get(j).gettID()+ " процесса "+ arrProcessWithoutInterrupts.get(i).getpID() +" начал выполнение");
                            if (arrProcessWithoutInterrupts.get(i).arrThreadWithoutInterrupt.get(j).isDeviceNecessary()) { //планировщик приостановлен, пока драйвер не скажет, что устройство отработало
                                System.out.println("процесс "+ arrProcessWithoutInterrupts.get(i).getpID() +" заблокирован, управление передано устройству");
                                device.setWorkingTime(deviceTime);
                                System.out.println("устройство работает...");
                                while (!device.isReadiness()) {
                                    device.working();
                                }
                                arrProcessWithoutInterrupts.get(i).arrThreadWithoutInterrupt.get(j).setDeviceNecessary(false);
                                System.out.println("устройство отработало, процесс "+ arrProcessWithoutInterrupts.get(i).getpID()+" продолжил выполнение");
                                totalTime+=deviceTime;
                            }
                            totalTime+= arrProcessWithoutInterrupts.get(i).arrThreadWithoutInterrupt.get(j).getNecessaryTime();
                            arrProcessWithoutInterrupts.get(i).arrThreadWithoutInterrupt.get(j).working();
                            arrProcessWithoutInterrupts.get(i).arrThreadWithoutInterrupt.remove(j);
                            arrProcessWithoutInterrupts.get(i).setValue(arrProcessWithoutInterrupts.get(i).getValue()-valueThr);
                            sumValue-=valueThr;
                            if (arrProcessWithoutInterrupts.get(i).getValue()==0) {
                                System.out.println("процесс " + arrProcessWithoutInterrupts.get(i).getpID()+ " выполнен успешно");
                                System.out.println();
                                arrProcessWithoutInterrupts.remove(i);
                                break;
                            }
                        }
                    }
                    //выбираем нужный нам поток и обрабатываем его
                    //уменьшаем ценность процесса и вырезаем поток из процесса
                }
            }
        }
        System.out.println();
        System.out.println("Вариант без прерываний отработан");
        System.out.println();
        System.out.println();
        return totalTime;
    }
    public int startProgram() {
        createProcess();
        return planProcess();
    }
}
//указываем начало выполнение процесса, заканчиваем выполнение, только когда кончатся все объекты массива процессов