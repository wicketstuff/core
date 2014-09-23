package ${package}

import org.eclipse.jetty.server.Connector
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.bio.SocketConnector
import org.eclipse.jetty.webapp.WebAppContext

object Start {

  def main(args:Array[String]): Unit = {
    val server = new Server
    val connector = new SocketConnector

    // Set some timeout options to make debugging easier.
    connector.setMaxIdleTime(1000 * 60 * 60)
    connector.setSoLingerTime(-1)
    connector.setPort(8080)
    server.setConnectors(Array[Connector](connector))

    val bb = new WebAppContext
    bb.setServer(server)
    bb.setContextPath("/")
    bb.setWar("src/main/webapp")

    // START JMX SERVER
    // val mBeanServer = ManagementFactory.getPlatformMBeanServer
    // val mBeanContainer = new MBeanContainer(mBeanServer)
    // server.getContainer().addEventListener(mBeanContainer)
    // mBeanContainer.start

    server.setHandler(bb)

    try {
      System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP")
      server.start()
      System.in.read()
      System.out.println(">>> STOPPING EMBEDDED JETTY SERVER")
      server.stop()
      server.join()
    } catch {
      case x: Exception =>
        x.printStackTrace()
        System.exit(100)
    }
  }
}
