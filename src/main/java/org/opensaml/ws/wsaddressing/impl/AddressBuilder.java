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
package org.opensaml.ws.wsaddressing.impl;

import org.opensaml.ws.wsaddressing.Address;

/**
 * AddressBuilder
 * 
 */
public class AddressBuilder extends AbstractWSAddressingObjectBuilder<Address> {

    /*
     * (non-Javadoc)
     * 
     * @see org.opensaml.ws.wsaddressing.impl.AbstractWSAddressObjectBuilder#buildObject()
     */
    public Address buildObject() {
        return buildObject(Address.ELEMENT_NAME);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opensaml.xml.AbstractXMLObjectBuilder#buildObject(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public Address buildObject(String namespaceURI, String localName,
            String namespacePrefix) {
        return new AddressImpl(namespaceURI, localName, namespacePrefix);
    }

}
