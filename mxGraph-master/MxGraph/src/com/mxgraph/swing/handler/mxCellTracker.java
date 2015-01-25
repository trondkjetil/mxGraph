/**
 * $Id: mxCellTracker.java,v 1.1 2012/11/15 13:26:44 gaudenz Exp $
 * Copyright (c) 2008, Gaudenz Alder
 */
package com.mxgraph.swing.handler;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxRectangle;

/**
 * Event handler that highlights cells. Inherits from mxCellMarker.
 */
public class mxCellTracker extends mxCellMarker implements MouseListener,
		MouseMotionListener
{

	private ArrayList<Object> parentS = new ArrayList<Object>();
	private HashMap<Object,Object> originalParent = new HashMap<Object,Object>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 7372144804885125688L;

	/**
	 * Constructs an event handler that highlights cells.
	 */
	public mxCellTracker(mxGraphComponent graphComponent, Color color)
	{
		super(graphComponent, color);

		graphComponent.getGraphControl().addMouseListener(this);
		graphComponent.getGraphControl().addMouseMotionListener(this);
	}

	/**
	 * 
	 */
	public void destroy()
	{
		graphComponent.getGraphControl().removeMouseListener(this);
		graphComponent.getGraphControl().removeMouseMotionListener(this);
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{

		// empty
	
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e)
	{
		// empty
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e)
	{
		// empty
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		// empty
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		reset();
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e){
		
		try{
			
			double childX =0;
			double childY =0;
			double parentX=0;
			double parentY=0;
			double childW=0;
			double childH=0;
			double parentW=0;
			double parentH=0;
			
			Object child = graphComponent.getGraph().getSelectionCell();
			Object parent = graphComponent.getGraph().getModel().getParent(child);
			
			/*
			if(parentS.isEmpty()){
				parentS.add(parent);
			}else{
				parent = parentS.get(0);
			}
		
			*/
			if(originalParent.isEmpty()){
				originalParent.put(child, parent);
			}else{
				parent = originalParent.get(child);
			}
				
			
			 mxRectangle rec = graphComponent.getGraph().getCellBounds(graphComponent.getGraph().getSelectionCell());
			 childX  = rec.getX(); 
			 childY = rec.getY();
			 childW = rec.getWidth();
			 childH = rec.getHeight();
			 
		//mxRectangle rec2 = graphComponent.getGraph().getCellBounds(graphComponent.getGraph().getModel().getParent(child)); 
		
		//denne er fikset på	 //mxRectangle rec2 =  graphComponent.getGraph().getCellBounds(parentS.get(0));
			 mxRectangle rec2 =  graphComponent.getGraph().getCellBounds(originalParent.get(child));
			
			 if(rec2 != null){
			 parentX = rec2.getX();
			 parentY = rec2.getY();
			 parentH=rec2.getHeight();
			 parentW=rec2.getWidth();
			 
			 
			// parentS.add(parent);
			 }
			 
				int cX = (int) childX;
				int cY = (int) childY;
				int pX= (int) parentX;
				int pY= (int) parentY;
				int cW = (int) childW;
				int cH= (int) childH;
				int pW= (int) parentW;
				int pH= (int) parentH;
			 
				Rectangle cr = new Rectangle(cX,cY,cW,cH);
				Rectangle pr = new Rectangle(pX,pY,pW,pH);
				
			 System.out.println("Child: " + childX +" " + childY);
			 System.out.println("Parent: " + parentX +" " + parentY);
			 System.out.println("Childs current parent" + graphComponent.getGraph().getModel().getParent(child));
			 System.out.println(graphComponent.getGraph().getSelectionCell() + " CELLEN SOM ER AKTIV!(Child)");
		
		
			 
			 if (!pr.contains(cr)) {
					System.out.println("INSIDE IF EDGE!!");
					//graphComponent.getGraph().insertEdge(null, null, null,parentS.get(0),child);
					graphComponent.getGraph().insertEdge(null, null, null,originalParent.get(child),child);
					 
				} else if (pr.contains(cr)) {
					Object[] edges = graphComponent.getGraph().getEdgesBetween(parent, child);
					for (Object edge : edges) {
						graphComponent.getGraph().getModel().remove(edge);
					}
				}
			 
			}catch(Exception en){
				en.printStackTrace();
			}

	}
	


	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{
		process(e);
	}

}
