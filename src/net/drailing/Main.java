package net.drailing;

import net.drailing.server.RemoteServer;
import net.drailing.surface.Surface;

public class Main {

	public static void main(String[] args) {

		try {

			new Surface();
			new RemoteServer();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
