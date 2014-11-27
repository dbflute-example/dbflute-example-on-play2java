package domainfw.db;

import java.sql.Connection;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.dbflute.hook.AccessContext;

import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import domainfw.core.Components;

/**
 * @author jflute
 */
public class TxAction extends Action<TxController> {

    @Override
    public Promise<Result> call(Context ctx) throws Throwable {
        DataSource dataSource = Components.find(DataSource.class);
        Connection conn = dataSource.getConnection();
        try {
            AccessContext accessContext = new AccessContext();
            accessContext.setAccessLocalDateTime(LocalDateTime.now());
            accessContext.setAccessUser("abc");
            AccessContext.setAccessContextOnThread(accessContext);

            System.out.println("...Beginning transaction");
            conn.setAutoCommit(false);
            Promise<Result> result = delegate.call(ctx);
            System.out.println("...Committing transaction");
            conn.commit();
            return result;
        } finally {
            System.out.println("...Rollbacking transaction");
            conn.rollback();
            AccessContext.clearAccessContextOnThread();
        }
    }
}
