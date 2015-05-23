package org.wicketstuff.foundation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.dropdown.DropdownHover;
import org.wicketstuff.foundation.dropdown.DropdownListAlignment;
import org.wicketstuff.foundation.dropdown.DropdownOptions;
import org.wicketstuff.foundation.dropdown.DropdownType;
import org.wicketstuff.foundation.dropdown.FoundationContentDropdown;
import org.wicketstuff.foundation.dropdown.FoundationDropdown;

public class DropdownsPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public DropdownsPage(PageParameters params) {
		super(params);
		
		List<String> links = Arrays.asList("First link", "Second link", "Third link");
		FoundationDropdown basicDropdown = new FoundationDropdown("basicDropdown", "Has Dropdown", new DropdownOptions(), links) {
				@Override
				protected AbstractLink createDropdownLink(int idx,
						String id) {
					return new Link<String>(id) {
						@Override
						public void onClick() {}
					};
				}
		};
		add(basicDropdown);
		
		final String content = "<p>Some text that people will think is awesome! Some text that people will think is awesome! Some text that people will think is awesome!</p>";
		FoundationContentDropdown basicContentDropdown = new FoundationContentDropdown("basicContentDropdown", "Has Content Dropdown", new DropdownOptions(), content);
		add(basicContentDropdown);
		
		List<String> advancedLinks = Arrays.asList("This is a link", "This is another", "Yet another");
		FoundationDropdown advancedDropdownLink = new FoundationDropdown("advancedDropdownLink", "Link Dropdown", new DropdownOptions(DropdownType.DROPDOWNLINK), advancedLinks) {
				@Override
				protected AbstractLink createDropdownLink(int idx,
						String id) {
					return new Link<String>(id) {
						@Override
						public void onClick() {}
					};
				}
		};
		add(advancedDropdownLink);		
		
		List<String> directionLinks = Arrays.asList("This is a link", "This is another", "Yet another");
		add(createDirectionDropdown("directionTop", "Top", directionLinks, DropdownListAlignment.TOP));
		add(createDirectionDropdown("directionLeft", "Left", directionLinks, DropdownListAlignment.LEFT));
		add(createDirectionDropdown("directionDown", "Down", directionLinks, null));
		add(createDirectionDropdown("directionRight", "Right", directionLinks, DropdownListAlignment.RIGHT));
		
		List<String> hoverLinks = Arrays.asList("First link", "Second link", "Third link");
		FoundationDropdown hoverDropdown = new FoundationDropdown("hoverDropdown", "Has Hover Dropdown", new DropdownOptions(DropdownHover.HOVERABLE), hoverLinks) {
				@Override
				protected AbstractLink createDropdownLink(int idx, String id) {
					return new Link<String>(id) {
						@Override
						public void onClick() {}
					};
				}
		};
		add(hoverDropdown);
	}
	
	private FoundationDropdown createDirectionDropdown(String id, String title, List<String> links, DropdownListAlignment align) {
		DropdownOptions options = new DropdownOptions(DropdownType.DROPDOWNLINK).setListAlignment(align);
		return new FoundationDropdown(id, title, options, Collections.unmodifiableList(links)) {
				@Override
				protected AbstractLink createDropdownLink(int idx, String id) {
					return new Link<String>(id) {
						@Override
						public void onClick() {}
					};
				}
		};
	}
}
