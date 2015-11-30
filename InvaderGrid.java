import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class InvaderGrid extends Component {
    public static int DEFAULT_ROWS = 5, DEFAULT_COLUMNS = 11;
    public static int DEFAULT_DX = 2, DEFAULT_DY = Invader.HEIGHT;

    private ArrayList<ArrayList<Invader>> invaders = new ArrayList<>();
    private int rows = DEFAULT_ROWS;
    private int columns = DEFAULT_COLUMNS;
    private int dx = DEFAULT_DX;
    private int dy = DEFAULT_DY;

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
                    version = Invader.Version.LARGE;
                } else if (i < 3) {
                    version = Invader.Version.MEDIUM;
                } else {
                    version = Invader.Version.SMALL;
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
       boolean move_down = checkMoveDown(max_left, max_right);
       dx = move_down ? -dx : dx;

       for (int i = 0; i < invaders.size(); i++) {
           for (int j = 0; j < invaders.get(i).size(); j++) {
               if (move_down) {
                   invaders.get(i).get(j).moveDown(dy);
               }
               invaders.get(i).get(j).moveX(dx);
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



    private boolean checkMoveDown(int max_left, int max_right) {
        return getLeftBound() < max_left || getRightBound() > max_right;
    }

    /**
     * @return Gets the left-most location of the grid
     */
    private int getLeftBound() {
        int result = 0;
        for (int i = 0; i < invaders.size(); i++) {
            if (i == 0) {
                result = invaders.get(i).get(0).getX();
            } else if (invaders.get(i).get(0).getX() < result) {
                result = invaders.get(i).get(0).getX();
            }
        }
        return result;
    }

    /**
     * @return Gets the right-most location of the grid
     */
    private int getRightBound() {
        int result = 0;
        for (int i = 0; i < invaders.size(); i++) {
            int last = invaders.get(i).size() - 1;
            if (i == 0) {
                result = invaders.get(i).get(last).getX();
            } else if (invaders.get(i).get(last).getX() > result) {
                result = invaders.get(i).get(last).getX();
            }
        }
        return result + Invader.WIDTH + Invader.WIDTH_PAD;
    }



    public boolean runBulletCollision(Bullet bullet) {
        boolean hit = false;
        for (int i = 0; i < invaders.size(); i++) {
            for (int j = 0; j < invaders.get(i).size(); j++) {
                if (invaders.get(i).get(j).hitByBullet(bullet)) {
                    hit = true;
                    invaders.get(i).remove(j);
                    break;
                }
            }

            if (invaders.get(i).size() == 0) invaders.remove(i);
            if (hit) break;
        }
        return hit;
    }

    public Bullet getRandomBullet() {
        Random random = new Random();
        int row = random.nextInt(invaders.size());
        int column = random.nextInt(invaders.get(row).size());

        return invaders.get(row).get(column).getBullet();
    }



    public boolean isEmpty () {
        return !(invaders.size() > 0);
    }



}
