/*
 *   casmi
 *   http://casmi.github.com/
 *   Copyright (C) 2011, Xcoo, Inc.
 *
 *  casmi is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
  
package casmi.hit;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import casmi.graphics.element.Element;
import casmi.graphics.element.Renderable;

/**
 * Detecting and checking hit area for rect
 * HitRect
 * 
 * @author K. Nishimura
 * 
 */
public class HitRect extends Element implements Hittable, Renderable {
    private boolean hitted = false;
    private double x = 0.0;
    private double y = 0.0;
    private double w;
    private double h;
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double x3;
    private double y3;
    private double x4;
    private double y4;
 
    
    /**
     * Creates a new HitRect object using width and height.
     *
     * @param w
     *              The width of the rectangle.
     * @param h 
     *              The height of the rectangle.                          
     */
    public HitRect(double w, double h) {
        this.w = w;
        this.h = h;
    }
    
    public boolean hit(int x, int y){
    	if( x >= this.x - this.w / 2.0 
    			&& x <= this.x + this.w / 2.0
    			&& y >= this.y - this.h / 2.0
    			&& y <= this.y + this.h / 2.0){
    		hitted = true;
    	}else{
    		hitted = false;
    	}
    	return hitted;
    }

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
    public void setPosition(double x, double y){
    	this.x = x;
    	this.y = y;
    }
    
    private void calcRect() {
        this.x1 = x - w / 2;
        this.y1 = y + h / 2;
        this.x2 = x - w / 2;
        this.y2 = y - h / 2;
        this.x3 = x + w / 2;
        this.y3 = y - h / 2;
        this.x4 = x + w / 2;
        this.y4 = y + h / 2;
    }
    
    @Override
    public void render(GL gl, GLU glu, int width, int height) {
        calcRect();
        if(this.fillColor.getA()!=1||this.strokeColor.getA()!=1)
            gl.glDisable(GL.GL_DEPTH_TEST);

        if (this.fill) {
            this.fillColor.setup(gl);
            gl.glBegin(GL.GL_QUADS);
            gl.glVertex2d(x1, y1);
            gl.glVertex2d(x2, y2);
            gl.glVertex2d(x3, y3);
            gl.glVertex2d(x4, y4);
            gl.glEnd();
        }

        if (this.stroke) {
            gl.glLineWidth(this.strokeWidth);
            this.strokeColor.setup(gl);
            gl.glBegin(GL.GL_LINE_STRIP);
            gl.glVertex2d(x1, y1);
            gl.glVertex2d(x2, y2);
            gl.glVertex2d(x3, y3);
            gl.glVertex2d(x4, y4);
            gl.glVertex2d(x1, y1);
            gl.glEnd();
        }
        
        if(this.fillColor.getA()!=1||this.strokeColor.getA()!=1)
            gl.glEnable(GL.GL_DEPTH_TEST);
    }
}