/**
 *
 */
package de.hybris.ceea.facades.service.impl;

import de.hybris.ceea.core.event.Ceea3DImageSubmitEvent;
import de.hybris.ceea.facades.service.Upload3DImageProductService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.search.restriction.SearchRestrictionService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionExecutionBody;
import de.hybris.platform.servicelayer.session.SessionService;

import java.io.IOException;
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




	private ModelService modelService;
	private MediaService mediaService;
	private ProductService productService;
	private EventService eventService;
	private CatalogVersionService catalogVersionService;

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

	private SessionService sessionService;

	private SearchRestrictionService searchRestrictionService;

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

	@Override
	public String setProduct3DImageByCode(final String code, final MultipartFile file, final String annotation)
	{

		ProductModel searchProduct = null;
		MediaModel mediaModel = null;
		try (final InputStream inputStream = file.getInputStream())
		{
			mediaModel = modelService.create(MediaModel.class);

			mediaModel.setCode(code + "-" + System.currentTimeMillis());
			mediaModel.setRealFileName(file.getOriginalFilename());
			mediaModel.setMime(file.getContentType());

			modelService.save(mediaModel);
			mediaService.setStreamForMedia(mediaModel, inputStream);
			modelService.save(mediaModel);
			modelService.refresh(mediaModel);

			searchProduct = getSessionService().executeInLocalView(new SessionExecutionBody()
			{

				@Override
				public Object execute()
				{
					try
					{
						getSearchRestrictionService().disableSearchRestrictions();

						return productService.getProductForCode(code);

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
		catch (final IOException e1)
		{
			e1.printStackTrace();
		}
		//final ProductModel productmodel = productService.getProductForCode(code);
		if (searchProduct != null)
		{
			LOGGER.info("PRODUCTMODEL CODE:" + searchProduct.getCode());
			LOGGER.info("PRODUCTMODEL:" + searchProduct.getCode());
			searchProduct.setAnnotation(annotation);
			searchProduct.setThreeDimensionalImage(mediaModel);
			modelService.save(searchProduct);

			final Ceea3DImageSubmitEvent event = new Ceea3DImageSubmitEvent(searchProduct);
			this.eventService.publishEvent(event);


			return "3D image saved Successfully";
		}
		else
		{

			return "Unable to upload 3D image";
		}
	}

	@Override
	public String setProduct3DImageAndAnnotationByCode(final String code, final MultipartFile file)
	{

		ProductModel searchProduct = null;
		MediaModel mediaModel = null;
		try (final InputStream inputStream = file.getInputStream())
		{
			mediaModel = modelService.create(MediaModel.class);

			mediaModel.setCode(code + "-" + System.currentTimeMillis());
			mediaModel.setRealFileName(file.getOriginalFilename());
			mediaModel.setMime(file.getContentType());

			modelService.save(mediaModel);
			mediaService.setStreamForMedia(mediaModel, inputStream);
			modelService.save(mediaModel);
			modelService.refresh(mediaModel);

			searchProduct = getSessionService().executeInLocalView(new SessionExecutionBody()
			{

				@Override
				public Object execute()
				{
					try
					{
						getSearchRestrictionService().disableSearchRestrictions();

						return productService.getProductForCode(code);

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
		catch (final IOException e1)
		{
			e1.printStackTrace();
		}
		//final ProductModel productmodel = productService.getProductForCode(code);
		if (searchProduct != null)
		{
			LOGGER.info("PRODUCTMODEL CODE:" + searchProduct.getCode());
			LOGGER.info("PRODUCTMODEL:" + searchProduct.getCode());
			searchProduct.setThreeDimensionalImage(mediaModel);
			modelService.save(searchProduct);

			final Ceea3DImageSubmitEvent event = new Ceea3DImageSubmitEvent(searchProduct);
			this.eventService.publishEvent(event);


			return "3D image saved Successfully";
		}
		else
		{

			return "Unable to upload 3D image";
		}
	}


	@SuppressWarnings("removal")
	@Override
	public String setProduct2DImageByCode(final String code, final MultipartFile file)
	{
		ProductModel productModel = null;
		MediaModel mediaModel = null;
		//final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion("apparelProductCatalog", "STAGED");
		try
		{
			mediaModel = modelService.create(MediaModel.class);
			mediaModel.setCode(code + "-" + System.currentTimeMillis());
			mediaModel.setRealfilename(file.getOriginalFilename());
			mediaModel.setMime(file.getContentType());
			//mediaModel.setCatalogVersion(catalogVersion);

			modelService.save(mediaModel);
			mediaService.setStreamForMedia(mediaModel, file.getInputStream());
			modelService.refresh(mediaModel);


			productModel = getSessionService().executeInLocalView(new SessionExecutionBody()
			{

				@Override
				public Object execute()
				{
					try
					{
						getSearchRestrictionService().disableSearchRestrictions();

						return productService.getProductForCode(code);

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
		catch (final IOException e1)
		{
			e1.printStackTrace();
		}
		if (productModel != null)
		{
			productModel.setPicture(mediaModel);
			modelService.save(productModel);

			return "2d Image Saved Successfully";

		}
		else
		{

			return "Unable to upload 2d image";
		}
	}


}

