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

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Http.Cookie;
import play.mvc.Http.Cookies;
import play.mvc.Http.Request;
import play.mvc.Http.RequestBody;
import play.mvc.Http.Response;
import play.mvc.Http.Session;

/**
 * @param <RESULT> The type of play result.
 * @author jflute
 * @since 1.1.0 (2015/01/02)
 */
public class RequestLoggingPlayer<RESULT> {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger LOG = LoggerFactory.getLogger(RequestLoggingPlayer.class);
    protected static final String LF = "\n";
    protected static final String IND = "  ";

    // ===================================================================================
    //                                                                               Play!
    //                                                                               =====
    public RESULT play(Request request, Context ctx, ActionNextPlayer<RESULT> nextPlayer) throws Throwable {
        Long before = null;
        if (LOG.isDebugEnabled()) {
            before = before(request, ctx);
        }
        boolean existsServerError = false;
        try {
            return nextPlayer.play();
        } catch (Exception e) { // not catch error
            existsServerError = true;
            // TODO jflute example: Play2, #hope arrange log message (want to remove duplicate display)
            String msg = buildErrorMessage(request, ctx, "#exception occurred.", e);
            LOG.error(msg); // play2 shows details so request info only here
            throw e;
        } finally {
            if (LOG.isDebugEnabled()) {
                if (existsServerError) {
                    attention(request, ctx);
                } else {
                    final long after = System.currentTimeMillis();
                    after(request, ctx, before, after);
                }
            }
        }
    }

    // ===================================================================================
    //                                                                              Before
    //                                                                              ======
    public long before(Request request, Context ctx) {
        final StringBuilder sb = new StringBuilder();
        final String beginDecoration = "* * * * * * * * * * {BEGIN}: ";
        sb.append(beginDecoration);
        sb.append(getTitlePath(request));
        sb.append("\n").append("  ");
        buildRequestInfo(sb, request, ctx, false);
        LOG.debug(sb.toString().trim());
        return System.currentTimeMillis();
    }

    protected void buildRequestInfo(StringBuilder sb, Request request, Context ctx, boolean showResponse) {
        sb.append("Request class=" + request.getClass().getName());
        //sb.append(", RequestedSessionId=").append(request.getRequestedSessionId());

        sb.append(LF).append(IND);
        sb.append(", REQUEST_URI=").append(request.uri());
        sb.append(", REQUEST_METHOD=").append(request.method());
        sb.append(", isSecure=").append(request.secure());

        sb.append(LF).append(IND);
        sb.append(", REMOTE_ADDR=").append(request.remoteAddress());
        sb.append(", REMOTE_HOST=").append(request.host());
        sb.append(", RemoteUser=").append(request.username());

        sb.append(LF).append(IND);
        sb.append(", QUERY_STRING=").append(request.queryString());
        if (showResponse) {
            sb.append(LF).append(IND);
            buildResponseInfo(sb, request, ctx);
        }

        sb.append(LF);
        buildRequestHeaders(sb, request, ctx);
        buildRequestParameters(sb, request, ctx);
        buildCookies(sb, request, ctx);
        buildRequestAttributes(sb, request, ctx);
        buildSessionAttributes(sb, request, ctx);
    }

    // ===================================================================================
    //                                                                               After
    //                                                                               =====
    public void after(Request request, Context ctx, Long before, Long after) {
        if (!LOG.isDebugEnabled()) {
            return;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(LF).append(IND);
        buildResponseInfo(sb, request, ctx);

        // show only dynamic values in request
        sb.append(LF);
        // hope response cookie (not request cookie)
        //buildCookies(sb, request);
        buildRequestAttributes(sb, request, ctx);
        buildSessionAttributes(sb, request, ctx);

        final String endDecoration = "* * * * * * * * * * {END}: ";
        sb.append(endDecoration);
        sb.append(getTitlePath(request));
        sb.append(" [" + convertToPerformanceView(after.longValue() - before.longValue()) + "]");
        sb.append(LF);
        sb.append(LF);

        String logString = sb.toString();
        LOG.debug(logString);
    }

    // ===================================================================================
    //                                                                        Request Info
    //                                                                        ============
    protected String getTitlePath(Request request) {
        return request.path();
    }

    protected void buildRequestHeaders(StringBuilder sb, Request request, Context ctx) {
        final Map<String, String[]> headers = request.headers();
        for (Entry<String, String[]> entry : headers.entrySet()) {
            final String name = entry.getKey();
            final String[] values = entry.getValue();
            if (values == null || values.length == 0) {
                continue;
            }
            sb.append(IND);
            sb.append("[header] ").append(name);
            sb.append("=").append(values.length > 1 ? Arrays.asList(values) : values[0]);
            sb.append(LF);
        }
    }

    protected void buildRequestParameters(StringBuilder sb, Request request, Context ctx) {
        final RequestBody body = request.body();
        if (body == null) {
            return;
        }
        Map<String, String[]> parameterMap = body.asFormUrlEncoded();
        parameterMap = parameterMap != null ? parameterMap : request.queryString();
        if (parameterMap == null) {
            return;
        }
        for (Entry<String, String[]> entry : parameterMap.entrySet()) {
            final String name = entry.getKey();
            final String[] values = entry.getValue();
            if (values == null || values.length == 0) {
                continue;
            }
            sb.append(IND);
            sb.append("[param] ").append(name).append("=");
            for (int i = 0; i < values.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(values[i]);
            }
            sb.append(LF);
        }
    }

    protected void buildCookies(StringBuilder sb, Request request, Context ctx) {
        final Cookies cookies = request.cookies();
        if (cookies == null) {
            return;
        }
        for (Cookie cookie : cookies) {
            sb.append(IND);
            sb.append("[cookie] ").append(cookie.name());
            sb.append("=").append(cookie.value());
            sb.append(LF);
        }
    }

    protected void buildRequestAttributes(StringBuilder sb, Request request, Context ctx) {
        final Context context = Http.Context.current.get();
        if (context == null) {
            return;
        }
        for (Entry<String, Object> entry : context.args.entrySet()) {
            final String key = entry.getKey();
            if (key.startsWith("ROUTE_")) { // internal info, and other logging shows it
                continue;
            }
            sb.append(IND);
            sb.append("[request] ").append(key);
            sb.append("=").append(filterAttributeDisp(entry.getValue()));
            sb.append(LF);
        }
    }

    protected void buildSessionAttributes(StringBuilder sb, Request request, Context ctx) {
        Session session = ctx.session();
        if (session == null) {
            return;
        }
        for (Entry<String, String> entry : session.entrySet()) {
            sb.append(IND);
            sb.append("[session] ").append(entry.getKey());
            sb.append("=").append(filterAttributeDisp(entry.getValue()));
            sb.append(LF);
        }
    }

    protected String filterAttributeDisp(final Object attr) {
        if (attr == null) {
            return "null";
        }
        final String stringExp;
        if (attr instanceof Throwable) { // exception will be displayed in another way
            stringExp = ((Throwable) attr).getMessage();
        } else {
            stringExp = attr.toString();
        }
        // might contain line separator in the expression
        // and large display is noisy for debug so one liner
        return convertToOneLinerDisp(stringExp);
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

    protected void buildResponseInfo(StringBuilder sb, Request request, Context ctx) {
        final Response response = ctx.response();
        if (response == null) {
            return;
        }
        sb.append("Response class=" + response.getClass().getName());
        String exp = response.toString().trim();
        if (exp != null) {
            exp = replaceString(exp, "\r\n", "\n");
            exp = replaceString(exp, "\n", " ");
            final int limitLength = 120;
            if (exp.length() >= limitLength) {
                // it is possible that Response toString() show all HTML strings
                // so cut it to suppress too big logging here
                exp = exp.substring(0, limitLength) + "...";
            }
            sb.append(LF).append(IND);
            sb.append(", toString()=").append(exp);
            // e.g. Jetty
            // HTTP/1.1 200  Expires: Thu, 01-Jan-1970 00:00:00 GMT Set-Cookie: ...
        }
    }

    // ===================================================================================
    //                                                                             Failure
    //                                                                             =======
    protected String buildErrorMessage(Request request, Context ctx, String comment, Throwable e) {
        final StringBuilder sb = new StringBuilder();
        sb.append(comment);
        sb.append(LF);
        sb.append("/= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =: " + getTitlePath(request));
        sb.append(LF).append(IND);
        try {
            buildRequestInfo(sb, request, ctx, true);
        } catch (RuntimeException continued) {
            sb.append("*Failed to get request info: " + continued.getMessage());
            sb.append(LF);
        }
        sb.append("= = = = = = = = = =/");
        return sb.toString().trim();
    }

    protected void attention(Request request, Context ctx) {
        final StringBuilder sb = new StringBuilder();
        sb.append("{FAILURE}: ").append(getTitlePath(request));
        sb.append(LF);
        sb.append(" *Read the exception message!");
        sb.append(LF);
        LOG.debug(sb.toString());
    }

    // ===================================================================================
    //                                                                       Assist Helper
    //                                                                       =============
    protected String replaceString(String str, String fromStr, String toStr) {
        StringBuilder sb = null; // lazy load
        int basePos = 0;
        int nextPos = 0;
        do {
            basePos = str.indexOf(fromStr, nextPos);
            if (nextPos == 0 && basePos < 0) { // first loop and not found
                return str; // without creating StringBuilder
            }
            if (sb == null) {
                sb = new StringBuilder();
            }
            if (basePos == 0) {
                sb.append(toStr);
                nextPos = fromStr.length();
            } else if (basePos > 0) {
                sb.append(str.substring(nextPos, basePos));
                sb.append(toStr);
                nextPos = basePos + fromStr.length();
            } else { // (basePos < 0) second or after loop only
                sb.append(str.substring(nextPos));
                return sb.toString();
            }
        } while (true);
    }

    /**
     * Convert to performance view.
     * @param afterMinusBefore The difference between before time and after time.
     * @return The view string to show performance. e.g. 01m40s012ms (NotNull)
     */
    protected String convertToPerformanceView(long afterMinusBefore) { // from DfTraceViewUtil.java
        if (afterMinusBefore < 0) {
            return String.valueOf(afterMinusBefore);
        }

        long sec = afterMinusBefore / 1000;
        final long min = sec / 60;
        sec = sec % 60;
        final long mil = afterMinusBefore % 1000;

        final StringBuffer sb = new StringBuffer();
        if (min >= 10) { // Minute
            sb.append(min).append("m");
        } else if (min < 10 && min >= 0) {
            sb.append("0").append(min).append("m");
        }
        if (sec >= 10) { // Second
            sb.append(sec).append("s");
        } else if (sec < 10 && sec >= 0) {
            sb.append("0").append(sec).append("s");
        }
        if (mil >= 100) { // Millisecond
            sb.append(mil).append("ms");
        } else if (mil < 100 && mil >= 10) {
            sb.append("0").append(mil).append("ms");
        } else if (mil < 10 && mil >= 0) {
            sb.append("00").append(mil).append("ms");
        }

        return sb.toString();
    }
}