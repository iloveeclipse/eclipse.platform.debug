/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.debug.internal.ui.model.elements;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.debug.internal.ui.views.DebugModelPresentationContext;
import org.eclipse.debug.internal.ui.views.launch.DebugElementHelper;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

/**
 * @since 3.3
 */
public class DebugElementLabelProvider extends ElementLabelProvider {

	protected String getLabel(TreePath elementPath, IPresentationContext presentationContext, String columnId) throws CoreException {
		Object element = elementPath.getLastSegment();
		if (presentationContext instanceof DebugModelPresentationContext) {
			DebugModelPresentationContext debugContext = (DebugModelPresentationContext) presentationContext;
			return debugContext.getModelPresentation().getText(element);
		}
		return DebugElementHelper.getLabel(element);
	}

	protected RGB getBackground(TreePath elementPath, IPresentationContext presentationContext, String columnId) throws CoreException {
		Object element = elementPath.getLastSegment();
		if (presentationContext instanceof DebugModelPresentationContext) {
			DebugModelPresentationContext debugContext = (DebugModelPresentationContext) presentationContext;
			return DebugElementHelper.getBackground(element, debugContext.getModelPresentation());
		}
		return DebugElementHelper.getBackground(element);
	}

	protected FontData getFontData(TreePath elementPath, IPresentationContext presentationContext, String columnId) throws CoreException {
		Object element = elementPath.getLastSegment();
		if (presentationContext instanceof DebugModelPresentationContext) {
			DebugModelPresentationContext debugContext = (DebugModelPresentationContext) presentationContext;
			return DebugElementHelper.getFont(element, debugContext.getModelPresentation());
			
		}
		return DebugElementHelper.getFont(element);
	}

	protected RGB getForeground(TreePath elementPath, IPresentationContext presentationContext, String columnId) throws CoreException {
		Object element = elementPath.getLastSegment();
		if (presentationContext instanceof DebugModelPresentationContext) {
			DebugModelPresentationContext debugContext = (DebugModelPresentationContext) presentationContext;
			return DebugElementHelper.getForeground(element, debugContext.getModelPresentation());	
		}
		return DebugElementHelper.getForeground(element);
	}

	protected ImageDescriptor getImageDescriptor(TreePath elementPath, IPresentationContext presentationContext, String columnId) throws CoreException {
		Object element = elementPath.getLastSegment();
		if (presentationContext instanceof DebugModelPresentationContext) {
			DebugModelPresentationContext debugContext = (DebugModelPresentationContext) presentationContext;
			return DebugElementHelper.getImageDescriptor(element, debugContext.getModelPresentation());	
		}
		return DebugElementHelper.getImageDescriptor(element);
	}
	


}