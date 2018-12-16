package chess.uci;

import chess.bitboards.BitBoard;
import chess.bitboards.BitBoardMove;
import helpers.FENLoader;

import java.sql.Time;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public abstract class UCIPlayer extends Thread {

    public boolean run;
    public BitBoard position;


    public int numProcessors = 1;
    public int numProcessorsMax = 1;
    public int numProcessorsMin = 1;

    public UCIGoInstance goInstance;

    public UCIPlayer() {
        run = true;
    }

    @Override
    public final void run() {
        Scanner s = new Scanner(System.in);
        while (run) {
            String input = s.nextLine().trim();
            String[] arr = input.split(" ");
            if (arr[0].equalsIgnoreCase("uci")) {
                System.out.println(getID());
                System.out.println(getOptions());
                System.out.println("uciok");
                continue;
            }
            if (arr[0].equalsIgnoreCase("setoption")) {
                String option = arr[2];
                String value = arr[4];
                setOption(option, value);
                continue;
            }
            if (arr[0].equalsIgnoreCase("isready")) {
                System.out.println("readyok");
                continue;
            }
            if (arr[0].equalsIgnoreCase("position")) {
                int startPos=3;
                if (arr[1].equalsIgnoreCase("startpos")) {
                    this.position = new BitBoard();
                } else if (arr[1].equalsIgnoreCase("fen")) {
                    String fen=arr[2];
                    int index=3;
                    while(index<arr.length&&!arr[index].equalsIgnoreCase("moves")){
                        fen+=" "+arr[index];
                        index++;
                    }
                    startPos=index;
                    this.position = FENLoader.getBitBoardFromFen(fen);
                } else {
                    System.out.println("info string illegal position string!");
                    continue;
                }
                for (int i = startPos; i < arr.length; i++) {
                    String move = arr[i];
                    //Load move
                    int x1 = move.charAt(0) - 'a';
                    int y1 = 8 - move.charAt(1) + '0';
                    int x2 = move.charAt(2) - 'a';
                    int y2 = 8 - move.charAt(3) + '0';
                    char desc = ' ';
                    if (move.length() == 5) {
                        desc = move.charAt(4);
                    }
                    this.position.initBoard();
                    for (BitBoardMove bbm : this.position.bm.legalMoves) {
                        if (desc == ' ') {
                            if (bbm.x1 == x1 && bbm.y1 == y1 && bbm.x2 == x2 && bbm.y2 == y2) {
                                this.position = this.position.bm.legalFollowingGameStates.get(bbm);
                                break;
                            }
                        } else {
                            if (bbm.x1 == x1 && bbm.y1 == y1 && bbm.x2 == x2 && bbm.y2 == y2) {
                                if (desc == 'q' && bbm.desc == 'Q' || desc == 'r' && bbm.desc == 'R' || desc == 'b' && bbm.desc == 'B' || desc == 'n' && bbm.desc == 'N') {
                                    this.position = this.position.bm.legalFollowingGameStates.get(bbm);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (arr[0].equalsIgnoreCase("go")) {
                if (arr[1].equalsIgnoreCase("infinite")) {
                    setupgo();
                    go();
                } else {
                    int wtime = 0;
                    int btime = 0;
                    int winc = 0;
                    int binc = 0;
                    for (int i = 1; i < arr.length; i += 2) {
                        String str = arr[i];
                        if (str.equalsIgnoreCase("wtime")) {
                            wtime = Integer.parseInt(arr[i + 1]);
                        } else if (str.equalsIgnoreCase("btime")) {
                            btime = Integer.parseInt(arr[i + 1]);
                        } else if (str.equalsIgnoreCase("winc")) {
                            winc = Integer.parseInt(arr[i + 1]);
                        } else if (str.equalsIgnoreCase("binc")) {
                            binc = Integer.parseInt(arr[i + 1]);
                        }
                    }
                    setupgo(wtime, btime, winc, binc);
                    go();
                }
            }
            if (arr[0].equalsIgnoreCase("stop")) {
                stopGoInstance();
            }
            if (arr[0].equalsIgnoreCase("quit")) {
                stopGoInstance();
                run = false;
            }
        }
    }

    public abstract void setupgo();

    public abstract void setupgo(int wtime, int btime, int winc, int binc);

    public final void stopGoInstance() {
        if (this.goInstance != null && this.goInstance.isAlive()) {
            goInstance.stop();
            System.out.println("bestmove " + goInstance.bestMove);
        }

    }

    public final void go() {
        if (this.goInstance != null) {
            this.goInstance.start();
        }
    }

    public abstract String getID();

    public String getOptions() {
        return "option NumberOfProcessors type spin default " + this.numProcessors + " min " + this.numProcessorsMin + " max " + this.numProcessorsMax;
    }

    public void setOption(String option, String value) {
        if (option.equalsIgnoreCase("numberofprocessors")) {
            int x = Integer.parseInt(value);
            if (x < this.numProcessorsMin || x > this.numProcessorsMax) {
                System.out.println("info string illegal value for numprocessors");
            } else {
                this.numProcessors = Integer.parseInt(value);
            }
        }
    }
}
