package dev.minestomunited.entrypoint.environment;


public enum Environment {
    PRODUCTION, // Production & Staging environment
    DEPLOYING, // (Staging ~ Production) Deploying environment
    STAGING, // Staging environment only
    TESTING, // (Dev ~ Staging) Testing environment
    DEVELOPMENT, // Development environment only
    ANY; // Any environment


    /**
     * Tests whether {@code this} environment satisfies the given {@code environment} requirement.
     *
     * <p>Each environment implicitly includes "lower" environments in the hierarchy:
     * {@code PRODUCTION ⊃ DEPLOYING ⊃ STAGING ⊃ TESTING ⊃ DEVELOPMENT}.
     * {@link #ANY} always returns {@code true} regardless of the argument.
     *
     * @param environment the required environment level to test against
     * @return {@code true} if this environment meets the requirement
     */
    public boolean test(Environment environment) {
        if (this == ANY) return true;

        return switch (environment) {
            case PRODUCTION -> this == PRODUCTION || this == DEPLOYING;
            case DEPLOYING -> this == STAGING || this == DEPLOYING || this == PRODUCTION;
            case STAGING -> this == STAGING || this == DEPLOYING;
            case TESTING -> this == DEVELOPMENT || this == TESTING || this == STAGING;
            case DEVELOPMENT -> this == DEVELOPMENT || this == TESTING;
            default -> this == environment;
        };
    }
}
