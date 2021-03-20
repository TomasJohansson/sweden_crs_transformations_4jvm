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

        GaussKreuger gkProjection = GaussKreuger.create(crsProjection);
        
        return gkProjection;
    }
}
