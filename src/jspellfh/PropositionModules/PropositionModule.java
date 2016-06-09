package jspellfh.PropositionModules;

import java.util.List;

/**
 * Created by axelheine on 05/06/2016.
 */
public interface PropositionModule {
    /**
     * Fonction utilisée par les modules de proposition pour trouver les mots les plus proches du mot str
     * @param str Word to compare with dictionary
     * @return List of string that match the str param
     */

    List<String> findWords(String str);
}
