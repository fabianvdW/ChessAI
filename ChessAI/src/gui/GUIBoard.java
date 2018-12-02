package gui;

import Chess.*;
import Chess.pieces.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIBoard  extends JPanel {
    private ChessBoard board;
    private static ChessBoard simulated;
    private ChessPiece[][] brett; // auf diesem Brett wird der ganze Verlauf erneut durchsimuliert
    private static Timer t; // schrittweise Reproduktion der Schritte
    private int atMove;
    private JTextArea log;
    private boolean loop;
    private static int step;
    private Dimension size;

    public static void main(String[] args){
        GUIBoard.draw();
    }

    public GUIBoard(ChessBoard board,Dimension size/*, JTextArea tOutput, boolean loop*/) {
        this.board = board;
        this.size=size;
        //this.log = tOutput;
        //this.loop = loop;
        //brett = m.initalMap;
        //this.atMove = 0;

        /*
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
        */
    }

    public static void draw() {
        ChessBoard cb= new ChessBoard();
        int moves=0;
        while(cb.status== ChessGameStatus.INGAME){
            moves++;
            java.util.List<ChessMove> availableMoves= ChessLogic.getAllPossibleMoves(cb,cb.move);
            cb.applyChessMove(availableMoves.get((int)(availableMoves.size()*Math.random())));
            //System.out.println(cb.toString());
        }
        System.out.println(cb.toString());

        //System.out.println(cb.status);
        //System.out.println("Moves: "+moves);

        Dimension size = new Dimension(1080,1080);
        JPanel panel = new GUIBoard(cb,size);
        panel.setLayout(null);
        JFrame frame = new JFrame();
        frame.add(panel, null);
        frame.setSize(size);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // simulated
        GUIBoard.step = 0;
        GUIBoard.simulated = new ChessBoard();
        GUIBoard.t = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*System.out.println(GUIBoard.simulated.toString());
                System.out.println(cb.history.get(step));
                System.out.println("WHITE");
                for (ChessPiece p: GUIBoard.simulated.WHITE_PIECES) {
                    System.out.println(p.representation + "\t" + p.position.toString() + "\t" + p.onBoard + "\t" + GUIBoard.simulated.getChessPiece(p.position));

                }
                System.out.println("BLACK");
                for (ChessPiece p: GUIBoard.simulated.BLACK_PIECES) {
                    System.out.println(p.representation + "\t" + p.position.toString() + "\t" + p.onBoard + "\t" + GUIBoard.simulated.getChessPiece(p.position));
                }
                System.out.println(GUIBoard.simulated.noSync());
                */
                GUIBoard.simulated.applyChessMove(cb.history.get(step));
                panel.repaint();
                GUIBoard.step++;
                if(GUIBoard.step == cb.history.size()) {
                    t.stop();
                }

            }
        });
        GUIBoard.t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        int xScale  =(int)((this.size.width)/9.0-2);
        int yScale= (int)((this.size.height-21)/9.0-2);
        boolean color = true;
        for (int i = 0; i < 9; i++) {
            for (int n = 0; n < 9; n++) {
                if (color) {
                    g.setColor(new Color(255, 248, 220));
                } else {
                    g.setColor(new Color(139, 69, 19));
                }
                if(i==8||n==8){
                    if(! (i==8&&n==8)) {
                        g.setFont(new Font("TimesRoman", Font.PLAIN, (yScale+xScale)*2/6));
                        g.setColor(Color.BLACK);
                        if(i==8){
                            g.drawString(n+"",(int)((i+1.0/10)*xScale),(int)((n+1.0/2+0.2)*yScale));
                        }else {
                            g.drawString(i+"",(int)((i+1.0/3)*xScale),(int)((n+1.0/2+0.1)*yScale));
                        }
                    }
                }else {
                    color = !color;
                    g.fillRect((int) (i * xScale), (int) (n * yScale), (int) (xScale), (int) (yScale));
                }
            }
            color = !color;
        }
        if(!GUIBoard.simulated.history.isEmpty()) {
            ChessMove currMove = GUIBoard.simulated.history.get(GUIBoard.simulated.history.size() - 1);
            ChessPosition start = currMove.from;
            g.setColor(Color.GREEN);
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(10));
            g.drawLine((int) (start.getX() * xScale + xScale / 2), (int) (start.getY() * yScale + yScale / 2), (int) (currMove.to.getX() * xScale + xScale / 2), (int) (currMove.to.getY() * yScale + yScale / 2));
        }
        g.setFont(new Font("TimesRoman", Font.PLAIN, (xScale+yScale)/2));
        g.setColor(new Color(255, 187, 25));
        for (ChessPiece p: simulated.WHITE_PIECES) {
            g.drawString(p.representation, (int)(p.position.getX() * xScale + 0.05*xScale),(int)(p.position.getY() * yScale + 0.90*yScale));
        }
        g.setColor(Color.BLACK);
        for (ChessPiece p: simulated.BLACK_PIECES) {
            g.drawString(p.representation, (int)(p.position.getX() * xScale + 0.05*xScale),(int)(p.position.getY() * yScale + 0.9*yScale));
        }
    }
}
