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


import org.opensaml.ws.wsaddressing.Action;
import org.opensaml.xml.XMLObject;

/**
 * Unmarshaller for the &lt;wsa:Action&gt; element.
 * 
 * @see Action
 * 
 * @author Valery Tschopp &lt;tschopp@switch.ch&gt;
 * @version $Revision$
 */
public class ActionUnmarshaller extends AbstractWSAddressingObjectUnmarshaller {

    /**
     * Default constructor.
     * <p>
     * {@inheritDoc}
     */
    public ActionUnmarshaller() {
        super(Action.ELEMENT_NAME.getNamespaceURI(),
              Action.ELEMENT_NAME.getLocalPart());
    }

    /**
     * Unmarshalls the &lt;wsa:Action&gt; element content.
     * <p>
     * {@inheritDoc}
     */
    @Override
    protected void processElementContent(XMLObject xmlObject,
            String elementContent) {
        if (elementContent != null) {
            Action action= (Action) xmlObject;
            action.setValue(elementContent);
        }
    }

}
