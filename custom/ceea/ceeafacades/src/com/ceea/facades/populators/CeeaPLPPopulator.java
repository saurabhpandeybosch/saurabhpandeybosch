/**
 *
 */
package com.ceea.facades.populators;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceea.facades.product.data.AnnotationData;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author AEN8KOR
 *
 */
public class CeeaPLPPopulator extends SearchResultVariantProductPopulator
{

	private static final Logger LOG = LoggerFactory.getLogger(CeeaPLPPopulator.class);

	@Override
	public void populate(final SearchResultValueData source, final ProductData target)
	{
		super.populate(source, target);

		target.setAnnotation(this.<String> getValue(source, "annotation"));
		target.setCeeaThreeDimensionalImage(createImageDataObj(source));
		target.setPrimaryImgURL(this.<String> getValue(source, "primaryImgURL_string"));
		try
		{
			convertJsonToObject(target);
		}
		catch (final IOException e)
		{
			LOG.info("Exception Occured :: " + e.getMessage());
		}
	}

	/**
	 * @param target
	 */
	private void convertJsonToObject(final ProductData target) throws IOException
	{
		final ObjectMapper mapper = new ObjectMapper();
		AnnotationData[] readValue = null;
		if (StringUtils.isNotEmpty(target.getAnnotation()))
		{
			readValue = mapper.readValue(target.getAnnotation(), AnnotationData[].class);
			final List<AnnotationData> list = Arrays.asList(readValue);
			target.setAnnotationList(list);
		}

	}

	protected ImageData createImageDataObj(final SearchResultValueData source)
	{
		final ImageData result = new ImageData();

		addImageData(source, "thumbnail", result);
		addImageData(source, "product", result);

		return result;
	}

	protected void addImageData(final SearchResultValueData source, final String imageFormat, final ImageData image)
	{
		final String mediaFormatQualifier = getImageFormatMapping().getMediaFormatQualifierForImageFormat(imageFormat);
		if (mediaFormatQualifier != null && !mediaFormatQualifier.isEmpty())
		{
			addImageData(source, imageFormat, mediaFormatQualifier, "PRIMARY", image);
		}
	}

	protected void addImageData(final SearchResultValueData source, final String imageFormat, final String mediaFormatQualifier,
			final String type, final ImageData images)
	{
			
		final String imgValue = getValue(source, "ceeaThreeDimensionalImage_string");

			images.setUrl(imgValue);


		}
	}


