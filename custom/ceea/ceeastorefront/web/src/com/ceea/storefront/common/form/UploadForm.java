/**
 *
 */
package com.ceea.storefront.common.form;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author hybris2go
 *
 */
public class UploadForm
{
	private String code;
	private String annotation;
	private MultipartFile image;

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code
	 *           the code to set
	 */
	public void setCode(final String code)
	{
		this.code = code;
	}

	/**
	 * @return the annotation
	 */
	public String getAnnotation()
	{
		return annotation;
	}

	/**
	 * @param annotation
	 *           the annotation to set
	 */
	public void setAnnotation(final String annotation)
	{
		this.annotation = annotation;
	}

	/**
	 * @return the image
	 */
	public MultipartFile getImage()
	{
		return image;
	}

	/**
	 * @param image
	 *           the image to set
	 */
	public void setImage(final MultipartFile image)
	{
		this.image = image;
	}
}
