package com.programmerare.sweden_crs_transformations_4jvm;

/**
 * Base class for TransformStrategy implementations, providing a helper method to enforce preconditions.
 */
abstract class TransformStrategyBase {
    
    protected enum SourceOrTargetProjection {
        SourceProjection, TargetProjection;
    }

    /**
     * Asserts two preconditions. One condition that must be true for the source projection and one for the target projection.   
     * @param sourceProjection the source projection
     * @param conditionThatMustBeTrueForSourceProjection the boolean precondition, belonging to the source projection, that must be true 
     * @param transformStrategySource an instance of the TransformStrategy implementation class that requires the preconditions to be enforced  
     * @param targetProjection the target projection
     * @param conditionThatMustBeTrueForTargetProjection the boolean precondition, belonging to the target projection, that must be true
     * @throws IllegalArgumentException if a precondition is violated
     */    
    protected void assertCoordinateProjections(
        CrsProjection sourceProjection,
        boolean conditionThatMustBeTrueForSourceProjection,
        TransformStrategy transformStrategySource,
        CrsProjection targetProjection,
        boolean conditionThatMustBeTrueForTargetProjection        
    ) {
        this.assertCoordinateProjection(
            SourceOrTargetProjection.SourceProjection,
            sourceProjection,
            conditionThatMustBeTrueForSourceProjection,
            transformStrategySource
        );
        assertCoordinateProjection(
            SourceOrTargetProjection.TargetProjection,
            targetProjection,
            conditionThatMustBeTrueForTargetProjection,
            transformStrategySource
        );        
    }

    private void assertCoordinateProjection(
        SourceOrTargetProjection sourceOrTargetProjection,
        CrsProjection crsProjection,
        boolean conditionThatMustBeTrue,
        TransformStrategy _transformStrategySource
    ) {
        if(!conditionThatMustBeTrue) {
            String message = "Precondition violated for " + sourceOrTargetProjection + " " + crsProjection + ". It is not valid as " + sourceOrTargetProjection + " for the class " + _transformStrategySource.getClass().getName();
            throw new IllegalArgumentException(message);
        }
    }    
}
