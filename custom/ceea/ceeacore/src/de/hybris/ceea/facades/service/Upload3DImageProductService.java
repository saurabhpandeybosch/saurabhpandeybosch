
package de.hybris.ceea.facades.service;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author ZKS7KOR
 *
 */
public interface Upload3DImageProductService
{

	String setProduct3DImageByCode(String code, MultipartFile file, String annotation);
}
