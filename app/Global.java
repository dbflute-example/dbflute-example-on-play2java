import java.lang.reflect.Method;

import org.dbflute.hook.AccessContext;
import org.dbflute.hook.AccessContext.AccessContextHolder;

import play.Application;
import play.GlobalSettings;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.mvc.Result;
import domainfw.core.Components;

/**
 * @author jflute
 */
public class Global extends GlobalSettings {

    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        return Components.find(controllerClass);
    }

    @Override
    public void onStart(Application app) {
        AccessContext.unlock();
        AccessContext.useSurrogateHolder(new AccessContextHolder() {
            private static final String CTX_ARG_ACCESS_CONTEXT = "CTX_ARG_ACCESS_CONTEXT";

            @Override
            public void save(AccessContext accessContext) {
                Http.Context.current().args.put(CTX_ARG_ACCESS_CONTEXT, accessContext);
            }

            @Override
            public AccessContext provide() {
                return (AccessContext) Http.Context.current().args.get(CTX_ARG_ACCESS_CONTEXT);
            }
        });
        AccessContext.lock();
    }

    @Override
    public Action<Void> onRequest(Request request, Method actionMethod) {
        final String controllerName = actionMethod.getDeclaringClass().getSimpleName();
        return createAction(controllerName);
    }

    protected Action.Simple createAction(final String controllerName) {
        return new Action.Simple() {
            public F.Promise<Result> call(Context ctx) throws Throwable {
                AccessContext accessContext = new AccessContext();
                accessContext.setAccessUser("EXAMPLE_USER"); // actually from login user
                accessContext.setAccessProcess(controllerName);
                AccessContext.setAccessContextOnThread(accessContext);
                try {
                    return delegate.call(ctx);
                } finally {
                    AccessContext.clearAccessContextOnThread();
                }
            }
        };
    }
}
