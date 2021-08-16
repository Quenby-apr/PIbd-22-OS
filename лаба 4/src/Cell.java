public class Cell {
    private int size;
    private int indexNextCell;
    private int status=0;

    public Cell(int size) {
        this.size = size;
    }

    public int getIndexNextCell() {
        return indexNextCell;
    }

    public void setIndexNextCell(int indexNextCell) {
        this.indexNextCell = indexNextCell;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
