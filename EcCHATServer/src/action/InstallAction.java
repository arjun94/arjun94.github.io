package action;

import manager.InstallManager;
import testing.ServerApp;
import util.Constants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class InstallAction extends ActionSupport {

	private static final long serialVersionUID = -1786913598801528008L;

	public String install() {

		//Server server = new Server();
		//server.startServer();
		new ServerApp().createSocket();
		System.out.println("server stared");
		InstallManager installManager = new InstallManager();
		try {
			installManager.install();
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Installation Completed Successfully");
		} catch (Exception e) {
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Installation failed because " + e.getMessage());
		}
		return SUCCESS;

	}

}
