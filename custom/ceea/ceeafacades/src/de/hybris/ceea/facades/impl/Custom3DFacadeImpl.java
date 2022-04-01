/**
 *
 */
package de.hybris.ceea.facades.impl;

import de.hybris.ceea.facades.Custom3DFacade;
import de.hybris.ceea.facades.service.Upload3DImageProductService;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author ZKS7KOR
 *
 */
public class Custom3DFacadeImpl implements Custom3DFacade
{

	Upload3DImageProductService upload3DImageProductService;

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

	@Override
	public String setProduct3DImageByCode(final String code, final MultipartFile file, final String annotation)
	{

		final String setProduct3DImageByCode = upload3DImageProductService.setProduct3DImageByCode(code, file, annotation);
		return setProduct3DImageByCode;
	}





}
