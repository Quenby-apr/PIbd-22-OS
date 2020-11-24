import java.util.ArrayList;
import java.util.Random;

public class CoreWithInterrupt {
    DeviceAndDriverWithInterrupt device = new DeviceAndDriverWithInterrupt();
    ArrayList<ProcessWithInterrupt> arrProcessWithInterrupts =new ArrayList<>();
    ArrayList<ThreadWithInterrupt> arrBusyThreadWithInterrupts = new ArrayList<>();// массив для поочерёдной записи поток для работы с устройством
    Random rand = new Random();
    private int deviceTime=23;
    private int totalTime=0;
    private int sumValue=0;
    public void createProcess() {
        for (int i = 0; i < 6 ; i++) {
            int value=1;
            arrProcessWithInterrupts.add(new ProcessWithInterrupt(i,value));
            arrProcessWithInterrupts.get(i).start();
            value= arrProcessWithInterrupts.get(i).getSumValue();
            arrProcessWithInterrupts.get(i).setValue(value);
            sumValue+=value;
        }
    }
    public int planProcess() {
        while (!arrProcessWithInterrupts.isEmpty()) {
            int lotteryResult = rand.nextInt(sumValue);
            for (int i = 0; i < arrProcessWithInterrupts.size(); i++) {
                if (lotteryResult - arrProcessWithInterrupts.get(i).getValue() > 0) {
                    lotteryResult -= arrProcessWithInterrupts.get(i).getValue();
                } else {
                    for (int j = 0; j < arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.size() ; j++) {
                        if (lotteryResult - arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.get(j).getValue() > 0) {
                            lotteryResult -= arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.get(j).getValue();
                        }
                        else {
                            if (device.isReadiness() && !arrBusyThreadWithInterrupts.isEmpty()) { // если устройство закончило работу, то поток завершился
                                System.out.println("поток "+ arrBusyThreadWithInterrupts.get(0).gettID()+ " процесса "+ arrBusyThreadWithInterrupts.get(0).getpID()+" выполнен успешно");
                                arrBusyThreadWithInterrupts.remove(0);
                            }
                            int valueThr= arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.get(j).getValue();
                            if (arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.get(j).isDeviceNecessary() && !device.isReadiness()) { // если устройство сейчас занято, а выбранный поток должен с ним работать, то заносим его в очередь
                                arrBusyThreadWithInterrupts.add(arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.get(j));
                                arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.remove(j);
                                arrProcessWithInterrupts.get(i).setValue(arrProcessWithInterrupts.get(i).getValue()-valueThr);
                                if (arrProcessWithInterrupts.get(i).getValue()==0) { // раз мы занесли в очередь, то из списка потоков его можно убрать
                                    System.out.println("процесс " + arrProcessWithInterrupts.get(i).getpID()+ " выполнен успешно");
                                    System.out.println();
                                    arrProcessWithInterrupts.remove(i);
                                }
                                break;
                            }
                            device.working(arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.get(j).getNecessaryTime());//устройство работает во время выполнения других потоков
                            System.out.println("поток " + arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.get(j).gettID()+ " процесса "+ arrProcessWithInterrupts.get(i).getpID() +" начал выполнение");
                            if (arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.get(j).isDeviceNecessary()) { //планировщик заблокировал процесс, и продолжает работать
                                System.out.println("процесс "+ arrProcessWithInterrupts.get(i).getpID() +" заблокирован, управление передано устройству");
                                device.setWorkingTime(deviceTime);
                                System.out.println("устройство работает...");
                                if (device.isReadiness()) {
                                    device.working(0);
                                }
                                arrBusyThreadWithInterrupts.add(arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.get(j));
                                arrBusyThreadWithInterrupts.get(arrBusyThreadWithInterrupts.size()-1).setpID(i);
                                arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.remove(j);
                                arrProcessWithInterrupts.get(i).setValue(arrProcessWithInterrupts.get(i).getValue()-valueThr);
                                if (arrProcessWithInterrupts.get(i).getValue()==0) {
                                    System.out.println("процесс " + arrProcessWithInterrupts.get(i).getpID()+ " выполнен успешно");
                                    System.out.println();
                                    arrProcessWithInterrupts.remove(i);
                                }
                                break;
                            }
                            totalTime+= arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.get(j).getNecessaryTime();
                            arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.get(j).working();
                            arrProcessWithInterrupts.get(i).arrThreadWithInterrupt.remove(j);
                            arrProcessWithInterrupts.get(i).setValue(arrProcessWithInterrupts.get(i).getValue()-valueThr);
                            sumValue-=valueThr;
                            if (arrProcessWithInterrupts.get(i).getValue()==0) {
                                System.out.println("процесс " + arrProcessWithInterrupts.get(i).getpID()+ " выполнен успешно");
                                System.out.println();
                                arrProcessWithInterrupts.remove(i);
                                break;
                            }
                        }
                    }
                    //выбираем нужный нам поток и обрабатываем его
                    //уменьшаем ценность процесса и вырезаем поток из процесса
                }
            }
            if (!arrBusyThreadWithInterrupts.isEmpty()) {
                totalTime+=device.getWorkingTime();
                device.working(device.getWorkingTime());
                System.out.println("поток "+ arrBusyThreadWithInterrupts.get(0).gettID()+ " процесса "+ arrBusyThreadWithInterrupts.get(0).getpID()+" выполнен успешно");
                arrBusyThreadWithInterrupts.remove(0);
                while (!arrBusyThreadWithInterrupts.isEmpty()) {
                    device.working(deviceTime);
                    totalTime+=deviceTime;
                    System.out.println("поток "+ arrBusyThreadWithInterrupts.get(0).gettID()+ " процесса "+ arrBusyThreadWithInterrupts.get(0).getpID()+" выполнен успешно");
                    arrBusyThreadWithInterrupts.remove(0);
                }
            }
        }
        return totalTime;
    }
    public int startProgram() {
        createProcess();
       return planProcess();
    }
}
//указываем начало выполнение процесса, заканчиваем выполнение, только когда кончатся все объекты массива процессов