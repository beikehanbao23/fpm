package com.mappy.data.batches.tomtom.shapefiles;

import com.mappy.data.batches.tomtom.TomtomFolder;
import com.mappy.data.batches.tomtom.TomtomShapefile;
import com.mappy.data.batches.tomtom.dbf.names.NameProvider;
import com.mappy.data.batches.utils.Feature;
import com.mappy.data.batches.utils.GeometrySerializer;

import javax.inject.Inject;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class WaterLineShapefile extends TomtomShapefile {

    private final NameProvider nameProvider;

    @Inject
    public WaterLineShapefile(TomtomFolder folder, NameProvider nameProvider) {
        super(folder.getFile("wl.shp"));
        this.nameProvider = nameProvider;
        this.nameProvider.loadFromFile("wxnm.dbf", "NAME", false);
    }

    @Override
    public void serialize(GeometrySerializer geometrySerializer, Feature feature) {
        Map<String, String> tags = newHashMap();
        String name = feature.getString("NAME");
        if (name != null) {
            tags.put("name", name);
        }
        switch (feature.getInteger("DISPCLASS")) {
            case 1:
            case 2:
                tags.put("waterway", "river");
                break;
            default:
                tags.put("waterway", "stream");
                break;
        }
        tags.put("natural", "water");
        tags.putAll(nameProvider.getAlternateNames(feature.getLong("ID")));
        geometrySerializer.write(feature.getMultiLineString(), tags);
    }
}