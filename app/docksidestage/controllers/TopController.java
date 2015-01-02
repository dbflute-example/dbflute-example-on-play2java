package docksidestage.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.google.inject.Inject;

import docksidestage.dbflute.exbhv.MemberBhv;

/**
 * @author jflute
 */
public class TopController extends Controller {

    private static final Logger LOG = LoggerFactory.getLogger(TopController.class);

    @Inject
    protected MemberBhv memberBhv;

    @Transactional
    public Result index() {
        // TODO jflute example: Play2 Transaction
        memberBhv.selectEntity(cb -> {
            cb.setupSelect_MemberStatus();
            cb.acceptPK(1);
        }).alwaysPresent(member -> {
            LOG.debug("***: {}", member.getMemberName());
            member.setMemberName("seasea");
            memberBhv.update(member);
        });
        return ok(index.render("Your new application is ready."));
    }
}
