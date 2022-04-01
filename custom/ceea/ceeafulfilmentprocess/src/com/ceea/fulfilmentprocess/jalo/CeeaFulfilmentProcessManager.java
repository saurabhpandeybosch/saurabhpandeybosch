/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.ceea.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.ceea.fulfilmentprocess.constants.CeeaFulfilmentProcessConstants;

public class CeeaFulfilmentProcessManager extends GeneratedCeeaFulfilmentProcessManager
{
	public static final CeeaFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (CeeaFulfilmentProcessManager) em.getExtension(CeeaFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
