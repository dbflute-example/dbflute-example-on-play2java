package docksidestage.controllers.profile;

import java.time.LocalDate;

import docksidestage.dbflute.exentity.Member;

/**
 * @author toshiaki.arai
 */
public class ProfileWebBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** ID */
    public Integer memberId;
    /** name */
    public String memberName;
    /** account */
    public String memberAccount;
    /** address */
    public String memberAddress;
    /** birthdate */
    public LocalDate birthdate;

    // ===================================================================================
    //                                                                          Initialize
    //                                                                          ==========
    public ProfileWebBean initialize(Member entity) {
        memberId = entity.getMemberId();
        memberName = entity.getMemberName();
        memberAccount = entity.getMemberAccount();
        birthdate = entity.getBirthdate();
        entity.getMemberAddressAsValid().ifPresent(address -> {
            memberAddress = address.getAddress();
        });
        return this;
    }
}
