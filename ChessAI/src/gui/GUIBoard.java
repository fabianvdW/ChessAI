package gui;

import Chess.*;
import Chess.pieces.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIBoard extends JPanel {
    private ChessGame game; // auf diesem Brett wird der ganze Verlauf erneut durchsimuliert
    private int atMove;
    private JTextArea log;
    private boolean loop;
    protected int step;
    private Dimension size;

    public static void main(String[] args) {
        GUIBoard.draw();
    }

    public GUIBoard(ChessGame game, Dimension size/*, JTextArea tOutput, boolean loop*/) {
        this.size = size;
        this.game = game;
        this.step=0;
    }

    public static void draw() {
        long t0= System.currentTimeMillis();
        ChessGame cb = new ChessGame(null, null);
        int moves = 0;
        while (cb.status == ChessGameStatus.INGAME) {
            moves++;
            java.util.List<ChessMove> availableMoves = ChessLogic.getAllPossibleMoves(cb.currentBoard, cb.move);
            cb.applyChessMove(availableMoves.get((int) (availableMoves.size() * Math.random())));
            //System.out.println(cb.toString());
        }
        System.out.println("Time: "+(System.currentTimeMillis()-t0));
        System.out.println(cb.toString());
        System.out.println("Moves: "+moves);

        Dimension size = new Dimension(1080, 1080);
        GUIBoard panel = new GUIBoard(cb, size);
        panel.setLayout(null);
        JFrame frame = new JFrame();
        frame.add(panel, null);
        frame.setSize(size);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // simulated

        TimerListener tl = new TimerListener(panel,cb);
        Timer t = new Timer(2000, tl);
        tl.setTimer(t);
        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        int xScale = (int) ((this.size.width) / 9.0 - 2);
        int yScale = (int) ((this.size.height - 21) / 9.0 - 2);
        boolean color = true;
        for (int i = 0; i < 9; i++) {
            for (int n = 0; n < 9; n++) {
                if (color) {
                    g.setColor(new Color(255, 248, 220));
                } else {
                    g.setColor(new Color(139, 69, 19));
                }
                if (i == 8 || n == 8) {
                    if (!(i == 8 && n == 8)) {
                        g.setFont(new Font("TimesRoman", Font.PLAIN, (yScale + xScale) * 2 / 6));
                        g.setColor(Color.BLACK);
                        if (i == 8) {
                            g.drawString(n + "", (int) ((i + 1.0 / 10) * xScale), (int) ((n + 1.0 / 2 + 0.2) * yScale));
                        } else {
                            g.drawString(i + "", (int) ((i + 1.0 / 3) * xScale), (int) ((n + 1.0 / 2 + 0.1) * yScale));
                        }
                    }
                } else {
                    color = !color;
                    g.fillRect((int) (i * xScale), (int) (n * yScale), (int) (xScale), (int) (yScale));
                }
                if(i<8&&n<8) {
                    g.setFont(new Font("TimesRoman", Font.PLAIN, (xScale + yScale) / 2));
                    ChessPiece p = this.game.boardHistory.get(this.step).getBoard()[i][n];
                    if (p != null) {
                        if (p.color == ChessColor.WHITE) {
                            g.setColor(new Color(255, 187, 25));
                        } else {
                            g.setColor(Color.BLACK);
                        }
                        g.drawString(p.representation, (int) (p.position.getX() * xScale + 0.05 * xScale), (int) (p.position.getY() * yScale + 0.90 * yScale));
                    }
                }
            }
            color = !color;
        }
        if (this.step>0) {
            ChessMove currMove = this.game.moveHistory.get(this.step-1);
            ChessPosition start = currMove.from;
            g.setColor(Color.GREEN);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(10));
            g.drawLine((int) (start.getX() * xScale + xScale / 2), (int) (start.getY() * yScale + yScale / 2), (int) (currMove.to.getX() * xScale + xScale / 2), (int) (currMove.to.getY() * yScale + yScale / 2));
        }
    }
}

class TimerListener implements ActionListener {
    private Timer t;
    private GUIBoard panel;
    private ChessGame cg;
    public TimerListener(GUIBoard panel, ChessGame cg) {
        this.panel=panel;
        this.cg=cg;
    }

    public void setTimer(Timer t) {
        this.t = t;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        panel.step++;
        if (panel.step == cg.boardHistory.size()) {
            t.stop();
        }

    }
}
