/**
 *
 */
package com.ceea.facades.populators;

import de.hybris.platform.commercefacades.user.converters.populator.CustomerPopulator;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;

import org.springframework.util.Assert;


/**
 * @author FCG1KOR
 *
 */
public class CeeaCustomerPopulator extends CustomerPopulator
{
	@Resource
	private Converter<AddressModel, AddressData> addressConverter;

	@Override
	public void populate(final CustomerModel source, final CustomerData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");


		if (!source.getAddresses().isEmpty())
		{
			target.setAddresses(addressConverter.convertAll(source.getAddresses()));
		}
		//super.populate(source, target);
	}
}
