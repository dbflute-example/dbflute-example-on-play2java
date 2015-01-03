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

import org.dbflute.util.DfStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Http.Request;

/**
 * @param <RESULT> The type of play result.
 * @author jflute
 * @since 1.1.0 (2015/01/02)
 */
public class CallingLoggingPlayer<RESULT> {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger LOG = LoggerFactory.getLogger(CallingLoggingPlayer.class);

    // ===================================================================================
    //                                                                               Play!
    //                                                                               =====
    public RESULT play(Request request, Context ctx, NextPlayer<RESULT> nextPlayer) throws Throwable {
        showController();
        return nextPlayer.play();
    }

    // ===================================================================================
    //                                                                     Show Controller
    //                                                                     ===============
    protected void showController() {
        if (LOG.isDebugEnabled()) {
            final String controllerName = getRouteController();
            if (controllerName == null) {
                return;
            }
            final String actionMethodName = getRouteActionMethod();
            if (actionMethodName == null) {
                return;
            }
            final String simpleName = DfStringUtil.substringLastRear(controllerName, ".");
            final String comments = filterComments(getRouteComments());
            LOG.debug("...Beginning #controller {}.{}() // {}", simpleName, actionMethodName, comments);
        }
    }

    protected String getRouteController() {
        return (String) Http.Context.current.get().args.get("ROUTE_CONTROLLER");
    }

    protected String getRouteActionMethod() {
        return (String) Http.Context.current.get().args.get("ROUTE_ACTION_METHOD");
    }

    protected String getRouteComments() {
        return (String) Http.Context.current.get().args.get("ROUTE_COMMENTS");
    }

    protected String filterComments(final String msg) {
        final String linerDisp = convertToOneLinerDisp(msg);
        return linerDisp != null ? DfStringUtil.ltrim(linerDisp) : null;
    }

    protected String convertToOneLinerDisp(final String msg) {
        final String filtered;
        final String ln = "\n";
        if (msg != null && msg.contains(ln)) {
            filtered = msg.substring(0, msg.indexOf(ln)) + "...";
        } else {
            filtered = msg;
        }
        return filtered;
    }
}