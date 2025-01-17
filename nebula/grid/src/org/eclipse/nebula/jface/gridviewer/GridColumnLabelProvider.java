/*******************************************************************************
 * Copyright (c) 2007 Tom Schindl and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl - initial API and implementation
 *     Claes Rosell<claes.rosell@solme.se> - rowspan in bug 272384
 *******************************************************************************/

package org.eclipse.nebula.jface.gridviewer;

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


import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridItem;

/**
 * A label provider that provides hooks for extra functionality in the {@link Grid}.  This is currently
 * limited to supplying the row header text.
 * <p>
 * <b> Only one from all {@link GridColumnLabelProvider} in a viewer should
 * return a none <code>null</code></b>
 * </p>
 */
public class GridColumnLabelProvider extends ColumnLabelProvider {

	/**
	 * Returns the row header text for this element.
	 *
	 * @param element
	 *            the model element
	 * @return the text displayed in the row-header or <code>null</code> if
	 *         this label provider would not like to modify the default text
	 */
	public String getRowHeaderText(Object element) {
		return null;
	}

	/**
	 * Returns the number of columns this element should span
	 *
	 * @param element
	 *            the model element
	 * @return colSpan
	 */
	public int getColumnSpan(Object element)
	{
		return 0;
	}

	/**
	 * Returns the number of rows this element should span
	 *
	 * @param element
	 *            the model element
	 * @return rowSpan
	 */
	public int getRowSpan(Object element)
	{
		return 0;
	}


	/**
	 * {@inheritDoc}
	 */
	public void update(ViewerCell cell) {
		super.update(cell);

		Object element = cell.getElement();

		String rowText = getRowHeaderText(element);
		int colSpan = getColumnSpan(element);
		int rowSpan = getRowSpan(element);

		GridItem gridItem = (GridItem)cell.getViewerRow().getItem();
		if (rowText != null) {
			gridItem.setHeaderText(rowText);
		}

		gridItem.setColumnSpan(cell.getColumnIndex(), colSpan);
		gridItem.setRowSpan(cell.getColumnIndex(), rowSpan);
	}

}
