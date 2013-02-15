/*******************************************************************************
 * Copyright (c) 2013 Wind River Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Wind River Systems - initial API and implementation
 *******************************************************************************/
package org.eclipse.debug.ui.contexts;


/**
 * Debug context provider that allows a view to create a "pin" control for a 
 * data view.  
 * 
 * @since 3.9
 * @see IPinnedContextFactory
 * @see IDebugContextManager#getPinnedContextViewerFactory(String)
 */
public interface IPinnableDebugContextProvider extends IDebugContextProvider {
	
	/**
	 * Returns the ID for the factory for a pinned context viewer.
	 * The factory creates a viewer that pins the view to the active context
	 * in the given debug context provider.
	 */
    public String getFactoryId();
}