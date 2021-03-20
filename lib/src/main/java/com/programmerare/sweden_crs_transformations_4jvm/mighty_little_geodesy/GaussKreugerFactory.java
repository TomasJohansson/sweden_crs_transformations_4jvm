package com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy;

import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;

public class GaussKreugerFactory
{
    private final static GaussKreugerFactory _gaussKreugerFactory = new GaussKreugerFactory();
    
    public static GaussKreugerFactory getInstance() {
        return _gaussKreugerFactory;
    }

    private GaussKreugerFactory() {
    }
    
    public GaussKreuger getGaussKreuger(CrsProjection crsProjection) {
        // TODO cache the 'GaussKreuger' instances instead of creating new instances every time in this method

        GaussKreuger gkProjection = new GaussKreuger();
        // TODO make the 'GaussKreuger' immutable, e.g. provide the projection as parameter to the above constructor instead of the below method   
        gkProjection.swedish_params(crsProjection);
        
        return gkProjection;
    }
}
