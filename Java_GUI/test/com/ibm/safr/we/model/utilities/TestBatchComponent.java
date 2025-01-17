package com.ibm.safr.we.model.utilities;

/*
 * Copyright Contributors to the GenevaERS Project. SPDX-License-Identifier: Apache-2.0 (c) Copyright IBM Corporation 2008.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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


import java.util.logging.Logger;

import junit.framework.TestCase;

import com.ibm.safr.we.constants.ActivityResult;
import com.ibm.safr.we.data.TestDataLayerHelper;
import com.ibm.safr.we.model.query.ControlRecordQueryBean;

public class TestBatchComponent extends TestCase {
	static transient Logger logger = Logger
			.getLogger("com.ibm.safr.we.model.TestBatchComponent");

	TestDataLayerHelper helper = new TestDataLayerHelper();

	public void dbStartup() {
		helper.initDataLayer();

	}

	public void tearDown() {

		helper.closeDataLayer();
	}

	public void testGetComponent() {
		dbStartup();

		BatchComponent batchComponent = new BatchComponent(null, null);
		assertNull(batchComponent.getComponent());

		batchComponent = new BatchComponent(new ControlRecordQueryBean(1, 0,
				"Test", null, null, "", null, ""), null);
		assertNotNull(batchComponent.getComponent());
	}

	public void testGetSetResult() {
		dbStartup();

		BatchComponent batchComponent = new BatchComponent(null, null);
		batchComponent.setResult(ActivityResult.PASS);
		assertEquals(ActivityResult.PASS, batchComponent.getResult());

		batchComponent.setResult(ActivityResult.FAIL);
		assertEquals(ActivityResult.FAIL, batchComponent.getResult());

		batchComponent.setResult(ActivityResult.LOADERRORS);
		assertEquals(ActivityResult.LOADERRORS, batchComponent.getResult());
	}

	public void testGetSetActive() {
		dbStartup();

		BatchComponent batchComponent = new BatchComponent(null, null);
		batchComponent.setActive(true);
		assertTrue(batchComponent.isActive());

		batchComponent.setActive(false);
		assertFalse(batchComponent.isActive());

	}

	public void testGetSetSelected() {
		dbStartup();

		BatchComponent batchComponent = new BatchComponent(null, null);
		batchComponent.setSelected(true);
		assertTrue(batchComponent.isSelected());

		batchComponent.setSelected(false);
		assertFalse(batchComponent.isSelected());

	}
}
