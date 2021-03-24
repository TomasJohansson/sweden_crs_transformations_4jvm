package com.programmerare.sweden_crs_transformations_4jvm.transformation;

import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;

abstract class TransformStrategyBase {
    
    protected enum SourceOrTargetProjection {
        SourceProjection, TargetProjection;
    } 
    
    protected void assertCoordinateProjections(
        CrsProjection sourceProjection,
        boolean conditionThatMustBeTrueForSourceProjection,
        TransformStrategy _transformStrategySource,
        CrsProjection targetProjection,
        boolean conditionThatMustBeTrueForTargetProjection        
    ) {
        this.assertCoordinateProjection(
            SourceOrTargetProjection.SourceProjection,
            sourceProjection,
            conditionThatMustBeTrueForSourceProjection,
            _transformStrategySource
        );
        assertCoordinateProjection(
            SourceOrTargetProjection.TargetProjection,
            sourceProjection,
            conditionThatMustBeTrueForTargetProjection,
            _transformStrategySource
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
