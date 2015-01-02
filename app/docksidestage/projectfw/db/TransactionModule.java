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
package docksidestage.projectfw.db;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * @author jflute
 * @since 1.1.0 (2015/01/02)
 */
public class TransactionModule extends AbstractModule {

    protected final DataSource dataSource;

    public TransactionModule(DataSource dataSource) {
        if (dataSource == null) {
            String msg = "The argument 'dataSource' should not be null.";
            throw new IllegalArgumentException(msg);
        }
        this.dataSource = dataSource;
    }

    @Override
    protected void configure() { // using Spring Transaction
        final PlatformTransactionManager transactionManager = createPlatformTransactionManager();
        final AnnotationTransactionAttributeSource attributeSource = createAnnotationTransactionAttributeSource();
        final TransactionInterceptor transactionInterceptor = createTransactionInterceptor(transactionManager, attributeSource);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class), transactionInterceptor);
        bind(PlatformTransactionManager.class).toInstance(transactionManager);
    }

    protected PlatformTransactionManager createPlatformTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    protected AnnotationTransactionAttributeSource createAnnotationTransactionAttributeSource() {
        return new AnnotationTransactionAttributeSource(false);
    }

    protected TransactionInterceptor createTransactionInterceptor(PlatformTransactionManager transactionManager,
            AnnotationTransactionAttributeSource attributeSource) {
        return new TransactionInterceptor(transactionManager, attributeSource);
    }

    public DataSource provideDBFluteDataSource() {
        return new TransactionAwareDataSourceProxy(dataSource);
    }
}