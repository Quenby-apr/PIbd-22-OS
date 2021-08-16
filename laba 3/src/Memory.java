import java.util.ArrayList;
import java.util.Random;

public class Memory {
    Random random = new Random();
    int countElements = 0;
    int numberQueue = 0;
    int change = 0;

    private Page[] arrayPages;

    public Memory(int MemorySize, int PageSize) {
        arrayPages = new Page[MemorySize/PageSize];
    }

    public Page addPage(Page page, int number) {
        page.setVirtAddress(number);
        page.setModif(random.nextBoolean());
        if (countElements == arrayPages.length) {
            sendPage();
        }
        for (int i = 0; i < arrayPages.length; i++) {
            if (arrayPages[i] == null) {
                page.setPhysAddress(i);
                page.setPresence(true);
                arrayPages[i] = page;
                System.out.println("В оперативную память добавлена страница " + number);
                countElements++;
                showSlots();
                return page;
            }
        }
        System.out.println("ОШИБКА");
        return null;
    }

    public Page sendPage() {
        for (int i = 0; i < arrayPages.length; i++) {
            if(numberQueue==arrayPages.length)
                numberQueue=0;
            if (arrayPages[numberQueue].isModif()) {
                arrayPages[numberQueue].setModif(false);
                numberQueue++;
                continue;
            }
            if (!arrayPages[numberQueue].isModif()) {
                arrayPages[numberQueue].setPresence(false);
                arrayPages[numberQueue].setPhysAddress(-1);
                System.out.println("На диск выгружена страница с номером " + arrayPages[numberQueue].getVirtAddress()+ " процесса " + arrayPages[numberQueue].getProcessId());
                Page page = arrayPages[numberQueue];
                countElements--;
                arrayPages[numberQueue] = null;
                change = 1;
                numberQueue ++;
                return page;
            }
        }
        System.out.println("Выгрузить страницу не удалось");
        return null;
    }

    public void showSlots() {
        System.out.println("слот памяти | номер процесса | виртуальный адрес | признак востребованности");
        for (int i = 0; i < countElements; i++) {
            System.out.printf(" %10d | \t%12d | \t%16d |\t %10b\n",i,arrayPages[i].getProcessId(),arrayPages[i].getVirtAddress(),arrayPages[i].isModif());
        }
    }
}
