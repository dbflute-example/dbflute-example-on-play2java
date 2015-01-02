package docksidestage.projectfw.core;

import com.google.inject.Injector;

/**
 * @author jflute
 * @since 1.1.0 (2015/01/02)
 */
public class Components {

    protected static Injector injector;

    public static void acceptInjector(Injector initializedInjector) {
        injector = initializedInjector;
    }

    public static <COMPONENT> COMPONENT find(Class<COMPONENT> type) {
        return injector.getInstance(type);
    }
}
