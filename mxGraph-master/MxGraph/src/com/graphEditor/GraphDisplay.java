package com.graphEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class GraphDisplay extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public ConstructsCollection collection;
	

	public GraphDisplay(ArrayList<Constructs> collection, int index, final mxGraph graph,final mxGraphComponent graphComponent,HashMap<String,mxGeometry> geo) {
		setResizable(true);
		setMaximizable(true);
		setForeground(Color.WHITE);
		setClosable(true);
		setBorder(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);
		contentPane.setBackground(Color.white);
		contentPane.setForeground(Color.white);
		setContentPane(contentPane);
		contentPane.add(graphComponent);
		//GraphEditor editor = new GraphEditor("GraphVisualizer",graphComponent);
		//contentPane.add(editor);
		Object parent = graph.getDefaultParent();
		Layout layout; 
		
	    graph.getModel().beginUpdate();
	    graphComponent.setConnectable(true);
	    graphComponent.getConnectionHandler().setCreateTarget(true);
	    
		try {
			if(Editor.blackWhite == true){
				layout = new Layout(true);
			}else{
				layout = new Layout();
			}

			if (Editor.layout == true) {
				layout.makeGraphLayout(index, collection, parent, graph);
			} else {

				layout.makeGraph(index, collection, parent, graph);
				layout.nestedLayout(parent, graph, collection, index);
				Labels label = new Labels();
				label.applyEdgeDefaults(graph);
				graph.setKeepEdgesInForeground(true);
				graph.setVertexLabelsMovable(true);
				new RelationTracker(graphComponent,Color.black);
				
			}

			
		} finally

		{
		
			
			graph.getModel().endUpdate();
			
		}
	  
		
		graphComponent.setPanning(false);
		graphComponent.setPreferPageSize(true);
		graphComponent.getViewport().setOpaque(true);
		graphComponent.getViewport().setBackground(Color.WHITE);
		graphComponent.getViewport().setMaximumSize(contentPane.getSize());
		graphComponent.getViewport().setSize(contentPane.getSize());
		getContentPane().add(graphComponent, BorderLayout.CENTER);
		
		
		

	
	}	
	
	 
	    }
	

