import java.awt.EventQueue;

public class TyperShark {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }
}