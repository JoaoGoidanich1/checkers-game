record position (int line, int col) {

	boolean évalido()

	{

		if(line < 0 || col < 0)

			return false;

		return true;

	}
	
		
	
}


public class Damas {

    public int[][] tabuleiro; // Matriz para representar o tabuleiro
    private int tamanho;      // Tamanho do tabuleiro (n x n)
    private int numPecas;     
    public int line ;
    public int col ;
    public boolean vezbranca = true; 
    private View view;
    // Construtor para inicializar o tabuleiro
    public Damas(int tamanho, int numPecas) {
        this.tamanho = tamanho;
        this.numPecas = numPecas;
        this.tabuleiro = new int[tamanho][tamanho]; // Cria um tabuleiro vazio
        iniciarTabuleiro(); // Distribui as peças
    }

    public boolean vezbranca() {
        return tabuleiro[line][col] == 1;
    }
    public void setView(View view) {
        this.view = view;}

    void iniciarTabuleiro() {
        // Coloca as peças pretas (2)
        int pecasColocadas = 0;
        for (int i = 0; i < tamanho && pecasColocadas < numPecas; i++) {
            for (int j = 0; j < tamanho; j++) {
                if ((i + j) % 2 == 1) { // Apenas nas casas alternadas
                    tabuleiro[i][j] = 2; // pretas
                    pecasColocadas++;
                    if (pecasColocadas == numPecas) break;
                }
            }
        }

        // Coloca as peças brancas (1)
        pecasColocadas = 0;
        for (int i = tamanho - 1; i   > tamanho /2 ; i--) {
            for (int j = 0; j < tamanho; j++) {
                if ((i + j) % 2 == 1) { // Apenas nas casas alternadas
                    tabuleiro[i][j] = 1; // Jogador de baixo
                    pecasColocadas++;
                    if (pecasColocadas == numPecas) break;
                }
            }
        }
    }
    private boolean existeCapturaGlobal() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if ((vezbranca && tabuleiro[i][j] == 2) || (!vezbranca && tabuleiro[i][j] == 1)) {
                    if (existeCapturaObrigatoria(i, j)) {
                        return true; // Existe pelo menos uma peça com captura disponível
                    }
                }
            }
    }
        return false;
    }

    public int quadrado (int line ,int col)  {
        return tabuleiro[line][col];
    }

    public boolean éDamas(int line, int col) {
        return tabuleiro[line][col] == 1 || tabuleiro[line][col] == 2;
    }

    public boolean foraDoTabuleiro(int line, int col) {
        return line < 0 || col < 0 || line >= tamanho || col >= tamanho;
    }

    // Método  verificar se há capturas obrigatórias
    private boolean existeCapturaObrigatoria(int line, int col) {
        int jogador = tabuleiro[line][col];
        int oponente = jogador == 1 ? 2 : 1;

        // Diagonal direita (captura)
        if (!foraDoTabuleiro(line + 2, col + 2) && tabuleiro[line + 1][col + 1] == 1 &&
            tabuleiro[line + 2][col + 2] == 0) {
            return true;
        }

        // Diagonal esquerda (captura)
        if (!foraDoTabuleiro(line + 2, col - 2) && tabuleiro[line + 1][col - 1] == 1 &&
            tabuleiro[line + 2][col - 2] == 0) {
            return true;
        }

    
        if (jogador == 1) {
            // Diagonal direita (captura)
            if (!foraDoTabuleiro(line - 2, col + 2) && tabuleiro[line - 1][col + 1] == oponente &&
                tabuleiro[line - 2][col + 2] == 0) {
                return true;
            }

            // Diagonal esquerda (captura)
            if (!foraDoTabuleiro(line - 2, col - 2) && tabuleiro[line - 1][col - 1] == oponente &&
                tabuleiro[line - 2][col - 2] == 0) {
                return true;
            }
        }

        return false;
    }

    public void moverpeca(int linhaDestino, int colunaDestino) {
        
        if (existeCapturaGlobal()) {
            // Verifica se a peça selecionada é capaz de realizar uma captura
            if (existeCapturaObrigatoria(line, col)) {
                // Realizar a captura se a peça pode capturar e a jogada corresponde a um movimento válido de captura
                realizarCaptura(linhaDestino, colunaDestino);
                return;
            } else {
                return; 
            }
        }

        // Movimentos simples (somente se não houver capturas obrigatórias)
        if (vezbranca && tabuleiro[line][col] == 2) { 
            if ((linhaDestino == line + 1 && Math.abs(colunaDestino - col) == 1) &&
                tabuleiro[linhaDestino][colunaDestino] == 0) {
                tabuleiro[linhaDestino][colunaDestino] = 2;
                tabuleiro[line][col] = 0;
                vezbranca = false; 
            }
        } else if (!vezbranca && tabuleiro[line][col] == 1) { // Jogador preto
            if ((linhaDestino == line - 1 && Math.abs(colunaDestino - col) == 1) &&
                tabuleiro[linhaDestino][colunaDestino] == 0) {
                tabuleiro[linhaDestino][colunaDestino] = 1;
                tabuleiro[line][col] = 0;
                vezbranca =true; 
            }
        }
    }

    private void realizarCaptura(int linhaDestino, int colunaDestino) {
        int jogador = tabuleiro[line][col];
        int oponente = jogador == 1 ? 2 : 1;

        // Diagonal direita (captura)
        if (!foraDoTabuleiro(line + 2, col + 2) && linhaDestino == line + 2 &&
            colunaDestino == col + 2 && tabuleiro[line + 1][col + 1] == oponente &&
            tabuleiro[line + 2][col + 2] == 0) {
            tabuleiro[line + 2][col + 2] = jogador;
            tabuleiro[line + 1][col + 1] = 0;
            tabuleiro[line][col] = 0;
            vezbranca = !vezbranca;
            return;
        }

        // Diagonal esquerda (captura)
        if (!foraDoTabuleiro(line + 2, col - 2) && linhaDestino == line + 2 &&
            colunaDestino == col - 2 && tabuleiro[line + 1][col - 1] == oponente &&
            tabuleiro[line + 2][col - 2] == 0) {
            tabuleiro[line + 2][col - 2] = jogador;
            tabuleiro[line + 1][col - 1] = 0;
            tabuleiro[line][col] = 0;
            vezbranca = !vezbranca; 
            return;
        }

        // Para o jogador preto
        if (jogador == 1) {
            // Diagonal direita (captura)
            if (!foraDoTabuleiro(line - 2, col + 2) && linhaDestino == line - 2 &&
                colunaDestino == col + 2 && tabuleiro[line - 1][col + 1] == oponente &&
                tabuleiro[line - 2][col + 2] == 0) {
                tabuleiro[line - 2][col + 2] = jogador;
                tabuleiro[line - 1][col + 1] = 0;
                tabuleiro[line][col] = 0;
                vezbranca = !vezbranca; 
                return;
            }

            // Diagonal esquerda (captura)
            if (!foraDoTabuleiro(line - 2, col - 2) && linhaDestino == line - 2 &&
                colunaDestino == col - 2 && tabuleiro[line - 1][col - 1] == oponente &&
                tabuleiro[line - 2][col - 2] == 0) {
                tabuleiro[line - 2][col - 2] = jogador;
                tabuleiro[line - 1][col - 1] = 0;
                tabuleiro[line][col] = 0;
                vezbranca = !vezbranca;
                return;
            }
        }
    }
    

    // Verifica se uma peça pode se mover  (não é captura)
    private boolean podeMover(int line, int col) {
        int jogador = tabuleiro[line][col];

        //branca
        if (jogador == 2) {
            return (!foraDoTabuleiro(line + 1, col + 1) && tabuleiro[line + 1][col + 1] == 0) ||
                   (!foraDoTabuleiro(line + 1, col - 1) && tabuleiro[line + 1][col - 1] == 0);
        }

        // pretas
        if (jogador == 1) {
            return (!foraDoTabuleiro(line - 1, col + 1) && tabuleiro[line - 1][col + 1] == 0) ||
                   (!foraDoTabuleiro(line - 1, col - 1) && tabuleiro[line - 1][col - 1] == 0);
        }

        return false;
    } public void verificarFimDeJogo() {
        boolean podeMoverBrancas = false, podeMoverPretas = false;
        int pecasBrancas = 0, pecasPretas = 0;

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] == 2) {
                    pecasBrancas++;
                    if (existeCapturaObrigatoria(i, j) || podeMover(i, j)) {
                        podeMoverBrancas = true;
                    }
                } else if (tabuleiro[i][j] == 1) {
                    pecasPretas++;
                    if (existeCapturaObrigatoria(i, j) || podeMover(i, j)) {
                        podeMoverPretas = true;
                    }
                }
            }
        }

        String mensagemFim = null;
        if (!podeMoverBrancas && !podeMoverPretas) {
            if (pecasBrancas > pecasPretas) {
                mensagemFim = "Fim de Jogo! Jogador Branco venceu";
            } else if (pecasPretas > pecasBrancas) {
                mensagemFim = "Fim de Jogo! Jogador Preto venceu com ";
            } else {
                mensagemFim = "Fim de Jogo! Empate com " + pecasBrancas + " peças para cada jogador.";
            }
        } else if (!podeMoverBrancas) {
            mensagemFim = "Fim de Jogo! Jogador Preto venceu! ";
        } else if (!podeMoverPretas) {
            mensagemFim = "Fim de Jogo! Jogador Branco venceu! ";
        }

        if (mensagemFim != null && view != null) {
            view.mostrarMensagem(mensagemFim);
        }
    }

  
}