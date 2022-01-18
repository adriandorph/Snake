package View;

import Model.Snake.SnakeGame;

public class SnakeRender {
    private final RenderGrid renderGrid;
    private final int score;

    public SnakeRender(SnakeGame sg){
        this.renderGrid = sg.getRenderGrid();
        this.score = sg.getScore();
    }

    public RenderGrid getRenderGrid() {
        return renderGrid;
    }

    public int getScore() {
        return score;
    }
}
