package net.drailing.storage;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class DB {

	private static String database_path = "db/";
	private static String database_file = "database.json";
	private static DB instance;
	
	private File file;
	private JSONObject data;
	private int lastCommandId;
	private int size;

	public static DB getInstance() {
		if (instance == null) {
			instance = new DB();
		}
		return instance;
	}
	
	public long getSizeForData(String data){
		return this.size;
	}
	
	public long getNextId(){
		this.lastCommandId++;
		try {
			this.data.put(Command.DB_NAME+"_lastId", this.lastCommandId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this.lastCommandId;
	}
	
	public JSONObject findAll(String target) {
		try {
			return this.data.getJSONObject(target);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}
	
	private void decrementSize(){
		this.size--;
		try {
			this.data.put(Command.DB_NAME+"_size", this.size);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void incrementSize(){
		this.size++;
		try {
			this.data.put(Command.DB_NAME+"_size", this.size);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void removeObjectWithId(int id, String target){
		JSONObject tmp = this.findAll(target);
		tmp.remove("" + id);
		this.decrementSize();
		this.flush();
	}
	
	public JSONObject getObjectWithId(int id, String target){
		JSONObject tmp = this.findAll(target);
		try {
			return tmp.getJSONObject("" + id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateObject(JSONObject obj, String target){
		try {
			this.removeObjectWithId(obj.getInt("id"), target);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.addObject(obj, target);
	}
	
	public void addObject(JSONObject object, String target){
		try {
			this.data.getJSONObject(target).put(object.getInt("id") + "", object);
			this.incrementSize();
			this.flush();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private DB() {
		this.file = new File(database_path + database_file);
		if (this.file.exists()) {
			try {
				String tmp = FileUtils.readFileToString(this.file);
				this.data = new JSONObject(tmp);
				this.lastCommandId = this.data.getInt(Command.DB_NAME+"_lastId");
				this.size = this.data.getInt(Command.DB_NAME+"_size");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}else{
			try {
				this.data = new JSONObject();
				this.data.put(Command.DB_NAME, new JSONObject());
				this.data.put(Command.DB_NAME+"_lastId", 0);
				this.data.put(Command.DB_NAME+"_size", 0);
				flush();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	private void flush() {
		try {
			FileUtils.writeStringToFile(this.file, this.data.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


}
