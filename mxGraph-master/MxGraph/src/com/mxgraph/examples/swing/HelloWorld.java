package com.mxgraph.examples.swing;

import java.awt.Color;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class HelloWorld extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2707712944901661771L;

	public HelloWorld() {
		super("Hello, World!");
		setBackground(Color.WHITE);

		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		int tall =2;
		char ch = 'a';
		
		try{
			Object v1 = graph.insertVertex(parent, null, tall, 20, 20, 80,
					30);
			Object v2 = graph.insertVertex(parent, null, ch, 240, 150,
					80, 30);
			graph.addCell(v1, parent);
			graph.addCell(v2, parent);
			graph.insertEdge(parent, null, "Edge1", v1, v2);
		
		}
		finally
		{
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		
		getContentPane().add(graphComponent);
		
	}

	public static void main(String[] args)
	{
		HelloWorld frame = new HelloWorld();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 8);
		frame.setVisible(true);
		
	}

}
