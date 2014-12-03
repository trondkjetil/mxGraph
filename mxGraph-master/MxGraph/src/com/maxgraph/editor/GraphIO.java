package com.maxgraph.editor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.mxgraph.io.mxCodec;
import com.mxgraph.io.mxGdCodec;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.util.png.mxPngEncodeParam;
import com.mxgraph.util.png.mxPngImageEncoder;
import com.mxgraph.view.mxGraph;

public class GraphIO {

	// protected void openXmlPng(BasicGraphEditor editor, File file)throws
	// IOException{
	protected void openXmlPng(mxGraph graph, File file, String gdText,
			JPanel contentPane) throws IOException {
		// protected void openGD(BasicGraphEditor editor, File file, String
		// gdText){
		// mxGraph graph = editor.getGraphComponent().getGraph();
		// Replaces file extension with .mxe
		String filename = file.getName();
		filename = filename.substring(0, filename.length() - 4) + ".mxe";

		if (new File(filename).exists()
				&& JOptionPane.showConfirmDialog(contentPane,
						mxResources.get("overwriteExistingFile")) != JOptionPane.YES_OPTION) {
			return;
		}

		((mxGraphModel) graph.getModel()).clear();
		mxGdCodec.decode(gdText, graph);

		// graph.zoomAndCenter();
		// editor.setCurrentFile(new File(lastDir + "/" + filename));
	}

	protected void openGD(mxGraph graph, File file, String gdText,
			JPanel contentPane) throws IOException {

		// protected void openGD(BasicGraphEditor editor, File file, String
		// gdText){
		// mxGraph graph = editor.getGraphComponent().getGraph();

		// Replaces file extension with .mxe
		String filename = file.getName();
		filename = filename.substring(0, filename.length() - 4) + ".mxe";

		if (new File(filename).exists()
				&& JOptionPane.showConfirmDialog(contentPane,
						mxResources.get("overwriteExistingFile")) != JOptionPane.YES_OPTION) {
			return;
		}

		((mxGraphModel) graph.getModel()).clear();
		mxGdCodec.decode(gdText, graph);

		// graph.zoomAndCenter();
		// editor.setCurrentFile(new File(lastDir + "/" + filename));
	}

	// save graph as image.
	// protected void saveXmlPng(BasicGraphEditor editor, String filename, Color
	// bg) throws IOException{
	protected void saveXmlPng(mxGraphComponent graphComponent, mxGraph graph,
			String filename, Color bg) throws IOException {
		// mxGraphComponent graphComponent = editor.getGraphComponent();
		// mxGraph graph = graphComponent.getGraph();

		// Creates the image for the PNG file
		BufferedImage image = mxCellRenderer.createBufferedImage(graph, null,
				1, bg, graphComponent.isAntiAlias(), null,
				graphComponent.getCanvas());

		// Creates the URL-encoded XML data
		mxCodec codec = new mxCodec();
		String xml = URLEncoder.encode(
				mxXmlUtils.getXml(codec.encode(graph.getModel())), "UTF-8");
		mxPngEncodeParam param = mxPngEncodeParam.getDefaultEncodeParam(image);
		param.setCompressedText(new String[] { "mxGraphModel", xml });

		// Saves as a PNG file
		FileOutputStream outputStream = new FileOutputStream(new File(filename));
		try {
			mxPngImageEncoder encoder = new mxPngImageEncoder(outputStream,
					param);

			if (image != null) {
				encoder.encode(image);

				// editor.setModified(false);
				// editor.setCurrentFile(new File(filename));
			} else {
				JOptionPane.showMessageDialog(graphComponent,
						mxResources.get("noImageData"));
			}
		} finally {
			outputStream.close();
			System.out.println("Image file created!");
		}

	}

}
