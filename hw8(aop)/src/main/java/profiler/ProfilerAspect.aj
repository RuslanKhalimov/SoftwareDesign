package profiler;

public aspect ProfilerAspect {
    pointcut methodCall(): call (* application.*.*(..));

    before(): methodCall() {
        Profiler.methodIn(String.format("%s.%s",
                thisJoinPointStaticPart.getSignature().getDeclaringTypeName(),
                thisJoinPointStaticPart.getSignature().getName()
        ));
    }

    after(): methodCall() {
        Profiler.methodOut(String.format("%s.%s",
                thisJoinPointStaticPart.getSignature().getDeclaringTypeName(),
                thisJoinPointStaticPart.getSignature().getName()
        ));
    }

}
