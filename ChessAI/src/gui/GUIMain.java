package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUIMain extends JPanel {
        /*private Map map;
        private Figur[][] brett; // auf diesem Brett wird der ganze Verlauf erneut durchsimuliert
        private Timer t; // schrittweise Reproduktion der Schritte
        private int atMove;
        private JTextArea log;
        private boolean loop;

        public void GUI(Map m, JTextArea tOutput, boolean loop) {
            this.map = m;
            this.log = tOutput;
            this.loop = loop;
            brett = m.initalMap;
            this.atMove = 0;

            // Initalisierung des Timers für die Visualisierung
            this.t = new Timer(200, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (atMove == map.verlauf.size() && loop) {
                        System.out.println("Looping... Starting again");
                        log.append("Looping... Starting again\n\n");
                        atMove = 0;
                    }

                    if (atMove == map.verlauf.size() || map.verlauf.size() == 0) {
                        System.out.println("Finished with redo");
                        log.append("\nDone...");
                        t.stop();
                    } else {
                        Zug m = map.verlauf.get(atMove++);
                        System.out.println("Redo: " + m.toString());
                        if (brett[m.x][m.y].bauer) {
                            log.append("White: " + m.toString() + "\n");
                        } else {
                            log.append("\tBlack: " + m.toString() + "\n");
                        }
                        brett[m.toX][m.toY] = brett[m.x][m.y];
                        brett[m.x][m.y] = null;
                        repaint();
                        log.repaint();
                    }
                }
            });
            t.start();
        }

        @Override
        public void paintComponent(Graphics g) {
            boolean color = true;
            for (int i = 0; i < 8; i++) {
                for (int n = 0; n < 8; n++) {
                    if (color) {
                        g.setColor(new Color(255, 211, 155));
                    } else {
                        g.setColor(new Color(205, 133, 63));
                    }
                    color = !color;
                    g.fillRect(i * 50, n * 50, 50, 50);

                    // Draw Figur
                    if (brett[i][n] != null) {
                        if (brett[i][n].bauer) {
                            g.setColor(Color.WHITE); // Bauer
                        } else {
                            g.setColor(Color.BLACK); // Turm
                        }
                        g.fillOval(i * 50 + 10, n * 50 + 10, 30, 30);
                    }
                }
                color = !color;
            }
        }
    }*/

}
