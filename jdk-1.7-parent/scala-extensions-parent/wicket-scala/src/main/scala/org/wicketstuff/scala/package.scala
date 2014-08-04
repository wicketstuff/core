package org.wicketstuff

import org.apache.wicket.{MarkupContainer, Component}

/**
 *
 */
package object scala {

  implicit class ScalaComponentOps(val component: Component)
    extends ScalaComponent {

    override val self: component.type = component
  }

  implicit class ScalaMarkupContainerOps(val container: MarkupContainer)
    extends ScalaMarkupContainer {

    override val self: container.type = container
  }
}
