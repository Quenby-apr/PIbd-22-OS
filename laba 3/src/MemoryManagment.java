import java.util.ArrayList;

public class MemoryManagment {
    int countPages=0;
    Memory memory;
    private ArrayList<Process> processArray = new ArrayList<>();
    private ArrayList<Page> allPages = new ArrayList<>();

    public MemoryManagment(int MemorySize, int PageSize) {
         memory = new Memory(MemorySize,PageSize);
    }
    public void addProcess(int processID, int pageCount) {
        Process process = new Process(processID, pageCount);
        for (int i = 0; i <=pageCount ; i++) {
            process.addPage(new Page(processID,countPages));
        }
        processArray.add(process);
        System.out.println("Процесс " + processID + " создан и для него требуется " + pageCount + " страниц");
    }

    public  void work() {
        while (!processArray.isEmpty()) {
            for (int i = 0; i < processArray.size(); i++) {
                if (processArray.get(i).getCountPages()>=1) {
                    allPages.add(memory.addPage(processArray.get(i).getPage(),countPages+=1));
                }
                else {
                    System.out.println("Процесс " + processArray.get(i).getId() + " отработан успешно");
                    processArray.remove(i);
                    i--;
                }
            }
        }
    }
}
