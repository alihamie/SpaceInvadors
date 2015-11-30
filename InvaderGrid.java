import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class InvaderGrid extends Component {
    public static int DEFAULT_ROWS = 5, DEFAULT_COLUMNS = 11;
    public static int DEFAULT_DX = 20, DEFAULT_DY = Invader.HEIGHT;

    private ArrayList<ArrayList<Invader>> invaders = new ArrayList<>();
    private int rows = DEFAULT_ROWS;
    private int columns = DEFAULT_COLUMNS;
    private int dx = DEFAULT_DX;
    private int dy = DEFAULT_DY;
    private boolean moved_down = false; // Used if checking if already moved down in previous movement

    private int kill_count = 0;

    public InvaderGrid() {
        this(DEFAULT_ROWS, DEFAULT_COLUMNS, DEFAULT_DX, DEFAULT_DY);
    }
    public InvaderGrid(int rows, int columns) {
        this(rows, columns, DEFAULT_DX, DEFAULT_DY);
    }
    public InvaderGrid(int rows, int columns, int dx) {
        this(rows, columns, dx, DEFAULT_DY);
    }
    public InvaderGrid(int rows, int columns, int dx, int dy) {
        this.rows = rows;
        this.columns = columns;
        this.dx = dx;
        this.dy = dy;
        initialize_grid();
    }

    private void initialize_grid() {
        int v_distance = 0;
        for (int i = 0; i < rows; ++i) {
            invaders.add(new ArrayList<>());
            int h_distance = 0;

            v_distance += Invader.HEIGHT_PAD;
            for (int j = 0; j < columns; ++j) {
                h_distance += Invader.WIDTH_PAD;
                Invader.Version version;
                if (i < 1) {
                    version = Invader.Version.SMALL;
                } else if (i < 3) {
                    version = Invader.Version.MEDIUM;
                } else {
                    version = Invader.Version.LARGE;
                }
                invaders.get(i).add(new Invader(version, h_distance, v_distance));
                h_distance += Invader.WIDTH + Invader.WIDTH_PAD;
            }
            v_distance += Invader.HEIGHT + Invader.HEIGHT_PAD;
        }
    }

    /**
     * Moves the invader grid the set dx distance and if moving down, the set dy distance
     * @param max_left Left-most location permissible to perform the movement
     * @param max_right Right-most location permissible to perform the movement
     */
    public void performMovement(int max_left, int max_right) {
        boolean move_down = (getLeftBound() < max_left || getRightBound() > max_right) && !moved_down;
        moved_down = move_down;
        dx = move_down ? -dx : dx;

        Invader current;
        for (int i = 0; i < invaders.size(); i++) {
            for (int j = 0; j < invaders.get(i).size(); j++) {
                current = invaders.get(i).get(j);
                if (move_down) {
                    current.moveDown(dy);
                } else {
                    current.moveX(dx);
                }
                current.toggleSprite();
            }
        }

    }



    public void draw(Graphics g) {
        for (int i = 0; i < invaders.size(); ++i) {
            for (int j = 0; j < invaders.get(i).size(); ++j) {
                invaders.get(i).get(j).draw(g);
            }
        }
    }


    /**
     * @return Gets the left-most location of the grid
     */
    private int getLeftBound() {
        int result = 0;
        int current;
        for (int i = 0; i < invaders.size(); i++) {
            current = invaders.get(i).get(0).getX();
            if (i == 0) {
                result = current;
            } else if (current < result) {
                result = current;
            }
        }
        return result - Invader.WIDTH_PAD;
    }

    /**
     * @return Gets the right-most location of the grid
     */
    private int getRightBound() {
        int result = 0;
        int current;
        for (int i = 0; i < invaders.size(); i++) {
            current = invaders.get(i).get(invaders.get(i).size() - 1).getX();
            if (i == 0) {
                result = current;
            } else if (current > result) {
                result = current;
            }
        }
        return result + Invader.WIDTH + Invader.WIDTH_PAD;
    }


    /**
     *
     * @param bullet
     * @return int Number of points. 0 means no hits. More than 0 is the points of that hit
     */
    public int runBulletCollision(Bullet bullet) {
        int points = 0;
        for (int i = 0; i < invaders.size(); i++) {
            for (int j = 0; j < invaders.get(i).size(); j++) {
                if (invaders.get(i).get(j).hitByBullet(bullet)) {
                    points = invaders.get(i).get(j).getPoints();
                    kill_count++;
                    invaders.get(i).remove(j);
                    break;
                }
            }

            if (invaders.get(i).size() == 0) invaders.remove(i);
            if (points > 0) break;
        }
        return points;
    }

    public Bullet getRandomBullet() {
        Random random = new Random();
        int row = random.nextInt(invaders.size());
        int column = random.nextInt(invaders.get(row).size());

        return invaders.get(row).get(column).getBullet();
    }



    public boolean isEmpty () {
        return invaders.size() == 0;
    }

    public int numInvadersStart() {
        return rows * columns;
    }

    public int numInvadersNow() {
        return (rows * columns) - kill_count;
    }

    public int numInvadersDestroyed() {
        return kill_count;
    }

}
