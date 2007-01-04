/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.debug.internal.ui.views.launch;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.core.IExpressionManager;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IExpression;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IMemoryBlockRetrieval;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.internal.ui.elements.adapters.AsynchronousDebugLabelAdapter;
import org.eclipse.debug.internal.ui.elements.adapters.DebugTargetContentAdapter;
import org.eclipse.debug.internal.ui.elements.adapters.MemoryBlockContentAdapter;
import org.eclipse.debug.internal.ui.elements.adapters.MemoryBlockLabelAdapter;
import org.eclipse.debug.internal.ui.elements.adapters.MemoryRetrievalContentAdapter;
import org.eclipse.debug.internal.ui.elements.adapters.MemorySegmentLabelAdapter;
import org.eclipse.debug.internal.ui.elements.adapters.StackFrameSourceDisplayAdapter;
import org.eclipse.debug.internal.ui.elements.adapters.VariableColumnFactoryAdapter;
import org.eclipse.debug.internal.ui.model.elements.DebugElementLabelProvider;
import org.eclipse.debug.internal.ui.model.elements.DebugTargetContentProvider;
import org.eclipse.debug.internal.ui.model.elements.ExpressionContentProvider;
import org.eclipse.debug.internal.ui.model.elements.ExpressionLabelProvider;
import org.eclipse.debug.internal.ui.model.elements.ExpressionManagerContentProvider;
import org.eclipse.debug.internal.ui.model.elements.ExpressionsViewMementoProvider;
import org.eclipse.debug.internal.ui.model.elements.LaunchContentProvider;
import org.eclipse.debug.internal.ui.model.elements.LaunchManagerContentProvider;
import org.eclipse.debug.internal.ui.model.elements.MemoryBlockContentProvider;
import org.eclipse.debug.internal.ui.model.elements.MemoryBlockLabelProvider;
import org.eclipse.debug.internal.ui.model.elements.MemoryRetrievalContentProvider;
import org.eclipse.debug.internal.ui.model.elements.MemoryViewElementMementoProvider;
import org.eclipse.debug.internal.ui.model.elements.RegisterGroupContentProvider;
import org.eclipse.debug.internal.ui.model.elements.RegisterGroupLabelProvider;
import org.eclipse.debug.internal.ui.model.elements.StackFrameContentProvider;
import org.eclipse.debug.internal.ui.model.elements.ThreadContentProvider;
import org.eclipse.debug.internal.ui.model.elements.VariableContentProvider;
import org.eclipse.debug.internal.ui.model.elements.VariableEditor;
import org.eclipse.debug.internal.ui.model.elements.VariableLabelProvider;
import org.eclipse.debug.internal.ui.model.elements.VariablesViewElementMementoProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IColumnPresentationFactoryAdapter;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementContentProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementEditor;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementLabelProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementMementoProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelProxyFactoryAdapter;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelSelectionPolicyFactoryAdapter;
import org.eclipse.debug.internal.ui.viewers.provisional.IAsynchronousContentAdapter;
import org.eclipse.debug.internal.ui.viewers.provisional.IAsynchronousLabelAdapter;
import org.eclipse.debug.internal.ui.viewers.provisional.IColumnEditorFactoryAdapter;
import org.eclipse.debug.internal.ui.viewers.update.DefaultModelProxyFactory;
import org.eclipse.debug.internal.ui.viewers.update.DefaultModelSelectionPolicyFactory;
import org.eclipse.debug.internal.ui.views.memory.renderings.MemorySegment;
import org.eclipse.debug.ui.sourcelookup.ISourceDisplay;

/**
 * DebugElementAdapterFactory
 */
public class DebugElementAdapterFactory implements IAdapterFactory {
	
	private static IModelProxyFactoryAdapter fgModelProxyFactoryAdapter = new DefaultModelProxyFactory();
	private static ISourceDisplay fgStackFrameSourceDisplayAdapter = new StackFrameSourceDisplayAdapter();
	private static IModelSelectionPolicyFactoryAdapter fgModelSelectionPolicyFactoryAdapter = new DefaultModelSelectionPolicyFactory();
    
    private static IAsynchronousLabelAdapter fgDebugLabelAdapter = new AsynchronousDebugLabelAdapter();
    private static IAsynchronousLabelAdapter fgMemoryBlockLabelAdapter = new MemoryBlockLabelAdapter();
    private static IAsynchronousLabelAdapter fgTableRenderingLineLabelAdapter = new MemorySegmentLabelAdapter();
    
    private static IElementLabelProvider fgLPDebugElement = new DebugElementLabelProvider();
    private static IElementLabelProvider fgLPVariable = new VariableLabelProvider();
    private static IElementLabelProvider fgLPExpression = new ExpressionLabelProvider();
    private static IElementLabelProvider fgLPRegisterGroup = new RegisterGroupLabelProvider();
    private static IElementLabelProvider fgLPMemoryBlock = new MemoryBlockLabelProvider();
    
    private static IElementEditor fgEEVariable = new VariableEditor();
    
    private static IAsynchronousContentAdapter fgAsyncTarget = new DebugTargetContentAdapter();
    private static IAsynchronousContentAdapter fgAsyncMemoryRetrieval = new MemoryRetrievalContentAdapter();
    private static IAsynchronousContentAdapter fgAsyncMemoryBlock = new MemoryBlockContentAdapter();
    
    private static IElementContentProvider fgCPLaunchManger = new LaunchManagerContentProvider();
    private static IElementContentProvider fgCPLaunch = new LaunchContentProvider();
    private static IElementContentProvider fgCPTarget = new DebugTargetContentProvider();
    private static IElementContentProvider fgCPThread = new ThreadContentProvider();
    private static IElementContentProvider fgCPFrame = new StackFrameContentProvider();
    private static IElementContentProvider fgCPVariable = new VariableContentProvider();
    private static IElementContentProvider fgCPExpressionManager = new ExpressionManagerContentProvider();
    private static IElementContentProvider fgCPExpression = new ExpressionContentProvider();
    private static IElementContentProvider fgCPRegisterGroup = new RegisterGroupContentProvider();
    private static IElementContentProvider fgCPMemoryRetrieval = new MemoryRetrievalContentProvider();
    private static IElementContentProvider fgCPMemoryBlock = new MemoryBlockContentProvider();
    
    private static IElementMementoProvider fgMPFrame = new VariablesViewElementMementoProvider();
    private static IElementMementoProvider fgMPExpressions = new ExpressionsViewMementoProvider();
    private static IElementMementoProvider fgMPMemory = new MemoryViewElementMementoProvider();
    
    private static IColumnPresentationFactoryAdapter fgVariableColumnFactory = new VariableColumnFactoryAdapter();
    

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
     */
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adapterType.isInstance(adaptableObject)) {
			return adaptableObject;
		}
        
        if (adapterType.equals(IAsynchronousContentAdapter.class)) {
            if (adaptableObject instanceof IDebugTarget) {
                return fgAsyncTarget;
            }
            if (adaptableObject instanceof IMemoryBlockRetrieval) {
            	return fgAsyncMemoryRetrieval;
            }
            if (adaptableObject instanceof IMemoryBlock) {
            	return fgAsyncMemoryBlock;
            }
        }
        
        if (adapterType.equals(IElementContentProvider.class)) {
            if (adaptableObject instanceof ILaunchManager) {
                return fgCPLaunchManger;
            }
            if (adaptableObject instanceof ILaunch) {
                return fgCPLaunch;
            }
            if (adaptableObject instanceof IDebugTarget) {
            	return fgCPTarget;
            }
            if (adaptableObject instanceof IMemoryBlockRetrieval)
            {
            	return fgCPMemoryRetrieval;
            }
            if (adaptableObject instanceof IThread) {
            	return fgCPThread;
            }
            if (adaptableObject instanceof IStackFrame) {
            	return fgCPFrame;
            }
            if (adaptableObject instanceof IVariable) {
            	return fgCPVariable;
            }
            if (adaptableObject instanceof IExpressionManager) {
            	return fgCPExpressionManager;
            }
            if (adaptableObject instanceof IExpression) {
            	return fgCPExpression;
            }
            if (adaptableObject instanceof IRegisterGroup) {
            	return fgCPRegisterGroup;
            }
            if (adaptableObject instanceof IMemoryBlock) {
            	return fgCPMemoryBlock;
            }
        }        
        
        if (adapterType.equals(IAsynchronousLabelAdapter.class)) {
        	if (adaptableObject instanceof IMemoryBlock) {
        		return fgMemoryBlockLabelAdapter;
        	}
        	
        	if (adaptableObject instanceof MemorySegment) {
        		return fgTableRenderingLineLabelAdapter;
        	}
        	return fgDebugLabelAdapter;
        }
        
        if (adapterType.equals(IElementLabelProvider.class)) {
        	if (adaptableObject instanceof IVariable) {
        		return fgLPVariable;
        	}
        	if (adaptableObject instanceof IExpression) {
        		return fgLPExpression;
        	}
        	if (adaptableObject instanceof IRegisterGroup) {
        		return fgLPRegisterGroup;
        	}
        	if (adaptableObject instanceof IMemoryBlock) {
        		return fgLPMemoryBlock;
        	}
        	return fgLPDebugElement;
        }        
        
        if (adapterType.equals(IModelProxyFactoryAdapter.class)) {
        	if (adaptableObject instanceof ILaunch || adaptableObject instanceof IDebugTarget ||
        			adaptableObject instanceof IProcess || adaptableObject instanceof ILaunchManager ||
        			adaptableObject instanceof IStackFrame || adaptableObject instanceof IExpressionManager ||
        			adaptableObject instanceof IExpression || adaptableObject instanceof IMemoryBlockRetrieval ||
        			adaptableObject instanceof IMemoryBlock)
        	return fgModelProxyFactoryAdapter;
        }
        
        if (adapterType.equals(ISourceDisplay.class)) {
        	if (adaptableObject instanceof IStackFrame) {
        		return fgStackFrameSourceDisplayAdapter;
        	}
        }
        
        if (adapterType.equals(IModelSelectionPolicyFactoryAdapter.class)) {
        	if (adaptableObject instanceof IDebugElement) {
        		return fgModelSelectionPolicyFactoryAdapter;
        	}
        }
        
        if (adapterType.equals(IColumnPresentationFactoryAdapter.class)) {
        	if (adaptableObject instanceof IStackFrame) {
        		return fgVariableColumnFactory;
        	}
        }
        
        if (adapterType.equals(IColumnEditorFactoryAdapter.class)) {
        	if (adaptableObject instanceof IVariable) {
        		return fgVariableColumnFactory;
        	}
        }     
        
        if (adapterType.equals(IElementMementoProvider.class)) {
        	if (adaptableObject instanceof IStackFrame) {
        		return fgMPFrame;
        	}
        	if (adaptableObject instanceof IExpressionManager) {
        		return fgMPExpressions;
        	}
        	if (adaptableObject instanceof IMemoryBlockRetrieval) {
        		return fgMPMemory;
        	}
        }
        
        if (adapterType.equals(IElementEditor.class)) {
        	if (adaptableObject instanceof IVariable) {
        		return fgEEVariable;
        	}
        }
        
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     */
    public Class[] getAdapterList() {
        return new Class[] {
        		IAsynchronousLabelAdapter.class,
        		IAsynchronousContentAdapter.class,
        		IModelProxyFactoryAdapter.class,
        		ISourceDisplay.class,
        		IModelSelectionPolicyFactoryAdapter.class,
        		IColumnPresentationFactoryAdapter.class,
        		IColumnEditorFactoryAdapter.class,
        		IElementContentProvider.class,
        		IElementLabelProvider.class,
        		IElementMementoProvider.class,
        		IElementEditor.class};
    }

}