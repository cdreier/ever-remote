package net.drailing;

import net.drailing.server.RemoteServer;
import net.drailing.surface.Surface;

public class Main {

	public static void main(String[] args) {

		try {

			new Surface();
			new RemoteServer();

			// Process process = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Notepad++\\notepad++.exe");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
