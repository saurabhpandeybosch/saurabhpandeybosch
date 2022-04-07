/**
 *
 */
package com.ceea.storefront.controllers.pages;

import de.hybris.ceea.facades.Custom3DFacade;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.forms.ImportCSVSavedCartForm;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.ceea.storefront.common.form.UploadForm;


@Controller
@Scope("tenant")
@RequestMapping("/ceea")
public class CeeaProductController extends AbstractPageController
{
	private static final String CEEA_CMS_PAGE = "ceeaCMSPage";

	private static final Logger LOGGER = LoggerFactory.getLogger(CeeaProductController.class);

	@Resource
	private Custom3DFacade custom3DFacade;

	private final String[] DISALLOWED_FIELDS = new String[] {};

	@InitBinder
	public void initBinder(final WebDataBinder binder)
	{
		binder.setDisallowedFields(DISALLOWED_FIELDS);
	}

	@InitBinder
	public void init(final WebDataBinder binder)
	{
		binder.setBindEmptyMultipartFiles(false);
	}

	@RequireHardLogIn
	@Secured(
	{ "ROLE_CEEAWEBSITEMANAGERGROUP" })
	@RequestMapping(method = RequestMethod.GET)
	public String getProduct3DUpload(final Model model) throws CMSItemNotFoundException
	{
		final ContentPageModel ceeaCMSPage = getContentPageForLabelOrId(CEEA_CMS_PAGE);
		model.addAttribute("UploadForm", new UploadForm());
		model.addAttribute("importCSVSavedCartForm", new ImportCSVSavedCartForm());
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



	@RequestMapping(value = "/test", method =
	{ RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String test(final Model model, final UploadForm uploadForm) throws CMSItemNotFoundException
	{
		LOGGER.info("Controller got called");
		LOGGER.info("Product code is:" + uploadForm.getCode());
		LOGGER.info("annotation  is:" + uploadForm.getAnnotation());
		final ContentPageModel ceeaCMSPage = getContentPageForLabelOrId(CEEA_CMS_PAGE);
		model.addAttribute("UploadForm", new UploadForm());
		storeCmsPageInModel(model, ceeaCMSPage);
		setUpMetaDataForContentPage(model, ceeaCMSPage);

		return getViewForPage(model);
	}

	/**
	 * Creates a ticket.
	 *
	 * @param supportTicketForm
	 * @param bindingResult
	 * @return View String
	 */
	@RequestMapping(value = "/uploadData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequireHardLogIn
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public String uploadData(final UploadForm uploadForm)
	{
		LOGGER.info("Controller got called");
		LOGGER.info("Product code is:" + uploadForm.getCode());
		LOGGER.info("annotation  is:" + uploadForm.getAnnotation());
		return "true";
	}

	/**
	 * Creates a ticket.
	 *
	 * @param supportTicketForm
	 * @param bindingResult
	 * @return View String
	 */
	@RequestMapping(value = "/uploadData1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequireHardLogIn
	public String uploadData1(final UploadForm uploadForm)
	{
		LOGGER.info("Controller got called");
		LOGGER.info("Product code is:" + uploadForm.getCode());
		LOGGER.info("annotation  is:" + uploadForm.getAnnotation());
		return "true";
	}
}
