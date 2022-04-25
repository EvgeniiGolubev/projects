package com.company.view;

import com.company.controller.EventListener;
import com.company.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Field extends JPanel {
    private View view;
    private EventListener eventListener;

    public Field(View view) {
        this.view = view;
        KeyHandler keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        view.getGameObjects().getAll().stream().forEach(x -> x.draw(g));
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int event = e.getKeyCode();
            switch (event) {
                case KeyEvent.VK_LEFT:
                    eventListener.move(Direction.LEFT);
                    break;
                case KeyEvent. VK_RIGHT:
                    eventListener.move(Direction.RIGHT);
                    break;
                case KeyEvent. VK_UP:
                    eventListener.move(Direction.UP);
                    break;
                case KeyEvent. VK_DOWN:
                    eventListener.move(Direction.DOWN);
                    break;
                case KeyEvent. VK_R:
                    eventListener.restart();
                    break;
                default:
                    break;
            }
        }
    }
}
