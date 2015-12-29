package org.wicketstuff.select2;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.resource.StringResourceStream;

import java.io.ByteArrayOutputStream;


/**
 * A resource that serves JSON for stateless . You have to mount at application init like. E.g.
 * 
 * <p>
 * <pre>
 * 	mountResource(CATEGORIES_JSON, new JsonResourceReference<CategoryTranslation>() {
 * 			
 * 			private static final long serialVersionUID = 1L;
 * 	
 * 			protected ChoiceProvider<CategoryTranslation> getChoiceProvider() {
 * 				return CategoriesTextChoiceProvider.getInstance();
 * 			}
 * 		});
 * 		
 * </pre>	
 * </p>
 * 
 * and then on component you add
 * 
 * <p>
 *  <pre>
 *  	Select2MultiChoice<CategoryTranslation> c = new Select2MultiChoice<CategoryTranslation>("categories", new PropertyModel<Collection<CategoryTranslation>>(searchBean, "translations"), CategoriesTextChoiceProvider.getInstance());
 *  	c.getSettings().setStateless(true);
 *  	c.getSettings().setMountPath(Application.CATEGORIES_JSON);
 *  </pre>
 * <p>
 * 
 * @author Ernesto Reinaldo Barreiro (reiern70@mail.com).
 */
public abstract class JsonResourceReference<T> extends ResourceReference 
{
	private static final long serialVersionUID = 1L;

	public JsonResourceReference() 
    {
		super(JsonResourceReference.class, "images");
	}

	@Override
	public IResource getResource() 
    {
	    ByteArrayOutputStream webResponse = new ByteArrayOutputStream();
	    AbstractSelect2Choice.generateJSON(getChoiceProvider(), webResponse);
	    StringResourceStream resourceStream = new StringResourceStream(webResponse.toString(), "application/json");
	    return new ResourceStreamResource(resourceStream);
	}

	/**
	 * The choice provider.
	 * 
	 * @return
	 */
	protected abstract ChoiceProvider<T> getChoiceProvider();

}
