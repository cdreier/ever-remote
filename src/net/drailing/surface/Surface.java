package net.drailing.surface;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.drailing.storage.Command;
import net.drailing.storage.DB;

import org.json.JSONException;
import org.json.JSONObject;


public class Surface extends JFrame {
	
	private static final long serialVersionUID = 6317545179448793813L;

	private static final int row1width = 150;
	private static final int buttonTop = 370;
	private static final int listAndEditorHeight = 240;
	
	private Container contentpane;
	private ArrayList<Icon> icons;
	private Icon activeIcon;

	private JButton btnSave;
	private JButton btnDel;
	private JButton btnNew;
	private JButton btnTest;
	private FunctionList list;
	private JTextArea txtCommandEditor;
	private JTextField txtName;

	public Surface() {
		super("ever-remote Server");
		this.setSize(500, 430);
		this.setResizable(false);

		this.contentpane = getContentPane();
		this.contentpane.setLayout(null);
		
		fillIconList();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		createIPLabel();
		createFunctionList();
		createCommandNameEditor();
		createCommandEditor();
		createIconList();
		createButtons();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void createIconList() {
		
		JPanel container = new JPanel();
		
		int lastRight = 2;
		int paddingRight = 5;
		
		for(Icon i : this.icons){
			container.add(i);
			i.setPosition(lastRight, 0);
			lastRight += i.getWidth() + paddingRight;
		}
		
		JScrollPane scroll = new JScrollPane(container);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroll.setBounds(2, 280, 488, 80);
		scroll.setBorder(LineBorder.createGrayLineBorder());
		this.contentpane.add(scroll);
		
	}

	private void createButtons() {
		this.btnSave = new JButton();
		this.btnSave.setBounds(2, buttonTop, 100, 30);
		this.btnSave.setText("save");
		this.btnSave.setBorder(LineBorder.createGrayLineBorder());
		this.btnSave.setBackground(Color.LIGHT_GRAY);
		this.btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(txtName.getText().isEmpty() || txtCommandEditor.getText().isEmpty()){
					return;
				}
				
				if(list.getSelectedObject() == null){
					Command c = new Command(txtName.getText(), txtCommandEditor.getText());
					c.setIconId(getIconId());
					c.save();
					list.getDataSource().addElement(c);
				}else{
					
					JSONObject tmp = new JSONObject();
					try {
						tmp.put("id", list.getSelectedObject().getId());
						tmp.put("iconId", getIconId());
						tmp.put("name", txtName.getText());
						tmp.put("command", txtCommandEditor.getText());
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					
					list.getSelectedObject().setCommand(txtCommandEditor.getText());
					list.getSelectedObject().setIconId(getIconId());
					list.getSelectedObject().setName(txtName.getText());
					
					DB.getInstance().updateObject(tmp, Command.DB_NAME);
				}
			}

		});

		this.btnDel = new JButton();
		this.btnDel.setBounds(110, buttonTop, 100, 30);
		this.btnDel.setText("delete");
		this.btnDel.setBorder(LineBorder.createGrayLineBorder());
		this.btnDel.setBackground(Color.LIGHT_GRAY);
		this.btnDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(list.getSelectedObject() != null){
					txtName.setText("");
					txtCommandEditor.setText("");
					
					list.getSelectedObject().delete();
					list.setSelectedObject(null);
					list.getDataSource().remove(list.getSelectedIndex());
					list.clearSelection();
				}

			}
		});

		this.btnNew = new JButton();
		this.btnNew.setBounds(220, buttonTop, 100, 30);
		this.btnNew.setText("new");
		this.btnNew.setBorder(LineBorder.createGrayLineBorder());
		this.btnNew.setBackground(Color.LIGHT_GRAY);
		this.btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtName.setText("");
				txtCommandEditor.setText("");
				if(getIconId() > 0){
					activeIcon.setInactive();
				}
				list.setSelectedObject(null);
				list.clearSelection();
			}
		});
		
		
		this.btnTest = new JButton();
		this.btnTest.setBounds(390, buttonTop, 100, 30);
		this.btnTest.setText("test command");
		this.btnTest.setBorder(LineBorder.createGrayLineBorder());
		this.btnTest.setBackground(Color.LIGHT_GRAY);
		this.btnTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!txtCommandEditor.getText().isEmpty()){
					Command.execute(txtCommandEditor.getText());
				}
			}
		});

		this.contentpane.add(this.btnSave);
		this.contentpane.add(this.btnDel);
		this.contentpane.add(this.btnNew);
		this.contentpane.add(this.btnTest);
	}

	private void createCommandNameEditor() {
		JLabel l = new JLabel("Name: ");
		l.setBounds(160, 2, 40, 30);

		this.txtName = new JTextField();
		this.txtName.setBounds(210, 2, 280, 30);
		this.txtName.setBorder(LineBorder.createGrayLineBorder());

		this.contentpane.add(this.txtName);
		this.contentpane.add(l);
	}

	private void createCommandEditor() {
		this.txtCommandEditor = new JTextArea();
		this.txtCommandEditor.setBounds(160, 35, 330, listAndEditorHeight);
		this.txtCommandEditor.setBorder(LineBorder.createGrayLineBorder());
		this.txtCommandEditor.setLineWrap(true);

		this.contentpane.add(this.txtCommandEditor);
	}

	private void createIPLabel() {
		JLabel l = new JLabel("no IP address found");
		try {
			l.setText("IP: " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		l.setBounds(5, 2, row1width, 30);

		this.contentpane.add(l);
	}

	private void createFunctionList() {

		DefaultListModel<Command> listModel = new DefaultListModel<Command>();
		for(Command c : Command.findAll()){
			listModel.addElement(c);
		}
		this.list = new FunctionList(listModel , this);

		JScrollPane sp = new JScrollPane(this.list);
		sp.setBounds(2, 35, row1width, listAndEditorHeight);
		sp.setBorder(LineBorder.createGrayLineBorder());
		this.contentpane.add(sp);
	}

	public JTextArea getTxtCommandEditor() {
		return txtCommandEditor;
	}

	public void setTxtCommandEditor(JTextArea txtCommandEditor) {
		this.txtCommandEditor = txtCommandEditor;
	}

	public JTextField getTxtName() {
		return txtName;
	}

	public void setTxtName(JTextField txtName) {
		this.txtName = txtName;
	}

	private void fillIconList() {
		this.icons = new ArrayList<Icon>();
		this.icons.add(new Icon("play.png", 1, this));
		this.icons.add(new Icon("stop.png", 2, this));
		this.icons.add(new Icon("pause.png", 3, this));
		this.icons.add(new Icon("previous.png", 4, this));
		this.icons.add(new Icon("prev_track.png", 5, this));
		this.icons.add(new Icon("next_track.png", 6, this));
		this.icons.add(new Icon("next.png", 7, this));
		this.icons.add(new Icon("volume_plus.png", 8, this));
		this.icons.add(new Icon("volume_minus.png", 9, this));
		this.icons.add(new Icon("volume_down.png", 10, this));
		this.icons.add(new Icon("volume_up.png", 11, this));
		this.icons.add(new Icon("mute.png", 12, this));
		this.icons.add(new Icon("on.png", 13, this));
		this.icons.add(new Icon("off.png", 14, this));
		this.icons.add(new Icon("search.png", 15, this));
		this.icons.add(new Icon("trash.png", 16, this));
		this.icons.add(new Icon("pencil.png", 17, this));
		this.icons.add(new Icon("cross.png", 18, this));
		this.icons.add(new Icon("plus.png", 19, this));
		this.icons.add(new Icon("minus.png", 20, this));
		this.icons.add(new Icon("build.png", 21, this));
		this.icons.add(new Icon("comment.png", 22, this));
		this.icons.add(new Icon("cursor_down.png", 23, this));
		this.icons.add(new Icon("cursor_up.png", 24, this));
		this.icons.add(new Icon("cursor_left.png", 25, this));
		this.icons.add(new Icon("cursor_right.png", 26, this));
		this.icons.add(new Icon("round.png", 27, this));
		this.icons.add(new Icon("reload.png", 28, this));
		this.icons.add(new Icon("refresh.png", 29, this));
		
		this.icons.add(new Icon("file.png", 30, this));
		this.icons.add(new Icon("folder.png", 31, this));
		this.icons.add(new Icon("new_folder.png", 32, this));
		this.icons.add(new Icon("cursor_neutral.png", 33, this));
	}

	public void setActiveIcon(Icon icon) {
		this.activeIcon = icon;
	}
	
	public Icon getActiveIcon(){
		return this.activeIcon;
	}
	
	private int getIconId() {
		return (this.activeIcon != null)?activeIcon.getIconId():0;
	}
	
	public List<Icon> getIcons(){
		return this.icons;
	}
	
	public void resetIcons(){
		for(Icon i : this.icons){
			i.setInactive();
		}
	}
}
