/**
 * Licensed to Apereo under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. Apereo
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at the
 * following location:
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apereo.portal.groups.pags.testers;

import org.apereo.portal.groups.pags.dao.IPersonAttributesGroupTestDefinition;

/**
 * A tester for examining if an <code>IPerson</code> attribute has less than nth values.
 *
 * @author GIP RECIA - Julien Gribonvald
 * @since 5.0
 */
public final class NbValuesLTTester extends AbstractNbValuesTester {

    public NbValuesLTTester(IPersonAttributesGroupTestDefinition definition) {
        super(definition);
    }

    @Override
    protected boolean test(int numValues) {
        return numValues < getTestInteger();
    }

}
