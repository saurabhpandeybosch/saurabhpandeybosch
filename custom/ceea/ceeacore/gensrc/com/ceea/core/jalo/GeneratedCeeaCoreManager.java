/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Apr 25, 2022, 4:33:32 AM                    ---
 * ----------------------------------------------------------------
 */
package com.ceea.core.jalo;

import com.ceea.core.constants.CeeaCoreConstants;
import com.ceea.core.jalo.ApparelProduct;
import com.ceea.core.jalo.ApparelSizeVariantProduct;
import com.ceea.core.jalo.ApparelStyleVariantProduct;
import com.ceea.core.jalo.ElectronicsColorVariantProduct;
import com.ceea.core.jalo.ThreeDImgBusinessProcess;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type <code>CeeaCoreManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedCeeaCoreManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put("annotation", AttributeMode.INITIAL);
		tmp.put("ThreeDimensionalImage", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.product.Product", Collections.unmodifiableMap(tmp));
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.annotation</code> attribute.
	 * @return the annotation
	 */
	public String getAnnotation(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, CeeaCoreConstants.Attributes.Product.ANNOTATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.annotation</code> attribute.
	 * @return the annotation
	 */
	public String getAnnotation(final Product item)
	{
		return getAnnotation( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.annotation</code> attribute. 
	 * @param value the annotation
	 */
	public void setAnnotation(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, CeeaCoreConstants.Attributes.Product.ANNOTATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.annotation</code> attribute. 
	 * @param value the annotation
	 */
	public void setAnnotation(final Product item, final String value)
	{
		setAnnotation( getSession().getSessionContext(), item, value );
	}
	
	public ApparelProduct createApparelProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( CeeaCoreConstants.TC.APPARELPRODUCT );
			return (ApparelProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ApparelProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ApparelProduct createApparelProduct(final Map attributeValues)
	{
		return createApparelProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public ApparelSizeVariantProduct createApparelSizeVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( CeeaCoreConstants.TC.APPARELSIZEVARIANTPRODUCT );
			return (ApparelSizeVariantProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ApparelSizeVariantProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ApparelSizeVariantProduct createApparelSizeVariantProduct(final Map attributeValues)
	{
		return createApparelSizeVariantProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public ApparelStyleVariantProduct createApparelStyleVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( CeeaCoreConstants.TC.APPARELSTYLEVARIANTPRODUCT );
			return (ApparelStyleVariantProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ApparelStyleVariantProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ApparelStyleVariantProduct createApparelStyleVariantProduct(final Map attributeValues)
	{
		return createApparelStyleVariantProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public ElectronicsColorVariantProduct createElectronicsColorVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( CeeaCoreConstants.TC.ELECTRONICSCOLORVARIANTPRODUCT );
			return (ElectronicsColorVariantProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ElectronicsColorVariantProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ElectronicsColorVariantProduct createElectronicsColorVariantProduct(final Map attributeValues)
	{
		return createElectronicsColorVariantProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public ThreeDImgBusinessProcess createThreeDImgBusinessProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( CeeaCoreConstants.TC.THREEDIMGBUSINESSPROCESS );
			return (ThreeDImgBusinessProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ThreeDImgBusinessProcess : "+e.getMessage(), 0 );
		}
	}
	
	public ThreeDImgBusinessProcess createThreeDImgBusinessProcess(final Map attributeValues)
	{
		return createThreeDImgBusinessProcess( getSession().getSessionContext(), attributeValues );
	}
	
	@Override
	public String getName()
	{
		return CeeaCoreConstants.EXTENSIONNAME;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.ThreeDimensionalImage</code> attribute.
	 * @return the ThreeDimensionalImage
	 */
	public Media getThreeDimensionalImage(final SessionContext ctx, final Product item)
	{
		return (Media)item.getProperty( ctx, CeeaCoreConstants.Attributes.Product.THREEDIMENSIONALIMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.ThreeDimensionalImage</code> attribute.
	 * @return the ThreeDimensionalImage
	 */
	public Media getThreeDimensionalImage(final Product item)
	{
		return getThreeDimensionalImage( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.ThreeDimensionalImage</code> attribute. 
	 * @param value the ThreeDimensionalImage
	 */
	public void setThreeDimensionalImage(final SessionContext ctx, final Product item, final Media value)
	{
		item.setProperty(ctx, CeeaCoreConstants.Attributes.Product.THREEDIMENSIONALIMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.ThreeDimensionalImage</code> attribute. 
	 * @param value the ThreeDimensionalImage
	 */
	public void setThreeDimensionalImage(final Product item, final Media value)
	{
		setThreeDimensionalImage( getSession().getSessionContext(), item, value );
	}
	
}
