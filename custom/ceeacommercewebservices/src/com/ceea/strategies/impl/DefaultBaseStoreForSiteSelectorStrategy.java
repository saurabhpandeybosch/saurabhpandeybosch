/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.ceea.strategies.impl;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.store.BaseStoreModel;
import com.ceea.strategies.BaseStoreForSiteSelectorStrategy;


/**
 * Default implementation of {@link BaseStoreForSiteSelectorStrategy} which returns first base store from collection.
 */
public class DefaultBaseStoreForSiteSelectorStrategy implements BaseStoreForSiteSelectorStrategy
{

	@Override
	public BaseStoreModel getBaseStore(final BaseSiteModel baseSiteModel)
	{
		return baseSiteModel.getStores().get(0);
	}
}
