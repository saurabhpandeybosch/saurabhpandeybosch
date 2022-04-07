/**
 *
 */
package de.hybris.ceea.core.event;



import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.core.model.product.ProductModel;

import org.apache.log4j.Logger;


/**
 * @author ZKS7KOR
 *
 */
public class Ceea3DImageSubmitEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	private static final Logger LOG = Logger.getLogger(Ceea3DImageSubmitEventListner.class);
	private ProductModel productModel;



	/**
	 * @param productmodel2
	 */
	public Ceea3DImageSubmitEvent(final ProductModel productmodel2)
	{

		this.productModel = productmodel2;

		LOG.info("productmodel2 got called:" + productmodel2);
	}

	public ProductModel getProductModel()
	{
		return productModel;
	}

	/**
	 * @param productModel
	 *           the productModel to set
	 */
	public void setProductModel(final ProductModel productModel)
	{
		this.productModel = productModel;
	}

}
