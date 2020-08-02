package JSuso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SudokuGUI implements ActionListener {

    public JFrame frame;

    public SudokuGUI(){

        frame = new JFrame("JSuso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridLayout grid3x3 = new GridLayout(3, 3);

        JPanel board = new JPanel();
        JPanel boxA = new JPanel();
        JPanel boxB = new JPanel();
        JPanel boxC = new JPanel();
        JPanel boxD = new JPanel();
        JPanel boxE = new JPanel();
        JPanel boxF = new JPanel();
        JPanel boxG = new JPanel();
        JPanel boxH = new JPanel();
        JPanel boxI = new JPanel();
        JPanel bottom = new JPanel();

        ArrayList<JPanel> boxes3x3 = new ArrayList<>();
        boxes3x3.add(boxA);
        boxes3x3.add(boxB);
        boxes3x3.add(boxC);
        boxes3x3.add(boxD);
        boxes3x3.add(boxE);
        boxes3x3.add(boxF);
        boxes3x3.add(boxG);
        boxes3x3.add(boxH);
        boxes3x3.add(boxI);

        board.setLayout(grid3x3);
        board.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (JPanel i : boxes3x3){
            i.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            i.setLayout(grid3x3);
            for (int j = 0; j < 9; j++) {
                JTextPane numberBox = new JTextPane();
                numberBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                i.add(numberBox);
            }
            board.add(i);
        }


        JButton exit = new JButton("Exit");
        exit.setActionCommand("exit");
        exit.addActionListener(this);

        bottom.add(exit);

        frame.getContentPane().add(board, BorderLayout.CENTER);
        frame.getContentPane().add(bottom, BorderLayout.SOUTH);

        frame.setPreferredSize(new Dimension(500, 520));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGUI::new);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "solve":
                // Check if empty --> Pop up error

            case "exit":

                // If filled in --> pop up --> "Really quit?"

                frame.dispose();
                break;
        }
    }




}
