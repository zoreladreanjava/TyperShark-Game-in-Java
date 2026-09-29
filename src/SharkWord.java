import javax.swing.*;
import java.awt.*;

public class SharkWord extends JLabel {
    private Game game;
    private String word;
    private int x, y;
    private volatile boolean active = true;
    private int fallSpeed;
    private int sessionId;
    private javax.swing.Timer fallTimer; // Explicitly use Swing Timer

    public SharkWord(Game game, String word, int x, int fallSpeed, int sessionId) {
        this.game = game;
        this.word = word;
        this.x = x;
        this.y = 80;
        this.fallSpeed = fallSpeed;
        this.sessionId = sessionId;
        
        setBounds(x, y, 200, 35);
        setText(word);
        setFont(new Font("Verdana", Font.BOLD, 20));
        setForeground(Color.CYAN);
        setHorizontalAlignment(CENTER);
        setOpaque(false);
    }

    public void start() {
        fallTimer = new javax.swing.Timer(fallSpeed, e -> fall());
        fallTimer.start();
    }

    private void fall() {
        if (!active || sessionId != game.getCurrentSession()) {
            fallTimer.stop();
            return;
        }
        
        y += 15;
        setLocation(x, y);
        
        // Check if word reached bottom
        if (y >= game.getHeight() - 100) {
            fallTimer.stop();
            if (active && sessionId == game.getCurrentSession()) {
                active = false;
                game.wordMissed(this);
            }
        }
    }

    public boolean match(String input) {
        return word.equalsIgnoreCase(input) && active && sessionId == game.getCurrentSession();
    }

    public void deactivate() {
        active = false;
        if (fallTimer != null) {
            fallTimer.stop();
        }
    }

    public boolean isActive() {
        return active && sessionId == game.getCurrentSession();
    }
    
    public int getX() {
        return x;
    }
}