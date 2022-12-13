/**
 *
 */
package de.hybris.ceea.facades.service.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateIfSingleResult;
import static java.lang.String.format;

import de.hybris.ceea.core.event.Ceea3DImageSubmitEvent;
import de.hybris.ceea.facades.service.Upload3DImageProductService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.ceea.core.product.dao.CustomProductDao;
import com.ceea.facades.product.data.AnnotationData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;





/**
 * @author ZKS7KOR
 *
 */
public class Upload3DImageProductServiceImpl implements Upload3DImageProductService
{


	private static final Logger LOGGER = LoggerFactory.getLogger(Upload3DImageProductServiceImpl.class);




	private ModelService modelService;
	private MediaService mediaService;
	private ProductService productService;
	private EventService eventService;
	private CatalogVersionService catalogVersionService;
	private CustomProductDao customProductDao;

	/**
	 * @return the customProductDao
	 */
	public CustomProductDao getCustomProductDao()
	{
		return customProductDao;
	}

	/**
	 * @param customProductDao
	 *           the customProductDao to set
	 */
	public void setCustomProductDao(final CustomProductDao customProductDao)
	{
		this.customProductDao = customProductDao;
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

	/**
	 * @return the eventService
	 */
	public EventService getEventService()
	{
		return eventService;
	}




	/**
	 * @param eventService
	 *           the eventService to set
	 */
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}




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
	 * @return the mediaService
	 */
	public MediaService getMediaService()
	{
		return mediaService;
	}




	/**
	 * @param mediaService
	 *           the mediaService to set
	 */
	public void setMediaService(final MediaService mediaService)
	{
		this.mediaService = mediaService;
	}




	/**
	 * @return the productService
	 */
	public ProductService getProductService()
	{
		return productService;
	}




	/**
	 * @param productService
	 *           the productService to set
	 */
	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}


	@Override
	public String setProduct3DImageByCode(final String code, final MultipartFile file, final String annotation)
	{

		try (final InputStream inputStream = file.getInputStream())
		{
			final MediaModel mediaModel = modelService.create(MediaModel.class);

			mediaModel.setCode(code + "-" + System.currentTimeMillis());
			mediaModel.setRealFileName(file.getOriginalFilename());
			mediaModel.setMime(file.getContentType());

			modelService.save(mediaModel);
			mediaService.setStreamForMedia(mediaModel, inputStream);
			modelService.save(mediaModel);
			modelService.refresh(mediaModel);

			final ProductModel productmodel = productService.getProductForCode(code);

			LOGGER.info("PRODUCTMODEL CODE:" + productmodel.getCode());
			LOGGER.info("PRODUCTMODEL:" + productmodel.getCode());
			productmodel.setAnnotation(annotation);
			productmodel.setThreeDimensionalImage(mediaModel);
			modelService.save(productmodel);

			final Ceea3DImageSubmitEvent event = new Ceea3DImageSubmitEvent(productmodel);
			this.eventService.publishEvent(event);


			return "File saved Successfully";

		}
		catch (final Exception e)
		{

			e.printStackTrace();

		}
		return "Unable to save file";
	}

	@Override
	public String setProduct3DImageAndAnnotationByCode(final String code, final MultipartFile file,
			final AnnotationData annotation)
	{

		try (final InputStream inputStream = file.getInputStream())
		{
			final MediaModel mediaModel = modelService.create(MediaModel.class);

			mediaModel.setCode(code + "-" + System.currentTimeMillis());
			mediaModel.setRealFileName(file.getOriginalFilename());
			mediaModel.setMime(file.getContentType());

			modelService.save(mediaModel);
			mediaService.setStreamForMedia(mediaModel, inputStream);
			modelService.save(mediaModel);
			modelService.refresh(mediaModel);

			final ProductModel productmodel = productService.getProductForCode(code);

			LOGGER.info("PRODUCTMODEL CODE:" + productmodel.getCode());
			LOGGER.info("PRODUCTMODEL:" + productmodel.getCode());
			final ObjectMapper mapper = new ObjectMapper();
			final AnnotationData annotationData = new AnnotationData();
			annotationData.setHotspotId(annotation.getHotspotId());
			annotationData.setTransform(annotation.getTransform());
			annotationData.setRotation(annotation.getRotation());
			annotationData.setScale(annotation.getScale());
			annotationData.setText(annotation.getText());
			try
			{
				productmodel.setAnnotation(mapper.writeValueAsString(annotationData));
			}
			catch (final JsonProcessingException e)
			{
				e.printStackTrace();
			}
			productmodel.setThreeDimensionalImage(mediaModel);
			modelService.save(productmodel);

			final Ceea3DImageSubmitEvent event = new Ceea3DImageSubmitEvent(productmodel);
			this.eventService.publishEvent(event);


			return "File saved Successfully";

		}
		catch (final Exception e)
		{

			e.printStackTrace();

		}
		return "Unable to save file";
	}


	@SuppressWarnings("removal")
	@Override
	public String setProduct2DImageByCode(final String code, final MultipartFile file)
	{

		//final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion("apparelProductCatalog", "STAGED");
		try
		{
			final MediaModel mediaModel = modelService.create(MediaModel.class);
			mediaModel.setCode(code + "-" + System.currentTimeMillis());
			mediaModel.setRealfilename(file.getOriginalFilename());
			mediaModel.setMime(file.getContentType());
			//mediaModel.setCatalogVersion(catalogVersion);

			modelService.save(mediaModel);
			mediaService.setStreamForMedia(mediaModel, file.getInputStream());
			modelService.refresh(mediaModel);

			final ProductModel productModel = productService.getProductForCode(code);

			productModel.setPicture(mediaModel);
			modelService.save(productModel);

			return "Image Saved Successfully";

		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return "Unable to upload image";
	}



	public ProductModel getProductForCode(final CatalogVersionModel catalogVersion, final String code)
	{
		final List<ProductModel> products = getCustomProductDao().findProductsByCode(catalogVersion, code);

		validateIfSingleResult(products,
				format("Product with code '%s' and CatalogVersion '%s.%s' not found!", code, catalogVersion.getCatalog().getId(),
						catalogVersion.getVersion()),
				format("Code '%s' and CatalogVersion '%s.%s' are not unique. %d products found!", code,
						catalogVersion.getCatalog().getId(), catalogVersion.getVersion(), Integer.valueOf(products.size())));

		return products.get(0);
	}


}

