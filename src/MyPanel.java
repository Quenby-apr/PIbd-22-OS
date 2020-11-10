import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MyPanel {
    private FileManager fileManager;
    private PhysMemory physMemory;
    private DrawMemory drawMemory;

    private JFrame frame = new JFrame();
    private JButton buttonCreateFile = new JButton("Create file");
    private JButton buttonCreateFolder = new JButton("Create folder");
    private JButton buttonCopy = new JButton("Copy");
    private JButton buttonPaste = new JButton("Paste");
    private JButton buttonDelete = new JButton("Delete");
    private JButton buttonMove = new JButton("Move");
    private JButton buttonMoveComplete = new JButton("Complete move");
    private JButton buttonSetMemory = new JButton("Set memory");
    private JLabel labelName = new JLabel("Name");
    private JLabel labelSizeFile = new JLabel("Size file");
    private JLabel labelSizeDisc = new JLabel("Size disc");
    private JLabel labelSizeSector = new JLabel("Size sector");
    private JTextField textFieldName = new JTextField();
    private JTextField textFieldFile = new JTextField();
    private JTextField textFieldDisc = new JTextField();
    private JTextField textFieldSector = new JTextField();
    private DefaultMutableTreeNode treeFile;
    private JTree tree;


    public MyPanel() {
        frame.setBounds(0, 0, 1400, 800);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setLayout(null);

        buttonCreateFile.setBounds(1200, 100, 150, 20);
        buttonCreateFolder.setBounds(1200, 130, 150, 20);
        buttonCopy.setBounds(1200, 160, 150, 20);
        buttonPaste.setBounds(1200, 190, 150, 20);
        buttonDelete.setBounds(1200, 220, 150, 20);
        buttonMove.setBounds(1200, 450, 150, 20);
        buttonMoveComplete.setBounds(1200, 480, 150, 20);
        buttonSetMemory.setBounds(800, 720, 150, 20);

        labelName.setBounds(1260, 300, 100, 20);
        textFieldName.setBounds(1200, 320, 150, 20);
        labelSizeFile.setBounds(1250, 360, 100, 20);
        textFieldFile.setBounds(1200, 380, 150, 20);
        labelSizeDisc.setBounds(400, 720, 70, 20);
        textFieldDisc.setBounds(480, 720, 100, 20);
        labelSizeSector.setBounds(600, 720, 80, 20);
        textFieldSector.setBounds(680, 720, 100, 20);
        textFieldDisc.setText("1000");
        textFieldSector.setText("2");
        textFieldFile.setText("1");

        frame.getContentPane().add(buttonCreateFile);
        frame.getContentPane().add(buttonCreateFolder);
        frame.getContentPane().add(buttonCopy);
        frame.getContentPane().add(buttonPaste);
        frame.getContentPane().add(buttonDelete);
        frame.getContentPane().add(buttonMove);
        frame.getContentPane().add(buttonMoveComplete);

        frame.getContentPane().add(labelName);
        frame.getContentPane().add(textFieldName);
        frame.getContentPane().add(labelSizeDisc);
        frame.getContentPane().add(textFieldDisc);
        frame.getContentPane().add(labelSizeSector);
        frame.getContentPane().add(textFieldSector);
        frame.getContentPane().add(labelSizeFile);
        frame.getContentPane().add(textFieldFile);
        frame.getContentPane().add(buttonSetMemory);

        frame.repaint();
    }

    public void workButtons() {
        buttonCreateFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileManager.createFile(textFieldName.getText(), false, Integer.parseInt(textFieldFile.getText()));
                startChangeTree(fileManager.getRootFile().getChild());
                frame.repaint();
            }
        });

        buttonCreateFolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileManager.createFile(textFieldName.getText(), true, 1);
                startChangeTree(fileManager.getRootFile().getChild());
                frame.repaint();
            }
        });

        buttonCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileManager.copy();
            }
        });

        buttonPaste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileManager.paste();
                startChangeTree(fileManager.getRootFile().getChild());
                frame.repaint();
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileManager.delete();
                startChangeTree(fileManager.getRootFile().getChild());
                frame.repaint();
            }
        });

        buttonMove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileManager.move();
                startChangeTree(fileManager.getRootFile().getChild());
                frame.repaint();
            }
        });

        buttonMoveComplete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileManager.moveComplete();
                startChangeTree(fileManager.getRootFile().getChild());
                frame.repaint();
            }
        });

        buttonSetMemory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldDisc.setEditable(false);
                ;
                textFieldSector.setEditable(false);
                buttonSetMemory.setEnabled(false);
                buttonCreateFile.setEnabled(true);
                buttonDelete.setEnabled(true);
                buttonMove.setEnabled(true);
                buttonCopy.setEnabled(true);
                buttonCreateFolder.setEnabled(true);
                buttonPaste.setEnabled(true);
                labelName.setEnabled(true);
                physMemory = new PhysMemory(Integer.parseInt(textFieldDisc.getText()), Integer.parseInt(textFieldSector.getText()));
                drawMemory = new DrawMemory(physMemory);
                drawMemory.setBounds(220, 10, 900, 650);
                frame.getContentPane().add(drawMemory);
                fileManager = new FileManager(physMemory);
                physMemory.chooseFile(fileManager.getRootFile());
                frame.repaint();
                startChangeTree(fileManager.getRootFile().getChild());
            }
        });
    }

    private void startChangeTree(ArrayList<File> child) {
        treeFile = new DefaultMutableTreeNode(fileManager.getRootFile());
        changeTree(treeFile, child);
        if (!Objects.isNull(tree)) {
            frame.getContentPane().remove(tree);
        }
        tree = new JTree(treeFile);
        tree.setEnabled(true);
        tree.setShowsRootHandles(true);
        tree.setBounds(0, 0, 200, 600);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                fileManager.setSelectedFile((DefaultMutableTreeNode) Objects.requireNonNull(tree.getSelectionPath()).getLastPathComponent());
                physMemory.chooseFile(fileManager.getSelected());
                frame.repaint();
            }
        });
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(tree);
        tree.updateUI();
        tree.setScrollsOnExpand(true);
    }

    private void changeTree(DefaultMutableTreeNode treeFile, ArrayList<File> child) {//проблема тут, он выводит сам файл, а не его название
        for (File file : child) {
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file);
            treeFile.add(newNode);
            if (file.isFolder()) {
                changeTree(newNode, file.getChild());
            }
        }
    }
}
