/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.ceea.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.ceea.core.constants.CeeaCoreConstants;
import com.ceea.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class CeeaCoreManager extends GeneratedCeeaCoreManager
{
	public static final CeeaCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (CeeaCoreManager) em.getExtension(CeeaCoreConstants.EXTENSIONNAME);
	}
}
