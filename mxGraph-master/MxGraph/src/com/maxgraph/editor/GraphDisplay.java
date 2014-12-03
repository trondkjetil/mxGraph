package com.maxgraph.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class GraphDisplay extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public ConstructsCollection collection;
	public boolean layoutOn;
	public boolean active;
	
	public GraphDisplay(ArrayList<Constructs> collection, int index, final mxGraph graph,final mxGraphComponent graphComponent) {
		setResizable(true);
		setMaximizable(true);
		setForeground(Color.WHITE);
		setClosable(true);
		setBorder(null);
		setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);
		contentPane.setBackground(Color.white);
		contentPane.setForeground(Color.white);
		
		
		/*
	    editor = new GraphEditor(graphComponent);
	 	editor.createFrame(new EditorMenuBar(editor)).setVisible(true);
	    editor.setVisible(true);
	    contentPane.add(editor);
		*/
		setContentPane(contentPane);
		
		
		//final mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		Layout layout = new Layout();
		
	    graph.getModel().beginUpdate();
		try {

			if (Editor.layout == true) {
				layout.makeGraphLayout(index, collection, parent, graph);
			} else {

				layout.makeGraph(index, collection, parent, graph);
				layout.nestedLayout(parent, graph, collection, index);
				graph.setKeepEdgesInForeground(true);
				 
			}

			if (Editor.active == true) {
				graph.setLabelsVisible(false);
			}else{
				graph.setLabelsVisible(true);
			}

		} finally

		{
			graph.getModel().endUpdate();
		}
	  
		/*
		contentPane.removeAll();
		contentPane.repaint();
		editor = new GraphEditor(graphComponent);
	 	editor.createFrame(new EditorMenuBar(editor)).setVisible(true);
	    contentPane.add(editor);
		setContentPane(contentPane);
		
		*/
		
		
		
		graphComponent.setPanning(false);
		graphComponent.setPreferPageSize(true);
		graphComponent.getViewport().setOpaque(true);
		graphComponent.getViewport().setBackground(Color.WHITE);
		graphComponent.getViewport().setMaximumSize(contentPane.getSize());
		graphComponent.getViewport().setSize(contentPane.getSize());
		getContentPane().add(graphComponent, BorderLayout.CENTER);
		

	
	}	
}
