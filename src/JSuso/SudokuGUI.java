package JSuso;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGUI implements ActionListener {

    public JFrame frame;
    public static JTextPane[][] textPanes;
    public static final int[][] zeroSudoku = new int[][]{
                                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    public SudokuGUI(){

        frame = new JFrame("JSuso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel board = new JPanel();
        JPanel bottom = new JPanel();

        GridLayout gridLayout = new GridLayout(9, 9);
        board.setLayout(gridLayout);

        // Make empty board with textPanes
        textPanes = new JTextPane[9][9];
        for (int row = 0; row < 9; row++){
            for (int col = 0; col < 9; col++){
                // Makes individual textpanes and sets max insertString length to 1
                JTextPane numberBox = new JTextPane(new DefaultStyledDocument() {
                    @Override
                    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                        if ((getLength() + str.length()) <= 1) {
                            super.insertString(offs, str, a);
                        } else {
                            // makes a noise when user tries to add a second number to the same textpane
                            Toolkit.getDefaultToolkit().beep();
                        }
                    }
                });
                numberBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                textPanes[row][col] = numberBox;
                board.add(textPanes[row][col]);
            }
        }

        JButton exit = new JButton("Exit");
        exit.setActionCommand("exit");
        exit.addActionListener(this);

        JButton reset = new JButton("Reset");
        reset.setActionCommand("reset");
        reset.addActionListener(this);

        JButton solve = new JButton("Solve sudoku");
        solve.setActionCommand("solve");
        solve.addActionListener(this);

        bottom.add(exit);
        bottom.add(reset);
        bottom.add(solve);

        frame.getContentPane().add(board, BorderLayout.CENTER);
        frame.getContentPane().add(bottom, BorderLayout.SOUTH);

        frame.setPreferredSize(new Dimension(500, 520));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Gets text from GUI, checks correctness input, and makes a Sudoku object
    private static Sudoku makeSudoku(Sudoku emptySudoku){
        for (int row = 0; row < 9; row++){
            for (int col = 0; col < 9; col++){
                String content = textPanes[row][col].getText();
                if (!content.equals("")) {
                    try {
                        if (Integer.parseInt(content) < 1 && Integer.parseInt(content) > 9)
                            throw new IllegalArgumentException();
                        int value = Integer.parseInt(textPanes[row][col].getText());
                        emptySudoku.setCell(row, col, value);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Only numbers smaller than 10 allowed as input", "Unexpected error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }
            }
        }
        return emptySudoku;
    }


    // Fills the GUI board using the solved Sudoku
    private void fill_in(JTextPane[][] textPanes, Sudoku solved){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textPanes[i][j].setText(String.valueOf(solved.grid[i][j]));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGUI::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "solve":
                Sudoku solved = makeSudoku(new Sudoku(zeroSudoku));
                solved.solve();
                if (solved.check_solved()) {
                    fill_in(textPanes, solved);
                } else {
                    JOptionPane.showMessageDialog(null, "No solution available", "Unexpected error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "reset":
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        textPanes[i][j].setText("");
                    }
                }
                break;
            case "exit":
                frame.dispose();
                break;
        }
    }

}
