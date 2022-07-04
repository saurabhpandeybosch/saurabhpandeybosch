/**
 *
 */
package de.hybris.ceea.facades.impl;

import de.hybris.ceea.facades.CeeaCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author FCG1KOR
 *
 */
public class DefaultCeeaCustomerFacade implements CeeaCustomerFacade
{

	private static final Logger LOG = LoggerFactory.getLogger(DefaultCeeaCustomerFacade.class);

	private Converter<UserModel, CustomerData> customerConverter;
	private UserService userService;


	public Converter<UserModel, CustomerData> getCustomerConverter()
	{
		return customerConverter;
	}

	public void setCustomerConverter(final Converter<UserModel, CustomerData> customerConverter)
	{
		this.customerConverter = customerConverter;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	@Override
	public CustomerData getCurrentCustomer()
	{
		return getCustomerConverter().convert(getCurrentUser());
	}

	protected UserModel getCurrentUser()
	{
		return getUserService().getCurrentUser();
	}

	@Override
	public String getCurrentCustomerUid()
	{
		return getCurrentUser().getUid();
	}
}
