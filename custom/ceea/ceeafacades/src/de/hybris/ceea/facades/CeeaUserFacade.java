/**
 *
 */
package de.hybris.ceea.facades;

import de.hybris.platform.commercefacades.user.data.AddressData;


/**
 * @author FCG1KOR
 *
 */
public interface CeeaUserFacade
{

	/**
	 * @param addressData
	 */
	public void addAddress(AddressData addressData);

	/**
	 * @param addressData
	 */
	void setDefaultAddress(AddressData addressData);

}
