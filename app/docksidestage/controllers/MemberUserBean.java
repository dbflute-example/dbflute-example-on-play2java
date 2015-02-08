package docksidestage.controllers;

import docksidestage.dbflute.exentity.Member;

/**
 * @author jun_0915
 */
public class MemberUserBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final Integer memberId;
    protected final String memberName;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public MemberUserBean(Member member) {
        memberId = member.getMemberId();
        memberName = member.getMemberName();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public Integer getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }
}
