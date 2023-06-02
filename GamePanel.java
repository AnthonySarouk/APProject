import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 1300;
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 40;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE); //Divides total area of game screen by unit size
    static final int DELAY = 150; //Determines the delay and how quickly the snake or other game elements will move.
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false; //determines whether the game is running or not
    Timer timer; // Used to create a delay for when the actionPerformed methods are called.
    Random random;
    JButton reset;
    int highscore = 0;
    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
	    int initialX = random.nextInt((SCREEN_WIDTH / UNIT_SIZE - 2)) + 1; // Assigns snakes head and body parts. For loops iterates bodyParts number of times to assign intial positions
        int initialY = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE - 2)) + 1; 
        for (int i = 0; i < bodyParts; i++) {
            x[i] = initialX * UNIT_SIZE;
            y[i] = initialY * UNIT_SIZE;
            initialX--;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {
            //apple 
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            
            //snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 100,200));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Courier New", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
            
        } else {
            gameOver(g);
        }

    }

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }
    //Iterates through each body part and updates the position.
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) { // if snake has collided with apple
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {

        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        if (x[0] < 0) { //hit left wall
            running = false;
        }

        if (x[0] >= SCREEN_WIDTH) { //hit right wall
            running = false;
        }

        if (y[0] < 0) { //hit the ceiling or top boundary
            running = false;
        }

        if (y[0] >= SCREEN_HEIGHT) { //hits the bottom boundary/floor
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }
    public void restart() {
        bodyParts = 6;
        highscore = Math.max(applesEaten, highscore);
        applesEaten = 0;
        direction = 'R';
        running = false;

        startGame();
        move();
        reset.setVisible(false);
        repaint();
    }
    
    private void updateHighScore() {
        if (applesEaten > highscore) {
            highscore = applesEaten;
        }
        
        
    }

    public void gameOver(Graphics g) {
      
        updateHighScore();
        g.setColor(Color.red);
        g.setFont(new Font("Courier New", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        g.setColor(Color.red);
        g.setFont(new Font("Courier New", Font.BOLD, 40));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("High Score: " + highscore, (SCREEN_WIDTH - metrics3.stringWidth("High Score: " + highscore)) - 500 , 100);
        g.setColor(Color.red);
        g.setFont(new Font("Courier New", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT - 500);
        reset = new JButton();
        reset.setText("Restart");
        reset.setBounds(570, 350, 150, 50);
		Color customColor = new Color(250, 11, 11);
		reset.setBackground(customColor);
		Font font = new Font("Courier New", Font.BOLD, 20); 
		reset.setFont(font);  
        this.add(reset);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
        
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_W:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
