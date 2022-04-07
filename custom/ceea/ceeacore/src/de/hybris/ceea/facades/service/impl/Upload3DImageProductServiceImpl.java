/**
 *
 */
package de.hybris.ceea.facades.service.impl;

import de.hybris.ceea.core.event.Ceea3DImageSubmitEvent;
import de.hybris.ceea.facades.service.Upload3DImageProductService;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author ZKS7KOR
 *
 */
public class Upload3DImageProductServiceImpl implements Upload3DImageProductService
{


	private static final Logger LOGGER = LoggerFactory.getLogger(Upload3DImageProductServiceImpl.class);




	ModelService modelService;
	MediaService mediaService;
	ProductService productService;
	private EventService eventService;

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
			mediaModel.setRealFileName(file.getOriginalFilename());
			//final MediaModel mediaModel = mediaService.getMedia(code);
			mediaModel.setMime(file.getContentType());
			modelService.save(mediaModel);
			mediaService.setStreamForMedia(mediaModel, inputStream);
			/* modelService.save(mediaModel); */
			modelService.refresh(mediaModel);

			final ProductModel productmodel = productService.getProductForCode(code);

			LOGGER.info("PRODUCTMODEL:" + productmodel);
			productmodel.setAnnotation(annotation);
			/* productmodel.setThreeDimensionalImage(mediaModel); */
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
}
