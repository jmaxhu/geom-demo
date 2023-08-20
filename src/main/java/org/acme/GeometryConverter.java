package org.acme;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;

import java.util.logging.LogManager;
import java.util.logging.Logger;


//@Converter(autoApply = true)
public class GeometryConverter implements AttributeConverter<Geometry, String> {

    Logger log = LogManager.getLogManager().getLogger(this.getClass().getName());

    private static WKBWriter writer = new WKBWriter();
    private static WKBReader reader = new WKBReader();

    @Override
    public String convertToDatabaseColumn(Geometry geom) {
        String wkbString = WKBWriter.toHex(writer.write(geom));
        log.info("Convert " + geom + " to " + wkbString);
        return wkbString;
    }

    @Override
    public Geometry convertToEntityAttribute(String wkbString) {
        Geometry geom;
        try {
            geom = reader.read(WKBReader.hexToBytes(wkbString));
            log.info("Convert " + wkbString + " to " + geom);
            return geom;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
