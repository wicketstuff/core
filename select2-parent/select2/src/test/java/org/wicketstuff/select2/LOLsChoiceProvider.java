package org.wicketstuff.select2;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author cdjost
 */
public class LOLsChoiceProvider extends ChoiceProvider<List<ELOLsState>> {

    private final List<List<ELOLsState>> selectableStates;

    public LOLsChoiceProvider(List<List<ELOLsState>> selectableStates) {
        this.selectableStates = selectableStates;
    }

    @Override
    public String getDisplayValue(List<ELOLsState> listOfStates) {
        if (listOfStates != null) {
            final String prefix = "EState.";
            if (listOfStates.equals(ELOLsState.LIST_1)) {
                return "LIST_1+";
            }

            if (listOfStates.equals(ELOLsState.LIST_2)) {
                return "LIST_2+";
            }

            if (listOfStates.equals(ELOLsState.LIST_3)) {
                return "LIST_3+";
            }
        }
        return "Something is wrong";
    }

    @Override
    public String getIdValue(List<ELOLsState> listOfStates) {
        return String.valueOf(selectableStates.indexOf(listOfStates));
    }

    @Override
    public void query(String term, int page, Response<List<ELOLsState>> response) {
        // Could be implemented
        response.addAll(selectableStates);
    }

    @Override
    public Collection<List<ELOLsState>> toChoices(Collection<String> collection) {
        final String id = collection.stream().findFirst().orElseThrow(NoSuchElementException::new);
        final int index = Integer.parseInt(id);
        return Arrays.asList(selectableStates.get(index));
    }
}