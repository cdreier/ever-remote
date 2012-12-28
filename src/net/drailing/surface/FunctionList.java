package net.drailing.surface;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.drailing.storage.Command;

@SuppressWarnings("serial")
public class FunctionList extends JList<Command> implements ListSelectionListener{

	private Surface surface;
	private DefaultListModel<Command> dataSource;
	
	private Command selectedObject;
	
	public FunctionList(Command[] items){
		super(items);
		init();
	}
	
	public FunctionList(DefaultListModel<Command> listModel, Surface surface) {
		super(listModel);
		this.dataSource = listModel;
		this.surface = surface;
		init();
	}

	private void init() {
		this.setBorder(LineBorder.createGrayLineBorder());
		this.setSize(100, 350);
		this.addListSelectionListener(this);
	}


	public DefaultListModel<Command> getDataSource() {
		return dataSource;
	}

	public void setDataSource(DefaultListModel<Command> dataSource) {
		this.dataSource = dataSource;
	}

	public Command getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Command selectedObject) {
		this.selectedObject = selectedObject;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()){
			return;
		}

		try{
			this.selectedObject = this.dataSource.get(this.getSelectedIndex());
			this.surface.getTxtCommandEditor().setText(this.selectedObject.getCommand());
			this.surface.getTxtName().setText(this.selectedObject.getName());
			this.surface.resetIcons();
			if(this.selectedObject.getIconId() > 0){
				this.surface.getIcons().get(this.selectedObject.getIconId() - 1).setActive();
			}
		}catch(ArrayIndexOutOfBoundsException ex){
			this.selectedObject = null;
			this.surface.resetIcons();
			this.surface.getTxtCommandEditor().setText("");
			this.surface.getTxtName().setText("");
		}
	}
	
	
}
