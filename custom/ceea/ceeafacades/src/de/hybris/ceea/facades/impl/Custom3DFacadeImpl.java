/**
 *
 */
package de.hybris.ceea.facades.impl;

import de.hybris.ceea.facades.Custom3DFacade;
import de.hybris.ceea.facades.service.Upload3DImageProductService;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.web.multipart.MultipartFile;



/**
 * @author ZKS7KOR
 *
 */
public class Custom3DFacadeImpl implements Custom3DFacade
{

	/**
	 *
	 */
	ProductService productService;

	/**
	 * @param productService
	 *           the productService to set
	 */
	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	Upload3DImageProductService upload3DImageProductService;

	@Resource
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	@Resource
	private Converter<ProductModel, ProductData> productConverter;

	public Converter<ProductModel, ProductData> getProductConverter()
	{
		return productConverter;
	}

	/**
	 * @param productConverter
	 *           the productConverter to set
	 */
	public void setProductConverter(final Converter<ProductModel, ProductData> productConverter)
	{
		this.productConverter = productConverter;
	}

	public ProductService getProductService()
	{
		return productService;
	}

	/**
	 * @return the upload3DImageProductService
	 */
	public Upload3DImageProductService getUpload3DImageProductService()
	{
		return upload3DImageProductService;
	}

	/**
	 * @param upload3dImageProductService
	 *           the upload3DImageProductService to set
	 */
	public void setUpload3DImageProductService(final Upload3DImageProductService upload3dImageProductService)
	{
		upload3DImageProductService = upload3dImageProductService;
	}

	public ConfigurablePopulator<ProductModel, ProductData, ProductOption> getProductConfiguredPopulator()
	{
		return productConfiguredPopulator;
	}

	/**
	 * @param productConfiguredPopulator
	 *           the productConfiguredPopulator to set
	 */
	public void setProductConfiguredPopulator(
			final ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator)
	{
		this.productConfiguredPopulator = productConfiguredPopulator;
	}

	@Override
	public String setProduct3DImageByCode(final String code, final MultipartFile file, final String annotation)
	{

		final String setProduct3DImageByCode = upload3DImageProductService.setProduct3DImageByCode(code, file, annotation);
		return setProduct3DImageByCode;
	}

	@Override
	public ProductData getProductForCodeAndOptions(final String code, final Collection<ProductOption> options)
	{
		final ProductModel productModel = getProductService().getProductForCode(code);


		final ProductData productData = getProductConverter().convert(productModel);

		if (options != null)
		{
			getProductConfiguredPopulator().populate(productModel, productData, options);
		}

		return productData;
	}




}
