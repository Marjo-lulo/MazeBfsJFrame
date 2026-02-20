import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int bw = 600;
    int bh = 650;

    JFrame fr = new JFrame("TicTacToe");
    JLabel lb = new JLabel();
    JPanel p = new JPanel();
    JPanel bp = new JPanel();

    JButton[][] b = new JButton[3][3];
    String px = "M";
    String po = "L";
    String cu = px;

    boolean gameOver =false;

    TicTacToe() {
        fr.setVisible(true);
        fr.setSize(bw, bh);
        fr.setLocationRelativeTo(null);
        fr.setResizable(false);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLayout(new BorderLayout());

        lb.setBackground(Color.DARK_GRAY);
        lb.setForeground(Color.white);
        lb.setFont(new Font("Arial", Font.BOLD, 50));
        lb.setHorizontalAlignment(JLabel.CENTER);
        lb.setText("TicTacToe");
        lb.setOpaque(true);

        p.setLayout(new BorderLayout());
        p.add(lb);
        fr.add(p, BorderLayout.NORTH);

        bp.setLayout(new GridLayout(3, 3));
        bp.setBackground(Color.DARK_GRAY);
        fr.add(bp);

        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                JButton tl = new JButton();
                b[r][c] = tl;
                bp.add(tl);

                tl.setBackground(Color.darkGray);
                tl.setForeground(Color.white);
                tl.setFont(new Font("Arial", Font.BOLD, 120));
                tl.setFocusable(false);

                tl.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(gameOver) return;
                        JButton tl =(JButton) e.getSource();
                        if(tl.getText() == ""){
                            tl.setText(cu);
                            checkWinner();
                            if(!gameOver) {
                                cu = cu == px ? po : px;
                                lb.setText(cu + "'s turn.");
                            }
                        }
                    }
                });

            }
        }
    }

    void checkWinner(){
        for(int r = 0; r < 3; r++){
            if(b[r][0].getText() == "") continue;

            if( b[r][0].getText() == b[r][1].getText() &&
                b[r][1].getText() == b[r][2].getText()) {
                for(int i = 0; i < 3; i++){
                    setWinner(b[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        for(int c = 0; c < 3; c++){
            if(b[c][0].getText() == "") continue;

            if( b[0][c].getText() == b[1][c].getText() &&
                b[1][c].getText() == b[2][c].getText()) {
                for(int i = 0; i < 3; i++){
                    setWinner(b[i][c]);
                }
                gameOver = true;
                return;
            }
        }
        if( b[0][0].getText() == b[1][1].getText() &&
            b[1][1].getText() == b[2][2].getText() &&
            b[0][0].getText() != "") {
                for(int i = 0; i < 3; i++){
                    setWinner(b[i][i]);
                }
                gameOver = true;
                return;
        }
        if( b[0][2].getText() == b[1][1].getText() &&
                b[1][1].getText() == b[2][0].getText() &&
                b[0][2].getText() != "") {
                setWinner(b[0][2]);
                setWinner(b[1][1]);
                setWinner(b[2][0]);
                gameOver = true;
                return;
        }
    }

    void setWinner(JButton tl){
        tl.setForeground(Color.green);
        tl.setBackground(Color.gray);
        lb.setText(cu + " is the winner!");
    }

    public static void main(String[] args) {
        TicTacToe ti = new TicTacToe();
    }
}
