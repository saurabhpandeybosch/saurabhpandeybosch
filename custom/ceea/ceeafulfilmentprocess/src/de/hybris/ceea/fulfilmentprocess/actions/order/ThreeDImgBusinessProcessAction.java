/**
 *
 */
package de.hybris.ceea.fulfilmentprocess.actions.order;

import de.hybris.ceea.fulfilmentprocess.request.CEEAHotspotRequest;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.media.MediaService;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.microsoft.sqlserver.jdbc.StringUtils;


/**
 * @author SDQ1KOR
 *
 */
public class ThreeDImgBusinessProcessAction extends AbstractSimpleDecisionAction<BusinessProcessModel>


{
	/**
	 *
	 */
	private static final String CEEA_BASIC_AUTH_USERNAME = "ceea.basic.auth.username";

	private String product;

	private static final Logger LOG = Logger.getLogger(ThreeDImgBusinessProcessAction.class);


	private static final String CEEA_BASIC_AUTH_PASSWORD = "ceea.basic.auth.password";

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "mediaService")
	private MediaService mediaService;


	/**
	 * This method is used for basic authentication
	 *
	 * @return
	 */
	private HttpHeaders createHeaders()
	{
		final String basicAuthUser = configurationService.getConfiguration().getString(CEEA_BASIC_AUTH_USERNAME, StringUtils.EMPTY);
		final String basicAuthPwd = configurationService.getConfiguration().getString(CEEA_BASIC_AUTH_PASSWORD, StringUtils.EMPTY);
		return new HttpHeaders()
		{
			{
				final String auth = basicAuthUser + ":" + basicAuthPwd;
				final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				final String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}


	@Override
	public Transition executeAction(final BusinessProcessModel process)
	{
		LOG.info("Inside ImageAnnotationAction class. returing ok status ");
		final ProductModel product = new ProductModel();
		sendImageToCeea(product);
		return Transition.OK;
	}

	/**
	 * @param product
	 *
	 */
	private void sendProductData(final ProductModel product)
	{
		final HttpHeaders headers = createHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		final String serverUrl = configurationService.getConfiguration().getString("ceea.api.product.webservice.url",
				StringUtils.EMPTY);

		final RestTemplate restTemplate = new RestTemplate();
		final ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);

	}

	/**
	 * @param product
	 *
	 */
	private void sendAnnotationData(final ProductModel product)
	{
		final HttpHeaders headers = createHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final CEEAHotspotRequest annotationData = createAnnotationData(product);
		final HttpEntity<CEEAHotspotRequest> requestEntity = new HttpEntity<CEEAHotspotRequest>(annotationData, headers);
		final String serverUrl = configurationService.getConfiguration().getString("ceea.api.hotspot.webservice.url",
				StringUtils.EMPTY);
		final RestTemplate restTemplate = new RestTemplate();
		final ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
	}


	/**
	 * @param product2
	 * @return
	 */
	private CEEAHotspotRequest createAnnotationData(final ProductModel product2)
	{
		final CEEAHotspotRequest hotspotData = new CEEAHotspotRequest();
		return hotspotData;
	}


	/**
	 * @param product
	 *
	 */
	private void sendImageToCeea(final ProductModel product)
	{
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		final Collection<File> files = mediaService.getFiles(product.getThreeDimensionalImage());
		for (final File file : files)
		{
			body.add("file", file);
		}
		final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		final String serverUrl = configurationService.getConfiguration().getString("ceea.api.image.upload.webservice.url",
				StringUtils.EMPTY);
		final RestTemplate restTemplate = new RestTemplate();
		final ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);

	}
}










