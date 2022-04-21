/**
 *
 */
package com.ceea.core.actions;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.task.RetryLaterException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ceea.core.model.ThreeDImgBusinessProcessModel;
import com.ceea.core.utils.CeeaRestUtil;
import com.microsoft.sqlserver.jdbc.StringUtils;


/**
 * @author hybris2go
 *
 */
public class CeeaProductSendAction extends AbstractSimpleDecisionAction<ThreeDImgBusinessProcessModel>
{

	private static final String CEEA_BASIC_AUTH_USERNAME = "ceea.basic.auth.username";

	private String product;

	private static final Logger LOG = Logger.getLogger(ThreeDImgBusinessProcessAction.class);


	private static final String CEEA_BASIC_AUTH_PASSWORD = "ceea.basic.auth.password";

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "mediaService")
	private MediaService mediaService;


	/**
	 * @param product
	 *
	 */
	private boolean sendProductData(final ProductModel product)
	{
		boolean sendData = false;
		try
		{
			final String basicAuthUser = configurationService.getConfiguration().getString(CEEA_BASIC_AUTH_USERNAME,
					StringUtils.EMPTY);
			final String basicAuthPwd = configurationService.getConfiguration().getString(CEEA_BASIC_AUTH_PASSWORD,
					StringUtils.EMPTY);
			final HttpHeaders headers = CeeaRestUtil.createHeaders(basicAuthUser, basicAuthPwd);
			headers.setContentType(MediaType.APPLICATION_JSON);
			final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

			final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

			final String serverUrl = configurationService.getConfiguration().getString("ceea.api.product.webservice.url",
					StringUtils.EMPTY);

			final RestTemplate restTemplate = new RestTemplate();
			final ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
			LOG.info("sendProductData response status code ::" + response.getStatusCode());
			LOG.info("sendProductData response status body ::" + response.getBody());
			sendData = true;
		}
		catch (final Exception ex)
		{

			LOG.error("sendProductData exception occurs::" + ex.getMessage());
			LOG.error("sendProductData exception occurs::" + ex);
			return sendData;
		}
		return sendData;

	}

	@Override
	public Transition executeAction(final ThreeDImgBusinessProcessModel process) throws RetryLaterException, Exception
	{
		final ProductModel product = process.getProduct();
		return Transition.OK;
		/*
		 * final boolean sendAnnotaion = sendProductData(product); if (sendAnnotaion) { return Transition.OK; } return
		 * Transition.NOK;
		 */
	}

}
