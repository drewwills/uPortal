/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package edu.illinois.my.portal.groups.smartldap;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import javax.naming.directory.Attribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portal.groups.EntityTestingGroupImpl;
import org.jasig.portal.groups.IEntityGroup;
import org.jasig.portal.groups.smartldap.LdapRecord;
import org.jasig.portal.security.IPerson;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;

public final class SimpleContextMapper implements ContextMapper {

    private static final String GROUP_DESCRIPTION =
            "This group was pulled from the directory server.";
    /**
     * Indicator on converting RDN keys to uppercase 
     * normally true for active directory and false otherwise
     */
    private boolean convertRdnKeysToUpperCase = false;
    /**
     * Name of the LDAP attribute on a group that tells you 
     * the name of the group.
     */
    private String groupNameAttributeName = null;
    /**
     * Name of the LDAP attribute on a group that tells you 
     * who its children are.
     */
    private String membershipAttributeName = null;
    private final Log log = LogFactory.getLog(getClass());

    /*
     * Public API.
     */
    public Object mapFromContext(Object ctx) {
        // Assertions.
        if (groupNameAttributeName == null) {
            String msg = "The property 'groupNameAttributeName' must be set.";
            throw new IllegalStateException(msg);
        }
        if (membershipAttributeName == null) {
            String msg = "The property 'membershipAttributeName' must be set.";
            throw new IllegalStateException(msg);
        }

        if (log.isDebugEnabled()) {
            String msg = "SimpleContextMapper.mapFromContext() :: settings:  convertRdnKeysToUpperCase='" + convertRdnKeysToUpperCase + "', groupNameAttributeName='" + groupNameAttributeName + "', groupNameAttributeName='" + groupNameAttributeName + "'";
            log.debug(msg);
        }

        LdapRecord rslt;

        try {
            DirContextAdapter context = (DirContextAdapter) ctx;

            DistinguishedName groupKeyDn = (DistinguishedName) context.getDn();
            StringBuilder dnBuilder = new StringBuilder();
            for (int i=groupKeyDn.size()-1;i>=0; i--) {
                if (convertRdnKeysToUpperCase) {
                    dnBuilder.append(groupKeyDn.getLdapRdn(i).getKey().toUpperCase());
                } else {
                    dnBuilder.append(groupKeyDn.getLdapRdn(i).getKey());
                }
                dnBuilder.append('=').append(groupKeyDn.getLdapRdn(i).getValue());
                if (i !=0) {
                    dnBuilder.append(',');
                }
            }
        String key = dnBuilder.toString();
        if (log.isDebugEnabled()) {
        log.debug("SmartLdap ContextMapper DN: " + key);
        }
        String groupName = (String) context.getStringAttribute(groupNameAttributeName);

        IEntityGroup g = new EntityTestingGroupImpl(key, IPerson.class);
        g.setCreatorID("System");
        g.setName(groupName);
        g.setDescription(GROUP_DESCRIPTION);
        List<String> membership = new LinkedList<String>();
        Attribute m = context.getAttributes().get(membershipAttributeName);
        if (m != null) {
            for (Enumeration<?> en = m.getAll(); en.hasMoreElements();) {
                membership.add((String) en.nextElement());
            }
        }
        rslt = new LdapRecord(g, membership);

        if (log.isDebugEnabled()) {
            StringBuilder msg = new StringBuilder();
            msg.append("Record Details:").append("\n\tkey=").append(key).append("\n\tgroupName=").append(groupName).append("\n\tmembers:");
            for (String s : membership) {
                msg.append("\n\t\t").append(s);
            }
            log.debug(msg.toString());
        }

    }
    catch
        (Throwable t) {
            log.error("Error in SimpleContextMapper", t);
        String msg = "SimpleContextMapper failed to create a LdapRecord from the specified Context: " + ctx;
        throw new RuntimeException(msg, t);
    }
    return
    rslt ;
}

    public void setConvertRdnKeysToUpperCase(boolean convertRdnKeysToUpperCase) {
        this.convertRdnKeysToUpperCase = convertRdnKeysToUpperCase;
    }
public void

setGroupNameAttributeName(String groupNameAttributeName) {
        this.groupNameAttributeName = groupNameAttributeName;
    }

public void

setMembershipAttributeName(String membershipAttributeName) {
        this.membershipAttributeName = membershipAttributeName;
    }
}
