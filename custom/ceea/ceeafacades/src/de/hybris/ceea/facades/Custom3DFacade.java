/**
 *
 */
package de.hybris.ceea.facades;

import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;

import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

import com.ceea.facades.product.data.CustomProductData;



/**
 * @author ZKS7KOR
 *
 */
public interface Custom3DFacade
{

	String setProduct3DImageByCode(String code, MultipartFile file, String annotation);

	String setProduct3DImageAndAnnotationByCode(String code, MultipartFile file);

	ProductData getProductForCodeAndOptions(String code, Collection<ProductOption> options);

	/**
	 * @param code
	 * @param file
	 * @return
	 */
	String setProduct2DImageByCode(String code, MultipartFile file);

	/**
	 * @param product
	 * @return
	 * @throws DuplicateUidException
	 */
	String productCreate(CustomProductData product) throws DuplicateUidException;
}
