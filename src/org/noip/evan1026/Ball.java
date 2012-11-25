package org.noip.evan1026;

import java.awt.Color;

public class Ball {
	double x, y, velX, velY, radius, mass;
	Color c;
	
	public Ball(double x, double y, double velX, double velY, double radius, double mass, Color c){
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.radius = radius;
		this.mass = mass;
		this.c = c;
	}
	
	public void updatePosition(DrawingPanel top, boolean gravity){
		 x += velX;
		 y += velY;
		 
		 if (x - radius < 0){
			 x = radius;
			 velX = - velX;
		 }
		 else if (x + radius > top.getWidth()){
			 x = top.getWidth() - radius;
			 velX = - velX;
		 }
		 
		 if (y - radius < 0){
			 y = radius;
			 velY = - velY;
		 }
		 else if (y + radius > top.getHeight()){
			 y = top.getHeight() - radius;
			 velY = - velY;
		 }
		 
		 if (gravity) velY += 5;
	 }
}