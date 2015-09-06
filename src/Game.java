import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sztosz on 9/6/15.
 */
public class Game extends BasicGame {
    private TiledMap grassMap;
    private Image sprite;
    private Image[] sprites;
    private float x = 32f, y = 32f;
    private List<Position> obstacles;
    private static float TILE_HEIGHT = 32, TILE_WIDTH = 32;
    private static float MAP_HEIGHT = 10, MAP_WIDTH = 10;

    public Game() {
        super("First Java Slick2D test");
    }

    public static void main(String[] arguments) {
        try {
            AppGameContainer app = new AppGameContainer(new Game());
            app.setDisplayMode(640, 480, false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        grassMap = new TiledMap("assets/maps/grassmap.tmx");
        Image moveN = new Image("assets/tiles/N.png");
        Image moveS = new Image("assets/tiles/S.png");
        Image moveE = new Image("assets/tiles/E.png");
        Image moveW = new Image("assets/tiles/W.png");
        sprites = new Image[]{moveN, moveS, moveE, moveW};
        sprite = sprites[DIR.E.val()];
        obstacles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            obstacles.add(new Position(i, 0));
            obstacles.add(new Position(i, 9));
            obstacles.add(new Position(0, i));
            obstacles.add(new Position(9, i));
        }
        obstacles.add(new Position(5, 4));
        obstacles.add(new Position(5, 5));
        obstacles.add(new Position(5, 6));
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        Input input = gameContainer.getInput();
        if (input.isKeyDown(Input.KEY_UP)) {
            sprite = sprites[DIR.N.val()];
            update_position(DIR.N);
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            sprite = sprites[DIR.S.val()];
            update_position(DIR.S);
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            sprite = sprites[DIR.E.val()];
            update_position(DIR.E);
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            sprite = sprites[DIR.W.val()];
            update_position(DIR.W);
        }

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        grassMap.render(0, 0);
        sprite.draw(x, y);
    }

    public void update_position(DIR dir) {
        if (dir == DIR.N) {
            float _y = y;
            y = y - (1 *TILE_HEIGHT);
            Position position = new Position(x, y);
            for (Position p : obstacles){
                if (position.Equal(p)) {
                    y = _y;
                    break;
                }
            }
        }else if (dir == DIR.S) {
            float _y = y;
            y = y + (1 *TILE_HEIGHT);
            Position position = new Position(x, y);
            for (Position p : obstacles){
                if (position.Equal(p)) {
                    y = _y;
                    break;
                }
            }
        }else if (dir == DIR.E) {
            float _x = x;
            x = x + (1 *TILE_WIDTH);
            Position position = new Position(x, y);
            for (Position p : obstacles){
                if (position.Equal(p)) {
                    x = _x;
                    break;
                }
            }
        }else if (dir == DIR.W) {
            float _x = x;
            x = x - (1 *TILE_WIDTH);
            Position position = new Position(x, y);
            for (Position p : obstacles){
                if (position.Equal(p)) {
                    x = _x;
                    break;
                }
            }
        }
    }

    enum DIR {
        N(0),
        S(1),
        E(2),
        W(3);

        private final int value;

        private DIR(int value) {
            this.value = value;
        }

        public int val() {
            return value;
        }

    }

    private class Position {
        public float x, y;

        public Position(float x, float y) {
            this.x = x * TILE_WIDTH;
            this.y = y * TILE_HEIGHT;
        }

        public boolean Equal(Position position) {
            return this.x == position.x && this.y == position.y;
        }
    }
}
