package com.graphEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileFilter;

import org.w3c.dom.Document;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.canvas.mxSvgCanvas;
import com.mxgraph.examples.swing.GraphEditor;
import com.mxgraph.examples.swing.editor.DefaultFileFilter;
import com.mxgraph.examples.swing.editor.EditorMenuBar;
import com.mxgraph.io.mxCodec;
import com.mxgraph.io.mxGdCodec;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxCellRenderer.CanvasFactory;
import com.mxgraph.util.mxDomUtils;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
public class Editor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList<Object> list;
	private JScrollPane listScroller;
	private Parser parser;
	private GraphDisplay gd;
	private ConstructsCollection collection;
	private Util util;

	private static int indexx = 0;
	public static boolean layout;
	public static boolean active;
	public static boolean blackWhite;
	public static String constructName;
	private JPanel panel_1;
	private JPanel panel_3;
	public HashMap<String, mxGeometry> geo;
	private mxGraph graph;
	private mxGraphComponent graphComponent;
	private GraphIO io;
	public GraphEditor editor;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {

					Editor frame = new Editor();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Editor() {
		super("Graph Visualizer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1058, 865);
		geo = new HashMap<String, mxGeometry>();

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnLoadFile = new JMenu("File");
		menuBar.add(mnLoadFile);

		final JMenuItem mntmLoadFile = new JMenuItem("Open File");
		mntmLoadFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				collection.removeObjects();

				try {
					final JFileChooser fc = new JFileChooser();
					File file = new File("");

					if (e.getSource() == mntmLoadFile) {
						int returnVal = fc.showOpenDialog(Editor.this);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							file = fc.getSelectedFile();
							System.out.println("Opening: " + file.getName()
									+ ".");
							System.out.println("File opned is "
									+ file.getAbsolutePath());

							
							Util.removeDb();
							parser.loadDataSet(file);
							parser.addObject(collection);
							createList();

						} else {
							System.out
									.println("Open command cancelled by user.");
						}

					}

				}

				catch (Exception eks) {
					eks.printStackTrace();
					JOptionPane.showMessageDialog(contentPane,
							"Cant read! Try owl/txt/xml ", "Invalid filetype",
							JOptionPane.WARNING_MESSAGE);
				}

			}

		});

		mnLoadFile.add(mntmLoadFile);
		JMenuItem mntmSaveConstruct = new JMenuItem("Save Construct");
		mntmSaveConstruct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileFilter selectedFilter = null;
				DefaultFileFilter xmlPngFilter = new DefaultFileFilter(".png",
						"PNG+XML " + mxResources.get("file") + " (.png)");
				FileFilter vmlFileFilter = new DefaultFileFilter(".html",
						"VML " + mxResources.get("file") + " (.html)");
				String filename = null;
				boolean dialogShown = false;

				String wd = " THIS FILE";
				JFileChooser fc = new JFileChooser(wd);
				FileFilter defaultFilter = xmlPngFilter;
				fc.addChoosableFileFilter(defaultFilter);
				fc.addChoosableFileFilter(new DefaultFileFilter(".mxe",
						"mxGraph Editor " + mxResources.get("file") + " (.mxe)"));
				fc.addChoosableFileFilter(new DefaultFileFilter(".txt",
						"Graph Drawing " + mxResources.get("file") + " (.txt)"));
				fc.addChoosableFileFilter(new DefaultFileFilter(".svg", "SVG "
						+ mxResources.get("file") + " (.svg)"));
				fc.addChoosableFileFilter(vmlFileFilter);
				fc.addChoosableFileFilter(new DefaultFileFilter(".html",
						"HTML " + mxResources.get("file") + " (.html)"));
				Object[] imageFormats = ImageIO.getReaderFormatNames();
				HashSet<String> formats = new HashSet<String>();
				for (int i = 0; i < imageFormats.length; i++) {
					String ext = imageFormats[i].toString().toLowerCase();
					formats.add(ext);
				}

				imageFormats = formats.toArray();

				for (int i = 0; i < imageFormats.length; i++) {
					String ext = imageFormats[i].toString();
					fc.addChoosableFileFilter(new DefaultFileFilter("." + ext,
							ext.toUpperCase() + " " + mxResources.get("file")
									+ " (." + ext + ")"));
				}
				fc.addChoosableFileFilter(new DefaultFileFilter.ImageFileFilter(
						mxResources.get("allImages")));
				fc.setFileFilter(defaultFilter);
				int rc = fc.showDialog(null, mxResources.get("save"));
				dialogShown = true;

				if (rc != JFileChooser.APPROVE_OPTION) {
					return;
				}

				filename = fc.getSelectedFile().getAbsolutePath();
				selectedFilter = fc.getFileFilter();

				if (selectedFilter instanceof DefaultFileFilter) {
					String ext = ((DefaultFileFilter) selectedFilter)
							.getExtension();

					if (!filename.toLowerCase().endsWith(ext)) {
						filename += ext;
					}
				}

				if (new File(filename).exists()
						&& JOptionPane.showConfirmDialog(graphComponent,
								mxResources.get("overwriteExistingFile")) != JOptionPane.YES_OPTION) {
					return;
				}

				try {
					String ext = filename.substring(filename.lastIndexOf('.') + 1);

					if (ext.equalsIgnoreCase("svg")) {
						mxSvgCanvas canvas = (mxSvgCanvas) mxCellRenderer
								.drawCells(graph, null, 1, null,
										new CanvasFactory() {
											@Override
											public mxICanvas createCanvas(
													int width, int height) {
												mxSvgCanvas canvas = new mxSvgCanvas(
														mxDomUtils
																.createSvgDocument(
																		width,
																		height));
												canvas.setEmbedded(true);

												return canvas;
											}

										});
						mxUtils.writeFile(
								mxXmlUtils.getXml(canvas.getDocument()),
								filename);
					} else if (selectedFilter == vmlFileFilter) {
						mxUtils.writeFile(mxXmlUtils.getXml(mxCellRenderer
								.createVmlDocument(graph, null, 1, null, null)
								.getDocumentElement()), filename);
					} else if (ext.equalsIgnoreCase("html")) {
						mxUtils.writeFile(mxXmlUtils.getXml(mxCellRenderer
								.createHtmlDocument(graph, null, 1, null, null)
								.getDocumentElement()), filename);
					} else if (ext.equalsIgnoreCase("mxe")
							|| ext.equalsIgnoreCase("xml")) {
						mxCodec codec = new mxCodec();
						String xml = mxXmlUtils.getXml(codec.encode(graph
								.getModel()));

						mxUtils.writeFile(xml, filename);

					} else if (ext.equalsIgnoreCase("txt")) {
						String content = mxGdCodec.encode(graph);

						mxUtils.writeFile(content, filename);
					} else {
						Color bg = null;

						if ((!ext.equalsIgnoreCase("gif") && !ext
								.equalsIgnoreCase("png"))
								|| JOptionPane.showConfirmDialog(
										graphComponent, mxResources
												.get("transparentBackground")) != JOptionPane.YES_OPTION) {
							bg = graphComponent.getBackground();
						}

						if (selectedFilter == xmlPngFilter
								&& ext.equalsIgnoreCase("png") && !dialogShown) // )
						{

							io.saveXmlPng(graphComponent, graph, "text.mxe",
									Color.WHITE);
						} else {
							BufferedImage image = mxCellRenderer
									.createBufferedImage(graph, null, 1, bg,
											graphComponent.isAntiAlias(), null,
											graphComponent.getCanvas());

							if (image != null) {
								ImageIO.write(image, ext, new File(filename));
							} else {
								JOptionPane.showMessageDialog(graphComponent,
										mxResources.get("noImageData"));
							}
						}
					}
				} catch (Throwable ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(graphComponent,
							ex.toString(), mxResources.get("error"),
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		JMenuItem mntmLoadConstruct = new JMenuItem("Load Construct");
		mntmLoadConstruct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String lastDir = null;
				if (graph != null) {
					String wd = (lastDir != null) ? lastDir : System
							.getProperty("user.dir");

					JFileChooser fc = new JFileChooser(wd);
					DefaultFileFilter defaultFilter = new DefaultFileFilter(
							".mxe", mxResources.get("allSupportedFormats")
									+ " (.mxe, .png, .vdx)") {

						@Override
						public boolean accept(File file) {
							String lcase = file.getName().toLowerCase();

							return super.accept(file) || lcase.endsWith(".png")
									|| lcase.endsWith(".vdx");
						}
					};
					fc.addChoosableFileFilter(defaultFilter);

					fc.addChoosableFileFilter(new DefaultFileFilter(".mxe",
							"mxGraph Editor " + mxResources.get("file")
									+ " (.mxe)"));
					fc.addChoosableFileFilter(new DefaultFileFilter(".png",
							"PNG+XML  " + mxResources.get("file") + " (.png)"));

					fc.addChoosableFileFilter(new DefaultFileFilter(".vdx",
							"XML Drawing  " + mxResources.get("file")
									+ " (.vdx)"));

					// Adds file filter for GD import
					fc.addChoosableFileFilter(new DefaultFileFilter(".txt",
							"Graph Drawing  " + mxResources.get("file")
									+ " (.txt)"));

					fc.setFileFilter(defaultFilter);

					int rc = fc.showDialog(null, mxResources.get("openFile"));

					if (rc == JFileChooser.APPROVE_OPTION) {
						lastDir = fc.getSelectedFile().getParent();

						try {
							if (fc.getSelectedFile().getAbsolutePath()
									.toLowerCase().endsWith(".png")) {
								// openXmlPng(editor, fc.getSelectedFile());
								io.openXmlPng(graph, fc.getSelectedFile(), fc
										.getSelectedFile().getAbsolutePath(),
										contentPane);
							} else if (fc.getSelectedFile().getAbsolutePath()
									.toLowerCase().endsWith(".txt")) {
								io.openGD(graph, fc.getSelectedFile(), mxUtils
										.readFile(fc.getSelectedFile()
												.getAbsolutePath()),
										contentPane);
							} else {
								Document document = mxXmlUtils.parseXml(mxUtils
										.readFile(fc.getSelectedFile()
												.getAbsolutePath()));

								mxCodec codec = new mxCodec(document);
								codec.decode(document.getDocumentElement(),
										graph.getModel());

							}
						} catch (IOException ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(graphComponent,
									ex.toString(), mxResources.get("error"),
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		mnLoadFile.add(mntmLoadConstruct);
		mnLoadFile.add(mntmSaveConstruct);

		JMenuItem mntmSaveOntology = new JMenuItem("Save Ontology");
		mntmSaveOntology.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");
				File file = null;
				int userSelection = fileChooser.showSaveDialog(Editor.this);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					System.out.println("Save as file: "
							+ file.getAbsolutePath());
				}
				try {
					parser.saveModel(file);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnLoadFile.add(mntmSaveOntology);

		JMenu mnLayout = new JMenu("Layout");
		menuBar.add(mnLayout);

		JMenuItem mntmStandardLayout = new JMenuItem("Standard Layout");
		mntmStandardLayout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layout = true;
				initGraphDisplay();
			}
		});

		JMenuItem mntmNestedLayout = new JMenuItem("Nested Hierarchical Layout");
		mntmNestedLayout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layout = false;
				initGraphDisplay();
			}
		});
		mnLayout.add(mntmNestedLayout);
		mnLayout.add(mntmStandardLayout);

		JMenuItem mntmHorizontalTreeLayout = new JMenuItem(
				"Horizontal Tree Layout");
		mntmHorizontalTreeLayout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graph.getModel().beginUpdate();
				mxCompactTreeLayout tree = new mxCompactTreeLayout(graph, true);
				Object[] cell = graphComponent.getGraph().getChildCells(
						graph.getDefaultParent());
				if (cell.length > 1) {
					for (Object obj : cell) {
						tree.execute(obj);
						tree.setLevelDistance(20);
						tree.setNodeDistance(20);

					}
				} else {
					tree.execute(cell[0]);
					// String t = (((mxCell) cell[0]).getId());
					tree.setVertexLocation(cell[0], 0, 0);
				}
				graph.getModel().endUpdate();
			}
		});
		mnLayout.add(mntmHorizontalTreeLayout);

		JMenuItem mntmVerticalTreeLayout = new JMenuItem("Vertical Tree Layout");
		mntmVerticalTreeLayout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				graph.getModel().beginUpdate();
				mxCompactTreeLayout tree = new mxCompactTreeLayout(graph, false);
				Object[] cell = graphComponent.getGraph().getChildCells(
						graph.getDefaultParent());
				tree.execute(cell[0]);
				tree.setHorizontal(false);
				tree.setVertexLocation(cell[0], 0, 0);
				graph.getModel().endUpdate();
			}
		});
		mnLayout.add(mntmVerticalTreeLayout);

		JMenu mnLabels = new JMenu("Labels");
		menuBar.add(mnLabels);

		JMenuItem mntmHideLabels = new JMenuItem("Hide Labels");
		mntmHideLabels.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graph.getModel().beginUpdate();
				graph.setLabelsVisible(false);
				graph.getModel().endUpdate();
				graph.refresh();
			}
		});

		JMenuItem mntmDefaultLabels = new JMenuItem("Default Labels");
		mntmDefaultLabels.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Labels label = new Labels();
				graph.getModel().beginUpdate();
				label.applyEdgeDefaults(graph);
				graph.getModel().endUpdate();
				graph.refresh();
			}
		});
		mnLabels.add(mntmDefaultLabels);

		JMenuItem mntmSimpleLabels = new JMenuItem("Simple Labels");
		mntmSimpleLabels.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Labels label = new Labels();
				graph.getModel().beginUpdate();
				label.basicEdge(graph);
				graph.getModel().endUpdate();
				graph.refresh();
			}
		});
		mnLabels.add(mntmSimpleLabels);
		mnLabels.add(mntmHideLabels);

		JMenuItem mntmShowLabels = new JMenuItem("Show Labels");
		mntmShowLabels.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graph.getModel().beginUpdate();
				graph.setLabelsVisible(true);
				graph.getModel().endUpdate();
				graph.refresh();
			}
		});
		mnLabels.add(mntmShowLabels);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmEditGraph = new JMenuItem("Edit graph");
		mntmEditGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphEditor edit = new GraphEditor("GraphVisualizer",graphComponent);
				edit.createFrame(new EditorMenuBar(edit)).setVisible(true);

			}
		});
		mnEdit.add(mntmEditGraph);

		JMenu mnOntology = new JMenu("Ontology");
		menuBar.add(mnOntology);

		JMenuItem mntmAddToOntology = new JMenuItem("Add to ontology");
		mntmAddToOntology.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Util util = new Util();
				util.dfs(graph.getDefaultParent(), graph);
				parser.addXmlConstructToModel(constructName,
						util.getVertecies(), graphComponent);

				JOptionPane.showMessageDialog(contentPane, constructName
						+ " modification added to ontology!", "",
						JOptionPane.INFORMATION_MESSAGE);

			}
		});
		mnOntology.add(mntmAddToOntology);

		JMenuItem mntmGetFromOntology = new JMenuItem("Get from ontology");
		mntmGetFromOntology.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Util util = new Util();
				util.dfs(graph.getDefaultParent(), graph);
				geo = parser.getGeometries(util.getVertecies());

				for (Entry<String, mxGeometry> entry : geo.entrySet()) {
					mxCell test = (mxCell) ((mxGraphModel) graph.getModel())
							.getCell(Util.splitLabel(entry.getKey()));
					if (test != null)
						graph.getModel().setGeometry(test, entry.getValue());
				}
			}
		});
		mnOntology.add(mntmGetFromOntology);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(2, 2, 2, 2));
		setContentPane(contentPane);
		collection = new ConstructsCollection();
		parser = new Parser();

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 2, 0, new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignOnBaseline(true);
		panel.setBackground(SystemColor.inactiveCaption);

		JLabel lblGraphVisualizer = new JLabel("Graph Visualizer");
		lblGraphVisualizer.setHorizontalAlignment(SwingConstants.CENTER);
		lblGraphVisualizer.setFont(new Font("Segoe UI", Font.BOLD, 43));
		panel.add(lblGraphVisualizer);

		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane
				.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1038,
						Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 1038,
						Short.MAX_VALUE)
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addComponent(panel_3,
										GroupLayout.DEFAULT_SIZE, 1028,
										Short.MAX_VALUE).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 81,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 81,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 51,
								Short.MAX_VALUE)
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 478,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(
				Alignment.LEADING).addGap(0, 1014, Short.MAX_VALUE));
		gl_panel_3.setVerticalGroup(gl_panel_3.createParallelGroup(
				Alignment.LEADING).addGap(0, 512, Short.MAX_VALUE));
		panel_3.setLayout(gl_panel_3);
		list = new JList<Object>();
		list.setBorder(UIManager.getBorder("TextField.border"));
		list.setBounds(293, 108, 150, 150);
		list.setBackground(Color.WHITE);
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 40));
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		final JToggleButton tglbtnBlack = new JToggleButton("Color");
		tglbtnBlack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (tglbtnBlack.isSelected()) {
					blackWhite = true;
					tglbtnBlack.setText("Black");
				} else {
					blackWhite = false;
					tglbtnBlack.setText("Color");
				}
			}
		});
		panel_1.add(tglbtnBlack);
		panel_1.add(listScroller);
		list.updateUI();
		contentPane.setLayout(gl_contentPane);

		

	}

	public void createList() {
		list.setVisible(false);
		listScroller.setVisible(false);
		list = null;
		listScroller = null;
		list = new JList<Object>(parser.getConstructList().toArray());
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = list.getSelectedIndex();
					setTextCurrent(list.getSelectedValue().toString());
					indexx = index;
					initGraphDisplay();

					util = new Util();
					util.dfs(graph.getDefaultParent(), graph);
					geo = parser.getGeometries(util.getVertecies());

					if (geo != null) {
						for (Entry<String, mxGeometry> entry : geo.entrySet()) {
							mxCell test = (mxCell) ((mxGraphModel) graph
									.getModel()).getCell(Util.splitLabel(entry
									.getKey()));
							if (test != null)
								graph.getModel().setGeometry(test,
										entry.getValue());
						}

					}
				}

			}
		});

		list.setBorder(UIManager.getBorder("TextField.border"));
		list.setBounds(293, 108, 150, 150);
		list.setBackground(Color.WHITE);
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		listScroller.setBounds(260, 108, 215, 100);
		panel_1.add(listScroller);
		list.updateUI();
		initGraphDisplay();

	}

	public void initGraphDisplay() {
		if (gd != null) {
			gd.dispose();
		}
		graph = new mxGraph();
		graphComponent = new mxGraphComponent(graph);
		gd = new GraphDisplay(collection.getConstructList(), indexx, graph,
				graphComponent, geo);
		gd.setSize(contentPane.getWidth() - 40, contentPane.getHeight() - 150);
		gd.setVisible(true);
		panel_3.add(gd);

	}

	public void setTextCurrent(String text) {
		constructName = text;
	}
}
