/*
 * Copyright 2009 University Corporation for Advanced Internet Development, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.ws.soap.soap11;

import javax.xml.namespace.QName;

import org.opensaml.ws.soap.util.SOAPConstants;

/**
 * Interface for element having a <code>@soap11:actor</code> attribute.
 */
public interface ActorBearing {
    
    /** The soap11:@actor attribute local name. */
    public static final String SOAP11_ACTOR_ATTR_LOCAL_NAME = "actor";

    /** The soap11:@actor qualified attribute name. */
    public static final QName SOAP11_ACTOR_ATTR_NAME =
        new QName(SOAPConstants.SOAP11_NS, SOAP11_ACTOR_ATTR_LOCAL_NAME, SOAPConstants.SOAP11_PREFIX);
    
    /**
     * Get the attribute value.
     * 
     * @return return the attribute vlue
     */
    public String getSOAP11Actor();
    
    /**
     * Set the attribute value.
     * 
     * @param newActor the new attribute value
     */
    public void setSOAP11Actor(String newActor);

}
