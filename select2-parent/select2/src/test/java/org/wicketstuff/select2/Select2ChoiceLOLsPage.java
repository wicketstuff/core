package org.wicketstuff.select2;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author cdjost
 */
public class Select2ChoiceLOLsPage extends WebPage {

    final Select2Choice<List<ELOLsState>> listOfListsSelect;

    final Form<Void> form;

    public Select2ChoiceLOLsPage() {
        super();

        final List<List<ELOLsState>> states = new LinkedList<>();
        states.add(ELOLsState.LIST_1);
        states.add(ELOLsState.LIST_2);
        states.add(ELOLsState.LIST_3);

        List<ELOLsState> selectedStates = ELOLsState.LIST_1;

        IModel<List<ELOLsState>> selectedStatesModel = Model.ofList(selectedStates);
        listOfListsSelect = new Select2Choice<>("selectState", selectedStatesModel, new LOLsChoiceProvider(states));

        form = new Form<>("form");
        form.add(listOfListsSelect);
        add(this.form);
    }
}
