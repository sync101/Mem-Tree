/*
 * Copyright 2011 Nanda Emani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.orastager.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SmartSuggestOracle;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.Tree.Resources;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StaticVar {

	static RootPanel rootPanel = RootPanel.get();
	
	static boolean auto_open = false;
	
    static boolean moveResizePane = false;

	static MenuBar menuBar = new MenuBar();
	static MenuBar menuBarFile = new MenuBar(true);
	static MenuBar menuBarOptions = new MenuBar(true);
    static MenuBar menuBarHelp = new MenuBar(true);
    
    static MenuBar menuBarCommands = new MenuBar();
	
    static HorizontalPanel horCollaboratorsPanel = new HorizontalPanel();
    static ArrayList<CustomImage> collaboratorsList = new ArrayList<CustomImage>();
    
    static int codeTabId = 0;
    
	// JSON Text Area
	static TextArea jsontextArea = new TextArea();
	static Button replacejsonButton = new Button("Append JSON");
	
	//Tab Panel
	static CustomTabLayoutPanel tabPanel = new CustomTabLayoutPanel();
    
    //Current file id
    static String resource_id = new String("new_file");
	static String resource_title = Window.getTitle();
	static boolean resource_modified = false;
    static String fileDescr = new String("");
    static String mimeType = new String("text/plain");
    
	// Moved from Node
	static boolean show_preview_flag = false;
	static int nodeTextBoxWidth = 150;
	static int nodeTextAreaWidth = 110;
	static int nodeTextBoxMaxWidth = 120;
	
	static CustomTreeItem tiDrag = new CustomTreeItem();
	
    static Random randomGenerator = new Random();
	static HashSet<String> randomNumArr = new HashSet<String>();
	static String randomNum;
	
    public static HashMap<CustomTreeItem, Node> locate = new HashMap<CustomTreeItem, Node>();
    public static HashMap<Node, Node> locateChildNode = new HashMap<Node, Node>();
    static HashMap<DraggableFlowPanel, Node> locateNode = new HashMap<DraggableFlowPanel, Node>();
    static HashMap<KeyWordLabel, Node> locateTextBoxNode = new HashMap<KeyWordLabel, Node>();
    static ArrayList<ChildTextArea> textTabsArr = new ArrayList<ChildTextArea>();
    static HashMap<RichTextArea, CustomTreeItem> locateTextTab = new HashMap<RichTextArea, CustomTreeItem>();
    static HashMap<TextArea, CustomTreeItem> locateUrlTab = new HashMap<TextArea, CustomTreeItem>();
    static HashMap<TextArea, CustomTreeItem> locateCodeTab = new HashMap<TextArea, CustomTreeItem>();
    static int tabCount = 1;
    
    static SmartSuggestOracle oracle = new SmartSuggestOracle();

	//key_dma
	static String key_dma = new String();
	
	static interface MenuResources extends Resources {
        ImageResource mail();
        ImageResource blog();
        ImageResource sample();
		ImageResource CutNode();
		ImageResource PasteNode();
		ImageResource moveTo();
        ImageResource NewKeyWord();
        ImageResource newFile();
        ImageResource edit();
        ImageResource menuDown();
        ImageResource addNodePair();
        ImageResource feedback();

        @Source("c.gif")
        ImageResource c();
        @Source("cpp.gif")
        ImageResource cpp();
        @Source("css.gif")
        ImageResource css();
        @Source("html.gif")
        ImageResource html();
        @Source("java.gif")
        ImageResource java();
        @Source("javascript.png")
        ImageResource javascript();
        @Source("mysql.gif")
        ImageResource mysql();
        @Source("php.png")
        ImageResource php();
        @Source("python.png")
        ImageResource python();
        @Source("rCode.gif")
        ImageResource rCode();
        @Source("xml.gif")
        ImageResource xmlCode();
        @Source("shell.gif")
        ImageResource shellScript();
        @Source("sql.gif")
        ImageResource sql();
        
        ImageResource MoveUp();
        ImageResource MoveDown();
        ImageResource deleteNode();
        ImageResource close();
        ImageResource closeTab();
        ImageResource Maximize();
        ImageResource preview();
        ImageResource left();
        ImageResource right();
        ImageResource leftIcon();
        ImageResource rightIcon();
        ImageResource SubMenu();
        ImageResource xml();
        ImageResource bulkAdd();
        ImageResource menuBarSubMenuIcon();
        ImageResource find();
        
        ImageResource notes();
        ImageResource text();
        ImageResource linkUrl();
        ImageResource pdfUrl();
        ImageResource wordUrl();
        ImageResource excelUrl();
        ImageResource musicUrl();
        ImageResource video();
        ImageResource playList();
        ImageResource image();
        
        ImageResource processing();
        ImageResource save();
        ImageResource download();
        ImageResource reset();
        ImageResource sync();
        ImageResource removeSyncData();
        ImageResource privacy();
        ImageResource credits();
        ImageResource flexTable();
        ImageResource saveXml();
        ImageResource newFrame();
        ImageResource googleDrive();

        ImageResource showPreview();
        ImageResource hidePreview();
        
        
        ImageResource Reminder();
        ImageResource ReminderSmall();
        ImageResource Meeting();
        ImageResource MeetingSmall();
        ImageResource AddComment();
        ImageResource finish();
        ImageResource redFlag();
        ImageResource greenFlag();
        ImageResource clearFlags();
        ImageResource task();
        ImageResource tasks();
        ImageResource taskSmall();
        
        ImageResource features();
        ImageResource help();
        ImageResource pencil();
        ImageResource share();
        ImageResource share32();
        ImageResource facebook();
        ImageResource ChromeExtension();
        
        ImageResource expand();
        ImageResource collapse();
        ImageResource inherit();
        ImageResource barChart();
        ImageResource top();
        ImageResource bottom();
        ImageResource remove();
        ImageResource webStore();
    };
    
	static interface ChildTextResources extends ClientBundle {
	  	  @Source({"icons/small/notes.png"})
	  	  ImageResource notes();
	  	  @Source({"icons/small/text.png"})
	  	  ImageResource text();
	  	  @Source("icons/small/c.png")
	  	  ImageResource c();
	  	  @Source("icons/small/cpp.png")
	  	  ImageResource cpp();
	  	  @Source("icons/small/css.png")
	  	  ImageResource css();
	  	  @Source("icons/small/html.png")
	  	  ImageResource html();
	  	  @Source("icons/small/java.png")
	  	  ImageResource java();
	  	  @Source("icons/small/js.png")
	  	  ImageResource javascript();
	  	  @Source("icons/small/mysql.png")
	  	  ImageResource mysql();
	  	  @Source("icons/small/php.png")
	  	  ImageResource php();
	  	  @Source("icons/small/python.png")
	  	  ImageResource python();
	  	  @Source("icons/small/shell.png")
	  	  ImageResource shellScript();
	  	  @Source("icons/small/sql.png")
	  	  ImageResource sql();
	  	  @Source("icons/small/xml.png")
	  	  ImageResource xmlCode();
	  	  @Source("icons/small/rCode.png")
	  	  ImageResource rCode();

	  	  @Source("icons/small/link.png")
	  	  ImageResource linkUrl();
	  	  @Source("icons/small/pdf.png")
	  	  ImageResource pdfUrl();
	  	  @Source("icons/small/word.png")
	  	  ImageResource wordUrl();
	  	  @Source("icons/small/excel.png")
	  	  ImageResource excelUrl();
	  	  @Source("icons/small/music.png")
	  	  ImageResource musicUrl();
	  	  @Source("icons/small/video.png")
	  	  ImageResource video();
	  	  @Source("icons/small/playlist.png")
	  	  ImageResource playList();
	  	  @Source("icons/small/image.png")
	  	  ImageResource image();
	  	}
    
    static MenuResources menuResources = GWT.create(MenuResources.class);
	//Tree Resources
    static interface TreeResources extends Resources {
        ImageResource treeClosed();
        ImageResource treeLeaf();
        ImageResource treeOpen();
    }	
    
	static TreeResources treeResources = GWT.create(TreeResources.class);
	
	static CustomNodeMovePopupPanel moveNodeTreePopPanel = new CustomNodeMovePopupPanel();
	static Tree moveNodeTree = new Tree(treeResources);
	static VerticalPanel moveNodeTreePanel = new VerticalPanel(); 
	static CustomScrollPanel moveNodeTreeScrPanel = new CustomScrollPanel(moveNodeTree);
	static int moveNodeTreeScrWidth = Window.getClientWidth()/2;
	static int moveNodeTreeScrHeight = Window.getClientHeight()/2;
	
	static CustomPopupPanel addNodePairPanel = new CustomPopupPanel(true, true, true);
	static VerticalPanel addNodePairVertPanel = new VerticalPanel();
	static TextBox kwTextBox = new TextBox();
	static TextArea ctTextArea = new TextArea();
	static ListBox tpNodePair = new ListBox();
	static Button addNodePairButton = new Button("Add");
	static CustomPopupPanel jsonPopupPanel = new CustomPopupPanel(true, true, true);
	static VerticalPanel jsonFlowPanel = new VerticalPanel();
	
	static CustomPopupPanel msgPopPanel = new CustomPopupPanel(false, true, false);
	static HTML msgHtml = new HTML();
	
	static InlineLabel root_moveNode_label = new InlineLabel("Root");
	static TreeItem root_moveNode_ti = new TreeItem(root_moveNode_label);
	static HashMap<CustomTreeItem, TreeItem> locateMoveNodeMap = new HashMap<CustomTreeItem, TreeItem>();
	static HashMap<TreeItem, CustomTreeItem> locateMoveNodeMapRev = new HashMap<TreeItem, CustomTreeItem>();
	

	static Image img_processing = new Image(menuResources.processing());
	
    static interface ChildTextTypes{
    	final String type_notes = "NOTES";
    	final String type_text = "TEXT";
    	final String type_url  = "URL";
    	final String type_pdf  = "PDF";
    	final String type_word = "WORD";
		final String type_excel = "EXCEL";
		final String type_music = "MUSIC";
		final String type_video = "VIDEO";
		final String type_video_pl = "VIDEO_PLAYLIST";
		final String type_img = "IMAGE";
    	final String type_c = "C";
    	final String type_cpp = "CPP";
    	final String type_css = "CSS";
    	final String type_html = "HTML";
    	final String type_java = "JAVA";
    	final String type_javascript = "JAVASCRIPT";
    	final String type_mysql = "MYSQL";
    	final String type_php = "PHP";
    	final String type_python = "PYTHON";
    	final String type_shellScript = "SHELLSCRIPT";
    	final String type_sql = "SQL";
    	final String type_xml = "XML";
    	final String type_r = "R";
    }
    
    static final List<String> childTextTypeList = Arrays.asList("NOTES","TEXT"
    		            ,"URL","PDF","WORD","EXCEL","MUSIC","VIDEO","VIDEO_PLAYLIST","IMAGE"
    		            ,"C","CPP","CSS","HTML","JAVA","JAVASCRIPT","MYSQL","PHP","PYTHON","SHELLSCRIPT","SQL","XML","R"); 
    
    static final List<String> childTextTextTypeList = Arrays.asList("NOTES","TEXT"
                ,"URL","PDF","WORD","EXCEL","MUSIC","VIDEO","VIDEO_PLAYLIST","IMAGE");
    
    static final List<String> childTextCodeTypeList = Arrays.asList(
            "C","CPP","CSS","HTML","JAVA","JAVASCRIPT","MYSQL","PHP","PYTHON","SHELLSCRIPT","SQL","XML","R"); 

    static final List<String> childTextCodeTypeMimeList = Arrays.asList(
            "text/x-csrc","text/x-c++src","text/css","xml","text/x-java","text/javascript","text/x-mysql","application/x-httpd-php","python"
            ,"shell","text/x-plsql","xml","text/x-rsrc");
    
    private static StaticVar.ChildTextResources resources = GWT.create(StaticVar.ChildTextResources.class);
    
    static final List<ImageResource> childTextCodeTypeImages = Arrays.asList(resources.c(), resources.cpp(),resources.css()
    		                                             ,resources.html(),resources.java(),resources.javascript(),resources.mysql()
    		                                             ,resources.php(),resources.python(),resources.shellScript(),resources.sql()
    		                                             ,resources.xmlCode(),resources.rCode());

    static final List<ImageResource> childTextTextTypeImages = Arrays.asList(resources.notes(),resources.text(),resources.linkUrl(),resources.pdfUrl()
    		                                             ,resources.wordUrl(),resources.excelUrl(),resources.musicUrl(),resources.video()
    		                                             ,resources.playList(),resources.image());    

    static final List<ImageResource> childTextCodeTypeImagesBig = Arrays.asList(menuResources.c(), menuResources.cpp(),menuResources.css()
            ,menuResources.html(),menuResources.java(),menuResources.javascript(),menuResources.mysql()
            ,menuResources.php(),menuResources.python(),menuResources.shellScript(),menuResources.sql()
            ,menuResources.xmlCode(),menuResources.rCode());

    static final List<ImageResource> childTextTextTypeImagesBig = Arrays.asList(menuResources.notes(),menuResources.text(),menuResources.linkUrl(),menuResources.pdfUrl()
            ,menuResources.wordUrl(),menuResources.excelUrl(),menuResources.musicUrl(),menuResources.video()
            ,menuResources.playList(),menuResources.image());    

    static Boolean savingFlag = false;

    static int previewFrameWidth = Window.getClientWidth()-100;
    static int previewFrameHeight = Window.getClientHeight()-10;
    static CustomFrameSlideDialogBox dialogPreviewBox = new CustomFrameSlideDialogBox(previewFrameWidth, previewFrameHeight);
    static ArrayList<Node> previewFrames = new ArrayList<Node>();
    
	
	// GWT Tree
	static CustomTree dmaTree = new CustomTree(treeResources);
	//DmaTree dmaTree = new DmaTree();
	static CustomScrollPanel treeScroller = new CustomScrollPanel(dmaTree);
	static DraggableImage addNodePairImg = new DraggableImage(StaticVar.menuResources.addNodePair(), Commands.addNodePair, false);
	static Image collapseButton = new Image(StaticVar.menuResources.collapse());
	static Image expandButton = new Image(StaticVar.menuResources.expand());


	// Root Tree Item
	static Node root_node = new Node("Root","Root",(new Date()).toString(), "", "", "", "", "", "", "", "", true);
	static Node currNode = root_node;
    // Root Node
	static Node emptyCutNode = new Node("EmptyNode");
    static Node lastNode = new Node("EmptyNode");
    static Node cutNode = emptyCutNode;
    static boolean cutNodeFlag = false;

	static CustomTreeItem root_ti = new CustomTreeItem(root_node);
	
	// Check box for search
	static CheckBox searchchkBox = new CheckBox();
	
	static boolean dma_file = true;
	static String revision;
	static boolean readonly_file = false;
	
	static TextArea codeTextArea = new TextArea();
	static String origText;
	
    //Search Image
	//final Image searchImg = new Image("");

	//Local Storage
	//final static Storage localStorage = Storage.getLocalStorageIfSupported();
	
	//Searched Nodes
	static ArrayList<CustomTreeItem> searchList = new ArrayList<CustomTreeItem>();
	static ArrayList<TreeItem> searchListMoveTree = new ArrayList<TreeItem>();
	
	//searchBox, suggestBox
	final static CustomSuggestBox suggestBox = new CustomSuggestBox(oracle);
	static AbsolutePanel searchAbsResultsPanel = new AbsolutePanel();
	static VerticalPanel searchResultsPanelHdr = new VerticalPanel();
	static VerticalPanel searchResultsPanel = new VerticalPanel();
	static CustomScrollPanel scrAbsResultsPanel = new CustomScrollPanel(searchResultsPanel);
	static AbsolutePanel absSearchPanel = new AbsolutePanel();

	static final String APP_BASE_URL = "http://mem-tree.appspot.com";
	
	static int treeWidth;
	static int treeHeight;
	static int tabPanelWidth;
	static int tabPanelHeight;
	static int codeEditorWidth;
	static int codeEditorHeight;
	static int textEditorWidth;
	static int textEditorHeight;
	static int notesEditorWidth;
	static int notesEditorHeight;

	static Image top_img = new Image(StaticVar.menuResources.top());
  	static Image bottom_img = new Image(StaticVar.menuResources.bottom());

  	static HorizontalPanel hp = new HorizontalPanel();
	//static CustomScrollPanel tableScrPanel = new CustomScrollPanel(hp);
  	
	public static DateTimeFormat dateFormatter = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);
  	static TaskView taskView = new TaskView();
  	static TableView tableView = new TableView();

	static SyncView syncView = new SyncView();
	static Set<NodePair> selectedNodePairList = new HashSet<NodePair>();
	
	static Image driveIcon = new Image(StaticVar.menuResources.googleDrive());
	
	static DriveSearchResultsView driveResultsView = new DriveSearchResultsView();

	protected static boolean suggestBoxFocused = false;
	protected static boolean allowScroll = true;
	
	static HTML previewHtml = new HTML();
	static CustomPopupPanel previewPopup = new CustomPopupPanel(false, false, true);

	/*  static interface Templates extends SafeHtmlTemplates {
		    Templates INSTANCE = GWT.create(Templates.class);

		    @Template("<div id='dragHelper' style='border:1px solid black; background-color:#ffffff; color:black; position:absolute; top:50%; left:45%;'></div>")
		    SafeHtml outerHelper();
		  }
	*/
}
