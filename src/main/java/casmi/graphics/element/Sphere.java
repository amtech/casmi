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
  
package casmi.graphics.element;

import javax.media.opengl.GL;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * Sphere class.
 * Wrap JOGL and make it easy to use.
 * 
 * @author Y. Ban
 * 
 */
public class Sphere extends Element implements Renderable {
    
    private double r;

    private int slices = 30;
    private int stacks = 30;

    /**
     * Creates a new Sphere object using radius.
     * 
     * @param r
     *           The radius of the Sphere.              
     */
    public Sphere(double r) {
        this.r = r;
    }


    public Sphere(double r, int slices, int stacks) {
        this.r = r;
        this.slices = slices;
        this.stacks = stacks;
    }

    @Override
    public void render(GL gl, GLU glu, int width, int height) {
        
        if( this.fillColor.getA() < 1.0 || this.strokeColor.getA() < 1.0 ) {
            gl.glDisable(GL.GL_DEPTH_TEST);
        }
        
        this.material.setup(gl);
        gl.glPushMatrix();
        
        if (this.fill) {
            this.fillColor.setup(gl);
            drawSolidSphere(glu,(float)r, slices, stacks);
        }
        
        if (this.stroke) {
            this.strokeColor.setup(gl);
            drawWireSphere(glu, (float)r, slices, stacks);
        }

        gl.glPopMatrix();
        
        if( this.fillColor.getA() < 1.0 || this.strokeColor.getA() < 1.0 ) {
            gl.glEnable(GL.GL_DEPTH_TEST);
        }
    }
    
    private GLUquadric quadObj;
    
    private void quadObjInit(GLU glu) {
      if (quadObj == null) {
        quadObj = glu.gluNewQuadric();
      }
      if (quadObj == null) {
        throw new GLException("Out of memory");
      }
    }
    
    private void drawSolidSphere(GLU glu,float radius,int slices,int stacks){
        quadObjInit(glu);
        glu.gluQuadricDrawStyle(quadObj, GLU.GLU_FILL);
        glu.gluQuadricNormals(quadObj, GLU.GLU_SMOOTH);
        glu.gluSphere(quadObj, radius, slices, stacks);   
    }
    
    private void drawWireSphere(GLU glu,float radius,int slices,int stacks){
        quadObjInit(glu);
        glu.gluQuadricDrawStyle(quadObj, GLU.GLU_LINE);
        glu.gluQuadricNormals(quadObj, GLU.GLU_SMOOTH);
        glu.gluSphere(quadObj, radius, slices, stacks);   
    }
}
