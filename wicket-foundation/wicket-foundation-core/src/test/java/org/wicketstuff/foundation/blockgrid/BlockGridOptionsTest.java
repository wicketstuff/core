package org.wicketstuff.foundation.blockgrid;

import org.junit.Test;
import org.wicketstuff.foundation.blockgrid.BlockGridOptions;
import org.wicketstuff.foundation.blockgrid.BlockGridType;

public class BlockGridOptionsTest {

	@Test
	public void test() {
		BlockGridOptions options = new BlockGridOptions(BlockGridType.SMALL_BLOCK_GRID, 1);
		BlockGridOptions options2 = new BlockGridOptions(options);
		options2.setColumnCount(2);
		options2.setType(BlockGridType.LARGE_BLOCK_GRID);
		BlockGridOptions options3 = new BlockGridOptions(options2.getType(), options2.getColumnCount());
	}

}
