package docksidestage.projectfw.web;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import play.Application;
import play.db.DB;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

import docksidestage.dbflute.allcommon.DBFluteModule;
import docksidestage.projectfw.db.TransactionModule;

/**
 * @author jflute
 * @since 1.1.0 (2015/01/02)
 */
public class PlayingInjectorFactory {

    protected final Application app;

    public PlayingInjectorFactory(Application app) {
        this.app = app;
    }

    public Injector createInjector() {
        return Guice.createInjector(prepareStage(app), prepareModules(app));
    }

    protected Stage prepareStage(Application app) {
        return app.isProd() ? Stage.PRODUCTION : Stage.DEVELOPMENT;
    }

    protected Module[] prepareModules(Application app) {
        final List<Module> moduleList = new ArrayList<Module>();
        final DataSource dataSource = DB.getDataSource(); // needs 'db.default' settings in application.conf
        final TransactionModule transactionModule = new TransactionModule(dataSource);
        final DBFluteModule dbfluteModule = new DBFluteModule(transactionModule.provideDBFluteDataSource());
        moduleList.add(transactionModule);
        moduleList.add(dbfluteModule);
        return moduleList.toArray(new Module[moduleList.size()]);
    }
}