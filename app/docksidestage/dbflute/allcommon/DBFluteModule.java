/*
 * Copyright 2014-2014 the original author or authors.
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
package docksidestage.dbflute.allcommon;

import javax.sql.DataSource;

import com.google.inject.AbstractModule;
import org.dbflute.bhv.BehaviorSelector;
import org.dbflute.bhv.core.BehaviorCommandInvoker;
import org.dbflute.bhv.core.InvokerAssistant;
import org.dbflute.hook.CommonColumnAutoSetupper;
import docksidestage.dbflute.exbhv.*;

/**
 * @author DBFlute(AutoGenerator)
 */
public class DBFluteModule extends AbstractModule {

    protected DataSource dataSource;

    public DBFluteModule(DataSource dataSource) {
        if (dataSource == null) {
            String msg = "The argument 'dataSource' should not be null!";
            throw new IllegalArgumentException(msg);
        }
        this.dataSource = dataSource;
    }

    @Override
    protected void configure() {
        createDBFluteInitializer(); // initialized by only a creation

        // The components of DBFlute Runtime.
        ImplementedBehaviorSelector behaviorSelector = createImplementedBehaviorSelector();
        BehaviorCommandInvoker behaviorCommandInvoker = createBehaviorCommandInvoker();
        ImplementedInvokerAssistant invokerAssistant = createImplementedInvokerAssistant();
        behaviorCommandInvoker.setInvokerAssistant(invokerAssistant);
        ImplementedCommonColumnAutoSetupper commonColumnAutoSetupper = createImplementedCommonColumnAutoSetupper();

        bind(BehaviorSelector.class).toInstance(behaviorSelector);
        bind(BehaviorCommandInvoker.class).toInstance(behaviorCommandInvoker);
        bind(InvokerAssistant.class).toInstance(invokerAssistant);
        bind(CommonColumnAutoSetupper.class).toInstance(commonColumnAutoSetupper);

        // The components of Behavior.
        setupBehavior(behaviorCommandInvoker, behaviorSelector, commonColumnAutoSetupper);
    }

    protected DBFluteInitializer createDBFluteInitializer() {
        return new docksidestage.dbflute.allcommon.DBFluteInitializer();
    }

    protected ImplementedBehaviorSelector createImplementedBehaviorSelector() {
        return new ImplementedBehaviorSelector();
    }

    protected BehaviorCommandInvoker createBehaviorCommandInvoker() {
        return new BehaviorCommandInvoker();
    }

    protected ImplementedInvokerAssistant createImplementedInvokerAssistant() {
        ImplementedInvokerAssistant invokerAssistant = new docksidestage.dbflute.allcommon.ImplementedInvokerAssistant();
        invokerAssistant.setDataSource(dataSource);
        return invokerAssistant;
    }

    protected ImplementedCommonColumnAutoSetupper createImplementedCommonColumnAutoSetupper() {
        return new docksidestage.dbflute.allcommon.ImplementedCommonColumnAutoSetupper();
    }

    protected void setupBehavior(BehaviorCommandInvoker ker, BehaviorSelector tor, CommonColumnAutoSetupper per) {
        {
            MemberBhv bhv = new MemberBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(MemberBhv.class).toInstance(bhv);
        }
        {
            MemberAddressBhv bhv = new MemberAddressBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(MemberAddressBhv.class).toInstance(bhv);
        }
        {
            MemberFollowingBhv bhv = new MemberFollowingBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(MemberFollowingBhv.class).toInstance(bhv);
        }
        {
            MemberLoginBhv bhv = new MemberLoginBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(MemberLoginBhv.class).toInstance(bhv);
        }
        {
            MemberSecurityBhv bhv = new MemberSecurityBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(MemberSecurityBhv.class).toInstance(bhv);
        }
        {
            MemberServiceBhv bhv = new MemberServiceBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(MemberServiceBhv.class).toInstance(bhv);
        }
        {
            MemberStatusBhv bhv = new MemberStatusBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(MemberStatusBhv.class).toInstance(bhv);
        }
        {
            MemberWithdrawalBhv bhv = new MemberWithdrawalBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(MemberWithdrawalBhv.class).toInstance(bhv);
        }
        {
            ProductBhv bhv = new ProductBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(ProductBhv.class).toInstance(bhv);
        }
        {
            ProductCategoryBhv bhv = new ProductCategoryBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(ProductCategoryBhv.class).toInstance(bhv);
        }
        {
            ProductStatusBhv bhv = new ProductStatusBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(ProductStatusBhv.class).toInstance(bhv);
        }
        {
            PurchaseBhv bhv = new PurchaseBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(PurchaseBhv.class).toInstance(bhv);
        }
        {
            PurchasePaymentBhv bhv = new PurchasePaymentBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(PurchasePaymentBhv.class).toInstance(bhv);
        }
        {
            RegionBhv bhv = new RegionBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(RegionBhv.class).toInstance(bhv);
        }
        {
            ServiceRankBhv bhv = new ServiceRankBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(ServiceRankBhv.class).toInstance(bhv);
        }
        {
            SummaryProductBhv bhv = new SummaryProductBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor);
            bind(SummaryProductBhv.class).toInstance(bhv);
        }
        {
            SummaryWithdrawalBhv bhv = new SummaryWithdrawalBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor);
            bind(SummaryWithdrawalBhv.class).toInstance(bhv);
        }
        {
            WithdrawalReasonBhv bhv = new WithdrawalReasonBhv();
            bhv.setBehaviorCommandInvoker(ker); bhv.setBehaviorSelector(tor); bhv.setCommonColumnAutoSetupper(per);
            bind(WithdrawalReasonBhv.class).toInstance(bhv);
        }
    }
}
