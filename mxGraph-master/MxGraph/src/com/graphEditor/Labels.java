package com.graphEditor;

import java.util.HashMap;
import java.util.Map;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class Labels {

	public void basicEdge(mxGraph graph) {
		Map<String, Object> edge = new HashMap<String, Object>();
		edge.put(mxConstants.STYLE_STROKECOLOR, "#000000");
		edge.put(mxConstants.STYLE_FONTCOLOR, "#446299");
		edge.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
		edge.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
		edge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
		edge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);

		mxStylesheet edgeStyle = new mxStylesheet();
		mxStylesheet vertexStyle = new mxStylesheet();
		graph.setStylesheet(vertexStyle);
		edgeStyle.setDefaultEdgeStyle(edge);
		graph.setStylesheet(edgeStyle);
	}

	public void applyEdgeDefaults(mxGraph graph) {

		Map<String, Object> edge = new HashMap<String, Object>();
		edge.put(mxConstants.STYLE_ROUNDED, true);
		edge.put(mxConstants.STYLE_ORTHOGONAL, false);
		edge.put(mxConstants.STYLE_EDGE, "elbowEdgeStyle");
		edge.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
		edge.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
		edge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
		edge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
		edge.put(mxConstants.STYLE_STROKECOLOR, "#000000");
		edge.put(mxConstants.STYLE_FONTCOLOR, "#446299");

		Map<String, Object> vertex = new HashMap<String, Object>();
		mxStylesheet edgeStyle = new mxStylesheet();
		mxStylesheet vertexStyle = new mxStylesheet();
		vertexStyle.setDefaultVertexStyle(vertex);
		graph.setStylesheet(vertexStyle);
		edgeStyle.setDefaultEdgeStyle(edge);
		graph.setStylesheet(edgeStyle);
	}

}
