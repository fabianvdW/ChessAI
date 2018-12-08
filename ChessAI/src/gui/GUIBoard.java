package gui;

import chess.*;
import chess.pieces.ChessPiece;
import chess.pieces.Rook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIBoard extends JPanel {
    private ChessGame game; // auf diesem Brett wird der ganze Verlauf erneut durchsimuliert
    protected int step;
    private Dimension size;
    boolean pause;
    boolean showMoves;
    boolean showMoveOfFigure;
    ChessPiece show;
    int xScale;
    int yScale;

    public static void main(String[] args) {
        GUIBoard.draw();
    }

    public GUIBoard(ChessGame game, Dimension size/*, JTextArea tOutput, boolean loop*/) {
        this.size = size;
        this.game = game;
        this.step = 0;
    }

    public static void draw() {
        long t0 = System.currentTimeMillis();
        ChessGame cb = new ChessGame(null, null);
        int moves = 0;
        while (cb.status == ChessGameStatus.INGAME) {
            moves++;
            List<ChessMove> availableMoves = ChessLogic.getAllPossibleMoves(cb.currentBoard, cb.move);
            cb.applyChessMove(availableMoves.get((int) (availableMoves.size() * Math.random())));
            //System.out.println(cb.toString());
        }
        System.out.println("Time: " + (System.currentTimeMillis() - t0));
        System.out.println(cb.toString());
        System.out.println("Moves: " + moves);

        Dimension size = new Dimension(1080, 1080);
        GUIBoard panel = new GUIBoard(cb, size);
        panel.setLayout(null);
        panel.addKeyListener(new GUIKeyListener(panel,cb));
        panel.addMouseListener(new GUIMouseListener(panel,cb));
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        JFrame frame = new JFrame();
        frame.add(panel, null);
        frame.setSize(size);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // simulated

        TimerListener tl = new TimerListener(panel, cb);
        Timer t = new Timer(1000, tl);
        tl.setTimer(t);
        t.start();
    }

    public void drawChessMove(Graphics g,ChessMove cm){
        Graphics2D g2= (Graphics2D)g;
        g2.setStroke(new BasicStroke(10));
        g.drawLine((int) (cm.from.getX() * xScale + xScale / 2), (int) (cm.from.getY() * yScale + yScale / 2), (int) (cm.to.getX() * xScale + xScale / 2), (int) (cm.to.getY() * yScale + yScale / 2));
        g2.setStroke(new BasicStroke(5));
        g.drawOval(cm.to.getX() * xScale,(cm.to.getY() * yScale ),xScale-3,yScale-3);
    }
    @Override
    public void paintComponent(Graphics g) {
        xScale = (int) ((this.size.width) / 9.0 - 2);
        yScale = (int) ((this.size.height - 21) / 9.0 - 2);
        Graphics2D g2 = (Graphics2D) g;
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
                if (i < 8 && n < 8) {
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
        if (this.step > 0) {
            ChessMove currMove = this.game.moveHistory.get(this.step - 1);
            g.setColor(Color.GREEN);
            drawChessMove(g,currMove);
        }
        if (this.showMoves) {
            ChessBoard curr = this.game.boardHistory.get(this.step);
            Map<ChessPiece, List<ChessMove>> moves = step % 2 == 0 ? curr.WHITE_MOVES : curr.BLACK_MOVES;
            g.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(5));
            for (ChessPiece cp : moves.keySet()) {
                g.drawRect((int) (cp.position.getX() * xScale), (int) (cp.position.getY() * yScale), (int) (xScale - 3), (int) (yScale - 3));
            }
        }
        if(this.showMoveOfFigure){
            ChessBoard curr = this.game.boardHistory.get(this.step);
            Map<ChessPiece, List<ChessMove>> moves = step % 2 == 0 ? curr.WHITE_MOVES : curr.BLACK_MOVES;
            g.setColor(Color.RED);
            for(ChessMove cm: moves.getOrDefault(this.show,new ArrayList<>())){
                drawChessMove(g,cm);
            }
        }
    }
}
class GUIMouseListener implements MouseListener{
    GUIBoard panel;
    ChessGame cg;
    public GUIMouseListener(GUIBoard panel, ChessGame cg){
        this.panel=panel;
        this.cg=cg;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(panel.showMoves) {
            panel.showMoveOfFigure=!panel.showMoveOfFigure;
            int x = e.getX();
            int y = e.getY();
            int xBoard = x / panel.xScale;
            int yBoard = y / panel.yScale;
            ChessBoard current = cg.boardHistory.get(panel.step);
            if(xBoard<8&&yBoard<8) {
                ChessPiece cp = current.getChessPiece(new ChessPosition(xBoard, yBoard));
                if (cp !=null &&cp.color==(panel.step%2==0?ChessColor.WHITE:ChessColor.BLACK)){
                    panel.show=cp;
                    panel.repaint();
                    return;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
class GUIKeyListener implements KeyListener {
    GUIBoard panel;
    ChessGame cb;
    public GUIKeyListener(GUIBoard panel,ChessGame cb){
        this.panel=panel;
        this.cb=cb;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == ' ') {
            panel.pause = !panel.pause;
        } else if (e.getKeyChar() == 'j') {
            panel.showMoves = !panel.showMoves;
            if(!panel.showMoves){
                panel.showMoveOfFigure=false;
            }
            panel.repaint();
        }
        if (e.getKeyCode() == 37) {
            if (panel.step > 0) {
                panel.step -= 1;
            }
            panel.repaint();
        } else if (e.getKeyCode() == 39) {
            if (panel.step < cb.boardHistory.size() - 1) {
                panel.step += 1;
            }
            panel.repaint();
        }

        //System.out.println(e.getKeyCode());
        //System.out.println(e.getKeyChar());
    }
}

class TimerListener implements ActionListener {
    private Timer t;
    private GUIBoard panel;
    private ChessGame cg;
    private int listStep = 0;
    private List<Integer> rookMovesIndex;

    public TimerListener(GUIBoard panel, ChessGame cg) {
        this.panel = panel;
        this.cg = cg;
        rookMovesIndex = new ArrayList<>();
        for (int i = 0; i < cg.moveHistory.size(); i++) {
            ChessMove cm = cg.moveHistory.get(i);
            if (cm.moved instanceof Rook) {
                rookMovesIndex.add(i);
                rookMovesIndex.add(i + 1);
            }
        }
    }

    public void setTimer(Timer t) {
        this.t = t;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!this.panel.pause) {
            panel.step++;
        }
        if (panel.step == cg.boardHistory.size()) {
            panel.step -= 1;
            t.stop();
        }
        /*panel.step= this.rookMovesIndex.get(this.listStep);
        System.out.println(cg.boardHistory.get(panel.step));
        this.listStep++;
        if(this.listStep==this.rookMovesIndex.size()){
            t.stop();
        }*/
        panel.repaint();

    }
}
