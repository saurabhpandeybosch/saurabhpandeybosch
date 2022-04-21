/**
 *
 */
package com.ceea.storefront.common.form.validation;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.ceea.storefront.common.form.UploadForm;


/**
 * @author AEN8KOR
 *
 */
@Component("importImageSavedCartFormValidator")
public class ImportImageSavedCartFormValidator implements Validator
{
	public static final String IMPORT_IMAGE_FILE_MAX_SIZE_BYTES_KEY = "import.image.file.max.size.bytes";
	public static final String IMAGE_FILE_FIELD = "image";
	public static final String IMAGE_FILE_EXTENSION = ".gltf";

	private static final Logger LOG = Logger.getLogger(ImportImageSavedCartFormValidator.class);

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Override
	public boolean supports(final Class<?> aClass)
	{
		return UploadForm.class.equals(aClass);
	}

	@Override
	public void validate(final Object target, final Errors errors)
	{
		final UploadForm UploadForm = (UploadForm) target;
		final MultipartFile image = UploadForm.getImage();

		if (image == null || image.isEmpty())
		{
			errors.rejectValue(IMAGE_FILE_FIELD, "import.image.savedCart.fileRequired");
			return;
		}

		final String fileContentType = image.getContentType();
		final String fileName = image.getOriginalFilename();
		final String filename1 = image.getName();

		LOG.info("This is current file name:  " + filename1);
		LOG.info("This is current file name:  " + fileName);
		LOG.info("This is current fileContentType name:  " + fileContentType);


		if (fileName == null || !fileName.toLowerCase().endsWith(IMAGE_FILE_EXTENSION))
		{

			errors.rejectValue(IMAGE_FILE_FIELD, "import.image.savedCart.fileIMAGERequired");
			return;
		}

		if (image.getSize() > getFileMaxSize())
		{
			errors.rejectValue(IMAGE_FILE_FIELD, "import.image.savedCart.fileMaxSizeExceeded");
		}

	}


	protected long getFileMaxSize()
	{
		return getSiteConfigService().getLong(IMPORT_IMAGE_FILE_MAX_SIZE_BYTES_KEY, 0);
	}

	protected SiteConfigService getSiteConfigService()
	{
		return siteConfigService;
	}

}
