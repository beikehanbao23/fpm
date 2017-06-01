package com.mappy.data.batches.tomtom.shapefiles;

import javax.inject.Inject;
import com.mappy.data.batches.tomtom.TomtomFolder;
import com.mappy.data.batches.tomtom.helpers.BoundariesShapefile;

public class BoundariesA7Shapefile extends BoundariesShapefile {
    @Inject
    public BoundariesA7Shapefile(TomtomFolder folder) {
        super(folder.getFile("___a7.shp"), 6);
    }
}