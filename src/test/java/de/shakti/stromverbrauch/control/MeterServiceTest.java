package de.shakti.stromverbrauch.control;

import de.shakti.stromverbrauch.entity.Meter;
import de.shakti.stromverbrauch.entity.Position;
import de.shakti.stromverbrauch.entity.READINGTYPE;
import de.shakti.stromverbrauch.entity.Reading;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeterServiceTest {

    @Mock
    MeterDAOImpl meterDAO;


    @Test
    void createMeterEntry() {
        //Arrange
        ArgumentCaptor<Meter> argumentCaptor= ArgumentCaptor.forClass(Meter.class);
        Meter meter = getMeter();
        when(meterDAO.save(meter)).thenReturn(meter);
        MeterService meterService= new MeterService(meterDAO);


        //Action
        Meter _expected= meterService.createMeterEntry(meter);

        //Assert
        Assertions.assertEquals(_expected,meter);
        verify(meterDAO,atMost(1)).save(meter);
        verify(meterDAO).save(argumentCaptor.capture());
        Assertions.assertEquals(meter,argumentCaptor.getValue());

    }

    @Test
    void getMeterReading() {
        //Arrange
        ArgumentCaptor<String> argumentCaptor= ArgumentCaptor.forClass(String.class);
        Meter meter = getMeter();
        when(meterDAO.readMeter(meter.getId())).thenReturn(Optional.of(meter));
        MeterService meterService= new MeterService(meterDAO);


        //Action
        Meter _expected= meterService.getMeterReading(meter.getId());

        //Assert
        Assertions.assertEquals(_expected,meter);
        verify(meterDAO,atMost(1)).readMeter(meter.getId());
        verify(meterDAO).readMeter(argumentCaptor.capture());
        assertEquals(meter.getId(),argumentCaptor.getValue());

    }

    @Test
    void removeMeterReading() {

        //Arrange
        ArgumentCaptor<String> argumentCaptor= ArgumentCaptor.forClass(String.class);
        Meter meter = getMeter();
        when(meterDAO.readMeter(meter.getId())).thenReturn(Optional.of(meter));
        doNothing().when(meterDAO).deleteMeter(meter.getId());
        MeterService meterService= new MeterService(meterDAO);


        //Action
        meterService.removeMeterReading(meter.getId());

        //Assert
        verify(meterDAO,atMost(1)).deleteMeter(meter.getId());
        verify(meterDAO,atMost(1)).readMeter(meter.getId());
        verify(meterDAO).deleteMeter(argumentCaptor.capture());

        assertEquals(meter.getId(),argumentCaptor.getValue());

    }


    @Test
    void updateMeter() {
    }

    private Meter getMeter() {
        Meter meter = Meter.builder()
                            .id("200000")
                            .position(Position.ELECTRICITY)
                             .reading(Reading.builder().readingtype(READINGTYPE.HT).units(100)
                                     .build()).build();
        return meter;
    }
}