/****************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *	Marty Jones <martybjones@gmail.com> - initial API and implementation
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


import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerRow;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;

/**
 * TableComboViewerRow is basically identical to the TableRow class with a
 * few modifications to reference the TableComboViewer row instead of a standar
 * TableViewer row.
 *
 */
/**
 * @author martyj
 *
 */
public class TableComboViewerRow extends ViewerRow {
	private TableItem item;

	/**
	 * Create a new instance of the receiver from item.
	 * @param item
	 */
	TableComboViewerRow(TableItem item) {
		this.item = item;
	}

    /**
     * {@inheritDoc}
     */
	public Rectangle getBounds(int columnIndex) {
		return item.getBounds(columnIndex);
	}

    /**
     * {@inheritDoc}
     */
	public Rectangle getBounds() {
		return item.getBounds();
	}

    /**
     * {@inheritDoc}
     */
	public Widget getItem() {
		return item;
	}

	void setItem(TableItem item) {
		this.item = item;
	}

    /**
     * {@inheritDoc}
     */
	public int getColumnCount() {
		return item.getParent().getColumnCount();
	}

    /**
     * {@inheritDoc}
     */
	public Color getBackground(int columnIndex) {
		return item.getBackground(columnIndex);
	}

    /**
     * {@inheritDoc}
     */
	public Font getFont(int columnIndex) {
		return item.getFont(columnIndex);
	}

    /**
     * {@inheritDoc}
     */
	public Color getForeground(int columnIndex) {
		return item.getForeground(columnIndex);
	}

	/**
     * {@inheritDoc}
     */
	public Image getImage(int columnIndex) {
		return item.getImage(columnIndex);
	}

    /**
     * {@inheritDoc}
     */
	public String getText(int columnIndex) {
		return item.getText(columnIndex);
	}

    /**
     * {@inheritDoc}
     */
	public void setBackground(int columnIndex, Color color) {
		item.setBackground(columnIndex, color);
	}

    /**
     * {@inheritDoc}
     */	
	public void setFont(int columnIndex, Font font) {
		item.setFont(columnIndex, font);
	}

    /**
     * {@inheritDoc}
     */	
	public void setForeground(int columnIndex, Color color) {
		item.setForeground(columnIndex, color);
	}

    /**
     * {@inheritDoc}
     */	
	public void setImage(int columnIndex, Image image) {
		Image oldImage = item.getImage(columnIndex);
		if (oldImage != image) {
			item.setImage(columnIndex,image);
		}
	}

    /**
     * {@inheritDoc}
     */	
	public void setText(int columnIndex, String text) {
		item.setText(columnIndex, text == null ? "" : text); //$NON-NLS-1$
	}

    /**
     * {@inheritDoc}
     */
	public Control getControl() {
		return item.getParent();
	}

    /**
     * {@inheritDoc}
     */
	public ViewerRow getNeighbor(int direction, boolean sameLevel) {
		if( direction == ViewerRow.ABOVE ) {
			return getRowAbove();
		} else if( direction == ViewerRow.BELOW ) {
			return getRowBelow();
		} else {
			throw new IllegalArgumentException("Illegal value of direction argument."); //$NON-NLS-1$
		}
	}

	private ViewerRow getRowAbove() {
		int index = item.getParent().indexOf(item) - 1;

		if( index >= 0 ) {
			return new TableComboViewerRow(item.getParent().getItem(index));
		}

		return null;
	}

	private ViewerRow getRowBelow() {
		int index = item.getParent().indexOf(item) + 1;

		if( index < item.getParent().getItemCount() ) {
			TableItem tmp = item.getParent().getItem(index);
			//TODO NULL can happen in case of VIRTUAL => How do we deal with that
			if( tmp != null ) {
				return new TableComboViewerRow(tmp);
			}
		}

		return null;
	}

    /**
     * {@inheritDoc}
     */	
	public TreePath getTreePath() {
		return new TreePath(new Object[] {item.getData()});
	}

    /**
     * {@inheritDoc}
     */	
	public Object clone() {
		return new TableComboViewerRow(item);
	}

    /**
     * {@inheritDoc}
     */
	public Object getElement() {
		return item.getData();
	}

    /**
     * {@inheritDoc}
     */	
	public int getVisualIndex(int creationIndex) {
		int[] order = item.getParent().getColumnOrder();

		for (int i = 0; i < order.length; i++) {
			if (order[i] == creationIndex) {
				return i;
			}
		}

		return super.getVisualIndex(creationIndex);
	}

    /**
     * {@inheritDoc}
     */	
	public int getCreationIndex(int visualIndex) {
		if( item != null && ! item.isDisposed() && hasColumns() && isValidOrderIndex(visualIndex) ) {
			return item.getParent().getColumnOrder()[visualIndex];
		}
		return super.getCreationIndex(visualIndex);
	}

    /**
     * {@inheritDoc}
     */	
	public Rectangle getTextBounds(int index) {
		return item.getTextBounds(index);
	}
	
    /**
     * {@inheritDoc}
     */
	public Rectangle getImageBounds(int index) {
		return item.getImageBounds(index);
	}

	private boolean hasColumns() {
		return this.item.getParent().getColumnCount() != 0;
	}

	private boolean isValidOrderIndex(int currentIndex) {
		return currentIndex < this.item.getParent().getColumnOrder().length;
	}
	
	protected boolean scrollCellIntoView(int columnIndex) {
		item.getParent().showItem(item);
		if( hasColumns() ) {
			item.getParent().showColumn(item.getParent().getColumn(columnIndex));	
		}
		
		return true;
	}
}
