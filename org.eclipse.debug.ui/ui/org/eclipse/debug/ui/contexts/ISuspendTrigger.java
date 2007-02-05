/*******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.debug.ui.contexts;

/**
 * A suspend trigger notifies listeners when a launch suspends at a context
 * where debugging should begin. For example, when a breakpoint is encountered.
 * <p>
 * The debug platform retrieves a suspend trigger from each registered launch
 * and listens to suspend notifications in order to initiate debug sessions - i.e.
 * switch to the desired perspective, activate the debug view, etc., based on user
 * preferences. The debug platform asks each registered launch for its suspend
 * trigger adapter or registers with the launch itself if it implements
 * <code>ISuspendTrigger</code>.
 * </p>  
 * <p>
 * It is important that the same instance of a suspend trigger adapter is
 * returned each time it is asked for the same object, such that listeners
 * can be added and removed from the same instance. When a listener is removed
 * and no more listeners are registered, this trigger can be disposed or replaced
 * with a new adapter the next time one is requested. 
 * </p>
 * <p>
 * Clients may implement this interface. The debug platform provides a suspend trigger
 * adapter for implementations of <code>ILaunch</code>. The implementation provided by 
 * the platform is based on a standard debug model that fires debug events. Clients
 * wishing to provide their own implementation must also provide their own implementation
 * of <code>ILaunch</code> (or subclass of <code>Launch</code>), in order to register
 * their suspend trigger adapter.
 * </p>
 * @see ISuspendTriggerListener
 * @since 3.3
 */
public interface ISuspendTrigger {
	
    /**
     * Registers the given listener for suspend notifications.
     * 
     * @param listener suspend listener
     */
	public void addSuspendTriggerListener(ISuspendTriggerListener listener);
    
    /**
     * Unregisters the given listener for suspend notifications.
     * 
     * @param listener suspend listener
     */
	public void removeSuspendTriggerListener(ISuspendTriggerListener listener);
	
}