package com.ceea.facades.populators;


import de.hybris.platform.commercefacades.product.converters.populator.ProductPopulator;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;


/**
 * @author ZKS7KOR
 *
 */
public class CeeaProductPopulator extends ProductPopulator
{
	@Resource
	private Converter<MediaModel, ImageData> imageConverter;

	/**
	 * @return the imageConverter
	 */
	public Converter<MediaModel, ImageData> getImageConverter()
	{
		return imageConverter;
	}

	/**
	 * @param imageConverter
	 *           the imageConverter to set
	 */
	public void setImageConverter(final Converter<MediaModel, ImageData> imageConverter)
	{
		this.imageConverter = imageConverter;
	}

	@Override
	public void populate(final ProductModel source, final ProductData target)
	{
		target.setAnnotation(source.getAnnotation());
		final MediaModel media = source.getThreeDimensionalImage();
		if (media != null)
		{
			final ImageData imageData = getImageConverter().convert(media);
			target.setCeeaThreeDimensionalImage(imageData);
		}
		// super.populate(source, target);
	}
}

