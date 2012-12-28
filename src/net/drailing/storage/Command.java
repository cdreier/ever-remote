package net.drailing.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.drailing.Robo;

import org.json.JSONException;
import org.json.JSONObject;

public class Command implements Comparable<Command> {

	public static final String DB_NAME = "commands";
	private String name;
	private String command;
	private long id;
	private int iconId;

	public Command(String name, String command) {
		this.name = name;
		this.command = command;
	}

	public void execute() {
		Command.execute(this.command);
	}

	public static void execute(String command) {

		if (command.startsWith("#")) {
			Robo.pressKey(command);
		} else {
			try {
				
				Runtime.getRuntime().exec(command);
				
			} catch (IOException e) {
				
				ProcessBuilder pb = new ProcessBuilder(command);
				try {
					pb.start();
				} catch (IOException e1) {
					System.out.println("Unable to execute command.");
				}
				
			} catch (IllegalArgumentException e2) {
				e2.printStackTrace();
			}
		}
	}

	public void delete() {
		DB.getInstance().removeObjectWithId((int) this.id, DB_NAME);
	}

	public void save() {
		JSONObject tmp = new JSONObject();
		this.id = DB.getInstance().getNextId();
		try {
			tmp.put("name", this.name);
			tmp.put("command", this.command);
			tmp.put("iconId", this.getIconId());
			tmp.put("id", this.id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		DB.getInstance().addObject(tmp, DB_NAME);
	}

	public static List<Command> findAll() {
		JSONObject allObjects = DB.getInstance().findAll(DB_NAME);
		List<Command> result = new ArrayList<Command>();
		Iterator iter = allObjects.keys();

		while (iter.hasNext()) {
			try {
				String key = iter.next().toString();
				JSONObject tmp = allObjects.getJSONObject(key);

				Command c = new Command(tmp.getString("name"), tmp.getString("command"));
				c.id = tmp.getLong("id");
				c.setIconId(tmp.getInt("iconId"));
				result.add(c);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		Collections.sort(result);
		return result;
	}

	public static Command findById(int id) {
		JSONObject tmp = DB.getInstance().getObjectWithId(id, Command.DB_NAME);
		try {
			Command result = new Command(tmp.getString("name"), tmp.getString("command"));
			result.id = id;
			result.setIconId(tmp.getInt("iconId"));
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int compareTo(Command o) {
		return (int) (this.id - o.id);
	}

}
