/**
 *
 */
package de.hybris.ceea.facades;

import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


/**
 * @author FCG1KOR
 *
 */
public interface CeeaCustomerFacade
{

	/**
	 * @return
	 */
	CustomerData getCurrentCustomer() throws ConversionException;

	/**
	 * @return
	 */
	String getCurrentCustomerUid();

}
