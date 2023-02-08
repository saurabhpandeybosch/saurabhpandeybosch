/**
 *
 */
package de.hybris.ceea.facades.impl;

import de.hybris.ceea.facades.Custom3DFacade;
import de.hybris.ceea.facades.service.Upload3DImageProductService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.product.UnitService;
import de.hybris.platform.search.restriction.SearchRestrictionService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionExecutionBody;
import de.hybris.platform.servicelayer.session.SessionService;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.web.multipart.MultipartFile;

import com.ceea.facades.product.data.AnnotationData;
import com.ceea.facades.product.data.CustomProductData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


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

	SessionService sessionService;

	SearchRestrictionService searchRestrictionService;

	@Resource
	UnitService unitService;

	@Resource
	CommonI18NService commonI18NService;

	private static final String UNIT_CODE = "pieces";


	/**
	 * @return the searchRestrictionService
	 */
	public SearchRestrictionService getSearchRestrictionService()
	{
		return searchRestrictionService;
	}

	/**
	 * @param searchRestrictionService
	 *           the searchRestrictionService to set
	 */
	public void setSearchRestrictionService(final SearchRestrictionService searchRestrictionService)
	{
		this.searchRestrictionService = searchRestrictionService;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

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
	public String setProduct3DImageAndAnnotationByCode(final String code, final MultipartFile file)
	{
		final String setProduct3DImageAndAnnotationByCode = upload3DImageProductService.setProduct3DImageAndAnnotationByCode(code,
				file);
		return setProduct3DImageAndAnnotationByCode;
	}

	@Override
	public String setProduct2DImageByCode(final String code, final MultipartFile file)
	{

		final String setProduct2DImageByCode = upload3DImageProductService.setProduct2DImageByCode(code, file);
		return setProduct2DImageByCode;
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

	private Converter<CustomProductData, ProductModel> customProductReverseConverter;

	/**
	 * @return the customProductReverseConverter
	 */
	public Converter<CustomProductData, ProductModel> getCustomProductReverseConverter()
	{
		return customProductReverseConverter;
	}

	/**
	 * @param customProductReverseConverter
	 *           the customProductReverseConverter to set
	 */
	public void setCustomProductReverseConverter(final Converter<CustomProductData, ProductModel> customProductReverseConverter)
	{
		this.customProductReverseConverter = customProductReverseConverter;
	}

	private ModelService modelService;

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the catalogVersionService
	 */
	public CatalogVersionService getCatalogVersionService()
	{
		return catalogVersionService;
	}

	/**
	 * @param catalogVersionService
	 *           the catalogVersionService to set
	 */
	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}

	CatalogVersionService catalogVersionService;



	@Override
	public String productCreate(final CustomProductData productData) throws DuplicateUidException
	{
		final String productId = productData.getCode();
		ProductModel searchProduct = null;
		final CatalogVersionModel catalogversion = catalogVersionService.getCatalogVersion(productData.getCatalogId(),
				productData.getCatalogVersion());
		final String customProductData = null;

		try
		{
			//searchProduct = upload3DImageProductService.getProductForCode(catalogversion, productId);
			//searchProduct = productService.getProductForCode(catalogversion, productId);
			searchProduct = getSessionService().executeInLocalView(new SessionExecutionBody()
			{

				@Override
				public Object execute()
				{
					try
					{
						getSearchRestrictionService().disableSearchRestrictions();

						return productService.getProductForCode(catalogversion, productId);

					}

					catch (final Exception e)
					{
						return null;
					}
					finally
					{
						getSearchRestrictionService().enableSearchRestrictions();

					}
				}

			});

		}
		catch (final UnknownIdentifierException e)
		{
			e.printStackTrace();
		}

		if (searchProduct != null)
		{
			searchProduct.setName(productData.getName());
			searchProduct.setDescription(productData.getProductDescription().toString());
			searchProduct.setSummary(productData.getProductSpecification().toString());
			searchProduct.setCategoryid(productData.getCategoryid());
			searchProduct.setProductManual(productData.getProductManual());
			searchProduct.setProductWarranty(productData.getProductWarranty().toString());
			searchProduct.setProductFeatures(productData.getProductFeatures());
			searchProduct.setProductModel(productData.getProductModel());
			searchProduct.setProductCompany(productData.getProductCompany());
			searchProduct.setProductPreview(productData.getProductPreview());
			searchProduct.setProductAuthor(productData.getProductAuthor());
			searchProduct.setSeller(productData.getSeller());
			searchProduct.setFullfilmentCriteria(productData.getFullfilmentCriteria());
			searchProduct.setQuantity(productData.getQuantity().toString());
			searchProduct.setPaymentMode(productData.getPaymentMode());

			CurrencyModel currencyModel = commonI18NService.getCurrency(productData.getCurrency());
			currencyModel.setIsocode(productData.getCurrency());

			final UnitModel unitModel = unitService.getUnitForCode(UNIT_CODE);

			final PriceRowModel newPrice = getModelService().create(PriceRowModel.class);

			newPrice.setCurrency(currencyModel);
			newPrice.setPrice(Double.valueOf(productData.getPrice()));
			newPrice.setUnit(unitModel);
			getModelService().save(newPrice);

			final Collection<PriceRowModel> priceRows = Arrays
					.asList(newPrice);

			final StockLevelModel newStock = getModelService().create(StockLevelModel.class);
			searchProduct.setEurope1Prices(priceRows);
			final ObjectMapper mapper = new ObjectMapper();
			final AnnotationData annotation = new AnnotationData();
			annotation.setHotspotId(productData.getHotspot().getHotspotId());
			annotation.setTransform(productData.getHotspot().getTransform());
			annotation.setRotation(productData.getHotspot().getScale());
			annotation.setScale(productData.getHotspot().getScale());
			annotation.setText(productData.getHotspot().getText());
			try
			{
				searchProduct.setAnnotation(mapper.writeValueAsString(annotation));
			}
			catch (final JsonProcessingException e)
			{
				e.printStackTrace();
			}
			modelService.save(searchProduct);
		}

		else
		{
			final ProductModel newProduct = getModelService().create(ProductModel.class);
			getCustomProductReverseConverter().convert(productData, newProduct);
			modelService.save(newProduct);
		}

		return "Product created/updated Successfully" + productId;
	}

	/**
	 * @param productData
	 * @param productExists
	 * @param productId
	 * @return
	 */
	CatalogVersionModel catalogVersionModel;

	/**
	 * @return the catalogVersionModel
	 */
	public CatalogVersionModel getCatalogVersionModel()
	{
		return catalogVersionModel;
	}

	/**
	 * @param catalogVersionModel
	 *           the catalogVersionModel to set
	 */
	public void setCatalogVersionModel(final CatalogVersionModel catalogVersionModel)
	{
		this.catalogVersionModel = catalogVersionModel;
	}

}
