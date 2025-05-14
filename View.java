import pt.iscte.guitoo.Color;
import pt.iscte.guitoo.StandardColor;
import pt.iscte.guitoo.board.Board;

public class View {
    Board board;
    Damas Damas;

    View(Damas Damas) {
        this.Damas = Damas;
        board = new Board("Tabuleiro", 8, 8, 40);
        board.setIconProvider(this::icon);
        board.addMouseListener(this::click);
        board.setBackgroundProvider(this::background);
        board.addAction("new", this::action);
        board.addAction("random", this::random);
    }

    public void mostrarMensagem(String mensagem) {
    	 System.out.println(mensagem);
    }

    String icon(int line, int col) {
        if (Damas.quadrado(line, col) == 1) {
            return "white.png";
        }
        if (Damas.quadrado(line, col) == 2) {
            return "black.png";
        }
        return null;
    }

    void click(int line, int col) {
        if (Damas.éDamas(line, col)) {
            if (line != Damas.line && col != Damas.col) {
                Damas.line = line;
                Damas.col = col;
                return;
            }
        }

        Damas.moverpeca(line, col);
        Damas.verificarFimDeJogo();
    }

    Color background(int line, int col) {
        if ((line + col) % 2 == 0) {
            return StandardColor.WHITE;
        }
        return StandardColor.BLACK;
    }

    void action() {}

    private void random() {}

    void start() {
        board.open();
    }

    public static void main(String[] args) {
        Damas jogo = new Damas(8
        		, 12);
        View gui = new View(jogo);
        jogo.setView(gui);
        gui.start();
    }
}
