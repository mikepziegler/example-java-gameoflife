import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Board extends JPanel {

    //Member Variable
    private boolean[][] fields = new boolean[100][100];
    private boolean running = false;

    java.util.Timer Tm = new java.util.Timer();

    public Board() {
        setVisible(true);
    }

    public void GenStep() {
        boolean[][] newField = new boolean[100][100];

        for (int y = 0; y < fields[1].length; y++) {
            for (int x = 0; x < fields[0].length; x++) {
                int n = countNeighbour(x, y);

                if (fields[x][y] && (n < 2 || n > 3)) {
                    newField[x][y] = false;
                } else {
                    if (fields[x][y] && (n == 2 || n == 3)) {
                        newField[x][y] = true;
                    } else {
                        if (!fields[x][y] && n == 3) {
                            newField[x][y] = true;
                        }
                    }
                }

            }
        }
        fields = newField.clone();
        repaint();
    }

    public void GenRun(int speed) {
        running = !running;

        TimerTask tmTask = new TimerTask() {
            @Override
            public void run() {
                GenStep();
                repaint();
            }
        };
        if (!running) {
            tmTask.cancel();
            Tm.cancel();
            Tm.purge();
        }
        Tm = new Timer();
        Tm.schedule(tmTask, 10, speed);
    }

    private int countNeighbour(int x, int y) {
        int s = 0;
        if (x > 0 && fields[x - 1][y]) {
            s++;//Mitte links
        }

        if (x < fields[0].length - 1 && fields[x + 1][y]) {
            s++; //Mitte rechts
        }
        if (y > 0 && fields[x][y - 1]) {
            s++; //Oben
        }
        if (y < fields[1].length - 1 && fields[x][y + 1]) {
            s++; //Unten
        }
        if (x > 0 && y > 0 && fields[x - 1][y - 1]) {
            s++; //Oben links
        }
        if (x < fields[0].length - 1 && y < fields[1].length - 1 && fields[x + 1][y + 1]) {
            s++; //Unten rechts
        }
        if (x > 0 && y < fields[1].length - 1 && fields[x - 1][y + 1]) {
            s++; //Unten links
        }
        if (y > 0 && x < fields[0].length - 1 && fields[x + 1][y - 1]) {
            s++; //Oben rechts
        }
        return s;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[0].length; x++) {
                if (fields[x][y]) {
                    g.setColor(Color.red);
                    g.fillRect(5 * x, 5 * y, 5, 5);
                }
            }
        }
    }

    public void GenDelete() {
        for (int y = 0; y < fields[1].length; y++) {
            for (int x = 0; x < fields[0].length; x++) {
                if (fields[x][y]) {
                    fields[x][y] = false;
                }
            }
        }
        repaint();
    }

    public void MouseEvent(int x, int y) {
        x = ((x - (x % 5)) / 5);
        y = ((y - (y % 5)) / 5);

        fields[x][y] = true;

        repaint();
    }

    public void generateRandomCulture(int percentage) {
        Random rnd = new Random();

        for (int y = 0; y < fields[1].length; y++) {
            for (int x = 0; x < fields[0].length; x++) {
                if (fields[x][y]) {
                    fields[x][y] = false;
                }
            }
        }

        for (int y = 0; y < fields[1].length; y++) {
            for (int x = 0; x < fields[0].length; x++) {
                if (percentage > rnd.nextInt(100)) {
                    fields[x][y] = true;
                }
            }
        }
        repaint();
    }

}
