/****************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *	Marty Jones <martybjones@gmail.com> - initial API and implementation
 *  Enrico Schnepel <enrico.schnepel@randomice.net> - added logic to fix bug 304353
 *****************************************************************************/
package org.eclipse.nebula.jface.tablecomboviewer;

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


import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.ViewerRow;
import org.eclipse.nebula.widgets.tablecombo.TableCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;



/**
 * TableComboViewerRow is basically identical to the TableViewer class with a
 * few modifications to reference the Table within the TableCombo widget 
 * instead of a parent Table widget.
  */
public class TableComboViewer extends AbstractTableViewer {
	
	private TableCombo tableCombo;

	/**
	 * The cached row which is reused all over
	 */
	private TableComboViewerRow cachedRow;
	
	
	public TableComboViewer(Composite parent) {
		this(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	}

	/**
	 * Creates a table viewer on a newly-created table control under the given
	 * parent. The table control is created using the given style bits. The
	 * viewer has no input, no content provider, a default label provider, no
	 * sorter, and no filters. The table has no columns.
	 * 
	 * @param parent
	 * 		the parent control
	 * @param style
	 * 		SWT style bits
	 */
	public TableComboViewer(Composite parent, int style) {
		this(new TableCombo(parent, style));
	}

	/**
	 * Creates a table viewer on the given table control. The viewer has no
	 * input, no content provider, a default label provider, no sorter, and no
	 * filters.
	 * 
	 * @param table
	 * 		the table control
	 */
	public TableComboViewer(TableCombo tableCombo) {
		this.tableCombo = tableCombo;
		hookControl(tableCombo);
	}	

    /**
     * {@inheritDoc}
     */	
	protected void doClear(int index) {
		tableCombo.getTable().clear(index);
		
	}

    /**
     * {@inheritDoc}
     */	
	protected void doClearAll() {
		tableCombo.getTable().clearAll();
		
	}

    /**
     * {@inheritDoc}
     */	
	protected void doDeselectAll() {
		tableCombo.getTable().deselectAll();
		
	}

    /**
     * {@inheritDoc}
     */	
	protected Widget doGetColumn(int index) {
		return tableCombo.getTable().getColumn(index);
	}

    /**
     * {@inheritDoc}
     */	
	protected Item doGetItem(int index) {
		return tableCombo.getTable().getItem(index);
	}

    /**
     * {@inheritDoc}
     */	
	protected int doGetItemCount() {
		return tableCombo.getTable().getItemCount();
	}

    /**
     * {@inheritDoc}
     */
	protected Item[] doGetItems() {
		return tableCombo.getTable().getItems();
	}

    /**
     * {@inheritDoc}
     */
	protected Item[] doGetSelection() {
		return tableCombo.getTable().getSelection();
	}

    /**
     * {@inheritDoc}
     */
	protected int[] doGetSelectionIndices() {
		return tableCombo.getTable().getSelectionIndices();
	}

    /**
     * {@inheritDoc}
     */	
	protected int doIndexOf(Item item) {
		return tableCombo.getTable().indexOf((TableItem)item);
	}

    /**
     * {@inheritDoc}
     */
	protected void doRemove(int[] indices) {
		tableCombo.getTable().remove(indices);
	}

    /**
     * {@inheritDoc}
     */
	protected void doRemove(int start, int end) {
		tableCombo.getTable().remove(start, end);
	}

    /**
     * {@inheritDoc}
     */	
	protected void doRemoveAll() {
		tableCombo.getTable().removeAll();
	}

    /**
     * {@inheritDoc}
     */
	protected void doResetItem(Item item) {
		TableItem tableItem = (TableItem) item;
		int columnCount = Math.max(1, tableCombo.getTable().getColumnCount());
		for (int i = 0; i < columnCount; i++) {
			tableItem.setText(i, ""); //$NON-NLS-1$
			if (tableItem.getImage(i) != null) {
				tableItem.setImage(i, null);
			}
		}
	}

    /**
     * {@inheritDoc}
     */	
	protected void doSelect(int[] indices) {
		tableCombo.select(indices != null && indices.length > 0 ? indices[0] : -1);
	}

    /**
     * {@inheritDoc}
     */	
	protected void doSetItemCount(int count) {
		tableCombo.getTable().setItemCount(count);
	}

    /**
     * {@inheritDoc}
     */	
	protected void doSetSelection(Item[] items) {
		if (items != null && items.length > 0) {
			tableCombo.select(tableCombo.getTable().indexOf((TableItem)items[0]));
		}
		else {
			tableCombo.select(-1);
		}
	}

    /**
     * {@inheritDoc}
     */
	protected void doSetSelection(int[] indices) {
		tableCombo.select(indices != null && indices.length > 0 ? indices[0] : -1);
	}

    /**
     * {@inheritDoc}
     */	
	protected void doShowItem(Item item) {
		tableCombo.getTable().showItem((TableItem)item);
	}

    /**
     * {@inheritDoc}
     */
	protected void doShowSelection() {
		tableCombo.getTable().showSelection();
	}

    /**
     * {@inheritDoc}
     */
	protected ViewerRow internalCreateNewRowPart(int style, int rowIndex) {
		TableItem item;

		if (rowIndex >= 0) {
			item = new TableItem(tableCombo.getTable(), style, rowIndex);
		} else {
			item = new TableItem(tableCombo.getTable(), style);
		}

		return getViewerRowFromItem(item);
	}

    /**
     * {@inheritDoc}
     */
	protected ColumnViewerEditor createViewerEditor() {
		return null;
	}

    /**
     * {@inheritDoc}
     */
	protected int doGetColumnCount() {
		return tableCombo.getTable().getColumnCount();
	}

    /**
     * {@inheritDoc}
     */
	protected Item getItemAt(Point point) {
		return tableCombo.getTable().getItem(point);
	}

    /**
     * {@inheritDoc}
     */
	protected ViewerRow getViewerRowFromItem(Widget item) {
		if (cachedRow == null) {
			cachedRow = new TableComboViewerRow((TableItem) item);
		} else {
			cachedRow.setItem((TableItem) item);
		}

		return cachedRow;
	}

    /**
     * {@inheritDoc}
     */
	public Control getControl() {
		return tableCombo;
	}

	/**
	 * returns the TableCombo reference.
	 * @return
	 */
	public TableCombo getTableCombo() {
		return tableCombo;
	}

    /**
     * {@inheritDoc}
     */
	protected void handleLabelProviderChanged(LabelProviderChangedEvent event) {
		super.handleLabelProviderChanged(event);
		setSelection(getSelection());
	}
}
