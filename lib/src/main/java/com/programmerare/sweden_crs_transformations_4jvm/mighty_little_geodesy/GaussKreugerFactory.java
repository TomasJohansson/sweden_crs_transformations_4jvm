/*
 * Copyright (c) Tomas Johansson , http://www.programmerare.com
 * The code in this library is licensed with MIT.
 * The library is based on the C#.NET library 'sweden_crs_transformations_4net' (https://github.com/TomasJohansson/sweden_crs_transformations_4net)
 * which in turn is based on 'MightyLittleGeodesy' (https://github.com/bjornsallarp/MightyLittleGeodesy/)
 * which is also released with MIT.
 * License information about 'sweden_crs_transformations_4jvm' and 'MightyLittleGeodesy':
 * https://github.com/TomasJohansson/sweden_crs_transformations_4jvm/blob/java_SwedenCrsTransformations/LICENSE
 * For more information see the webpage below.
 * https://github.com/TomasJohansson/sweden_crs_transformations_4jvm
 */
package com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy;

import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class 'GaussKreugerFactory' was not part of the original 'MightyLittleGeodesy' library. 
 */
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
