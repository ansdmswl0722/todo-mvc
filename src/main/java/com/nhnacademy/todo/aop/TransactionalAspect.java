package com.nhnacademy.todo.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.List;

@RequiredArgsConstructor
//@EnableAspectJAutoProxy
@Configuration
public class TransactionalAspect {
    private final TransactionManager transactionManager;

    private static final String AOP_EXPRESSION = "execution(* com.nhnacademy.todo.service..*Impl.*(..))";

    @Bean
    public TransactionInterceptor transactionInterceptor() { // 에러가 나며는 롤백를 해준다.
        List<RollbackRuleAttribute> rollbackRules = List.of(
                new RollbackRuleAttribute(Exception.class),
                new RollbackRuleAttribute(Error.class)
        );
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setRollbackRules(rollbackRules);
        transactionAttribute.setName("my-transaction");

        MatchAlwaysTransactionAttributeSource attributeSource = new MatchAlwaysTransactionAttributeSource();
        attributeSource.setTransactionAttribute(transactionAttribute);

        return new TransactionInterceptor(transactionManager, attributeSource);
    }

    @Bean
    public Advisor transactionAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, transactionInterceptor());
    }

}