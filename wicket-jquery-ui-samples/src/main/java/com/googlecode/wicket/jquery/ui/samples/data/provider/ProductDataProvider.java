package com.googlecode.wicket.jquery.ui.samples.data.provider;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import com.googlecode.wicket.jquery.ui.samples.data.bean.Product;
import com.googlecode.wicket.jquery.ui.samples.data.dao.ProductsDAO;


public class ProductDataProvider extends ListDataProvider<Product> implements ISortStateLocator<String>
{
	private static final long serialVersionUID = 1L;

	private final SingleSortState<String> state = new SingleSortState<String>();

	public ProductDataProvider()
	{
		super(ProductsDAO.all());
	}

	@Override
	protected List<Product> getData()
	{
		List<Product> list = super.getData();
		SortParam<String> param =  this.state.getSort();

		if (param != null)
		{
			Collections.sort(list, new ProductComparator(param.getProperty(), param.isAscending()));
		}

		return list;
	}

	@Override
	public ISortState<String> getSortState()
	{
		return this.state;
	}

	static class ProductComparator implements Comparator<Product>
	{
		private final String property;
		private final boolean ascending;

		public ProductComparator(String property, boolean ascending)
		{
			this.property = property;
			this.ascending = ascending;
		}

		@Override
		public int compare(Product p1, Product p2)
		{
			Object o1 = PropertyResolver.getValue(this.property, p1);
			Object o2 = PropertyResolver.getValue(this.property, p2);

			if (o1 != null && o2 != null)
			{
				Comparable<Object> c1 = toComparable(o1);
				Comparable<Object> c2 = toComparable(o2);

				return c1.compareTo(c2) * (this.ascending ? 1 : -1);
			}

			return 0;
		}

		@SuppressWarnings("unchecked")
		private static Comparable<Object> toComparable(Object o)
		{
			if (o instanceof Comparable<?>)
			{
				return (Comparable<Object>) o;
			}

			throw new WicketRuntimeException("Object should be a Comparable");
		}
	}
}
