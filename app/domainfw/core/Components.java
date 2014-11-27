package domainfw.core;

import javax.sql.DataSource;

import play.db.DB;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import dbflute.allcommon.DBFluteModule;

/**
 * @author jflute
 */
public class Components {

    protected static Injector injector;

    public static <COMPONENT> COMPONENT find(Class<COMPONENT> type) {
        if (injector == null) {
            synchronized (Components.class) {
                if (injector == null) {
                    prepareInjector();
                }
            }
        }
        return injector.getInstance(type);
    }

    protected static void prepareInjector() {
        DataSource dataSource = DB.getDataSource(); // needs 'db.default' settings in application.conf
        injector = Guice.createInjector(new DBFluteModule(dataSource), createJdbcModule(dataSource));
    }

    protected static Module createJdbcModule(DataSource dataSource) {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(DataSource.class).toInstance(dataSource);
            }
        };
    }
}
