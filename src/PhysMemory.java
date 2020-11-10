import java.util.ArrayList;

public class PhysMemory {
    private int sizeDisc;
    private int sizeSector;
    private Cell[] cells;

    public PhysMemory(int sizeDisc, int sizeSector) {
        this.sizeDisc = sizeDisc;
        this.sizeSector = sizeSector;
        cells = new Cell[sizeDisc / sizeSector];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell(sizeSector);
        }
    }

    public void searchPlace(File file) {
        int buf = -2;
        for (int i = 0, writeCells = 0; i < cells.length && writeCells != file.getSize(); i++) {
            if (cells[i].getStatus() == 0) {
                cells[i].setStatus(1);
                if (buf == -2) {
                    file.setIndexFirstCell(i);
                }
                else {
                    cells[buf].setIndexNextCell(i);
                }
                buf = i;
                writeCells++;
                if (writeCells == file.getSize()) {
                    cells[i].setIndexNextCell(-1);
                }
            }
        }
    }

    public void chooseFile(File file) {
        notChooseFile();
        if (file.isFolder()) {
            for (int i = 0; i <file.getChild().size(); i++) {
                chooseFileWithoutUnchoose(file.getChild().get(i));
            }
        }// если удалить этот иф, то будет выделяться ячейка самой папки, без всего содержимого этой папки
        int index = file.getIndexFirstCell();
        for (int i = 0; i < file.getSize(); i++) {
            if(index==-1) {
                break;
            }
            cells[index].setStatus(2);
            index = cells[index].getIndexNextCell();
        }
    }
    public void chooseFileWithoutUnchoose(File file) {
        if (file.isFolder()) {
            for (int i = 0; i <file.getChild().size(); i++) {
                chooseFileWithoutUnchoose(file.getChild().get(i));
            }
        }
        int index = file.getIndexFirstCell();
        for (int i = 0; i < file.getSize(); i++) {
            if(index==-1) {
                break;
            }
            cells[index].setStatus(2);
            index = cells[index].getIndexNextCell();
        }
    }

    public void notChooseFile() {
        for (int i = 0; i <cells.length ; i++) {
            if (cells[i].getStatus()==2) {
                cells[i].setStatus(1);
            }
        }
    }

    public void clearFile(File file) {
        if (!file.isFolder()) {
            int index = file.getIndexFirstCell();
            for (int i = 0; i < file.getSize(); i++) {
                if(index ==-1) {
                    continue;
                }
                cells[index].setStatus(0);
                index = cells[index].getIndexNextCell();
            }
        }
        if (file.isFolder()) {
            ArrayList<File> deletedChild = file.getChild();
            for (int i = 0; i < deletedChild.size(); i++) {
                clearFile(deletedChild.get(deletedChild.size()-1));
            }
            cells[file.getIndexFirstCell()].setStatus(0);
        }
    }

    public Cell[] getCells() {
        return cells;
    }

}
