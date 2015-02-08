package docksidestage.controllers.mypage;

import java.time.LocalDateTime;

import docksidestage.dbflute.exentity.Member;


/**
 * @author toshiaki.arai
 */
public class MyPageWebBean {

    // ===================================================================================
    public Integer memberId;
    public String memberName;
    public LocalDateTime lastLoginDatetime;
    
    // ===================================================================================
    //                                                                          Initialize
    //                                                                          ==========
    /**
     * @param entity (Not Null)
     * @return MyPageWebBean (Not Null)
     */
    public MyPageWebBean initialize(Member entity) {
        memberId = entity.getMemberId();
        memberName = entity.getMemberName();
        entity.getMemberLoginAsLatest().ifPresent(lastLogin -> {
            lastLoginDatetime = lastLogin.getLoginDatetime();
        });
        return this;
    }
}
