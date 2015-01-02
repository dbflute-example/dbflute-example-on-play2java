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
package docksidestage.projectfw.web;

import org.dbflute.hook.AccessContext;
import org.dbflute.system.DBFluteSystem;

import play.mvc.Http.Context;
import play.mvc.Http.Request;

/**
 * @author jflute
 * @since 1.1.0 (2015/01/02)
 */
public class RequestAccessContextFactory {

    public AccessContext createAccessContext(Request request, Context ctx) {
        final AccessContext context = new AccessContext();
        context.setAccessUser("EXAMPLE_USER"); // actually from login user
        context.setAccessLocalDateTime(DBFluteSystem.currentLocalDateTime());
        return context;
    }
}