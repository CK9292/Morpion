import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Morpion extends JFrame {
    private JButton[][] board;
    private int boardSize = 3;
    private boolean isPlayer1 = true;
    private boolean isVsPC = false;
    private JLabel statusBar;

    public Morpion() {
        setTitle("Jeu de Morpion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem sizeItem = new JMenuItem("Définir les dimensions");
        sizeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Entrez les dimensions :");
                try {
                    int size = Integer.parseInt(input);
                    if (size > 0) {
                        boardSize = size;
                        restart();
                    } else {
                        JOptionPane.showMessageDialog(null, "Entrée invalide.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Entrée invalide.");
                }
            }
        });
        JMenuItem vsPCItem = new JMenuItem("Jouer contre PC");
        vsPCItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isVsPC = !isVsPC;
                restart();
            }
        });
        optionsMenu.add(sizeItem);
        optionsMenu.add(vsPCItem);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);

        // Create board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        board = new JButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                JButton button = new JButton();
                int fontSize = boardSize == 6 ? 50 : boardSize == 7 ? 45 : boardSize == 8 ? 35 : boardSize == 9 ? 25
              			 : boardSize == 10 ? 18 : boardSize == 11 ? 13 : boardSize == 12 ? 8 : boardSize == 13 ? 4
              			 : boardSize == 14 ? 2 : boardSize == 15 ? 2 : boardSize == 16 ? 2 : boardSize == 17 ? 2  
              		     : boardSize == 18 ? 2 : boardSize == 19 ? 2 : boardSize == 20 ? 2 : boardSize == 21 ? 2 
              			 : boardSize == 22 ? 2 : boardSize == 23 ? 2 : boardSize == 24 ? 2 : boardSize == 25 ? 2 :60;
                button.setBackground(Color.BLACK);
                button.addActionListener(new ActionListener() {
                	
                	public void actionPerformed(ActionEvent e) {
                	    JButton button = (JButton)e.getSource();
                	    if (isPlayer1 && button.getText().isEmpty()) {
                	        button.setText("X");
                	        button.setFont(new Font("Arial", Font.BOLD, fontSize));
                	        button.setForeground(Color.WHITE);
                	        if (checkWin("X")) {
                	            gameOver("Le joueur X");
                	        } else if (isBoardFull()) {
                	        	statusBar.setText("Jeu terminé : Égalité !");

                	        } else if (!isVsPC) {
                	            isPlayer1 = false;
                	            statusBar.setText("Tour du joueur O");
                	        } else {
                	            playPC();
                	        }
                	    } else if (!isPlayer1 && button.getText().isEmpty()) {
                	        button.setText("O");
                	        button.setFont(new Font("Arial", Font.BOLD, fontSize));
                	        button.setForeground(Color.WHITE);
                	        if (checkWin("O")) {
                	            gameOver("Le joueur O");
                	        } else if (isBoardFull()) {
                	        	statusBar.setText("Jeu terminé : Égalité !");
                	        } else {
                	            isPlayer1 = true;
                	            statusBar.setText("Tour du joueur X");
                	        }
                	    }
                	}

                	private boolean isBoardFull() {
                	    for (int i = 0; i < boardSize; i++) {
                	        for (int j = 0; j < boardSize; j++) {
                	            if (board[i][j].getText().isEmpty()) {
                	                return false;
                	            }
                	        }
                	    }
                	    return true;
                	}

                });
                board[i][j] = button;
                boardPanel.add(button);
            }
        }
        getContentPane().add(boardPanel, BorderLayout.CENTER);

        // Create status bar
        statusBar = new JLabel("Tour du joueur X");
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        getContentPane().add(statusBar, BorderLayout.SOUTH);

        // Create restart button
        JButton restartButton = new JButton("Recommencer");
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(Color.BLACK);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
        getContentPane().add(restartButton, BorderLayout.NORTH);

        setVisible(true);
    }

    private boolean checkWin(String symbol) {
        // Check rows
        for (int i = 0; i < boardSize; i++) {
            boolean win = true;
            for (int j = 0; j < boardSize; j++) {
                if (!board[i][j].getText().equals(symbol)) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }
        // Check columns
        for (int j = 0; j < boardSize; j++) {
            boolean win = true;
            for (int i = 0; i < boardSize; i++) {
                if (!board[i][j].getText().equals(symbol)) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }
        // Check diagonal
        boolean win = true;
        for (int i = 0; i < boardSize; i++) {
            if (!board[i][i].getText().equals(symbol)) {
                win = false;
                break;
            }
        }
        if (win) {
            return true;
        }
        // Check reverse diagonal
        win = true;
        for (int i = 0; i < boardSize; i++) {
            if (!board[i][boardSize - i - 1].getText().equals(symbol)) {
                win = false;
                break;
            }
        }
        if (win) {
            return true;
        }
        // Check for tie
        boolean isBoardFull = true;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].getText().isEmpty()) {
                    isBoardFull = false;
                    break;
                }
            }
            if (!isBoardFull) {
                break;
            }
        }
        if (isBoardFull) {
            gameOver("Égalité !");
        }
        return false;
    }

   
    private void playPC() {
        // Find an empty spot and place "O"
        boolean placed = false;
        for (int i = 0; i < boardSize && !placed; i++) {
            for (int j = 0; j < boardSize && !placed; j++) {
            	int fontSize = boardSize == 6 ? 50 : boardSize == 7 ? 45 : boardSize == 8 ? 35 : boardSize == 9 ? 25
                        : boardSize == 10 ? 18 : boardSize == 11 ? 13 : boardSize == 12 ? 8 : boardSize == 13 ? 4
                        : boardSize == 14 ? 2 : boardSize == 15 ? 2 : boardSize == 16 ? 2 : boardSize == 17 ? 2  
                        : boardSize == 18 ? 2 : boardSize == 19 ? 2 : boardSize == 20 ? 2 : boardSize == 21 ? 2 
                        : boardSize == 22 ? 2 : boardSize == 23 ? 2 : boardSize == 24 ? 2 : boardSize == 25 ? 2 : 60;
                if (board[i][j].getText().isEmpty()) {
                    board[i][j].setText("O");
                    board[i][j].setFont(new Font("Arial", Font.BOLD, fontSize));
                    board[i][j].setForeground(Color.WHITE);
                    if (checkWin("O")) {
                        gameOver("L'ordinateur");
                    } else {
                        isPlayer1 = true;
                    }
                    placed = true;
                }
            }
        }
    }

    private void gameOver(String winner) {
        if (winner.equals("Égalité !")) {
            statusBar.setText("Jeu terminé : " + winner);
        } else {
            statusBar.setText("Jeu terminé : " + winner + " a gagné !");
        }
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j].setEnabled(false);
            }
        }
    }


    private void restart() {
        getContentPane().removeAll();
        isPlayer1 = true;
        board = new JButton[boardSize][boardSize];
        Morpion.this.getContentPane().invalidate();
        Morpion.this.getContentPane().validate();
        Morpion.this.getContentPane().repaint();

     // Create board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        board = new JButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                JButton button = new JButton();
                int fontSize = boardSize == 6 ? 50 : boardSize == 7 ? 45 : boardSize == 8 ? 35 : boardSize == 9 ? 25
              			 : boardSize == 10 ? 18 : boardSize == 11 ? 13 : boardSize == 12 ? 8 : boardSize == 13 ? 4
              			 : boardSize == 14 ? 2 : boardSize == 15 ? 2 : boardSize == 16 ? 2 : boardSize == 17 ? 2  
              		     : boardSize == 18 ? 2 : boardSize == 19 ? 2 : boardSize == 20 ? 2 : boardSize == 21 ? 2 
              			 : boardSize == 22 ? 2 : boardSize == 23 ? 2 : boardSize == 24 ? 2 : boardSize == 25 ? 2 :60;
                button.setBackground(Color.BLACK);
                button.addActionListener(new ActionListener() {
                	
                	public void actionPerformed(ActionEvent e) {
                	    JButton button = (JButton)e.getSource();
                	    if (isPlayer1 && button.getText().isEmpty()) {
                	        button.setText("X");
                	        button.setFont(new Font("Arial", Font.BOLD, fontSize));
                	        button.setForeground(Color.WHITE);
                	        if (checkWin("X")) {
                	            gameOver("Le joueur X");
                	        } else if (isBoardFull()) {
                	        	statusBar.setText("Jeu terminé : Égalité !");

                	        } else if (!isVsPC) {
                	            isPlayer1 = false;
                	            statusBar.setText("Tour du joueur O");
                	        } else {
                	            playPC();
                	        }
                	    } else if (!isPlayer1 && button.getText().isEmpty()) {
                	        button.setText("O");
                	        button.setFont(new Font("Arial", Font.BOLD, fontSize));
                	        button.setForeground(Color.WHITE);
                	        if (checkWin("O")) {
                	            gameOver("Le joueur O");
                	        } else if (isBoardFull()) {
                	        	statusBar.setText("Jeu terminé : Égalité !");
                	        } else {
                	            isPlayer1 = true;
                	            statusBar.setText("Tour du joueur X");
                	        }
                	    }
                	}

                	private boolean isBoardFull() {
                	    for (int i = 0; i < boardSize; i++) {
                	        for (int j = 0; j < boardSize; j++) {
                	            if (board[i][j].getText().isEmpty()) {
                	                return false;
                	            }
                	        }
                	    }
                	    return true;
                	}

                });
                board[i][j] = button;
                boardPanel.add(button);
            }
        }
        getContentPane().add(boardPanel, BorderLayout.CENTER);

        // Create status bar
        statusBar = new JLabel("Tour du joueur X");
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        getContentPane().add(statusBar, BorderLayout.SOUTH);

        // Create restart button
        JButton restartButton = new JButton("Recommencer");
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(Color.BLACK);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
        getContentPane().add(restartButton, BorderLayout.NORTH);

        setVisible(true);
    }

    public static void main(String[] args) {
    	Morpion game = new Morpion();
        game.setVisible(true);
    }
}
