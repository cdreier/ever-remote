package net.drailing.surface;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Icon extends JLabel implements MouseListener {

	private Surface surface;
	private int iconId;
	private boolean active;

	private int width = 50;
	private int height = 50;

	public Icon(String imgPath, int iconId, Surface surface) {
		super(new ImageIcon(ClassLoader.getSystemResource("img/" + imgPath)));
		this.setSize(this.width, this.height);
		
		this.setBorder(BorderFactory.createEtchedBorder());
		
		this.surface = surface;
		this.iconId = iconId;
		this.addMouseListener(this);
	}

	public void setPosition(int x, int y) {
		this.setBounds(x, y, this.width, this.height);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.isActive()) {
			this.setInactive();
		} else {
			this.setActive();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public boolean isActive() {
		return active;
	}

	public void setInactive() {
		this.active = false;
		this.setBorder(BorderFactory.createEtchedBorder());
		this.surface.setActiveIcon(null);
	}

	public void setActive() {
		this.active = true;
		if (this.surface.getActiveIcon() != null) {
			this.surface.getActiveIcon().setInactive();
		}
		this.surface.setActiveIcon(this);
		this.setBorder(BorderFactory.createLineBorder(Color.red, 2));
	}

	public int getIconId() {
		return this.iconId;
	}

}
