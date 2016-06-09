package jspellfh.PropositionModules;

import java.util.List;

/**
 * Created by axelheine on 05/06/2016.
 */
public interface PropositionModule {
    /*Fonction utilisée par les modules de proposition pour trouver les mots les plus proches du mot str*/
    List<String> findWords(String str);
}
