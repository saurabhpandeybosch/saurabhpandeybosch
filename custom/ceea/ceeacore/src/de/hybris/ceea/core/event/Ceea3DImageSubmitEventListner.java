/**
 *
 */
package de.hybris.ceea.core.event;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.ceea.core.model.ThreeDImgBusinessProcessModel;


/**
 * @author ZKS7KOR
 *
 */
public class Ceea3DImageSubmitEventListner extends AbstractEventListener<Ceea3DImageSubmitEvent>
{


	private BusinessProcessService businessProcessService;

	private ModelService modelService;



	public BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}


	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}



	private static final Logger LOG = Logger.getLogger(Ceea3DImageSubmitEventListner.class);



	public ModelService getModelService()
	{
		return modelService;
	}


	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	@Override
	protected void onEvent(final Ceea3DImageSubmitEvent event)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("Received Ceea3DImageSubmitEvent..");
		}
		final ProductModel product = event.getProductModel();
		final ThreeDImgBusinessProcessModel businessProcess = (ThreeDImgBusinessProcessModel) businessProcessService.createProcess(
				"threeDImgBusinessProcess-" + product.getCode() + "-" + System.currentTimeMillis(), "threeDImgBusinessProcess");
		businessProcess.setProduct(product);
		modelService.save(businessProcess);
		businessProcessService.startProcess(businessProcess);
		LOG.info("onEvent method got called");
	}

}
