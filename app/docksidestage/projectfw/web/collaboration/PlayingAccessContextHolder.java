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

import org.dbflute.hook.AccessContext;
import org.dbflute.hook.AccessContext.AccessContextHolder;

import play.mvc.Http;

/**
 * @author jflute
 * @since 1.1.0 (2015/01/02)
 */
public class PlayingAccessContextHolder implements AccessContextHolder {

    protected static final String CTX_ARG_ACCESS_CONTEXT = "CTX_ARG_ACCESS_CONTEXT";

    @Override
    public void save(AccessContext accessContext) {
        Http.Context.current().args.put(CTX_ARG_ACCESS_CONTEXT, accessContext);
    }

    @Override
    public AccessContext provide() {
        return (AccessContext) Http.Context.current().args.get(CTX_ARG_ACCESS_CONTEXT);
    }

    public static void clearAccessContextFromHttpContext() {
        Http.Context.current().args.remove(CTX_ARG_ACCESS_CONTEXT);
    }
}