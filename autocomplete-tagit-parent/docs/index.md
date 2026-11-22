---
title: Tag-it Autocomplete
category: js
description: Tag input with autocompletion, based on jquery.tagit.
artifactId: wicketstuff-autocomplete-tagit
tags: tags
---

This project is an integration between [Apache Wicket](https://wicket.apache.org/) and [Tag-it](https://aehlke.github.io/tag-it/), an extension of the jQuery UI autocomplete component.

## Usage

Add an instance of `org.wicketstuff.tagit.TagItTextField` to your form. For example:

```java
form.add(new TagItTextField<String>("tagit", Model.of("")) {

	@Override
	protected Iterable<String> getChoices(String input) {
		return suggestService.getSuggestions(input);
	}
});
```

The `TagItTextField.getChoices(String)` implementation should return a collection of possible choices for the passed input from the user.

After submit the form component value is a comma separated list of all chosen values.

To modify the default Tag-it configuration, override `org.wicketstuff.tagit.TagItAjaxBehavior.getTagItConfig()`.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-autocomplete-tagit</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

The [`autocomplete-tagit-examples`](https://github.com/wicketstuff/core/tree/master/autocomplete-tagit-parent/autocomplete-tagit-examples) module contains a runnable example application.

Source: [`autocomplete-tagit-parent`](https://github.com/wicketstuff/core/tree/master/autocomplete-tagit-parent)
