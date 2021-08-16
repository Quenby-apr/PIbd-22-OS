import javax.swing.*;
import java.awt.*;

public class DrawMemory extends JPanel {
    private Cell[] cells;

    public DrawMemory(PhysMemory physMemory) {
        cells = physMemory.getCells();
    }

    public void paint(Graphics g) {
        int sizeX = (int)(600/Math.sqrt(cells.length));
        int sizeY = (int)(600/Math.sqrt(cells.length));
        if(cells.length<=40) {
            sizeX = 100;
            sizeY = 100;
        }
        int x = 0;
        int y = 0;
        for (int i = 0; i < cells.length; i++) {
            if (x+sizeX>=900-sizeX) {
                x=0;
                y+=sizeY;
            }
            if(cells[i].getStatus()==2) {
                g.setColor(Color.red);
            } else if(cells[i].getStatus()==0) {
                g.setColor(Color.gray);
            } else {
                g.setColor(Color.blue);
            }
            g.fillRect(x, y, sizeX, sizeY);
            g.setColor(Color.black);
            g.drawRect(x, y, sizeX, sizeY);
            x+=sizeX;
        }
    }
}
