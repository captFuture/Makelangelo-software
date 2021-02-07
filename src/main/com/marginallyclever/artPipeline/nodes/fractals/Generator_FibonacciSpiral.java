package com.marginallyclever.artPipeline.nodes.fractals;

import java.util.Stack;

import com.marginallyclever.artPipeline.nodeConnector.NodeConnectorTurtle;
import com.marginallyclever.artPipeline.nodes.panels.Generator_FibonacciSpiral_Panel;
import com.marginallyclever.convenience.log.Log;
import com.marginallyclever.convenience.nodes.Node;
import com.marginallyclever.convenience.nodes.NodeConnectorInt;
import com.marginallyclever.convenience.nodes.NodePanel;
import com.marginallyclever.convenience.turtle.Turtle;
import com.marginallyclever.makelangelo.Translator;

/**
 * generates a fibonacci spiral
 * @author dan royer
 *
 */
public class Generator_FibonacciSpiral extends Node {
	// controls complexity of curve
	private NodeConnectorInt inputOrder = new NodeConnectorInt("Generator_FibonacciSpiral.inputOrder",7);
	// results
	private NodeConnectorTurtle outputTurtle = new NodeConnectorTurtle("Generator_FibonacciSpiral.NodeConnectorTurtle");
	
	private float xMax = 100;
	private float yMax = 100;
	private Stack<Integer> fibonacciSequence;
	
	public Generator_FibonacciSpiral() {
		super();
		inputs.add(inputOrder);
		outputs.add(outputTurtle);
	}
	
	@Override
	public String getName() {
		return Translator.get("FibonacciSpiralName");
	}

	@Override
	public NodePanel getPanel() {
		return new Generator_FibonacciSpiral_Panel(this);
	}

	private void buildFibonacciSequence(int order) {
		fibonacciSequence = new Stack<Integer>();
		fibonacciSequence.add(1);
		fibonacciSequence.add(1);
		int a = 1;
		int b = 1;
		int c;
		
		while(order>2) {
			c = a+b;
			fibonacciSequence.add(c);
			a=b;
			b=c;
			order--;
		}
	}

	@Override
	public boolean iterate() {
		Turtle turtle = new Turtle();
		Log.message("xMax="+xMax);
		Log.message("yMax="+yMax);
		
		// build the Fibonacci sequence.
		buildFibonacciSequence(inputOrder.getValue());
		
		// scale the fractal to fit on the page
		// short side
		float s1 = fibonacciSequence.peek();
		Log.message("s1="+s1);
		float scale1 = Math.min(xMax, yMax) * 2.0f / s1;
		// long side
		float s2 = fibonacciSequence.get(fibonacciSequence.size()-2) + s1;
		Log.message("s2="+s2);
		float scale2 = Math.max(xMax, yMax) * 2.0f / s2;

		if(scale1>scale2) scale1=scale2;
		
		turtle = new Turtle();
		
		// move to starting position
		float shortSide = fibonacciSequence.peek() * scale1 /2.0f; 
		Log.message("shortSide="+shortSide);
		if( xMax < yMax ) {
			Log.message("tall thin");
			// tall thin paper, top left corner
			turtle.moveTo(shortSide,yMax);
			turtle.turn(180);
		} else {
			Log.message("short wide");
			// short wide paper, bottom left corner
			turtle.moveTo(-xMax,shortSide);
			turtle.turn(-90);
		}
		
		turtle.penDown();
		
		// do the curve, one square at a time.
		while(!fibonacciSequence.isEmpty()) {
			int o = fibonacciSequence.pop();
			fibonacciCell(turtle,o*scale1);
		}

		outputTurtle.setValue(turtle);
	    return false;
	}


	// L System tree
	private void fibonacciCell(Turtle turtle,float size) {
		// make the square around the cell
		turtle.forward(size);
		turtle.turn(90);
		turtle.forward(size);
		turtle.turn(90);
		double x2 = turtle.getX();
		double y2 = turtle.getY();
		turtle.forward(size);
		turtle.turn(90);
		double x0 = turtle.getX();
		double y0 = turtle.getY();
		turtle.forward(size);
		turtle.turn(90);

		// make the curve
		double x1 = turtle.getX();
		double y1 = turtle.getY();
		
		double dx, dy, px, py, len;
		final int steps = 20;
		int i;
		for(i=0;i<steps;++i) {
			px = (x2-x1) * ((double)i/steps) + x1;
			py = (y2-y1) * ((double)i/steps) + y1;
			dx = px - x0;
			dy = py - y0;
			len = Math.sqrt(dx*dx+dy*dy);
			px = dx*size/len + x0;
			py = dy*size/len + y0;
			turtle.moveTo(px, py);
		}
		turtle.moveTo(x2, y2);
		turtle.turn(90);
	}
}
