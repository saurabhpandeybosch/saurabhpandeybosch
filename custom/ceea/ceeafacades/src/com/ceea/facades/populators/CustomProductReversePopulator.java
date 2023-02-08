/**
 *
 */
package com.ceea.facades.populators;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.enums.ArticleApprovalStatus;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.product.UnitService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

import com.ceea.facades.product.data.AnnotationData;
import com.ceea.facades.product.data.CustomProductData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * @author FCG1KOR
 *
 */

public class CustomProductReversePopulator implements Populator<CustomProductData, ProductModel>
{
	private static final Logger LOG = LoggerFactory.getLogger(CustomProductReversePopulator.class);
	@Resource
	private Converter<CustomProductData, ProductModel> customProductReverseConverter;

	CatalogVersionService catalogVersionService;

	@Resource
	CommonI18NService commonI18NService;

	@Resource
	ModelService modelService;

	@Resource
	UnitService unitService;

	private static final String UNIT_CODE = "pieces";

	/**
	 * @return the catalogVersionService
	 */
	public CatalogVersionService getCatalogVersionService()
	{
		return catalogVersionService;
	}

	public void populate(final CustomProductData source, final ProductModel target) throws ConversionException
	{

		final CatalogVersionModel catalogversion = catalogVersionService.getCatalogVersion(source.getCatalogId(),
				source.getCatalogVersion());
		target.setCatalogVersion(catalogversion);
		Assert.notNull(source, "Parameter source cannot be null.");
		target.setCode(source.getCode());
		target.setName(source.getName());
		target.setApprovalStatus(ArticleApprovalStatus.CHECK);
		target.setDescription(source.getProductDescription().toString());
		target.setSummary(source.getProductSpecification().toString());
		target.setCategoryid(source.getCategoryid());
		target.setProductManual(source.getProductManual());
		target.setProductWarranty(source.getProductWarranty().toString());
		target.setProductFeatures(source.getProductFeatures());
		target.setProductModel(source.getProductModel());
		target.setProductCompany(source.getProductCompany());
		target.setProductPreview(source.getProductPreview());
		target.setProductAuthor(source.getProductAuthor());
		target.setSeller(source.getSeller());
		target.setFullfilmentCriteria(source.getFullfilmentCriteria());
		target.setQuantity(source.getQuantity().toString());
		target.setPaymentMode(source.getPaymentMode());

		CurrencyModel currencyModel = new CurrencyModel();
		currencyModel = commonI18NService.getCurrency(source.getCurrency());
		currencyModel.setIsocode(source.getCurrency());

		final UnitModel unitModel = unitService.getUnitForCode(UNIT_CODE);

		final PriceRowModel newPrice = modelService.create(PriceRowModel.class);

		newPrice.setCurrency(currencyModel);
		newPrice.setPrice(Double.valueOf(source.getPrice()));
		newPrice.setUnit(unitModel);
		modelService.save(newPrice);

		final Collection<PriceRowModel> priceRows = Arrays.asList(newPrice);

		final StockLevelModel newStock = modelService.create(StockLevelModel.class);
		target.setEurope1Prices(priceRows);
		final ObjectMapper mapper = new ObjectMapper();
		final AnnotationData annotation = new AnnotationData();
		annotation.setHotspotId(source.getHotspot().getHotspotId());
		annotation.setTransform(source.getHotspot().getTransform());
		annotation.setRotation(source.getHotspot().getRotation());
		annotation.setScale(source.getHotspot().getScale());
		annotation.setText(source.getHotspot().getText());
		LOG.info("annotaion :: " + annotation);
		try
		{
			target.setAnnotation(mapper.writeValueAsString(annotation));
		}
		catch (final JsonProcessingException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * @param catalogVersionService
	 *           the catalogVersionService to set
	 */
	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}
}
