/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Apr 6, 2022, 3:55:27 AM                     ---
 * ----------------------------------------------------------------
 */
package com.ceea.core.jalo;

import com.ceea.core.constants.CeeaCoreConstants;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.processengine.jalo.BusinessProcess;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.processengine.jalo.BusinessProcess ThreeDImgBusinessProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedThreeDImgBusinessProcess extends BusinessProcess
{
	/** Qualifier of the <code>ThreeDImgBusinessProcess.product</code> attribute **/
	public static final String PRODUCT = "product";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(BusinessProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PRODUCT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ThreeDImgBusinessProcess.product</code> attribute.
	 * @return the product - ThreeDImageAnnotation product.
	 */
	public Product getProduct(final SessionContext ctx)
	{
		return (Product)getProperty( ctx, PRODUCT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ThreeDImgBusinessProcess.product</code> attribute.
	 * @return the product - ThreeDImageAnnotation product.
	 */
	public Product getProduct()
	{
		return getProduct( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ThreeDImgBusinessProcess.product</code> attribute. 
	 * @param value the product - ThreeDImageAnnotation product.
	 */
	public void setProduct(final SessionContext ctx, final Product value)
	{
		setProperty(ctx, PRODUCT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ThreeDImgBusinessProcess.product</code> attribute. 
	 * @param value the product - ThreeDImageAnnotation product.
	 */
	public void setProduct(final Product value)
	{
		setProduct( getSession().getSessionContext(), value );
	}
	
}
