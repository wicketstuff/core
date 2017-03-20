package org.wicketstuff.lambda.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.link.Link;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.danekja.java.util.function.serializable.SerializableConsumer;

public class ComponentFactory 
{

    /**
     * Creates an {@link AjaxLink} based on lambda expressions
     * 
     * @param id
     *            the id of the ajax link
     * @param onClick
     *            the consumer of the clicked link and an {@link AjaxRequestTarget}
     * @return the {@link AjaxLink}
     * 
     */
    public static <T> AjaxLink<T> ajaxLink(String id,
        SerializableBiConsumer<AjaxLink<T>, AjaxRequestTarget> onClick)
    {
        return new AjaxLink<T>(id)
        {

            /**
             * 
             */
            private static final long serialVersionUID = -7879252384382557716L;

            @Override
            public void onClick(AjaxRequestTarget target)
            {
                onClick.accept(this, target);
            }
        };
    }

    /**
     * Creates an {@link AjaxButton} based on lambda expressions
     * 
     * @param id
     *            the id of the ajax button
     * @param onSubmit
     *            the consumer of the submitted button and an {@link AjaxRequestTarget}
     * @return the {@link AjaxButton}
     * 
     */
    public static AjaxButton ajaxButton(String id,
        SerializableBiConsumer<AjaxButton, AjaxRequestTarget> onSubmit)
    {
        return new AjaxButtonLambda(id, onSubmit, (button, target) -> {});
    }

    /**
     * Creates an {@link AjaxButton} based on lambda expressions
     * 
     * @param id
     *            the id of the ajax button
     * @param onSubmit
     *            the consumer of the submitted button and an {@link AjaxRequestTarget}
     * @param onError
     *            the consumer of the button in error and an {@link AjaxRequestTarget}
     * @return the {@link AjaxButton}
     * 
     */
    public static AjaxButton ajaxButton(String id,
        SerializableBiConsumer<AjaxButton, AjaxRequestTarget> onSubmit,
        SerializableBiConsumer<AjaxButton, AjaxRequestTarget> onError)
    {
        return new AjaxButtonLambda(id, onSubmit, onError);
    }

    /**
     * Creates an {@link AjaxCheckBox} based on lambda expressions
     * 
     * @param id
     *            the id of ajax check box
     * @param onUpdate
     *            the consumer of the updated checkbox and an {@link AjaxRequestTarget}
     * @return the {@link AjaxCheckBox}
     * 
     */
    public static AjaxCheckBox ajaxCheckBox(String id,
        SerializableBiConsumer<AjaxCheckBox, AjaxRequestTarget> onUpdate)
    {
        return new AjaxCheckBox(id)
        {

            /**
             * 
             */
            private static final long serialVersionUID = 615006993199791039L;

            @Override
            protected void onUpdate(AjaxRequestTarget target)
            {
                onUpdate.accept(this, target);
            }
        };
    }

    /**
     * Creates an {@link AjaxSubmitLink} based on lambda expressions
     * 
     * @param id
     *            the id of ajax submit link
     * @param onSubmit
     *            the consumer of the submitted button and an {@link AjaxRequestTarget}
     * @return the {@link AjaxSubmitLink}
     * 
     */
    public static AjaxSubmitLink ajaxSubmitLink(String id,
        SerializableBiConsumer<AjaxSubmitLink, AjaxRequestTarget> onSubmit)
    {
        return new AjaxSubmitLinkLambda(id, onSubmit, (link, target) -> {});
    }

    /**
     * Creates an {@link AjaxSubmitLink} based on lambda expressions
     * 
     * @param id
     *            the id of ajax submit link
     * @param onSubmit
     *            the consumer of the submitted link and an {@link AjaxRequestTarget}
     * @param onError
     *            the consumer of the link in error and an {@link AjaxRequestTarget}
     * @return the {@link AjaxSubmitLink}
     * 
     */
    public static AjaxSubmitLink ajaxSubmitLink(String id,
        SerializableBiConsumer<AjaxSubmitLink, AjaxRequestTarget> onSubmit,
        SerializableBiConsumer<AjaxSubmitLink, AjaxRequestTarget> onError)
    {
        return new AjaxSubmitLinkLambda(id, onSubmit, onError);
    }

    /**
     * Creates a {@link Link} based on lambda expressions
     * 
     * @param id
     *            the id of the link
     * @param onClick
     *            the consumer of the clicked link
     * @return the {@link Link}
     * 
     */
    public static <T> Link<T> link(String id, SerializableConsumer<Link<T>> onClick)
    {
        return new Link<T>(id)
        {
            /**
             * 
             */
            private static final long serialVersionUID = 4426455567301117747L;

            @Override
            public void onClick()
            {
                onClick.accept(this);
            }
        };
    }

    private static final class AjaxButtonLambda extends AjaxButton
    {
        /**
         * 
         */
        private static final long serialVersionUID = 5357407089802522143L;
        private final SerializableBiConsumer<AjaxButton, AjaxRequestTarget> onSubmit;
        private final SerializableBiConsumer<AjaxButton, AjaxRequestTarget> onError;

        private AjaxButtonLambda(String id, SerializableBiConsumer<AjaxButton, AjaxRequestTarget> onSubmit, 
            SerializableBiConsumer<AjaxButton, AjaxRequestTarget> onError)
        {
            super(id);
            this.onSubmit = onSubmit;
            this.onError = onError;
        }

        @Override
        protected void onSubmit(AjaxRequestTarget target)
        {
            onSubmit.accept(this, target);
        }

        @Override
        protected void onError(AjaxRequestTarget target)
        {
            onError.accept(this, target);
        }
    }

    private static final class AjaxSubmitLinkLambda extends AjaxSubmitLink
    {
        /**
         * 
         */
        private static final long serialVersionUID = -4648835813629191811L;
        private final SerializableBiConsumer<AjaxSubmitLink, AjaxRequestTarget> onSubmit;
        private final SerializableBiConsumer<AjaxSubmitLink, AjaxRequestTarget> onError;

        private AjaxSubmitLinkLambda(String id, SerializableBiConsumer<AjaxSubmitLink,AjaxRequestTarget> onSubmit, 
            SerializableBiConsumer<AjaxSubmitLink,AjaxRequestTarget> onError)
        {
            super(id);
            this.onSubmit = onSubmit;
            this.onError = onError;
        }

        @Override
        protected void onSubmit(AjaxRequestTarget target)
        {
            onSubmit.accept(this, target);
        }

        @Override
        protected void onError(AjaxRequestTarget target)
        {
            onError.accept(this, target);
        }
    }
}
