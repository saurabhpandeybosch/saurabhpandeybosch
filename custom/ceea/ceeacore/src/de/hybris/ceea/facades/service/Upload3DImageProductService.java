
package de.hybris.ceea.facades.service;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;

import org.springframework.web.multipart.MultipartFile;

import com.ceea.facades.product.data.AnnotationData;


/**
 * @author ZKS7KOR
 *
 */
public interface Upload3DImageProductService
{

	String setProduct3DImageByCode(String code, MultipartFile file, String annotation);

	String setProduct3DImageAndAnnotationByCode(String code, MultipartFile file, AnnotationData annotationData);

	String setProduct2DImageByCode(String code, MultipartFile file);

	ProductModel getProductForCode(final CatalogVersionModel catalogVersion, final String code);

}
