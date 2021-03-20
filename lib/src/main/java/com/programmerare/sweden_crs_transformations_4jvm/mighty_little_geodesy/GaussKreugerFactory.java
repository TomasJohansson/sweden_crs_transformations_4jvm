package com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy;

import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;
import java.util.HashMap;
import java.util.Map;

public class GaussKreugerFactory {
    private final static GaussKreugerFactory _gaussKreugerFactory = new GaussKreugerFactory();
    
    public static GaussKreugerFactory getInstance() {
        return _gaussKreugerFactory;
    }

    private GaussKreugerFactory() {
        CrsProjection[] crsProjections = CrsProjection.values();
        for (CrsProjection crsProjection : crsProjections) {
            GaussKreugerParameterObject gaussKreugerParameterObject = new GaussKreugerParameterObject(crsProjection);
            GaussKreuger gaussKreuger = GaussKreuger.create(gaussKreugerParameterObject);
            mapWithAllGaussKreugers.put(crsProjection, gaussKreuger);
        }        
    }

    private final Map<CrsProjection, GaussKreuger>
        mapWithAllGaussKreugers = new HashMap<CrsProjection, GaussKreuger>();
    
    public GaussKreuger getGaussKreuger(CrsProjection crsProjection) {
        if(mapWithAllGaussKreugers.containsKey(crsProjection)) {
            return mapWithAllGaussKreugers.get(crsProjection);            
        }
        throw new IllegalArgumentException("Could not find GaussKreuger for crsProjection " + crsProjection);
    }
}
