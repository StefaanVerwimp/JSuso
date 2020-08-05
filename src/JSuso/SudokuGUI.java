package JSuso;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuGUI implements ActionListener, KeyListener {

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
        Font font = new Font(Font.DIALOG, Font.BOLD, 30);
        for (int row = 0; row < 9; row++){
            for (int col = 0; col < 9; col++){
                // Makes individual textpanes and sets max insertString length to 1
                JTextPane numberBox = new JTextPane(new DefaultStyledDocument() {
                    @Override
                    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                        if ((getLength() + str.length()) <= 1) {
                            super.insertString(offs, str, a);
                        }
                        else {
                            // makes a noise when user tries to add a second number to the same textpane
                            Toolkit.getDefaultToolkit().beep();
                        }
                    }
                });

                numberBox.addKeyListener(this);

                // Centers text horizontally
                StyledDocument doc = numberBox.getStyledDocument();
                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                doc.setParagraphAttributes(0, doc.getLength(), center, false);

                numberBox.setFont(font);
                numberBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                textPanes[row][col] = numberBox;
                board.add(textPanes[row][col]);
            }
        }
        colorizeSquares();

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
                // This string is necessary, foreground needs to be set after it is filled in, but
                // checking if cell is empty needs to happen before that...
                String filled = textPanes[i][j].getText();
                textPanes[i][j].setText(String.valueOf(solved.grid[i][j]));
                if (filled.equals("")){
                    textPanes[i][j].setForeground(new Color(205, 50, 20));
                }
            }
        }
    }

    private static void colorizeSquares() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                // First and last three rows
                if (row < 3 || row > 5) {
                    // First three columns
                    if (col < 3) {
                        textPanes[row][col].setBackground(Color.LIGHT_GRAY);
                    }
                    // Last three columns
                    if (col >= 6) {
                        textPanes[row][col].setBackground(Color.LIGHT_GRAY);
                    }
                }
                // Middle three rows
                if (row > 2 && row < 6) {
                    // Middle three columns
                    if (col > 2 && col < 6) {
                        textPanes[row][col].setBackground(Color.LIGHT_GRAY);
                    }
                }
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
                        textPanes[i][j].setForeground(Color.DARK_GRAY);
                        textPanes[i][j].setText("");
                    }
                }
                break;
            case "exit":
                frame.dispose();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        JTextPane pane = (JTextPane) e.getSource();
        try {
            switch (keyCode){
                case KeyEvent.VK_UP:
                    for (int row = 0; row < 9; row++){
                        for (int col = 0; col < 9; col++) {
                            if (textPanes[row][col].equals(pane)){
                                textPanes[row-1][col].grabFocus();
                            }
                        }
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    for (int row = 0; row < 9; row++){
                        for (int col = 0; col < 9; col++) {
                            if (textPanes[row][col].equals(pane)){
                                textPanes[row+1][col].grabFocus();
                            }
                        }
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    for (int row = 0; row < 9; row++){
                        for (int col = 0; col < 9; col++) {
                            if (textPanes[row][col].equals(pane)){
                                textPanes[row][col-1].grabFocus();
                            }
                        }
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    for (int row = 0; row < 9; row++){
                        for (int col = 0; col < 9; col++) {
                            if (textPanes[row][col].equals(pane)){
                                textPanes[row][col+1].grabFocus();
                            }
                        }
                    }
                    break;
                case KeyEvent.VK_DELETE:
                    e.consume();
                case KeyEvent.VK_BACK_SPACE:
                    pane.setText(" ");
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) { }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
