
package com.graphEditor;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxCellMarker;
import com.mxgraph.util.mxRectangle;

/**
 * Event handler that highlights cells. Inherits from mxCellMarker.
 */
public class RelationTracker extends mxCellMarker implements MouseListener,
		MouseMotionListener
{
	private HashMap<Object,Object> originalParent = new HashMap<Object,Object>();
	private static final long serialVersionUID = 7372144804885125688L;

	/**
	 * Constructs an event handler that highlights cells.
	 */
	public RelationTracker(mxGraphComponent graphComponent, Color color)
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
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}
	@Override
	public void mousePressed(MouseEvent e)
	{
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
			
				if(originalParent.isEmpty() || originalParent.get(child) == null){
					originalParent.put(child, parent);
				}else
					parent = originalParent.get(child);
				
				 mxRectangle rec = graphComponent.getGraph().getCellBounds(graphComponent.getGraph().getSelectionCell());
				 childX  = rec.getX(); 
				 childY = rec.getY();
				 childW = rec.getWidth();
				 childH = rec.getHeight();
				
				 mxRectangle rec2 =  graphComponent.getGraph().getCellBounds(originalParent.get(child));
				 if(rec2 != null){
				 parentX = rec2.getX();
				 parentY = rec2.getY();
				 parentH=rec2.getHeight();
				 parentW=rec2.getWidth();
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
				 
				 if (!pr.contains(cr)) {
						graphComponent.getGraph().insertEdge(null, null, null,originalParent.get(child),child);		 
					} else if (pr.contains(cr)) {
						Object[] edges = graphComponent.getGraph().getEdgesBetween(parent, child);
						for (Object edge : edges) {
							graphComponent.getGraph().getModel().remove(edge);
						}
					}
				
			}catch(NullPointerException nul){
				
				}catch(Exception en){
					en.printStackTrace();
				}

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	//	reset();
		
	}

	@Override
	public void mouseDragged(MouseEvent e){
	
	}
	@Override
	public void mouseMoved(MouseEvent e)
	{
		process(e);
		
	}

}
