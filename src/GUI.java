import javax.swing.*;
import java.awt.*;
import java.lang.ref.Reference;

public class GUI extends JFrame {

    //Components
    private JFrame frame;
    private JPanel conpanel, drawPanel;
    private JButton btnStep, btnRun, btnRand, btnExit, btnDelete;
    private JLabel lStep, lRun, lRand, placeholder;
    private JSpinner spinner1, spinner2;

    //Member Variables
    public boolean[][] fields = new boolean[100][100];

    public boolean running = false;

    public GUI() {
        //ImageIcon icon = new ImageIcon(getClass().getResource("/resources/Preview_Image.png"));

        initComponents();
    }


    private void initComponents() {
        //Grid bzw Gitter erstellen
        GridBagConstraints c = new GridBagConstraints();

        //Frame
        frame = new JFrame("Game of Life");
        frame.setBackground(Color.white);
        frame.setSize(598, 529);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //Panel
        drawPanel = new Board();
        drawPanel.setPreferredSize(new Dimension(505, 505));
        drawPanel.setBackground(Color.white);
        add_DrawPanel_Actions();

        //Button
        btnStep = new JButton("Schritt");
        btnExit = new JButton("Beenden");
        btnRun = new JButton("Start");
        btnDelete = new JButton("Löschen");
        btnRand = new JButton("Zufall");
        giveBtnAction();

        //Spinner
        spinner1 = new JSpinner();
        spinner1.setPreferredSize(new Dimension(50, 20));
        spinner1.setValue(100);
        spinner2 = new JSpinner();
        spinner2.setPreferredSize(new Dimension(50, 20));

        //Label
        lStep = new JLabel("Ein Schritt");
        lRun = new JLabel("Start");
        lRand = new JLabel("Zufalls");
        placeholder = new JLabel("  ");

        setGrid(c);

        //Frame das drawPanel zum Quadraten zeichnen hinzufügen
        frame.add(drawPanel);
        //Frame ein Layout hinzufügen
        frame.add(conpanel, BorderLayout.EAST);

        //Frame initialisieren und visual erstellen
        frame.setVisible(true);
    }

    private void add_DrawPanel_Actions() {
        drawPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                drawPanel_MouseDragged(evt);
            }
        });
        drawPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drawPanel_MouseClicked(evt);
            }
        });
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                frame_KeyPressed(evt);
            }
        });
        conpanel = new JPanel(new GridBagLayout());
        conpanel.setBackground(Color.gray);
    }

    private void giveBtnAction() {
        btnStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStep_ActionPerformed(evt);
            }
        });

        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRun_ActionPerformed(evt);
            }
        });

        btnRand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRand_ActionPerformed(evt);
            }
        });

        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete_ActionPerformed(evt);
            }
        });

        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExit_ActionPerformed(evt);
            }
        });
    }

    private void setGrid(GridBagConstraints c) {
        //Komponenten in den Panel mit einem Gridposition hinzufügen
        c.insets = new Insets(1, 1, 1, 1); //Abstand für das Gitter
        c.gridx = 0; c.gridy = 1;
        conpanel.add(lStep, c);
        c.gridx = 0; c.gridy = 2;
        conpanel.add(btnStep, c);
        c.gridx = 0; c.gridy = 3;
        conpanel.add(placeholder, c);
        c.gridx = 0; c.gridy = 4;
        conpanel.add(lRun, c);
        c.gridx = 0; c.gridy = 5;
        conpanel.add(spinner1, c);
        c.gridx = 0; c.gridy = 6;
        conpanel.add(btnRun, c);
        c.gridx = 0; c.gridy = 7;
        conpanel.add(placeholder, c);
        c.gridx = 0; c.gridy = 8;
        conpanel.add(lRand, c);
        c.gridx = 0; c.gridy = 9;
        conpanel.add(spinner2, c);
        c.gridx = 0; c.gridy = 10;
        conpanel.add(btnRand, c);
        c.gridx = 0; c.gridy = 11;
        conpanel.add(placeholder, c);
        c.gridx = 0; c.gridy = 12;
        conpanel.add(btnDelete, c);
        c.gridx = 0; c.gridy = 13;
        conpanel.add(placeholder, c);
        c.gridx = 0; c.gridy = 14;
        conpanel.add(placeholder, c);
        c.gridx = 0; c.gridy = 15;
        conpanel.add(placeholder, c);
        c.gridx = 0; c.gridy = 16;
        conpanel.add(btnExit, c);
    }

    private void btnStep_ActionPerformed(java.awt.event.ActionEvent evt) {
        Board panel = (Board) drawPanel;
        panel.GenStep();
    }

    private void btnRun_ActionPerformed(java.awt.event.ActionEvent evt) {
        Board panel = (Board) drawPanel;

        btnStep.setEnabled(running);
        btnRand.setEnabled(running);
        btnDelete.setEnabled(running);
        spinner1.setEnabled(running);
        spinner2.setEnabled(running);

        running = !running;
        panel.GenRun((int) spinner1.getValue());
        if (running) {
            btnRun.setText("Stop");
        }
        if (!running) {
            btnRun.setText("Start");
        }
        btnRun.invalidate();
    }

    private void btnRand_ActionPerformed(java.awt.event.ActionEvent evt) {
        Board panel = (Board) drawPanel;
        panel.generateRandomCulture((int)spinner2.getValue());
    }

    private void btnDelete_ActionPerformed(java.awt.event.ActionEvent evt) {
        Board panel = (Board) drawPanel;
        System.out.println("btnDelete");
        panel.GenDelete();
    }

    private void btnExit_ActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void drawPanel_MouseDragged(java.awt.event.MouseEvent evt) {
        Board panel = (Board) drawPanel;
        panel.MouseEvent(evt.getX(), evt.getY());
    }

    private void drawPanel_MouseClicked(java.awt.event.MouseEvent evt) {
        Board panel = (Board) drawPanel;
        panel.MouseEvent(evt.getX(), evt.getY());
    }

    private void frame_KeyPressed(java.awt.event.KeyEvent evt) {

    }

}
