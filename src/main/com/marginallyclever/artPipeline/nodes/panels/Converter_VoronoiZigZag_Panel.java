package com.marginallyclever.artPipeline.nodes.panels;

import java.beans.PropertyChangeEvent;

import com.marginallyclever.artPipeline.nodes.voronoi.Converter_VoronoiZigZag;
import com.marginallyclever.convenience.nodes.NodePanel;
import com.marginallyclever.convenience.select.SelectDouble;
import com.marginallyclever.convenience.select.SelectInteger;

/**
 * GUI for {@link Converter_VoronoiZigZag}
 * @author Dan Royer
 *
 */
public class Converter_VoronoiZigZag_Panel extends NodePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5791313991426136610L;
	private SelectInteger numCells;
	private SelectDouble minDotSize;
	private Converter_VoronoiZigZag converter;
	
	public Converter_VoronoiZigZag_Panel(Converter_VoronoiZigZag converter_VoronoiZigZag) {
		super();
		
		converter = converter_VoronoiZigZag;
		
		add(numCells = new SelectInteger("voronoiStipplingCellCount",converter.getNumCells()));
		add(minDotSize = new SelectDouble("voronoiStipplingDotMin",converter.getMinDotSize()));
		finish();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		if( numCells.getValue() != converter.getNumCells() ||
			minDotSize.getValue() != converter.getMinDotSize() ) {
			converter.setNumCells(numCells.getValue());
			converter.setMinDotSize(minDotSize.getValue());
			converter.restart();
		}
	}
}
