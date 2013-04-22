package org.noip.evan1026;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3773977354632678505L;
	
	ArrayList<Ball> balls = new ArrayList<Ball>();
	boolean gravity = false;
	int mouseX, mouseY;
	Timer ballAdder = new Timer(100, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Random rand = new Random();
			double number = rand.nextDouble();
			balls.add(new Ball(mouseX, mouseY, rand.nextDouble() * 10, rand.nextDouble() * 10, number * 25 + 25, number * 50 + 50, new Color(rand.nextInt(200), rand.nextInt(200), rand.nextInt(200))));
		}
	});

	public DrawingPanel(){
		super();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		Timer t = new Timer(1000/30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateBalls();
			}
		});
		t.start();
	}
	public void updateBalls(){
		for (Ball x : balls){
			x.updatePosition(this, gravity);
		}

		for (int i = 0; i < balls.size() - 1; i++){
			for (int j = i + 1; j < balls.size(); j++){
				if(Math.sqrt(Math.pow(balls.get(i).x - balls.get(j).x, 2) + Math.pow(balls.get(i).y - balls.get(j).y, 2)) < balls.get(i).radius + balls.get(j).radius){
					double[] vec = {balls.get(j).x - balls.get(i).x, balls.get(j).y - balls.get(i).y};
					double factor = Math.sqrt(Math.pow(vec[0], 2) + Math.pow(vec[1], 2));
					vec[0] = vec[0] / factor;
					vec[1] = vec[1] / factor;
					factor = balls.get(j).radius + balls.get(i).radius - Math.sqrt(Math.pow(balls.get(i).x - balls.get(j).x, 2) + Math.pow(balls.get(i).y - balls.get(j).y, 2));
					vec[0] = vec[0] * factor;
					vec[1] = vec[1] * factor;
					balls.get(i).x -= vec[0] / 2;
					balls.get(j).x += vec[0] / 2;
					balls.get(i).y -= vec[1] / 2;
					balls.get(j).y += vec[1] / 2;
					vec[0] = vec[0] / factor;
					vec[1] = vec[1] / factor;
					double factor1 = (balls.get(i).velX * vec[0] + balls.get(i).velY * vec[1]) / (Math.pow(vec[0], 2) + Math.pow(vec[1], 2));
					double factor2 = (balls.get(j).velX * vec[0] + balls.get(j).velY * vec[1]) / (Math.pow(vec[0], 2) + Math.pow(vec[1], 2));
					balls.get(i).velX += balls.get(j).mass / balls.get(i).mass * factor2 * vec[0] - factor1 * vec[0];
					balls.get(i).velY += balls.get(j).mass / balls.get(i).mass * factor2 * vec[1] - factor1 * vec[1];
					balls.get(j).velX += balls.get(i).mass / balls.get(j).mass * factor1 * vec[0] - factor2 * vec[0];
					balls.get(j).velY += balls.get(i).mass / balls.get(j).mass * factor1 * vec[1] - factor2 * vec[1];
				}
			}
		}
		drawBalls();
	}
	public void drawBalls(){
		BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for (Ball x : balls){
			g.setColor(x.c);
			g.fillOval((int)(x.x - x.radius), (int)(x.y - x.radius), (int)(2 * x.radius), (int)(2 * x.radius));
		}
		g = this.getGraphics();
		g.drawImage(image, 0, 0, null);
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1){
			Random rand = new Random();
			double number = rand.nextDouble();
			balls.add(new Ball(arg0.getX(), arg0.getY(), rand.nextDouble() * 10, rand.nextDouble() * 10, number * 25 + 25, number * 50 + 50, new Color(rand.nextInt(200), rand.nextInt(200), rand.nextInt(200))));
		}
		else if(arg0.getButton() == MouseEvent.BUTTON3){
			gravity = !gravity;
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1) ballAdder.start();
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		ballAdder.stop();
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseX = arg0.getX();
		mouseY = arg0.getY();
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		mouseX = arg0.getX();
		mouseY = arg0.getY();
	}
}