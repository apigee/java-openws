/*
 * Copyright 2008 Members of the EGEE Collaboration.
 * Copyright 2008 University Corporation for Advanced Internet Development, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.ws.wstrust.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.xml.io.MarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

/**
 * An abstract marshaller implementation for XMLObjects from {@link org.opensaml.ws.wstrust}.
 * 
 * @author Valery Tschopp &lt;tschopp@switch.ch&gt;
 * @version $Revision$
 */
public abstract class AbstractWSTrustObjectMarshaller extends AbstractXMLObjectMarshaller {

    /** Logger */
    private final Logger log = LoggerFactory.getLogger(AbstractWSTrustObjectMarshaller.class);

    /**
     * Constructor.
     * <p>
     * {@inheritDoc}
     */
    protected AbstractWSTrustObjectMarshaller() {
        super();
    }

    /*
     * No-op method. Extending implementations should override this method if they have attributes to marshall into the
     * Element.
     * 
     * @see org.opensaml.xml.io.AbstractXMLObjectMarshaller#marshallAttributes(org.opensaml.xml.XMLObject,
     *      org.w3c.dom.Element)
     */
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        log.debug("{} has no more attribute to marshall.", xmlObject.getElementQName().getLocalPart());

    }

    /*
     * No-op method. Extending implementations should override this method if they have text content to marshall into
     * the Element.
     * 
     * @see org.opensaml.xml.io.AbstractXMLObjectMarshaller#marshallElementContent(org.opensaml.xml.XMLObject,
     *      org.w3c.dom.Element)
     */
    protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
        log.debug("{} has no content to marshall.", xmlObject.getElementQName().getLocalPart());
    }
}
