package com.maxgraph.editor;

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
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
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
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxCellRenderer.CanvasFactory;
import com.mxgraph.util.mxDomUtils;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;

public class Editor extends JFrame  {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList<Object> list;
	private JScrollPane listScroller;
	private Parser parser; 
	private GraphDisplay gd;
	private ConstructsCollection collection;
	private static int indexx=0;
	public static boolean layout;
	public static boolean active;
	private JPanel panel_1;
	private JPanel panel_3;
	
	private mxGraph graph;
	private mxGraphComponent graphComponent;
	private GraphIO io;
	 GraphEditor editor;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnLoadFile = new JMenu("File");
		menuBar.add(mnLoadFile);
		
		JMenuItem mntmLoadLocalDatabase = new JMenuItem("Load Local Database");
		mntmLoadLocalDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				collection.removeObjects();
				try {
					parser.loadLocalDb();
					parser.addObject(collection);
					createList();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane,
							"Cant fint local db. Load dataset first!", "Error",
							JOptionPane.WARNING_MESSAGE);
				}

			}
			
		});
		mnLoadFile.add(mntmLoadLocalDatabase);
		
	final	JMenuItem mntmLoadFile = new JMenuItem("Open File");
		mntmLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Util.removeDb();
				collection.removeObjects();
			
				try{
				final JFileChooser fc = new JFileChooser();
				File file = new File("");
				
				if (e.getSource() == mntmLoadFile) {
					int returnVal = fc.showOpenDialog(Editor.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						file = fc.getSelectedFile();
						// This is where a real application would open the file.
						System.out.println("Opening: " + file.getName() + ".");
						System.out.println("File opned is "
								+ file.getAbsolutePath());
						// Load dataset from file
						parser.loadDataSet(file);
						// Swith to local dataset
						parser.loadLocalDb();
						// Creates the contructobjects from the dataset, which makes the graph
						parser.addObject(collection);
						// Creates the Jlist with the constructs
						System.out.println("File loaded!");
						createList();
						
					} else {
						System.out.println("Open command cancelled by user.");
					}
					
				}
					
				}
				
				catch(Exception eks){
					JOptionPane.showMessageDialog(contentPane,
						    "Cant read! Try owl/txt/xml ",
						    "Invalid filetype",
						    JOptionPane.WARNING_MESSAGE);
				}
				
			}

		});
				
				
			
		
		    
		    
		mnLoadFile.add(mntmLoadFile);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(2, 2, 2, 2));
		setContentPane(contentPane);

		collection = new ConstructsCollection();
		parser = new Parser();
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignOnBaseline(true);
		panel.setBackground(SystemColor.inactiveCaption);

		JLabel lblGraphVisualizer = new JLabel("Graph Visualizer");
		lblGraphVisualizer.setHorizontalAlignment(SwingConstants.CENTER);
		lblGraphVisualizer.setFont(new Font("Segoe UI", Font.BOLD, 43));
		panel.add(lblGraphVisualizer);
		
		ButtonGroup bg = new ButtonGroup();
		ButtonGroup bg1 = new ButtonGroup();
		
		 panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		
		 panel_3 = new JPanel();
		 panel_3.setBackground(Color.WHITE);
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 478, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGap(0, 1014, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGap(0, 512, Short.MAX_VALUE)
		);
		panel_3.setLayout(gl_panel_3);
		
		
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Show Labels");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBackground(Color.WHITE);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				active = false;
				initGraphDisplay();
				
			}
		});
		bg.add(rdbtnNewRadioButton);
		
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Hide Labels");
		rdbtnNewRadioButton_1.setBackground(Color.WHITE);
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				active = true;
				initGraphDisplay();
				 
			}
		});
		bg.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnLayout = new JRadioButton("Layout1");
		rdbtnLayout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout = true;
				initGraphDisplay();
				 
			}
		});
		rdbtnLayout.setBackground(Color.WHITE);
		bg1.add(rdbtnLayout);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Layout2");
		rdbtnNewRadioButton_2.setSelected(true);
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout = false;
				initGraphDisplay();
				
			}
		});
		
		rdbtnNewRadioButton_2.setBackground(Color.WHITE);
		bg1.add(rdbtnNewRadioButton_2);
		list = new JList<Object>();
		list.setBorder(UIManager.getBorder("TextField.border"));
		list.setBounds(293, 108, 150, 150);
		list.setBackground(Color.WHITE);
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 40));
		  panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		  panel_1.add(rdbtnNewRadioButton);
		  panel_1.add(rdbtnNewRadioButton_1);
		  panel_1.add(rdbtnLayout);
		  panel_1.add(rdbtnNewRadioButton_2);
		  panel_1.add(listScroller);
		  
		  JButton btnEdit = new JButton("Edit");
		  btnEdit.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
		  		/*
				try
				{
				//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}

			mxSwingConstants.SHADOW_COLOR = Color.LIGHT_GRAY;
			mxConstants.W3C_SHADOWCOLOR = "#D3D3D3";
*/
				
		  		GraphEditor edit = new GraphEditor(graphComponent);
		  		edit.createFrame(new EditorMenuBar(edit)).setVisible(true);
		  		//GraphEditor editor = new GraphEditor();  //(graph,graphComponent);
			//	editor.createFrame(new EditorMenuBar(editor)).setVisible(true);
			
		  	}
		  });
		  panel_1.add(btnEdit);
		  
		  JButton btnSave = new JButton("Save");
		  btnSave.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
		  		
			/*
		  		try {
					io.saveXmlPng(graphComponent,graph, "text.png", Color.WHITE);
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			*/
			//	GraphDisplay editor = new GraphDisplay()// getEditor(e);

			//	if (editor != null){
				//	mxGraphComponent graphComponent = editor.getGraphComponent();
				//	mxGraph graph = graphComponent.getGraph();
					FileFilter selectedFilter = null;
					DefaultFileFilter xmlPngFilter = new DefaultFileFilter(".png",
							"PNG+XML " + mxResources.get("file") + " (.png)");
					FileFilter vmlFileFilter = new DefaultFileFilter(".html",
							"VML " + mxResources.get("file") + " (.html)");
					String filename = null;
					boolean dialogShown = false;
					
					String wd =" THIS FILE";
				

						JFileChooser fc = new JFileChooser(wd);

						// Adds the default file format
						FileFilter defaultFilter = xmlPngFilter;
						fc.addChoosableFileFilter(defaultFilter);

						// Adds special vector graphics formats and HTML
						fc.addChoosableFileFilter(new DefaultFileFilter(".mxe",
								"mxGraph Editor " + mxResources.get("file")
										+ " (.mxe)"));
						fc.addChoosableFileFilter(new DefaultFileFilter(".txt",
								"Graph Drawing " + mxResources.get("file")
										+ " (.txt)"));
						fc.addChoosableFileFilter(new DefaultFileFilter(".svg",
								"SVG " + mxResources.get("file") + " (.svg)"));
						fc.addChoosableFileFilter(vmlFileFilter);
						fc.addChoosableFileFilter(new DefaultFileFilter(".html",
								"HTML " + mxResources.get("file") + " (.html)"));

						// Adds a filter for each supported image format
						Object[] imageFormats = ImageIO.getReaderFormatNames();

						// Finds all distinct extensions
						HashSet<String> formats = new HashSet<String>();

						for (int i = 0; i < imageFormats.length; i++)
						{
							String ext = imageFormats[i].toString().toLowerCase();
							formats.add(ext);
						}

						imageFormats = formats.toArray();

						for (int i = 0; i < imageFormats.length; i++)
						{
							String ext = imageFormats[i].toString();
							fc.addChoosableFileFilter(new DefaultFileFilter("."
									+ ext, ext.toUpperCase() + " "
									+ mxResources.get("file") + " (." + ext + ")"));
						}

						// Adds filter that accepts all supported image formats
						fc.addChoosableFileFilter(new DefaultFileFilter.ImageFileFilter(
								mxResources.get("allImages")));
						fc.setFileFilter(defaultFilter);
						int rc = fc.showDialog(null, mxResources.get("save"));
						dialogShown = true;

						if (rc != JFileChooser.APPROVE_OPTION)
						{
							return;
						}
					
						filename = fc.getSelectedFile().getAbsolutePath();
						selectedFilter = fc.getFileFilter();

						if (selectedFilter instanceof DefaultFileFilter)
						{
							String ext = ((DefaultFileFilter) selectedFilter)
									.getExtension();

							if (!filename.toLowerCase().endsWith(ext))
							{
								filename += ext;
							}
						}

						if (new File(filename).exists()
								&& JOptionPane.showConfirmDialog(graphComponent,
										mxResources.get("overwriteExistingFile")) != JOptionPane.YES_OPTION)
						{
							return;
						}
			

					try
					{
						String ext = filename
								.substring(filename.lastIndexOf('.') + 1);

						if (ext.equalsIgnoreCase("svg"))
						{
							mxSvgCanvas canvas = (mxSvgCanvas) mxCellRenderer.drawCells(graph, null, 1, null,
											new CanvasFactory()
											{
												public mxICanvas createCanvas(
														int width, int height)
												{
													mxSvgCanvas canvas = new mxSvgCanvas(
															mxDomUtils.createSvgDocument(
																	width, height));
													canvas.setEmbedded(true);

													return canvas;
												}

											});
							mxUtils.writeFile(mxXmlUtils.getXml(canvas.getDocument()),
									filename);
						}
						else if (selectedFilter == vmlFileFilter)
						{
							mxUtils.writeFile(mxXmlUtils.getXml(mxCellRenderer
									.createVmlDocument(graph, null, 1, null, null)
									.getDocumentElement()), filename);
						}
						else if (ext.equalsIgnoreCase("html"))
						{
							mxUtils.writeFile(mxXmlUtils.getXml(mxCellRenderer
									.createHtmlDocument(graph, null, 1, null, null)
									.getDocumentElement()), filename);
						}
						else if (ext.equalsIgnoreCase("mxe")
								|| ext.equalsIgnoreCase("xml"))
						{
							mxCodec codec = new mxCodec();
							String xml = mxXmlUtils.getXml(codec.encode(graph
									.getModel()));

							mxUtils.writeFile(xml, filename);

							
						}
						else if (ext.equalsIgnoreCase("txt"))
						{
							String content = mxGdCodec.encode(graph);

							mxUtils.writeFile(content, filename);
						}
						else
						{
							Color bg = null;

							if ((!ext.equalsIgnoreCase("gif") && !ext
									.equalsIgnoreCase("png"))
									|| JOptionPane.showConfirmDialog(
											graphComponent, mxResources
													.get("transparentBackground")) != JOptionPane.YES_OPTION)
							{
								bg = graphComponent.getBackground();
							}

							if (selectedFilter == xmlPngFilter
											&& ext.equalsIgnoreCase("png") && !dialogShown) // )
							{
							
								io.saveXmlPng(graphComponent,graph, "text.mxe", Color.WHITE);
							}
							else
							{
								BufferedImage image = mxCellRenderer
										.createBufferedImage(graph, null, 1, bg,
												graphComponent.isAntiAlias(), null,
												graphComponent.getCanvas());

								if (image != null)
								{
									ImageIO.write(image, ext, new File(filename));
								}
								else
								{
									JOptionPane.showMessageDialog(graphComponent,
											mxResources.get("noImageData"));
								}
							}
						}
					}
					catch (Throwable ex)
					{
						ex.printStackTrace();
						JOptionPane.showMessageDialog(graphComponent,
								ex.toString(), mxResources.get("error"),
								JOptionPane.ERROR_MESSAGE);
					}
				}
			
				
		
		});
		  panel_1.add(btnSave);
		  
		  JButton btnLoad = new JButton("Load");
		  btnLoad.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
		  		
//		  		BasicGraphEditor editor = getEditor(e);

				//	if (editor != null){
					//	if (!editor.isModified()|| JOptionPane.showConfirmDialog(editor,mxResources.get("loseChanges")) == JOptionPane.YES_OPTION){
							//mxGraph graph = editor.getGraphComponent().getGraph();
					String lastDir=null;
							if (graph != null)
							{
								String wd = (lastDir != null) ? lastDir : System
										.getProperty("user.dir");

								JFileChooser fc = new JFileChooser(wd);

								// Adds file filter for supported file format
								DefaultFileFilter defaultFilter = new DefaultFileFilter(
										".mxe", mxResources.get("allSupportedFormats")
												+ " (.mxe, .png, .vdx)")
								{

									public boolean accept(File file)
									{
										String lcase = file.getName().toLowerCase();

										return super.accept(file)
												|| lcase.endsWith(".png")
												|| lcase.endsWith(".vdx");
									}
								};
								fc.addChoosableFileFilter(defaultFilter);

								fc.addChoosableFileFilter(new DefaultFileFilter(".mxe",
										"mxGraph Editor " + mxResources.get("file")
												+ " (.mxe)"));
								fc.addChoosableFileFilter(new DefaultFileFilter(".png",
										"PNG+XML  " + mxResources.get("file")
												+ " (.png)"));

								// Adds file filter for VDX import
								fc.addChoosableFileFilter(new DefaultFileFilter(".vdx",
										"XML Drawing  " + mxResources.get("file")
												+ " (.vdx)"));

								// Adds file filter for GD import
								fc.addChoosableFileFilter(new DefaultFileFilter(".txt",
										"Graph Drawing  " + mxResources.get("file")
												+ " (.txt)"));

								fc.setFileFilter(defaultFilter);

								int rc = fc.showDialog(null,
										mxResources.get("openFile"));

								if (rc == JFileChooser.APPROVE_OPTION)
								{
									lastDir = fc.getSelectedFile().getParent();

									try
									{
										if (fc.getSelectedFile().getAbsolutePath()
												.toLowerCase().endsWith(".png"))
										{
										//	openXmlPng(editor, fc.getSelectedFile());
											io.openXmlPng(graph, fc.getSelectedFile(),fc.getSelectedFile().getAbsolutePath(),contentPane);
										}
										else if (fc.getSelectedFile().getAbsolutePath()
												.toLowerCase().endsWith(".txt"))
										{
											io.openGD(graph, fc.getSelectedFile(),mxUtils.readFile(fc.getSelectedFile().getAbsolutePath()),contentPane);
										}
										else
										{
											Document document = mxXmlUtils
													.parseXml(mxUtils.readFile(fc
															.getSelectedFile()
															.getAbsolutePath()));

											mxCodec codec = new mxCodec(document);
											codec.decode(
													document.getDocumentElement(),
													graph.getModel());
									//		editor.setCurrentFile(fc.getSelectedFile());

											//resetEditor(editor);
										}
									}
									catch (IOException ex)
									{
										ex.printStackTrace();
										JOptionPane.showMessageDialog(
												graphComponent,
												ex.toString(),
												mxResources.get("error"),
												JOptionPane.ERROR_MESSAGE);
									}
								}
							}
					//	}
				//	}
				}
			
			});
		  panel_1.add(btnLoad);
		  	
	

		  list.updateUI();
		contentPane.setLayout(gl_contentPane);
		File check = new File("db");
		if(check.exists()){
			System.out.println("Laster eksisterende db");
		collection.removeObjects();
		try {
			parser.loadLocalDb();
			parser.addObject(collection);
			createList();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(contentPane,
					"Cant fint local db. Load dataset first!", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		
		}
	}
	
	
	

	public void createList(){
		list.setVisible(false);
		listScroller.setVisible(false);
		list=null;
		listScroller = null;
		
		//Shows the contructs in the jlist
		list = new JList<Object>(parser.getConstructList().toArray());
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
				 int index = list.getSelectedIndex(); 
				 setTextCurrent("Current construct: "+list.getSelectedValue().toString());
				 indexx = index;
				 initGraphDisplay();
				 			 
				}
				
			}
		});

		list.setBorder(UIManager.getBorder("TextField.border"));
		list.setBounds(293, 108, 150, 150);
		
		list.setBackground(Color.WHITE);
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		listScroller.setBounds(260, 108, 215, 100);
		// contentPane.add(listScroller);
		panel_1.add(listScroller);
		list.updateUI();
		setText("Constructs: " + collection.getConstructList().size());
		initGraphDisplay();
	
	}
	
	
	
	public void setText(String text){
	//	this.lblConstructs.setText(text);
	}
	
	public void setTextCurrent(String text){
	//	this.lblNewLabel.setText(text);
	}
	
	public void initGraphDisplay(){
		if(gd != null){
			 gd.dispose();
		 }
		 	graph = new mxGraph();
		 	graphComponent = new mxGraphComponent(graph);
			gd = new GraphDisplay(collection.getConstructList(),indexx,graph,graphComponent);
			gd.setSize(contentPane.getWidth()-40, contentPane.getHeight()-150);
			gd.setVisible(true);
			panel_3.add(gd);
			
		
	}
	
	
}
