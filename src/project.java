import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

public class project extends JFrame {

    private static final int ROWS = 21;
    private static final int COLS = 31;
    private static final int CELL_SIZE = 24;
    private static final int PADDING = 10;
    private static final int STEP_DELAY = 50;
    private static final double WALL_PROBABILITY = 0.33;

    private final char[][] maze = new char[ROWS][COLS];
    private boolean[][] visited;
    private Point[][] parent;

    private Point start;
    private Point end;
    private Point current;

    private final Deque<Point> queue = new ArrayDeque<>();
    private List<Point> currentPath = new ArrayList<>();
    private final Random random = new Random();

    private final MazePanel mazePanel = new MazePanel();
    private final JLabel statusLabel = new JLabel("Gati.");
    private Timer timer;

    public project() {
        super("Maze BFS (Marjo's maze 🌪️)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(mazePanel, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        generateMaze();
        resetBfs();

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JPanel createControlPanel() {
        JButton startBtn = new JButton("Start");
        JButton resetBtn = new JButton("Reset");
        JButton randomBtn = new JButton("Random Map");

        startBtn.addActionListener(this::startBfs);
        resetBtn.addActionListener(e -> resetBfs());
        randomBtn.addActionListener(e -> {
            stopTimer();
            generateMaze();
            resetBfs();
            statusLabel.setText("U gjenerua random. Kliko Start BFS.");
        });

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.add(startBtn);
        panel.add(resetBtn);
        panel.add(randomBtn);
        panel.add(statusLabel);
        return panel;
    }

    private void startBfs(ActionEvent e) {
        if (timer != null && timer.isRunning()) return;

        statusLabel.setText("Po kërkon...");
        timer = new Timer(STEP_DELAY, ev -> bfsStep());
        timer.start();
    }

    private void bfsStep() {
        if (queue.isEmpty()) {
            statusLabel.setText("Nuk u gjet rrugë.");
            stopTimer();
            return;
        }

        current = queue.poll();
        currentPath = buildPath(current);

        if (current.equals(end)) {
            statusLabel.setText("U gjet rruga! Gjatësia: " + currentPath.size());
            stopTimer();
        }

        for (Point neighbor : getNeighbors(current)) {
            if (!visited[neighbor.r][neighbor.c] && maze[neighbor.r][neighbor.c] != '#') {
                visited[neighbor.r][neighbor.c] = true;
                parent[neighbor.r][neighbor.c] = current;
                queue.add(neighbor);
            }
        }

        mazePanel.repaint();
    }

    private void resetBfs() {
        stopTimer();

        visited = new boolean[ROWS][COLS];
        parent = new Point[ROWS][COLS];
        queue.clear();
        currentPath.clear();
        current = null;

        start = findPoint('O');
        end = findPoint('X');

        if (start != null) {
            visited[start.r][start.c] = true;
            queue.add(start);
        }

        mazePanel.repaint();
    }

    private void stopTimer() {
        if (timer != null) timer.stop();
    }

    private List<Point> buildPath(Point node) {
        List<Point> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = parent[node.r][node.c];
        }
        Collections.reverse(path);
        return path;
    }

    private List<Point> getNeighbors(Point p) {
        List<Point> neighbors = new ArrayList<>(4);
        if (p.r > 0) neighbors.add(new Point(p.r - 1, p.c));
        if (p.r < ROWS - 1) neighbors.add(new Point(p.r + 1, p.c));
        if (p.c > 0) neighbors.add(new Point(p.r, p.c - 1));
        if (p.c < COLS - 1) neighbors.add(new Point(p.r, p.c + 1));
        return neighbors;
    }

    private Point findPoint(char symbol) {
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                if (maze[r][c] == symbol)
                    return new Point(r, c);
        return null;
    }

    private void generateMaze() {
        for (char[] row : maze) Arrays.fill(row, '#');

        Point s = new Point(1, 1);
        Point e = new Point(ROWS - 2, COLS - 2);

        carvePath(s, e);

        for (int r = 1; r < ROWS - 1; r++) {
            for (int c = 1; c < COLS - 1; c++) {
                if (maze[r][c] != '.')
                    maze[r][c] = random.nextDouble() < WALL_PROBABILITY ? '#' : ' ';
            }
        }

        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                if (maze[r][c] == '.') maze[r][c] = ' ';

        maze[s.r][s.c] = 'O';
        maze[e.r][e.c] = 'X';
    }

    private void carvePath(Point s, Point e) {
        int r = s.r, c = s.c;
        maze[r][c] = '.';

        while (r != e.r || c != e.c) {
            if (random.nextBoolean() && r != e.r)
                r += Integer.signum(e.r - r);
            else if (c != e.c)
                c += Integer.signum(e.c - c);

            maze[r][c] = '.';
        }
    }

    private class MazePanel extends JPanel {

        MazePanel() {
            setPreferredSize(new Dimension(
                    PADDING * 2 + COLS * CELL_SIZE,
                    PADDING * 2 + ROWS * CELL_SIZE));
            setBackground(Color.DARK_GRAY);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    int x = PADDING + c * CELL_SIZE;
                    int y = PADDING + r * CELL_SIZE;

                    Color color = maze[r][c] == '#' ? Color.BLACK : Color.WHITE;
                    if (visited != null && visited[r][c]) color = new Color(90, 140, 255);
                    if (currentPath.contains(new Point(r, c))) color = new Color(40, 90, 220);
                    if (maze[r][c] == 'O') color = new Color(0, 200, 120);
                    if (maze[r][c] == 'X') color = new Color(220, 60, 60);

                    g.setColor(color);
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    private static class Point {
        final int r, c;
        Point(int r, int c) { this.r = r; this.c = c; }
        @Override public boolean equals(Object o) {
            return o instanceof Point p && r == p.r && c == p.c;
        }
        @Override public int hashCode() {
            return r * 31 + c;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new MazeBfsJFrame().setVisible(true));
    }
}