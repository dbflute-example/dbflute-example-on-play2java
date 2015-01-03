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
package docksidestage;

import java.lang.reflect.Method;

import org.dbflute.hook.AccessContext;

import play.Application;
import play.GlobalSettings;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.mvc.Result;

import com.google.inject.Injector;

import docksidestage.projectfw.core.Components;
import docksidestage.projectfw.web.cooperation.PlayingAccessContextHolder;
import docksidestage.projectfw.web.cooperation.PlayingInjectorFactory;
import docksidestage.projectfw.web.player.CallingLoggingPlayer;
import docksidestage.projectfw.web.player.DBFluteContextPlayer;
import docksidestage.projectfw.web.player.RequestLoggingPlayer;

/**
 * #appfit
 * @author jflute
 */
public class Global extends GlobalSettings {

    // ===================================================================================
    //                                                                 Controller Instance
    //                                                                 ===================
    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        return Components.find(controllerClass); // Components is initialized on start
    }

    // ===================================================================================
    //                                                                            on Start
    //                                                                            ========
    @Override
    public void onStart(Application app) {
        prepareAccessContextSurrogateHolder(app);
        prepareComponentsInjector(app);
    }

    // -----------------------------------------------------
    //                                         AccessContext
    //                                         -------------
    protected void prepareAccessContextSurrogateHolder(Application app) {
        AccessContext.unlock();
        AccessContext.useSurrogateHolder(createPlayingAccessContextHolder(app));
    }

    protected PlayingAccessContextHolder createPlayingAccessContextHolder(Application app) {
        return new PlayingAccessContextHolder();
    }

    // -----------------------------------------------------
    //                                              Injector
    //                                              --------
    protected void prepareComponentsInjector(Application app) {
        Components.acceptInjector(createInjector(app));
    }

    protected Injector createInjector(Application app) {
        return createPlayingInjectorFactory(app).createInjector();
    }

    protected PlayingInjectorFactory createPlayingInjectorFactory(Application app) {
        return new PlayingInjectorFactory(app);
    }

    // ===================================================================================
    //                                                                          on Request
    //                                                                          ==========
    @Override
    public Action<Void> onRequest(Request request, Method actionMethod) {
        final String controllerName = actionMethod.getDeclaringClass().getSimpleName();
        return createAction(request, controllerName);
    }

    protected Action.Simple createAction(Request request, String controllerName) {
        return new Action.Simple() {
            public F.Promise<Result> call(Context ctx) throws Throwable {
                return play(request, ctx, delegate);
            }
        };
    }

    protected F.Promise<Result> play(Request request, Context ctx, Action<?> delegate) throws Throwable {
        return createRequestLoggingFilter().play(request, ctx, () -> {
            return createDBFluteContextFilter().play(request, ctx, () -> {
                return createCallingLoggingFilter().play(request, ctx, () -> {
                    return delegate.call(ctx);
                });
            });
        });
    }

    protected RequestLoggingPlayer<F.Promise<Result>> createRequestLoggingFilter() {
        return new RequestLoggingPlayer<F.Promise<Result>>();
    }

    protected DBFluteContextPlayer<F.Promise<Result>> createDBFluteContextFilter() {
        return new DBFluteContextPlayer<F.Promise<Result>>();
    }

    protected CallingLoggingPlayer<F.Promise<Result>> createCallingLoggingFilter() {
        return new CallingLoggingPlayer<F.Promise<Result>>();
    }
}
