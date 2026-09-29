import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter; // Import KeyAdapter for key events
import java.awt.event.KeyEvent; // Import KeyEvent for key codes
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game extends JFrame {
    private int currentSession = 0;
    public int getCurrentSession() { return currentSession; }
    private JTextField input;
    private java.util.List<SharkWord> sharkWords;
    private int life;
    private int score;
    private JLabel scoreLabel, lifeLabel;
    private int highScore = 0;
    private JLabel highScoreLabel;
    private volatile boolean gameRunning = true;
    private javax.swing.Timer gameTimer;
    private Random rand = new Random();

    public Game() {
        super("Typer Shark");
        sharkWords = new CopyOnWriteArrayList<>();
        life = 3;
        score = 0;

        setupUI();
        startGame();
    }

    private void setupUI() {
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(20, 30, 50));

        // Score label
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(new Color(255, 215, 0));
        scoreLabel.setBounds(30, 10, 250, 40);
        add(scoreLabel);

        // High score label
        highScoreLabel = new JLabel("High Score: 0");
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        highScoreLabel.setForeground(new Color(144, 238, 144));
        highScoreLabel.setBounds(240, 10, 220, 40);
        add(highScoreLabel);

        // Life label
        lifeLabel = new JLabel("Lives: 3");
        lifeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        lifeLabel.setForeground(new Color(255, 99, 71));
        lifeLabel.setBounds(500, 10, 170, 40);
        add(lifeLabel);

        // Input field
        input = new JTextField();
        input.setFont(new Font("Consolas", Font.BOLD, 28));
        input.setBounds(200, 400, 300, 50);
        input.setBackground(Color.WHITE);
        input.setForeground(Color.BLACK);
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 3));
        
        // Remove the ActionListener and add KeyListener for Space key
        input.addKeyListener(new KeyAdapter() { // 
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    enterInput();
                    e.consume(); // Prevent space from being added to the text field
                }
            }
        });
        add(input);

        // Update instruction text
        JLabel instruction = new JLabel("Type the word and press Space!");
        instruction.setFont(new Font("Arial", Font.BOLD, 18));
        instruction.setForeground(new Color(173, 216, 230));
        instruction.setBounds(220, 360, 400, 30);
        add(instruction);
    }

    private void startGame() {
        gameTimer = new javax.swing.Timer(1000, e -> spawnWord());
        gameTimer.start();
        input.requestFocusInWindow();
    }

    private void spawnWord() {
        if (!gameRunning || life <= 0) return;

        int x = 50 + rand.nextInt(getWidth() - 200);
        int fallSpeed = Math.max(1000, 200 - (score / 10));
        
        SharkWord word = new SharkWord(this, getRandomWord(), x, fallSpeed, currentSession);
        sharkWords.add(word);
        
        add(word);
        repaint();
        word.start();
        
        System.out.println("Spawned word: " + word.getText() + " at x=" + x);
    }

    private void enterInput() {
        String typed = input.getText().trim().toLowerCase();
        if (typed.isEmpty()) return;

        boolean found = false;
        for (SharkWord word : sharkWords) {
            if (word.match(typed)) {
                score += 10;
                if (score > highScore) {
                    highScore = score;
                }
                updateLabels();
                
                word.deactivate();
                remove(word);
                sharkWords.remove(word);
                repaint();
                found = true;
                break;
            }
        }
        
        if (!found) {
            // Wrong word - flash red
            input.setBackground(Color.RED);
            javax.swing.Timer timer = new javax.swing.Timer(200, e -> {
                input.setBackground(Color.WHITE);
            });
            timer.setRepeats(false);
            timer.start();
        }
        
        input.setText("");
        input.requestFocusInWindow();
    }

    public void wordMissed(SharkWord word) {
        if (life <= 0) return;
        
        life--;
        updateLabels();
        
        if (life <= 0) {
            gameOver();
        }
        
        remove(word);
        sharkWords.remove(word);
        repaint();
    }

    private void updateLabels() {
        scoreLabel.setText("Score: " + score);
        lifeLabel.setText("Lives: " + life);
        highScoreLabel.setText("High Score: " + highScore);
    }

    private void gameOver() {
        gameRunning = false;
        gameTimer.stop();
        
        String message = "Game Over! Final Score: " + score;
        if (score > highScore) {
            highScore = score;
            message += "\nNew High Score!";
        } else {
            message += "\nHigh Score: " + highScore;
        }
        
        JOptionPane.showMessageDialog(this, message);
        
        // Restart game after 3 seconds
        javax.swing.Timer restartTimer = new javax.swing.Timer(3000, e -> restartGame());
        restartTimer.setRepeats(false);
        restartTimer.start();
    }

    private void restartGame() {
        // Clear all words
        for (SharkWord word : sharkWords) {
            remove(word);
        }
        sharkWords.clear();
        
        // Reset game state
        life = 3;
        score = 0;
        currentSession++;
        gameRunning = true;
        
        updateLabels();
        gameTimer.start();
        input.requestFocusInWindow();
    }

    private String getRandomWord() {
        return WordBank.WORDS.get(rand.nextInt(WordBank.WORDS.size()));
    }

    @Override
    public void dispose() {
        gameRunning = false;
        if (gameTimer != null) {
            gameTimer.stop();
        }
        super.dispose();
    }
}