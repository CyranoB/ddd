package be.wallonie.ddd.kernel.rule.model;

import be.wallonie.ddd.kernel.common.model.Guard;
import be.wallonie.ddd.kernel.rule.interfaces.RuleObject;
import be.wallonie.ddd.kernel.rule.interfaces.RuleViolation;
import be.wallonie.ddd.kernel.rule.type.RuleSeverityType;
import com.trigersoft.jaque.expression.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by asmolabs on 13/11/15.
 */
public class RuleGuard {

    public static <T> boolean equalsInvariant(RuleObject ruleObject, Property<T> propertyLambda, int invariant) {
        return equalsInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    public static <T> boolean equalsInvariant(RuleObject ruleObject, Property<T> propertyLambda, int invariant, RuleSeverityType severityType) {
        final T t = propertyLambda.get();

        List<String> values = new ArrayList<>();

        values.add(propertyLambda.get().toString());
        values.add(((Integer) invariant).toString());

        return (Guard.equals((Integer) t, invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, values, 1, severityType);
    }

    public static <T> void testFunction(Property<T> obj) {

        final Function<Object, Object> identity = Function.identity();

        final LambdaExpression<Supplier<T>> parse =
                LambdaExpression.parse(obj);
    }

    public static <T> boolean raiseViolation(RuleObject ruleObject, Property<T> propertyLambda, List<String> values, int ruleId, RuleSeverityType severityType) {
        RuleViolation ruleViolation = new RuleViolationImpl(
                ruleObject,
                new ArrayList<>(),
                ruleId,
                severityType,
                null,
                new ArrayList<>(),
                true);

        final LambdaExpression<Supplier<T>> parsedTree =
                LambdaExpression.parse(propertyLambda);

        final List<ParameterExpression> parameters = parsedTree.getParameters();

        Expression body = parsedTree.getBody();

        StringBuffer buffer = new StringBuffer();

        completePropertyPath(body, buffer);

        for (String value : values) {
            ruleViolation.getValues().add(value);
        }

        //if (severityType == RuleSeverityType.BlockingError)
        //    throw new RuleException(UnitOfWorkRule.Default.Violations);

        return false;
    }

    private static void completePropertyPath(Expression expression, StringBuffer buffer) {

        switch (expression.getExpressionType()) {
            case ExpressionType.Convert:

                UnaryExpression unaryExpression = (UnaryExpression) expression;

                final Expression first = unaryExpression.getFirst();

                completePropertyPath(first, buffer);

                System.out.println();
                break;
            case ExpressionType.Lambda:
                LambdaExpression lambdaExpression = (LambdaExpression) expression;

                completePropertyPath(lambdaExpression.getBody(), buffer);

                System.out.println();
                break;
            case ExpressionType.Invoke:
                InvocationExpression invocationExpression = (InvocationExpression) expression;

                final List<Expression> arguments = invocationExpression.getArguments();

                if (buffer.toString().equals("")) {

                    ConstantExpression constantExpression = (ConstantExpression) arguments.get(0);

                    Class baseClass = constantExpression.getResultType();

                    buffer.append(baseClass.getName());

                    buffer.append("|");

                    completePropertyPath(invocationExpression.getTarget(), buffer);
                } else {


                    completePropertyPath(invocationExpression.getTarget(), buffer);
                    System.out.println();
                }
                break;
            case ExpressionType.MethodAccess:
                MemberExpression memberExpression = (MemberExpression) expression;

                buffer.append(memberExpression.getMember().getName());

                System.out.println();
                break;
            default:
                break;
        }

    }

    public interface Property<T> extends Supplier<T>, Serializable {
    }
}
