package org.wicketstuff.select2;

import java.util.Arrays;
import java.util.List;

/**
 * @author cdjost
 */
public enum ELOLsState {
    STATE_1,
    STATE_2,
    STATE_3;

    public static final List<ELOLsState> LIST_1 = Arrays.asList(STATE_1, STATE_2);

    public static final List<ELOLsState> LIST_2 = Arrays.asList(STATE_1, STATE_3);

    public static final List<ELOLsState> LIST_3 = Arrays.asList(STATE_3);
}