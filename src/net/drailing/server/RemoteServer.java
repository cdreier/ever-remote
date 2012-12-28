package net.drailing.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.drailing.storage.Command;
import net.drailing.storage.DB;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoteServer extends AbstractHandler{
	
	private Server server;
	
	public RemoteServer() throws Exception{
		this.server = new Server(8081);
		this.server.setHandler(this);
		
		this.server.start();
		this.server.join();
		
	}

	@Override
	public void handle(String target,
            Request baseRequest,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
		
		
		JSONObject defaultResponse = new JSONObject();
		try {
			defaultResponse.put("connected", true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		String responseData = defaultResponse.toString();
		
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		
		if("/all".equals(target)){
			responseData = DB.getInstance().findAll(Command.DB_NAME).toString();
		}else if("/execute".equals(target)){
			try {
				JSONObject requestData = new JSONObject(request.getParameter("json"));
				Command c = Command.findById(requestData.getInt("commandId"));
				c.execute();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		response.getWriter().println(responseData);
	}

}
