package org.wicketstuff.foundation;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.foundation.blockgrid.BlockGridOptions;
import org.wicketstuff.foundation.blockgrid.BlockGridType;
import org.wicketstuff.foundation.blockgrid.FoundationBlockGrid;
import org.wicketstuff.foundation.blockgrid.FoundationBlockGridImageContent;

public class BlockGridPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public BlockGridPage(PageParameters params) {
		super(params);
		add(new FoundationBlockGrid<String>("basic", new BlockGridOptions(BlockGridType.SMALL_BLOCK_GRID, 3), Arrays.asList("satelite.jpg", "space.jpg", "spacewalk.jpg")) {
			@Override
			public WebMarkupContainer createContent(int idx, String id,
					IModel<String> model) {
				return new FoundationBlockGridImageContent(id, new PackageResourceReference(this.getClass(), model.getObject()));
			}			
		});
		List<BlockGridOptions> optionsList = Arrays.asList(new BlockGridOptions(BlockGridType.SMALL_BLOCK_GRID, 2), 
				new BlockGridOptions(BlockGridType.MEDIUM_BLOCK_GRID, 3), new BlockGridOptions(BlockGridType.LARGE_BLOCK_GRID, 4));
		List<String> imageList = Arrays.asList("satelite.jpg", "space.jpg", "spacewalk.jpg", "satelite.jpg", "space.jpg", "spacewalk.jpg");
		add(new FoundationBlockGrid<String>("advanced", optionsList, imageList) {
			@Override
			public WebMarkupContainer createContent(int idx, String id,
					IModel<String> model) {
				return new FoundationBlockGridImageContent(id, new PackageResourceReference(this.getClass(), model.getObject()));
			}			
		});
	}
}
