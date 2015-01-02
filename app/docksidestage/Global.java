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
import docksidestage.projectfw.web.PlayingAccessContextHolder;
import docksidestage.projectfw.web.PlayingInjectorFactory;
import docksidestage.projectfw.web.RequestAccessContextFactory;
import docksidestage.projectfw.web.RequestLoggingPlayer;

/**
 * @author jflute
 */
public class Global extends GlobalSettings {

    {
        System.out.println("@@@@@@@: " + toString());
    }

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
        prepareAccessContextSurrogateHolder();
        Components.acceptInjector(createInjector(app));
    }

    protected void prepareAccessContextSurrogateHolder() {
        AccessContext.unlock();
        AccessContext.useSurrogateHolder(new PlayingAccessContextHolder());
    }

    protected Injector createInjector(Application app) {
        final PlayingInjectorFactory factory = new PlayingInjectorFactory(app);
        return factory.createInjector();
    }

    // ===================================================================================
    //                                                                          on Request
    //                                                                          ==========
    @Override
    public Action<Void> onRequest(Request request, Method actionMethod) {
        final String controllerName = actionMethod.getDeclaringClass().getSimpleName();
        return createAction(request, controllerName);
    }

    protected Action.Simple createAction(Request request, final String controllerName) {
        return new Action.Simple() {
            public F.Promise<Result> call(Context ctx) throws Throwable {
                final RequestLoggingPlayer loggingPlayer = new RequestLoggingPlayer();
                return loggingPlayer.doFilter(request, ctx, () -> {
                    setupAccessContextOnRequestBegin(request, ctx);
                    try {
                        return delegate.call(ctx);
                    } finally {
                        clearAccessContextOnRequestEnd(request, ctx);
                    }
                });
            }
        };
    }

    protected void setupAccessContextOnRequestBegin(Request request, Context ctx) {
        final RequestAccessContextFactory factory = new RequestAccessContextFactory();
        final AccessContext context = factory.createAccessContext(request, ctx);
        AccessContext.setAccessContextOnThread(context);
    }

    protected void clearAccessContextOnRequestEnd(Request request, Context ctx) {
        AccessContext.clearAccessContextOnThread();
    }
}
