/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.gchart.gchart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicketstuff.gchart.ColumnDeclaration;
import org.wicketstuff.gchart.ColumnType;
import org.wicketstuff.gchart.DataRow;
import org.wicketstuff.gchart.DataTable;

/**
 *
 * @author Dieter Tremel
 */
public class DataTableTest {

    private List<ColumnDeclaration> colDefs;
    private List<DataRow> rows;

    public DataTableTest() {
    }

    @BeforeEach
    public void setUp() {
        colDefs = new ArrayList<>(2);
        colDefs.add(new ColumnDeclaration(ColumnType.STRING, "Topping"));
        colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, "Slices"));

        rows = new ArrayList<>(5);
        rows.add(new DataRow(Arrays.asList(new Object[]{"Mushrooms", 3})));
        rows.add(new DataRow(Arrays.asList(new Object[]{"Onions", 1})));
        rows.add(new DataRow(Arrays.asList(new Object[]{"Olives", 1})));
        rows.add(new DataRow(Arrays.asList(new Object[]{"Zucchini", 1})));
        rows.add(new DataRow(Arrays.asList(new Object[]{"Pepperoni", 2})));
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of toJavaScript method, of class DataTable.
     */
    @Test
    public void testToJavaScript() {
        DataTable tbl = new DataTable("data", colDefs, rows);
        String expResult1 = "var data = new google.visualization.DataTable({\"cols\":[{\"label\":\"Topping\",\"type\":\"string\"},{\"label\":\"Slices\",\"type\":\"number\"}],\"rows\":[{\"c\":[{\"v\":\"Mushrooms\"},{\"v\":3}]},{\"c\":[{\"v\":\"Onions\"},{\"v\":1}]},{\"c\":[{\"v\":\"Olives\"},{\"v\":1}]},{\"c\":[{\"v\":\"Zucchini\"},{\"v\":1}]},{\"c\":[{\"v\":\"Pepperoni\"},{\"v\":2}]}]});\n";
        String result = tbl.toJavaScript();
//        System.out.println(result);
        assertEquals(expResult1, result);
    }

    /**
     * Test of fromArray method, of class DataTable.
     */
    @Test
    public void testFromArray() {
        Object[][] data = new Object[][]{{"Zeile 1", 1},{"Zeile 2", 2, "Zusatz"}};
        List<DataRow> expResult = new ArrayList<>(2);
        DataRow row1 = new DataRow();
        DataRow row2 = new DataRow();
        expResult.add(row1);
        expResult.add(row2);
        row1.add("Zeile 1");
        row1.add(1);
        row2.add("Zeile 2");
        row2.add(2);
        row2.add("Zusatz");
        List<DataRow> result = DataTable.fromArray(data);
        assertEquals(expResult, result);
    }

}
