import java.util.ArrayList;
import java.util.Random;

public class Process {
    Random random = new Random();
    private int id;
    private int pagesCount;
    private ArrayList<Page> processes;
    private ArrayList<Integer> pagesIds;

    public Process(int id, int pagesCount) {
        this.id = id;
        this.pagesCount = pagesCount;
        this.processes = new ArrayList<>();
        this.pagesIds = new ArrayList<>();
    }

    public void addPage(Page page)
    {
        processes.add(page);
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getPagesIds() {
        return pagesIds;
    }

    public Page getPage() {
            Page page = processes.get(processes.size() - 1);
            processes.remove(processes.size()-1);
            pagesCount--;
            return page;
    }

    public int getCountPages() {
        return pagesCount;
    }
}