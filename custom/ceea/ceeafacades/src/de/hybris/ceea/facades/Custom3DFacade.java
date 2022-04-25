/**
 *
 */
package de.hybris.ceea.facades;

import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;

import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author ZKS7KOR
 *
 */
public interface Custom3DFacade
{

	String setProduct3DImageByCode(String code, MultipartFile file, String annotation);

	ProductData getProductForCodeAndOptions(String code, Collection<ProductOption> options);
}
