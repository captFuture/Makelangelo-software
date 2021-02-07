package com.marginallyclever.artPipeline.nodes.panels;

import java.beans.PropertyChangeEvent;

import com.marginallyclever.artPipeline.nodes.fractals.Generator_Dragon;
import com.marginallyclever.artPipeline.nodes.fractals.Generator_FibonacciSpiral;
import com.marginallyclever.convenience.nodes.NodePanel;
import com.marginallyclever.convenience.select.SelectReadOnlyText;
import com.marginallyclever.convenience.select.SelectSlider;
import com.marginallyclever.makelangelo.Translator;

/**
 * Panel for {@link Generator_FibonacciSpiral}
 * @author Dan Royer
 *
 */
public class Generator_FibonacciSpiral_Panel extends NodePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SelectSlider fieldOrder;
	private Generator_FibonacciSpiral generator;
	
	public Generator_FibonacciSpiral_Panel(Generator_FibonacciSpiral generator_FibonacciSpiral) {
		super();
		this.generator = generator_FibonacciSpiral;
		
		add(fieldOrder = new SelectSlider(Translator.get("HilbertCurveOrder"),16,0,Generator_Dragon.getOrder()));
		add(new SelectReadOnlyText("<a href='https://en.wikipedia.org/wiki/Fibonacci_number'>Learn more</a>"));
		finish();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		int newOrder = ((Number)fieldOrder.getValue()).intValue();
		if(newOrder<3) newOrder=1;
		
		if(newOrder != Generator_FibonacciSpiral.getOrder()) {
			Generator_FibonacciSpiral.setOrder(newOrder);
			generator.restart();
		}
	}
}
