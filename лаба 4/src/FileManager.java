import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class FileManager {
    private File rootFile = new File("root",null,true,1);
    private File selected = rootFile;
    private File fileForCopy;
    private File fileForMove;
    private PhysMemory physMemory;

    public FileManager(PhysMemory PhisMemory) {
        this.physMemory = PhisMemory;
        rootFile.setSize(1);
        PhisMemory.searchPlace(rootFile);
    }

    public File getRootFile() {
        return rootFile;
    }

    public File getSelected() {
        return selected;
    }

    public void setSelectedFile(DefaultMutableTreeNode node) {
        this.selected= (File) node.getUserObject();
    }

    public File copy() {
        return fileForCopy = selected;
    }

    public void copyFiles(File newFile) { //для каталога
        for (File file : newFile.getChild()) {
            physMemory.searchPlace(file);
            if(file.isFolder()) {
                copyFiles(file);
            }
        }
    }

    public boolean paste() {
        if(selected.isFolder()) {
            try {
                File newFile = fileForCopy.clone();
                newFile.setParent(selected);
                selected.getChild().add(newFile);
                physMemory.searchPlace(newFile);
                if (newFile.isFolder()) {
                    copyFiles(newFile);
                }
            } catch (Exception eх) {
                eх.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean createFile(String nameFile, boolean folder, int size) {
        if(selected.isFolder()) {
            File newFile = new File(nameFile,selected,folder,size);
            if(folder) {
                newFile.setSize(1);
            } else {
                newFile.setSize(size);
            }
            physMemory.searchPlace(newFile);
            selected.getChild().add(newFile);
            //selectedNodeTree.add(new DefaultMutableTreeNode(newFile));
            return true;
        } else {
            return false;
        }
    }

    public boolean delete() {
        if(selected == rootFile) {
            return false;
        } else {
            selected.getParent().getChild().remove(selected);
            if (selected.isFolder()) {
                delFolder(selected.getChild());
            }
            physMemory.clearFile(selected);
        }
        return true;
    }

    public void delFolder(ArrayList<File> files) {
        for (File file : files) {
            if(file.isFolder()) {
                delFolder(file.getChild());
            }
            physMemory.clearFile(file);
        }
    }

    public void move() {
        for (int i = 0; i <selected.getParent().child.size() ; i++) {
            if (selected.getParent().child.get(i)==selected) {
                selected.getParent().child.remove(i);
            }
        }
        fileForMove = selected;
    }
    public void moveComplete() {
        selected.child.add(fileForMove);
    }
}
