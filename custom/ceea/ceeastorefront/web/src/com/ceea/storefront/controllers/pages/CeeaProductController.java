/**
 *
 */
package com.ceea.storefront.controllers.pages;

import de.hybris.ceea.facades.Custom3DFacade;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@Scope("tenant")
@RequestMapping("/ceea")
public class CeeaProductController extends AbstractPageController
{
	private static final String CEEA_CMS_PAGE = "ceeaCMSPage";

	private static final Logger LOGGER = LoggerFactory.getLogger(CeeaProductController.class);

	@Resource
	private Custom3DFacade custom3DFacade;

	@RequireHardLogIn
	@Secured(
	{ "ROLE_CEEAWEBSITEMANAGERGROUP" })
	@RequestMapping(method = RequestMethod.GET)
	public String getProduct3DUpload(final Model model) throws CMSItemNotFoundException
	{
		final ContentPageModel ceeaCMSPage = getContentPageForLabelOrId(CEEA_CMS_PAGE);
		storeCmsPageInModel(model, ceeaCMSPage);
		setUpMetaDataForContentPage(model, ceeaCMSPage);
		return getViewForPage(model);
	}

	@ResponseBody
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST, consumes =
	{ MediaType.MULTIPART_FORM_DATA_VALUE })
	public String Upload3DImage(@RequestParam
	final String code, @RequestParam("image")
	final MultipartFile image, @RequestParam
	final String annotation) throws CMSItemNotFoundException
	{


		LOGGER.info("Controller got called");
		LOGGER.info("Product code is:" + code);
		LOGGER.info("annotation  is:" + annotation);


		final String setProduct3DImageByCode = custom3DFacade.setProduct3DImageByCode(code, image, annotation);

		return setProduct3DImageByCode;
	}
}
