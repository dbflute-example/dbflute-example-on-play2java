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
package docksidestage.projectfw.web.player;

import java.lang.reflect.Method;

import org.dbflute.bhv.proposal.callback.QueryTraceableSqlStringFilter;
import org.dbflute.hook.AccessContext;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlStringFilter;
import org.dbflute.system.DBFluteSystem;
import org.dbflute.util.DfReflectionUtil;

import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import docksidestage.projectfw.web.collaboration.PlayingAccessContextHolder;

/**
 * @param <RESULT> The type of play result.
 * @author jflute
 * @since 1.1.0 (2015/01/02)
 */
public class DBFluteContextPlayer<RESULT> {

    // ===================================================================================
    //                                                                               Play!
    //                                                                               =====
    public RESULT play(Request request, Context ctx, ActionNextPlayer<RESULT> nextPlayer) throws Throwable {
        try {
            setupAccessContextOnRequestBegin(request, ctx);
            setupCallbackContextOnRequestBegin();
            return nextPlayer.play();
        } finally {
            clearCallbackContextOnRequestEnd();
            clearAccessContextOnRequestEnd(request, ctx);
        }
    }

    // ===================================================================================
    //                                                                       AccessContext
    //                                                                       =============
    protected void setupAccessContextOnRequestBegin(Request request, Context ctx) {
        final AccessContext context = createAccessContext(request, ctx);
        AccessContext.setAccessContextOnThread(context);
    }

    public AccessContext createAccessContext(Request request, Context ctx) {
        final AccessContext context = new AccessContext();
        context.setAccessUser("EXAMPLE_USER"); // TODO jflute example: Play2, #later actually from login user
        context.setAccessLocalDateTime(DBFluteSystem.currentLocalDateTime());
        return context;
    }

    protected void clearAccessContextOnRequestEnd(Request request, Context ctx) {
        AccessContext.clearAccessContextOnThread();
        PlayingAccessContextHolder.clearAccessContextFromHttpContext();
    }

    // ===================================================================================
    //                                                                     CallbackContext
    //                                                                     ===============
    protected void setupCallbackContextOnRequestBegin() {
        CallbackContext.setSqlStringFilterOnThread(createSqlStringFilter());
    }

    protected SqlStringFilter createSqlStringFilter() {
        final Method actionMethod = findActionMethod();
        return new QueryTraceableSqlStringFilter(actionMethod, null);
    }

    protected Method findActionMethod() {
        // TODO jflute example: Play2, #later non reflection for performance, waiting for 1.1.0-SP1
        final Class<?> controllerType = DfReflectionUtil.forName(getRouteController());
        return DfReflectionUtil.getPublicMethod(controllerType, getRouteActionMethod(), (Class<?>[]) null);
    }

    protected String getRouteController() {
        return (String) Http.Context.current.get().args.get("ROUTE_CONTROLLER");
    }

    protected String getRouteActionMethod() {
        return (String) Http.Context.current.get().args.get("ROUTE_ACTION_METHOD");
    }

    protected void clearCallbackContextOnRequestEnd() {
        CallbackContext.clearSqlStringFilterOnThread();
    }
}