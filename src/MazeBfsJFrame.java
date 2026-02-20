import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeBfsJFrame extends JFrame {

    private static final int ROWS = 21;
    private static final int COLS = 31;

    private static final double WALL_PROB = 0.33;

    private static final int STEP_MS = 50;

    private char[][] maze = new char[ROWS][COLS];

    private final MazePanel mazePanel = new MazePanel();
    private final JLabel status = new JLabel("Gati.");
    private final JButton startBtn = new JButton("Start");
    private final JButton resetBtn = new JButton("Reset");
    private final JButton randomBtn = new JButton("Random Map");

    private final ArrayDeque<Point> q = new ArrayDeque<>();
    private boolean[][] visited;
    private Point[][] parent;
    private Point start, end, current;
    private List<Point> currentPath = new ArrayList<>();
    private Timer timer;

    private final Random rnd = new Random();

    public MazeBfsJFrame() {
        super("Maze BFS (Marjo's maze🌪️)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controls.add(startBtn);
        controls.add(resetBtn);
        controls.add(randomBtn);
        controls.add(status);

        add(mazePanel, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);

        startBtn.addActionListener(this::onStart);
        resetBtn.addActionListener(e -> resetBfsOnly());
        randomBtn.addActionListener(e -> {
            stopTimer();
            generateRandomMazeGuaranteed();
            resetBfsOnly();
            status.setText("U gjenerua random. Kliko Start BFS.");
            mazePanel.repaint();
        });

        generateRandomMazeGuaranteed();
        resetBfsOnly();

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void onStart(ActionEvent e) {
        if (start == null || end == null) {
            status.setText("Mungon O ose X.");
            return;
        }
        if (timer != null && timer.isRunning()) return;

        status.setText("Po kërkon...");
        timer = new Timer(STEP_MS, ev -> step());
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) timer.stop();
    }

    private void resetBfsOnly() {
        stopTimer();

        visited = new boolean[ROWS][COLS];
        parent = new Point[ROWS][COLS];
        q.clear();
        current = null;
        currentPath = new ArrayList<>();

        start = findChar('O');
        end = findChar('X');

        if (start != null) {
            q.add(start);
            visited[start.r][start.c] = true;
        }

        mazePanel.repaint();
    }

    private void step() {
        if (q.isEmpty()) {
            status.setText("Nuk u gjet rrugë. Provo Random Map.");
            stopTimer();
            return;
        }

        current = q.removeFirst();
        currentPath = reconstructPath(current);

        if (current.equals(end)) {
            status.setText("U gjet rruga! Gjatësia: " + currentPath.size());
            mazePanel.repaint();
            stopTimer();
            return;
        }

        for (Point nb : neighbors(current)) {
            if (visited[nb.r][nb.c]) continue;
            if (maze[nb.r][nb.c] == '#') continue;

            visited[nb.r][nb.c] = true;
            parent[nb.r][nb.c] = current;
            q.addLast(nb);
        }

        mazePanel.repaint();
    }

    private List<Point> reconstructPath(Point p) {
        ArrayList<Point> path = new ArrayList<>();
        Point cur = p;
        while (cur != null) {
            path.add(cur);
            cur = parent[cur.r][cur.c];
        }

        for (int i = 0, j = path.size() - 1; i < j; i++, j--) {
            Point tmp = path.get(i);
            path.set(i, path.get(j));
            path.set(j, tmp);
        }
        return path;
    }

    private List<Point> neighbors(Point p) {
        ArrayList<Point> nbs = new ArrayList<>(4);
        int r = p.r, c = p.c;
        if (r > 0) nbs.add(new Point(r - 1, c));
        if (r < ROWS - 1) nbs.add(new Point(r + 1, c));
        if (c > 0) nbs.add(new Point(r, c - 1));
        if (c < COLS - 1) nbs.add(new Point(r, c + 1));
        return nbs;
    }

    private Point findChar(char ch) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (maze[r][c] == ch) return new Point(r, c);
            }
        }
        return null;
    }

    private void generateRandomMazeGuaranteed() {

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                maze[r][c] = '#';
            }
        }

        Point s = new Point(1, 1);
        Point e = new Point(ROWS - 2, COLS - 2);

        carveGuaranteedPath(s, e);

        for (int r = 1; r < ROWS - 1; r++) {
            for (int c = 1; c < COLS - 1; c++) {
                if (maze[r][c] == '.') continue;

                maze[r][c] = (rnd.nextDouble() < WALL_PROB) ? '#' : ' ';
            }
        }

        for (int r = 1; r < ROWS - 1; r++) {
            for (int c = 1; c < COLS - 1; c++) {
                if (maze[r][c] == '.') maze[r][c] = ' ';
            }
        }

        maze[s.r][s.c] = 'O';
        maze[e.r][e.c] = 'X';

        openAround(s);
        openAround(e);
    }

    private void carveGuaranteedPath(Point s, Point e) {
        int r = s.r, c = s.c;
        maze[r][c] = '.';

        int safety = ROWS * COLS * 10;
        while ((r != e.r || c != e.c) && safety-- > 0) {
            int dr = Integer.compare(e.r, r);
            int dc = Integer.compare(e.c, c);

            boolean moveRow = rnd.nextBoolean();

            int mode = rnd.nextInt(100);

            int nr = r, nc = c;
            if (mode < 70) {

                if (moveRow && dr != 0) nr = r + dr;
                else if (!moveRow && dc != 0) nc = c + dc;
                else if (dr != 0) nr = r + dr;
                else if (dc != 0) nc = c + dc;
            } else {

                int pick = rnd.nextInt(4);
                if (pick == 0) nr = r - 1;
                if (pick == 1) nr = r + 1;
                if (pick == 2) nc = c - 1;
                if (pick == 3) nc = c + 1;
            }

            nr = Math.max(1, Math.min(ROWS - 2, nr));
            nc = Math.max(1, Math.min(COLS - 2, nc));

            r = nr; c = nc;
            maze[r][c] = '.';
        }
    }

    private void openAround(Point p) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int rr = p.r + dr;
                int cc = p.c + dc;
                if (rr <= 0 || rr >= ROWS - 1 || cc <= 0 || cc >= COLS - 1) continue;
                if (maze[rr][cc] != 'O' && maze[rr][cc] != 'X') {
                    maze[rr][cc] = ' ';
                }
            }
        }
    }

    private class MazePanel extends JPanel {
        private static final int CELL = 24;
        private static final int PAD = 10;

        MazePanel() {
            setPreferredSize(new Dimension(PAD * 2 + COLS * CELL, PAD * 2 + ROWS * CELL));
            setBackground(new Color(25, 25, 25));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    int x = PAD + c * CELL;
                    int y = PAD + r * CELL;

                    Color fill = baseColor(r, c);

                    if (visited != null && visited[r][c] && maze[r][c] != '#') {
                        fill = new Color(90, 140, 255);
                    }

                    if (containsPoint(currentPath, r, c)) {
                        fill = new Color(40, 90, 220);
                    }

                    if (maze[r][c] == 'O') fill = new Color(0, 200, 120);
                    if (maze[r][c] == 'X') fill = new Color(220, 60, 60);

                    g2.setColor(fill);
                    g2.fillRect(x, y, CELL, CELL);

                    g2.setColor(new Color(45, 45, 45));
                    g2.drawRect(x, y, CELL, CELL);
                }
            }

            if (current != null) {
                int x = PAD + current.c * CELL;
                int y = PAD + current.r * CELL;
                g2.setColor(Color.YELLOW);
                g2.drawRect(x + 2, y + 2, CELL - 4, CELL - 4);
            }

            g2.dispose();
        }

        private Color baseColor(int r, int c) {
            char v = maze[r][c];
            if (v == '#') return Color.BLACK;
            return Color.WHITE;
        }
        private boolean containsPoint(List<Point> path, int r, int c) {
            if (path == null) return false;
            for (Point p : path) {
                if (p.r == r && p.c == c) return true;
            }
            return false;
        }
    }
    private static class Point {
        final int r, c;
        Point(int r, int c) { this.r = r; this.c = c; }
        @Override public boolean equals(Object o) {
            if (!(o instanceof Point)) return false;
            Point p = (Point) o;
            return r == p.r && c == p.c;
        }
        @Override public int hashCode() { return r * 31 + c; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MazeBfsJFrame().setVisible(true));
    }
}
