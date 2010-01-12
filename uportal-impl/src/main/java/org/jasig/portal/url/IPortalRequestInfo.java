/**
 * Copyright (c) 2000-2009, Jasig, Inc.
 * See license distributed with this file and available online at
 * https://www.ja-sig.org/svn/jasig-parent/tags/rel-10/license-header.txt
 */
package org.jasig.portal.url;

import org.jasig.portal.portlet.om.IPortletWindowId;

/**
 * Provides information about the portal request.
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public interface IPortalRequestInfo {
    /**
     * @return The state rendered by the URL
     */
    public UrlState getUrlState();
    
    /**
     * @return The layout node being targeted by the request. If the request isn't targeting a particular layout node null is returned.
     */
    public String getTargetedLayoutNodeId();
    
    /**
     * @return The channel being targeted by the request. If the request isn't targeting a particular channel null is returned.
     */
    public String getTargetedChannelSubscribeId();
    
    /**
     * @return The portlet window being targeted by the request, If the request isn't targeting a particular channel null is returned.
     */
    public IPortletWindowId getTargetedPortletWindowId();
    
    /**
     * @return true if the request represents an action, false if it represents a render.
     */
    public boolean isAction();
}