

import play.GlobalSettings;
import domainfw.core.Components;

/**
 * @author jflute
 */
public class Global extends GlobalSettings {

    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        return Components.find(controllerClass);
    }
}
