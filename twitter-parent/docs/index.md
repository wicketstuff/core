---
title: Twitter
category: js
description: Components and behaviors for the Twitter widgets, such as Tweet and Follow buttons.
artifactId: wicketstuff-twitter
tags: social x
---

The Twitter library contains wicket components and behaviors to use the Twitter widgets with wicket.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-twitter</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Buttons

There are two button types:

* Tweet
* Follow

You can find some examples here: <https://twitter.com/about/resources/buttons>

These components can be used like normal wicket components. It is required to use a link-tag (a) for the markup.
The components should contain the major attributes documented on dev.twitter.com. It is possible to set the properties in the markup as well. They just get overridden if the property is set in at the java component too.

### Button Example

```java
		final FollowButton button = new FollowButton("followButton", "tfreier");
		button.setShowFollowerCount(true);
		add(button);
```

```html
		<a wicket:id="followButton"> </a>
```

## Web Intents

Web intents are links to twitter to perform actions on twitter.
<https://dev.twitter.com/docs/intents>

### Follow Link Example

```java
		final FollowLink link = new FollowLink("link");
		link.setScreenName("tfreier");
		add(link);
```

```html
		<a wicket:id="link">follow me</a>
```

## Behaviors

The anywhere behaviors add some behaviors to normal components like a label.
<https://dev.twitter.com/docs/anywhere/welcome>

### LinkifyBehavior

The LinkifyBehavior links all words beginning with @ to the matching twitter-account.

```java
		final Label label = new Label("label", "This is a linkify test by @tfreier");
		label.add(new LinkifyBehavior("your api key"));

		add(label);
```

```html
		<span wicket:id="label"></span>
```

### HovercardBehavior

The HovercardBehavior creates hover cards for any twitter account mentions beginning with an @.

```java
		final Label label = new Label("label", "This is a hovercard test by @tfreier");
		label.add(new HovercardBehavior("your api key"));

		add(label);
```

```html
		<span wicket:id="label"></span>
```

## Event Behaviors

Event Behaviors are basically ajax callbacks for events caused by web intents.
<https://dev.twitter.com/docs/intents/events>

### Follow Event Example

```java
		final IModel<String> responseModel = Model.of();

		final FollowLink link = new FollowLink("link");
		link.setScreenName("tfreier");

		add(link);

		final Label label = new Label("resonse", responseModel);
		label.setOutputMarkupId(true);
		add(label);

		add(new AjaxFollowEventBehavior()
		{
			@Override
			protected void onEvent(final AjaxRequestTarget target, final Event event)
			{
				responseModel.setObject("followed");
				target.add(label);
			}
		});
```

```html
		<a wicket:id="link">follow</a>
```

## License

Apache 2.0.

## Author

[Till Freier](https://github.com/tfreier)

The [`twitter-examples`](https://github.com/wicketstuff/core/tree/wicket-10.x/twitter-parent/twitter-examples) module contains a runnable example application.

Source: [`twitter-parent`](https://github.com/wicketstuff/core/tree/wicket-10.x/twitter-parent)
