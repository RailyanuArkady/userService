package org.user_service.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;

public class MaskPassportData extends CompositeConverter<ILoggingEvent> {


    public String transform(ILoggingEvent event, String in) {

        in = in.replaceAll("(?<=passportDivisionName=')[^']+?(?=')|(?<=\"firstName\":\")[^\"]+?(?=\")", "****");
        in = in.replaceAll("(?<=passportSeries=)\\d+(?=(,|\\s|}))|(?<=\"passportSeries\":)\\d+(?=(,|\\s|}))", "****");
        in = in.replaceAll("(?<=passportDivisionCode=)\\d+(?=(,|\\s|}))|(?<=\"passportDivisionCode\":)\\d+(?=(,|\\s|}))", "****");
        in = in.replaceAll("(?<=passportNumber=)\\d+(?=(,|\\s|}))|(?<=\"passportNumber\":)\\d+(?=(,|\\s|}))", "****");
        return in;
    }
}