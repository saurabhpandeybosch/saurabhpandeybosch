/**
 *
 */
package com.ceea.core.actions;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.media.MediaService;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ceea.core.model.ThreeDImgBusinessProcessModel;
import com.ceea.core.request.CEEAHotspotRequest;
import com.microsoft.sqlserver.jdbc.StringUtils;


/**
 * @author SDQ1KOR
 *
 */
public class ThreeDImgBusinessProcessAction extends AbstractSimpleDecisionAction<ThreeDImgBusinessProcessModel>


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
	public Transition executeAction(final ThreeDImgBusinessProcessModel process)
	{
		LOG.info("Inside ImageAnnotationAction class. returing ok status ");
		final ProductModel product = process.getProduct();
		final boolean sendAnnotaion = sendAnnotationData(product);
		if (sendAnnotaion)
		{
			final boolean sendImage = sendImageToCeea(product);
			if (sendImage)
			{
				return Transition.OK;
			}
		}
		return Transition.NOK;
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
	private boolean sendAnnotationData(final ProductModel product)
	{
		boolean sendData = false;
		try
		{
			final HttpHeaders headers = createHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			final CEEAHotspotRequest annotationData = createAnnotationData(product);
			final HttpEntity<CEEAHotspotRequest> requestEntity = new HttpEntity<CEEAHotspotRequest>(annotationData, headers);
			final String serverUrl = configurationService.getConfiguration().getString("ceea.api.hotspot.webservice.url",
					StringUtils.EMPTY);
			final RestTemplate restTemplate = new RestTemplate();
			final ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
			LOG.info("response status code ::" + response.getStatusCode());
			LOG.info("response status body ::" + response.getBody());
			sendData = true;
		}
		catch (final Exception ex)
		{
			LOG.error("exception occurs::" + ex.getMessage());
			LOG.error("exception occurs::" + ex);
			return sendData;
		}
		return sendData;
	}


	/**
	 * @param product2
	 * @return
	 */
	private CEEAHotspotRequest createAnnotationData(final ProductModel product2)
	{
		final CEEAHotspotRequest hotspotData = new CEEAHotspotRequest();
		hotspotData.setHotspotid("55");
		hotspotData.setHotspotimage("");
		hotspotData.setHotspotMedia("");
		hotspotData.setRotation("135");
		hotspotData.setTransform("0,0,0");
		hotspotData.setScale("0.2,0.2,0.2");
		hotspotData.setHotspotText("From Hybris");
		return hotspotData;
	}


	/**
	 * @param product
	 *
	 */
	private boolean sendImageToCeea(final ProductModel product)
	{
		boolean sendData = false;
		try
		{
			final HttpHeaders headers = createHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			final Collection<File> files = mediaService.getFiles(product.getThreeDimensionalImage());
			final File sendFile = files.stream().findFirst().get();
			final MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
			final ContentDisposition contentDisposition = ContentDisposition.builder("form-data").name("file")
					.filename(product.getThreeDimensionalImage().getRealFileName()).build();

			fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
			final HttpEntity<byte[]> fileEntity = new HttpEntity<>(Files.readAllBytes(sendFile.toPath()), fileMap);
			body.add("file", fileEntity);

			final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
			final String serverUrl = configurationService.getConfiguration().getString("ceea.api.image.upload.webservice.url",
					StringUtils.EMPTY);
			final RestTemplate restTemplate = new RestTemplate();
			final ResponseEntity<String> response = restTemplate.exchange(serverUrl + product.getCode(), HttpMethod.POST,
					requestEntity, String.class);

			LOG.info("response status code ::" + response.getStatusCode());
			LOG.info("response status body ::" + response.getBody());
			sendData = true;
		}
		catch (final Exception ex)
		{
			LOG.error("exception occurs::" + ex.getMessage());
			LOG.error("exception occurs::" + ex);
			return sendData;
		}
		return sendData;
	}
}



