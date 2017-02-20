package ru.scriptum.db4o.spring;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;

/**
 * Constructs client connections to db4o database servers.
 */
public class Db4oClientFactoryBean extends Db4oAbstractFactoryBean{
  /** DB host. */
  private String hostName;
  /** DB port. */
  private int port;
  /** DB user. */
  private String user;
  /** DB password. */
  private String password;
  /** Open the client from the embedded server passed in. */
  private ObjectServer server;

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /** If set the client will be opened using server.openClient. */
  public void setServer(ObjectServer server) {
    this.server = server;
  }

  protected Object createInstance() throws Exception {
    if (server != null)
      return server.openClient();
    else
      return Db4o.openClient(hostName, port, user, password);
  }

  public Class getObjectType() {
    return ObjectContainer.class;
  }

  public void destroy() throws Exception {
    ObjectContainer container = (ObjectContainer) getSingleton();
    if (container != null)
      container.close();
  }
}