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
import org.springframework.web.client.RestTemplate;

import com.ceea.core.model.ThreeDImgBusinessProcessModel;
import com.ceea.core.request.CEEAHotspotRequest;
import com.ceea.core.utils.CeeaRestUtil;
import com.microsoft.sqlserver.jdbc.StringUtils;


/**
 * @author hybris2go
 *
 */
public class CeeaAnnotationSendAction extends AbstractSimpleDecisionAction<ThreeDImgBusinessProcessModel>
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

	@Override
	public Transition executeAction(final ThreeDImgBusinessProcessModel process) throws RetryLaterException, Exception
	{
		final ProductModel product = process.getProduct();
		final boolean sendAnnotaion = sendAnnotationData(product);
		if (sendAnnotaion)
		{
			return Transition.OK;
		}
		return Transition.NOK;
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
			final String basicAuthUser = configurationService.getConfiguration().getString(CEEA_BASIC_AUTH_USERNAME,
					StringUtils.EMPTY);
			final String basicAuthPwd = configurationService.getConfiguration().getString(CEEA_BASIC_AUTH_PASSWORD,
					StringUtils.EMPTY);
			final HttpHeaders headers = CeeaRestUtil.createHeaders(basicAuthUser, basicAuthPwd);
			headers.setContentType(MediaType.APPLICATION_JSON);
			final CEEAHotspotRequest annotationData = createAnnotationData(product);
			final HttpEntity<CEEAHotspotRequest> requestEntity = new HttpEntity<CEEAHotspotRequest>(annotationData, headers);
			final String serverUrl = configurationService.getConfiguration().getString("ceea.api.hotspot.webservice.url",
					StringUtils.EMPTY);
			final RestTemplate restTemplate = new RestTemplate();
			final ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
			LOG.info("sendAnnotationData response status code ::" + response.getStatusCode());
			LOG.info("sendAnnotationData response status body ::" + response.getBody());
			sendData = true;
		}
		catch (final Exception ex)
		{
			LOG.error("sendAnnotationData exception occurs::" + ex.getMessage());
			LOG.error("sendAnnotationData exception occurs::" + ex);
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

}
