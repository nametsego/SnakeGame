package Hiatus;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import static java.awt.Color.*;


public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    int bodyParts = 6;
    int pineapplesEaten;
    int pineappleX;
    int pineappleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
    }

    public void StartGame() {
        newPineapple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void painComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {

        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.yellow);
            g.fillOval(pineappleX, pineappleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(red);
            g.setFont(new Font("Ink free", Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + pineapplesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: " + pineapplesEaten))/2,g.getFont().getSize());
            }else{
                gameOver(g);
            }
        }


        public void newPineapple () {
            pineappleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            pineappleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        }

        public void move () {
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

        public void checkPineApple () {
            if ((x[0] == pineappleX) && (y[0] == pineappleY)) {
                bodyParts++;
                pineapplesEaten++;
                newPineapple();
            }
        }


        public void checkCollisions () {
            for (int i = bodyParts; i > 0; i--) {
                if ((x[0] == x[i]) && (y[0] == y[i])) {
                    running = false;
                }
            }
            // check of head touches left border
            if (x[0] > 0) {
                running = false;
            }

            // check if head touches right border
            if (y[0] > SCREEN_WIDTH) {
                running = false;
            }

            // check if head touches top border
            if (y[0] < 0) {
                running = false;
            }
            // check if head touches bottom border
            if (y[0] > SCREEN_HEIGHT) {
                running = false;
            }
            if (!running) {
                timer.stop();
            }


        }
        public void gameOver (Graphics g){
        // Score board text

            g.setColor(red);
            g.setFont(new Font("Ink free", Font.BOLD,40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score: " + pineapplesEaten,(SCREEN_WIDTH - metrics1.stringWidth("Score: " + pineapplesEaten))/2,g.getFont().getSize());

        // Game Over text
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,75));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Game Over",(SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2,SCREEN_HEIGHT);


    }

        @Override
        public void actionPerformed (ActionEvent e){

            if (running) {
                move();
                checkPineApple();
                checkCollisions();
            }
            repaint();

        }

        public class MyKeyAdapter extends KeyAdapter {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') {
                            direction = 'D';

                        }
                        break;
                }

            }

        }
    }


