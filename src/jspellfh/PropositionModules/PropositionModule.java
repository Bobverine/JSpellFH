package jspellfh.PropositionModules;

import java.util.List;

/**
 * Created by axelheine on 05/06/2016.
 */
public interface PropositionModule {
    List<String> findWords(String str);
    void updateModule(String word);
}
