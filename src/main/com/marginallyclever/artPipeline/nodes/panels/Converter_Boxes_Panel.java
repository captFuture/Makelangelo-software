package com.marginallyclever.artPipeline.nodes.panels;

import java.beans.PropertyChangeEvent;

import com.marginallyclever.artPipeline.nodes.Converter_Boxes;
import com.marginallyclever.convenience.nodes.NodePanel;
import com.marginallyclever.convenience.select.SelectSlider;
import com.marginallyclever.makelangelo.Translator;

/**
 * GUI for {@link Converter_Boxes}
 * @author Dan Royer
 *
 */
public class Converter_Boxes_Panel extends NodePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Converter_Boxes converter;
	private SelectSlider boxSize;
	private SelectSlider cutoff;
	
	public Converter_Boxes_Panel(Converter_Boxes arg0) {
		super();
		this.converter=arg0;
		
		add(boxSize = new SelectSlider(Translator.get("BoxGeneratorMaxSize"),40,1,converter.getBoxMasSize()));
		add(cutoff = new SelectSlider(Translator.get("BoxGeneratorCutoff"),255,0,converter.getCutoff()));
		finish();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		converter.setBoxMaxSize(boxSize.getValue());
		converter.setCutoff(cutoff.getValue());
		converter.restart();
	}
}
