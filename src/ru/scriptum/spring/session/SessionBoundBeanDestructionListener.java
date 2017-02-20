/*
 * Created on Jan 3, 2006
 *
 * boba
 */
package ru.scriptum.spring.session;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;


/**
 * An HttpSession listener which will look at the attributes bound to a 
 * destroyed session. Calling the {@link org.springframework.beans.factory.DisposableBean#destroy()}
 * method on any attribute that implements the {@link org.springframework.beans.factory.DisposableBean}
 * interface.
 * 
 * @author Eric Dalquist <a href="mailto:edalquist@unicon.net">edalquist@unicon.net</a>
 * @version $Revision: 1.1 $
 */
public class SessionBoundBeanDestructionListener implements HttpSessionListener {
    private static final Log LOG = LogFactory.getLog(SessionBoundBeanDestructionListener.class);

    /**
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("Session Created");
    }

    /**
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("Session Destroyed");
        final HttpSession session = event.getSession();

        for (final Enumeration nameEnum = session.getAttributeNames(); nameEnum.hasMoreElements();) {
            final String attrName = (String)nameEnum.nextElement();
            final Object attribute = session.getAttribute(attrName);
            
            if (attribute instanceof DisposableBean) {
                final DisposableBean disposableAttr = (DisposableBean)attribute;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Destroying HttpSession bound bean. name='" + attrName + "', bean='" + disposableAttr + "'");
                }

                try {
                    disposableAttr.destroy();
                }
                catch (Exception e) {
                    LOG.warn("Exception while destroying HttpSession bound bean. name='" + attrName + "', bean='" + disposableAttr + "'", e);
                }
            }
        }
    }
}
