package com.googlecode.wicket.jquery.ui.samples.data.dao;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.data.bean.Product;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Product.Vendor;

public class ProductsDAO
{
	private static ProductsDAO instance = new ProductsDAO();

	public static List<Product> all()
	{
		return instance.list;
	}

	public static Product select(int id)
	{
		for (Product product : all())
		{
			if (product.getId() == id)
			{
				return product;
			}
		}

		return null;
	}

	public static void insert(Product product)
	{
		if (product.getId() == 0)
		{
			product.setId(nextId());
			all().add(product);
		}
	}

	public static void update(Product product)
	{
		Product updated = select(product.getId());

		if (updated != null)
		{
			updated.setName(product.getName());
			updated.setDescription(product.getDescription());
			updated.setDate(product.getDate());
			updated.setPrice(product.getPrice());
			// TODO: updated.setVendor(product.getVendor());
		}
	}

	public static void delete(Product product)
	{
		Product deleted = select(product.getId());

		if (deleted != null)
		{
			all().remove(deleted);
		}
	}

	private static int nextId()
	{
		int max = 0;

		for (Product product : all())
		{
			int id = product.getId();

			if (max < id)
			{
				max = id;
			}
		}

		return max + 1;
	}

	private final List<Product> list;

	public ProductsDAO()
	{
		this.list = new ArrayList<Product>();
		this.list.add(new Product(1, "Chai", "10 boxes x 20 bags", 18.0000, new Vendor("vendor #1")));
		this.list.add(new Product(2, "Chang", "24 - 12 oz bottles", 19.0000, new Vendor("vendor #2")));
		this.list.add(new Product(3, "Aniseed Syrup", "12 - 550 ml bottles", 10.0000, new Vendor("vendor #3")));
		this.list.add(new Product(4, "Chef Anton's Cajun Seasoning", "48 - 6 oz jars", 22.0000, new Vendor("vendor #4")));
		this.list.add(new Product(5, "Chef Anton's Gumbo Mix", "36 boxes", 21.3500, new Vendor("vendor #5")));
		this.list.add(new Product(6, "Grandma's Boysenberry Spread", "12 - 8 oz jars", 25.0000, new Vendor("vendor #1")));
		this.list.add(new Product(7, "Uncle Bob's Organic Dried Pears", "12 - 1 lb pkgs.", 30.0000, new Vendor("vendor #2")));
		this.list.add(new Product(8, "Northwoods \"Cranberry\" Sauce", "12 - 12 oz jars", 40.0000, new Vendor("vendor #3")));
		this.list.add(new Product(9, "Mishi Kobe Niku", "18 - 500 g pkgs.", 97.0000, new Vendor("vendor #4")));
		this.list.add(new Product(10, "Ikura", "12 - 200 ml jars", 31.0000, new Vendor("vendor #5")));
		this.list.add(new Product(11, "Queso Cabrales", "1 kg pkg.", 21.0000, new Vendor("vendor #1")));
		this.list.add(new Product(12, "Queso Manchego La Pastora", "10 - 500 g pkgs.", 38.0000, new Vendor("vendor #2")));
		this.list.add(new Product(13, "Konbu", "2 kg box", 6.0000, new Vendor("vendor #3")));
		this.list.add(new Product(14, "Tofu", "40 - 100 g pkgs.", 23.2500, new Vendor("vendor #4")));
		this.list.add(new Product(15, "Genen Shouyu", "24 - 250 ml bottles", 15.5000, new Vendor("vendor #5")));
		this.list.add(new Product(16, "Pavlova", "32 - 500 g boxes", 17.4500, new Vendor("vendor #1")));
		this.list.add(new Product(17, "Alice Mutton", "20 - 1 kg tins", 39.0000, new Vendor("vendor #2")));
		this.list.add(new Product(18, "Carnarvon Tigers", "16 kg pkg.", 62.5000, new Vendor("vendor #3")));
		this.list.add(new Product(19, "Teatime Chocolate Biscuits", "10 boxes x 12 pieces", 9.2000, new Vendor("vendor #4")));
		this.list.add(new Product(20, "Sir Rodney's Marmalade", "30 gift boxes", 81.0000, new Vendor("vendor #5")));
		this.list.add(new Product(21, "Sir Rodney's Scones", "24 pkgs. x 4 pieces", 10.0000, new Vendor("vendor #1")));
		this.list.add(new Product(22, "Gustaf's Knäckebröd", "24 - 500 g pkgs.", 21.0000, new Vendor("vendor #2")));
		this.list.add(new Product(23, "Tunnbröd", "12 - 250 g pkgs.", 9.0000, new Vendor("vendor #3")));
		this.list.add(new Product(24, "Guaraná Fantástica", "12 - 355 ml cans", 4.5000, new Vendor("vendor #4")));
		this.list.add(new Product(25, "NuNuCa Nuß-Nougat-Creme", "20 - 450 g glasses", 14.0000, new Vendor("vendor #5")));
		this.list.add(new Product(26, "Gumbär Gummibärchen", "100 - 250 g bags", 31.2300, new Vendor("vendor #1")));
		this.list.add(new Product(27, "Schoggi Schokolade", "100 - 100 g pieces", 43.9000, new Vendor("vendor #2")));
		this.list.add(new Product(28, "Rössle Sauerkraut", "25 - 825 g cans", 45.6000, new Vendor("vendor #3")));
		this.list.add(new Product(29, "Thüringer Rostbratwurst", "50 bags x 30 sausgs.", 123.7900, new Vendor("vendor #4")));
		this.list.add(new Product(30, "Nord-Ost Matjeshering", "10 - 200 g glasses", 25.8900, new Vendor("vendor #5")));
		this.list.add(new Product(31, "Gorgonzola Telino", "12 - 100 g pkgs", 12.5000, new Vendor("vendor #1")));
		this.list.add(new Product(32, "Mascarpone Fabioli", "24 - 200 g pkgs.", 32.0000, new Vendor("vendor #2")));
		this.list.add(new Product(33, "Geitost", "500 g", 2.5000, new Vendor("vendor #3")));
		this.list.add(new Product(34, "Sasquatch Ale", "24 - 12 oz bottles", 14.0000, new Vendor("vendor #4")));
		this.list.add(new Product(35, "Steeleye Stout", "24 - 12 oz bottles", 18.0000, new Vendor("vendor #5")));
		this.list.add(new Product(36, "Inlagd Sill", "24 - 250 g  jars", 19.0000, new Vendor("vendor #1")));
		this.list.add(new Product(37, "Gravad lax", "12 - 500 g pkgs.", 26.0000, new Vendor("vendor #2")));
		this.list.add(new Product(38, "Côte de Blaye", "12 - 75 cl bottles", 263.5000, new Vendor("vendor #3")));
		this.list.add(new Product(39, "Chartreuse verte", "750 cc per bottle", 18.0000, new Vendor("vendor #4")));
		this.list.add(new Product(40, "Boston Crab Meat", "24 - 4 oz tins", 18.4000, new Vendor("vendor #5")));
		this.list.add(new Product(41, "Jack's New England Clam Chowder", "12 - 12 oz cans", 9.6500, new Vendor("vendor #1")));
		this.list.add(new Product(42, "Singaporean Hokkien Fried Mee", "32 - 1 kg pkgs.", 14.0000, new Vendor("vendor #2")));
		this.list.add(new Product(43, "Ipoh Coffee", "16 - 500 g tins", 46.0000, new Vendor("vendor #3")));
		this.list.add(new Product(44, "Gula Malacca", "20 - 2 kg bags", 19.4500, new Vendor("vendor #4")));
		this.list.add(new Product(45, "Rogede sild", "1k pkg.", 9.5000, new Vendor("vendor #5")));
		this.list.add(new Product(46, "Spegesild", "4 - 450 g glasses", 12.0000, new Vendor("vendor #1")));
		this.list.add(new Product(47, "Zaanse koeken", "10 - 4 oz boxes", 9.5000, new Vendor("vendor #2")));
		this.list.add(new Product(48, "Chocolade", "10 pkgs.", 12.7500, new Vendor("vendor #3")));
		this.list.add(new Product(49, "Maxilaku", "24 - 50 g pkgs.", 20.0000, new Vendor("vendor #4")));
		this.list.add(new Product(50, "Valkoinen suklaa", "12 - 100 g bars", 16.2500, new Vendor("vendor #5")));
		this.list.add(new Product(51, "Manjimup Dried Apples", "50 - 300 g pkgs.", 53.0000, new Vendor("vendor #1")));
		this.list.add(new Product(52, "Filo Mix", "16 - 2 kg boxes", 7.0000, new Vendor("vendor #2")));
		this.list.add(new Product(53, "Perth Pasties", "48 pieces", 32.8000, new Vendor("vendor #3")));
		this.list.add(new Product(54, "Tourtière", "16 pies", 7.4500, new Vendor("vendor #4")));
		this.list.add(new Product(55, "Pâté chinois", "24 boxes x 2 pies", 24.0000, new Vendor("vendor #5")));
		this.list.add(new Product(56, "Gnocchi di nonna Alice", "24 - 250 g pkgs.", 38.0000, new Vendor("vendor #1")));
		this.list.add(new Product(57, "Ravioli Angelo", "24 - 250 g pkgs.", 19.5000, new Vendor("vendor #2")));
		this.list.add(new Product(58, "Escargots de Bourgogne", "24 pieces", 13.2500, new Vendor("vendor #3")));
		this.list.add(new Product(59, "Raclette Courdavault", "5 kg pkg.", 55.0000, new Vendor("vendor #4")));
		this.list.add(new Product(60, "Camembert Pierrot", "15 - 300 g rounds", 34.0000, new Vendor("vendor #5")));
		this.list.add(new Product(61, "Sirop d'érable", "24 - 500 ml bottles", 28.5000, new Vendor("vendor #1")));
		this.list.add(new Product(62, "Tarte au sucre", "48 pies", 49.3000, new Vendor("vendor #2")));
		this.list.add(new Product(63, "Vegie-spread", "15 - 625 g jars", 43.9000, new Vendor("vendor #3")));
		this.list.add(new Product(64, "Wimmers gute Semmelknödel", "20 bags x 4 pieces", 33.2500, new Vendor("vendor #4")));
		this.list.add(new Product(65, "Louisiana Fiery Hot Pepper Sauce", "32 - 8 oz bottles", 21.0500, new Vendor("vendor #5")));
		this.list.add(new Product(66, "Louisiana Hot Spiced Okra", "24 - 8 oz jars", 17.0000, new Vendor("vendor #1")));
		this.list.add(new Product(67, "Laughing Lumberjack Lager", "24 - 12 oz bottles", 14.0000, new Vendor("vendor #2")));
		this.list.add(new Product(68, "Scottish Longbreads", "10 boxes x 8 pieces", 12.5000, new Vendor("vendor #3")));
		this.list.add(new Product(69, "Gudbrandsdalsost", "10 kg pkg.", 36.0000, new Vendor("vendor #4")));
		this.list.add(new Product(70, "Outback Lager", "24 - 355 ml bottles", 15.0000, new Vendor("vendor #5")));
		this.list.add(new Product(71, "Flotemysost", "10 - 500 g pkgs.", 21.5000, new Vendor("vendor #1")));
		this.list.add(new Product(72, "Mozzarella di Giovanni", "24 - 200 g pkgs.", 34.8000, new Vendor("vendor #2")));
		this.list.add(new Product(73, "Röd Kaviar", "24 - 150 g jars", 15.0000, new Vendor("vendor #3")));
		this.list.add(new Product(74, "Longlife Tofu", "5 kg pkg.", 10.0000, new Vendor("vendor #4")));
		this.list.add(new Product(75, "Rhönbräu Klosterbier", "24 - 0.5 l bottles", 7.7500, new Vendor("vendor #5")));
		this.list.add(new Product(76, "Lakkalikööri", "500 ml", 18.0000, new Vendor("vendor #1")));
		this.list.add(new Product(77, "Original Frankfurter grüne Soße", "12 boxes", 13.0000, new Vendor("vendor #2")));
	}
}
