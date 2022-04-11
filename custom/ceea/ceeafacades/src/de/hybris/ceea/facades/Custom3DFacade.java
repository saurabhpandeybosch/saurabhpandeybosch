/**
 *
 */
package de.hybris.ceea.facades;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author ZKS7KOR
 *
 */
public interface Custom3DFacade
{

	String setProduct3DImageByCode(String code, MultipartFile file, String annotation);


}
