package com.megaman.userinterface;


import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	private Thread thread;
	private boolean isRunning;
	private InputManager inputManager;
	
	@Override //ghi đè phương thức
	// Xuất hình ảnh ra màn hình
	public void paint(Graphics g) {
		
		g.setColor(Color.black);
		g.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
	}
	
	public void startGame() {
		if(thread==null) {
			isRunning = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@Override
	// Tạo game loop để liên tục xuất hình ảnh ra màn hình
	public void run() {
		long fps = 60;
		long period = 1000 * 1000000 / fps;
		long beginTime, sleepTime;
		
		int a = 1;
		
		beginTime = System.nanoTime();
		
		while(isRunning) {
			//System.out.print("\na = "+(a++));
			long deltaTime = System.nanoTime() - beginTime;
			sleepTime = period - deltaTime;
			
			// Giới hạn số khung hình xuất ra
			try {
				if(sleepTime>0) {
					Thread.sleep(sleepTime/1000000);
				}
				else Thread.sleep(period/2000000);
			} catch (InterruptedException e) {
				beginTime = System.nanoTime();
			}
		}
	}
	
	//Tiếp nhận sự kiện nhập từ bàn phím
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		inputManager.processKeyPressed(e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		inputManager.processKeyReleased(e.getKeyCode());
	}
	
	public GamePanel() {
		inputManager = new InputManager();
		
	}
}
