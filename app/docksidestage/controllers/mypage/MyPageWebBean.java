package docksidestage.controllers.mypage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import docksidestage.dbflute.exentity.Member;
import docksidestage.dbflute.exentity.Purchase;

/**
 * @author toshiaki.arai
 */
public class MyPageWebBean {

    // ===================================================================================
    public Integer memberId;
    public String memberName;
    public LocalDateTime lastLoginDatetime;
    public List<Purchase> purchaseList;

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
        purchaseList = new ArrayList<Purchase>();
        entity.getPurchaseList().forEach(purchase -> {
            purchaseList.add(purchase);
        });

        return this;
    }
}
