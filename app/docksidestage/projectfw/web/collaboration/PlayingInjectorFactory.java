/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package docksidestage.projectfw.web.collaboration;

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
        prepareYourModules(moduleList);
        return moduleList.toArray(new Module[moduleList.size()]);
    }

    protected void prepareYourModules(List<Module> moduleList) {
        // you can override
    }
}