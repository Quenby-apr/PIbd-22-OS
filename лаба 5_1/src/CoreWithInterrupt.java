import java.util.ArrayList;
import java.util.Random;

public class CoreWithInterrupt {
    DeviceAndDriver device = new DeviceAndDriver();
    ArrayList<Process> arrProcesses =new ArrayList<>();
    ArrayList<Thread> arrBusyThreads = new ArrayList<>();// массив для поочерёдной записи поток для работы с устройством
    Random rand = new Random();
    private int deviceTime=23;
    private int totalTime=0;
    private int sumValue=0;
    public void createProcess() {
        for (int i = 0; i < 6 ; i++) {
            int value=1;
            arrProcesses.add(new Process(i,value));
            arrProcesses.get(i).start();
            value= arrProcesses.get(i).getSumValue();
            arrProcesses.get(i).setValue(value);
            sumValue+=value;
        }
    }
    public int planProcess() {
        while (!arrProcesses.isEmpty()) {
            int lotteryResult = rand.nextInt(sumValue);
            for (int i = 0; i < arrProcesses.size(); i++) {
                if (lotteryResult - arrProcesses.get(i).getValue() > 0) {
                    lotteryResult -= arrProcesses.get(i).getValue();
                } else {
                    for (int j = 0; j < arrProcesses.get(i).arrThread.size() ; j++) {
                        if (lotteryResult - arrProcesses.get(i).arrThread.get(j).getValue() > 0) {
                            lotteryResult -= arrProcesses.get(i).arrThread.get(j).getValue();
                        }
                        else {
                            if (device.isReadiness() && !arrBusyThreads.isEmpty()) { // если устройство закончило работу, то поток завершился
                                System.out.println("поток "+ arrBusyThreads.get(0).gettID()+ " процесса "+ arrBusyThreads.get(0).getpID()+" выполнен успешно");
                                arrBusyThreads.remove(0);
                            }
                            int valueThr= arrProcesses.get(i).arrThread.get(j).getValue();
                            if (arrProcesses.get(i).arrThread.get(j).isDeviceNecessary() && !device.isReadiness()) { // если устройство сейчас занято, а выбранный поток должен с ним работать, то заносим его в очередь
                                arrBusyThreads.add(arrProcesses.get(i).arrThread.get(j));
                                arrProcesses.get(i).arrThread.remove(j);
                                arrProcesses.get(i).setValue(arrProcesses.get(i).getValue()-valueThr);
                                if (arrProcesses.get(i).getValue()==0) { // раз мы занесли в очередь, то из списка потоков его можно убрать
                                    System.out.println("процесс " + arrProcesses.get(i).getpID()+ " выполнен успешно");
                                    System.out.println();
                                    arrProcesses.remove(i);
                                }
                                break;
                            }
                            device.working(arrProcesses.get(i).arrThread.get(j).getNecessaryTime());//устройство работает во время выполнения других потоков
                            System.out.println("поток " + arrProcesses.get(i).arrThread.get(j).gettID()+ " процесса "+ arrProcesses.get(i).getpID() +" начал выполнение");
                            if (arrProcesses.get(i).arrThread.get(j).isDeviceNecessary()) { //планировщик заблокировал процесс, и продолжает работать
                                System.out.println("процесс "+ arrProcesses.get(i).getpID() +" заблокирован, управление передано устройству");
                                device.setWorkingTime(deviceTime);
                                System.out.println("устройство работает...");
                                if (device.isReadiness()) {
                                    device.working(0);
                                }
                                arrBusyThreads.add(arrProcesses.get(i).arrThread.get(j));
                                arrBusyThreads.get(arrBusyThreads.size()-1).setpID(i);
                                arrProcesses.get(i).arrThread.remove(j);
                                arrProcesses.get(i).setValue(arrProcesses.get(i).getValue()-valueThr);
                                if (arrProcesses.get(i).getValue()==0) {
                                    System.out.println("процесс " + arrProcesses.get(i).getpID()+ " выполнен успешно");
                                    System.out.println();
                                    arrProcesses.remove(i);
                                }
                                break;
                            }
                            totalTime+= arrProcesses.get(i).arrThread.get(j).getNecessaryTime();
                            arrProcesses.get(i).arrThread.get(j).working();
                            arrProcesses.get(i).arrThread.remove(j);
                            arrProcesses.get(i).setValue(arrProcesses.get(i).getValue()-valueThr);
                            sumValue-=valueThr;
                            if (arrProcesses.get(i).getValue()==0) {
                                System.out.println("процесс " + arrProcesses.get(i).getpID()+ " выполнен успешно");
                                System.out.println();
                                arrProcesses.remove(i);
                                break;
                            }
                        }
                    }
                    //выбираем нужный нам поток и обрабатываем его
                    //уменьшаем ценность процесса и вырезаем поток из процесса
                }
            }
            if (!arrBusyThreads.isEmpty()) {
                totalTime+=device.getWorkingTime();
                device.working(device.getWorkingTime());
                System.out.println("поток "+ arrBusyThreads.get(0).gettID()+ " процесса "+ arrBusyThreads.get(0).getpID()+" выполнен успешно");
                arrBusyThreads.remove(0);
                while (!arrBusyThreads.isEmpty()) {
                    device.working(deviceTime);
                    totalTime+=deviceTime;
                    System.out.println("поток "+ arrBusyThreads.get(0).gettID()+ " процесса "+ arrBusyThreads.get(0).getpID()+" выполнен успешно");
                    arrBusyThreads.remove(0);
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