import java.util.ArrayList;

public class File {
    private String name;
    private File parent;
    private boolean folder;
    public ArrayList<File> child;
    private int indexFirstCell;
    private int size=-1;

    public File(String name, File parent, Boolean folder,int size) {
        this.name = name;
        this.parent = parent;
        this.folder = folder;
        this.size=size;
        if (folder) {
            child = new ArrayList<File>();
        }
    }
    public File() {

    }

    public File clone() {
        File newFile = new File();
        newFile.setSize(size);
        newFile.setName(name);
        newFile.setFolder(folder);
        if(folder) {
            ArrayList<File> child = new ArrayList<File>();
            for (File file : this.child) {
                child.add(file.clone());
            }
            newFile.setChild(child);
        }
        return newFile;
    }

    public String toString() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getParent() {
        return parent;
    }

    public void setParent(File parent) {
        this.parent = parent;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public ArrayList<File> getChild() {
        return child;
    }

    public void setChild(ArrayList<File> child) {
        this.child = child;
    }

    public int getIndexFirstCell() {
        return indexFirstCell;
    }

    public void setIndexFirstCell(int indexFirstCell) {
        this.indexFirstCell = indexFirstCell;
    }

}
