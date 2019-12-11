package org.wicketstuff.select2;

import java.util.List;

/**
 * @author cdjost
 */
public enum ELOLsState {
    STATE_1,
    STATE_2,
    STATE_3;

    public static final List<ELOLsState> LIST_1 = List.of(STATE_1, STATE_2);

    public static final List<ELOLsState> LIST_2 = List.of(STATE_1, STATE_3);

    public static final List<ELOLsState> LIST_3 = List.of(STATE_3);
}