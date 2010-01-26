package org.wicketstuff.push.cometd;

import java.io.InputStream;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.parser.XmlPullParser;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.protocol.http.WebApplication;
import org.mortbay.cometd.continuation.ContinuationCometdServlet;
import org.wicketstuff.push.dojo.AbstractRequireDojoBehavior;

/**
 * This behavior will be asked by client side when it will receive a cometd
 * event associated with the kind of event
 */
public abstract class CometdAbstractBehavior extends
    AbstractRequireDojoBehavior {
  private static final long serialVersionUID = 1L;

  // FIXME: put this in application scope, we may have several webapp using
  // CometdBehavior in the same web container!
  private final static String cometdServletPath = getCometdServletPath();

  private static short idex = 'a';

  private String channelId;

  private final short index;

  /**
   * Construct a commetd Behavior
   * @param channelId
   */
  public CometdAbstractBehavior(final String channelId) {
    super();
    this.channelId = channelId;
    index = idex++;
  }

  @Override
  public void setRequire(final Set<String> libs) {
    libs.add("dojo._base");
    libs.add("dojox.cometd");
    libs.add("dojox.cometd.timestamp");
    libs.add("dojox.cometd.timesync");
  }

  @Override
  public void renderHead(final IHeaderResponse response) {
    super.renderHead(response);
    if (channelId == null) {
      throw new IllegalArgumentException(
          "ChannelId in a CometdBehavior can not be null");
    }
    response.renderJavascript(getInitCometdScript(), "initCometd");
    final String cometdInterceptorScript = getCometdInterceptorScript();
    if (cometdInterceptorScript != null) {
      response.renderJavascript(cometdInterceptorScript, "Interceptor" + getBehaviorMarkupId());
    }
    response.renderJavascript(getSubscriberScript(), "Subscribe" + getBehaviorMarkupId());
  }

  /**
   * Return the string containing the client side logic when a new event comes
   * into the channel.
   * <p>
   * see
   * <code>CharSequence getSubscriberScript(String javascriptObject, String javascriptMethod)</code>
   * </p>
   * <p>
   * This script should contain an object <code>javascriptObject</code> having a
   * method <code>javascriptMethod</code>
   * </p>
   * @return
   */
  public abstract String getCometdInterceptorScript();

  /**
   * this method should return a part of javascript (String) allowing to give
   * code to execute when a cometd event is triggered in client side.
   * <p>
   * It can be a single function name comming form the DojoPackagesTextTemplate
   * such as <code>'MyFunction'</code> or an objectInstance and one of its function, i.e, <code>'MyObject','OneFunctionOfMyObject'</code>
   * </p>
   * @return part of javascript (String) allowing to give code to execute when a
   *         cometd event is triggered in client side.
   */
  public abstract CharSequence getPartialSubscriber();

  /**
   * Javascript allowing cometd to be initialized on commetd
   * @return javascript to initialize cometd on client Side
   */
  protected final CharSequence getInitCometdScript() {
    return "dojox.cometd.init('" + cometdServletPath + "')\n";
  }

  /**
   * Javascript allowing cometd to subscribe to a channel <br/> Channel to
   * subscribe comes from getChannelId method, Merthod or object to invoke when
   * a event is triggered on client side is given by getPartialSubscriber()
   * method
   * <p>
   * see also getCometdIntercepteur method and getPartialSubscriber method
   * </p>
   * @return Javascript allowing cometd to subscribe to a channel and intercept
   *         event
   */
  public final CharSequence getSubscriberScript() {
    return "dojox.cometd.subscribe('/" + getChannelId()
           + "', " + getPartialSubscriber() + ");\n";
  }

  /**
   * return the javascript to unsuscribe to th channel
   * @return javascript to unsuscribe to the channel
   */
  public final CharSequence getUnsuscribeScript() {
    return "dojox.cometd.unsubscribe('/" + getChannelId() + "');\n";
  }

  /**
   * get the channel where this behavior will wait for event
   * @return channelId channel where this behavior will wait for event
   */
  public String getChannelId() {
    return channelId;
  }

  /**
   * Set the channel where this behavior will wait for event
   * @param channelId channel where this behavior will wait for event
   */
  public void setChannelId(final String channelId) {
    this.channelId = channelId;
  }

  /** Returns the id string
   * @return
   */
  public final CharSequence getBehaviorMarkupId() {
    final StringBuilder sb = new StringBuilder(getComponent().getMarkupId());
    return sb.append(getChannelId()).append(index).toString();
  }


  /**
   * Parse the web.xml to find cometd context Path. This context path will be
   * cache for all the application
   * @return commetd context path
   */
  protected static String getCometdServletPath() {
    final ServletContext servletContext =
        ((WebApplication) Application.get()).getServletContext();
    final InputStream is =
        servletContext.getResourceAsStream("/WEB-INF/web.xml");
    /*
     * get the servlet name from class assignable to
     * org.mortbay.cometd.CometdServlet
     */
    try {
      final XmlPullParser parser = new XmlPullParser();
      parser.parse(is);
      String urlPattern = null;

      while (true) {
        XmlTag elem;
        // go down until servlet is found
        do {
          elem = (XmlTag) parser.nextTag();
        } while (elem != null
                 && !(elem.getName().equals("servlet") && elem.isOpen()));

        // stop if elm is null
        if (elem == null) {
          break;
        }

        // get the servlet name for org.mortbay.cometd.CometdServlet
        String servletName = null, servletClass = null;
        do {
          elem = (XmlTag) parser.nextTag();
          if (elem.isOpen()) {
            parser.setPositionMarker();
          } else if (elem.isClose() && elem.getName().equals("servlet-name")) {
            servletName =
                parser.getInputFromPositionMarker(elem.getPos()).toString();
          } else if (elem.isClose() && elem.getName().equals("servlet-class")) {
            servletClass =
                parser.getInputFromPositionMarker(elem.getPos()).toString();
          }
          // msparer: taking isAssignAbleFrom enables the use of subclasses of
          // CometdServlet
        } while (servletClass == null
                 || !ContinuationCometdServlet.class.isAssignableFrom(Class
                     .forName(servletClass)));

        // go down until servlet is found
        do {
          elem = (XmlTag) parser.nextTag();
        } while (elem != null
                 && !(elem.getName().equals("servlet-mapping") && elem
                     .isOpen()));

        // stop if elm is null
        if (elem == null) {
          break;
        }

        // get the servlet name for org.mortbay.cometd.CometdServlet
        String servletNameMapping = null;
        do {
          elem = (XmlTag) parser.nextTag();
          if (elem.isOpen()) {
            parser.setPositionMarker();
          } else if (elem.isClose() && elem.getName().equals("servlet-name")) {
            servletNameMapping =
                parser.getInputFromPositionMarker(elem.getPos()).toString();
          }
        } while (!servletName.equals(servletNameMapping));

        // and the urlPattern
        do {
          elem = (XmlTag) parser.nextTag();
          if (elem.isOpen()) {
            parser.setPositionMarker();
          } else if (elem.isClose() && elem.getName().equals("url-pattern")) {
            urlPattern =
                parser.getInputFromPositionMarker(elem.getPos()).toString();
          }
        } while (urlPattern == null);

        // all it is found
        break;
      }

      if (urlPattern == null) {
        throw new ServletException("Error searching for cometd Servlet");
      }

      // Check for leading '/' and trailing '*'.
      if (!urlPattern.startsWith("/") || !urlPattern.endsWith("/*")) {
        throw new ServletException(
            "Url pattern for cometd should start with / and finish with /*");
      }

      // Strip trailing '*' and leading '/'.
      final String path = urlPattern.substring(0, urlPattern.length() - 2);
      // prefix with context path
      return servletContext.getContextPath() + path;
    } catch (final Exception e) {
      throw new WicketRuntimeException(
          "Error finding filter cometd servlet in web.xml", e);
    }
  }

}
