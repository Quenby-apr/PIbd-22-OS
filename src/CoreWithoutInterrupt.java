import java.util.ArrayList;
import java.util.Random;

public class CoreWithoutInterrupt {
    DeviceAndDriver device = new DeviceAndDriver();
    ArrayList<Process> arrProcess =new ArrayList<>();
    Random rand = new Random();
    private int deviceTime=23;
    private int totalTime=0;
    private int sumValue=0;
    public void createProcess() {
        for (int i = 0; i < 6 ; i++) {
            int value=1;
            arrProcess.add(new Process(i,value));
            arrProcess.get(i).start();
            value= arrProcess.get(i).getSumValue();
            arrProcess.get(i).setValue(value);
            sumValue+=value;
        }
    }
    public int planProcess() {
        while (!arrProcess.isEmpty()) {
            int lotteryResult = rand.nextInt(sumValue);
            for (int i = 0; i < arrProcess.size(); i++) {
                if (lotteryResult - arrProcess.get(i).getValue() > 0) {
                    lotteryResult -= arrProcess.get(i).getValue();
                } else {
                    //int valueThr=0;
                    for (int j = 0; j < arrProcess.get(i).arrThread.size() ; j++) {
                        if (lotteryResult - arrProcess.get(i).arrThread.get(j).getValue() > 0) {
                            lotteryResult -= arrProcess.get(i).arrThread.get(j).getValue();
                        }
                        else {
                            int valueThr= arrProcess.get(i).arrThread.get(j).getValue();
                            System.out.println("поток " + arrProcess.get(i).arrThread.get(j).gettID()+ " процесса "+ arrProcess.get(i).getpID() +" начал выполнение");
                            if (arrProcess.get(i).arrThread.get(j).isDeviceNecessary()) { //планировщик приостановлен, пока драйвер не скажет, что устройство отработало
                                System.out.println("процесс "+ arrProcess.get(i).getpID() +" заблокирован, управление передано устройству");
                                device.setWorkingTime(deviceTime);
                                System.out.println("устройство работает...");
                                if (device.isReadiness()) {
                                    device.working(deviceTime);
                                }
                                arrProcess.get(i).arrThread.get(j).setDeviceNecessary(false);
                                System.out.println("устройство отработало, процесс "+ arrProcess.get(i).getpID()+" продолжил выполнение");
                                totalTime+=deviceTime;
                            }
                            totalTime+= arrProcess.get(i).arrThread.get(j).getNecessaryTime();
                            arrProcess.get(i).arrThread.get(j).working();
                            arrProcess.get(i).arrThread.remove(j);
                            arrProcess.get(i).setValue(arrProcess.get(i).getValue()-valueThr);
                            sumValue-=valueThr;
                            if (arrProcess.get(i).getValue()==0) {
                                System.out.println("процесс " + arrProcess.get(i).getpID()+ " выполнен успешно");
                                System.out.println();
                                arrProcess.remove(i);
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