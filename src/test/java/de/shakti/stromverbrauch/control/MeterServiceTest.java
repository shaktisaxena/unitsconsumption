package de.shakti.stromverbrauch.control;

import de.shakti.stromverbrauch.entity.ElectricMeter;
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
        ArgumentCaptor<ElectricMeter> argumentCaptor= ArgumentCaptor.forClass(ElectricMeter.class);
        ElectricMeter electricMeter = getMeter();
        when(meterDAO.save(electricMeter)).thenReturn(electricMeter);
        MeterService meterService= new MeterService(meterDAO);


        //Action
        ElectricMeter _expected= meterService.createMeterEntry(electricMeter);

        //Assert
        Assertions.assertEquals(_expected, electricMeter);
        verify(meterDAO,atMost(1)).save(electricMeter);
        verify(meterDAO).save(argumentCaptor.capture());
        Assertions.assertEquals(electricMeter,argumentCaptor.getValue());

    }

    @Test
    void getMeterReading() {
        //Arrange
        ArgumentCaptor<String> argumentCaptor= ArgumentCaptor.forClass(String.class);
        ElectricMeter electricMeter = getMeter();
        when(meterDAO.readMeter(electricMeter.getId())).thenReturn(Optional.of(electricMeter));
        MeterService meterService= new MeterService(meterDAO);


        //Action
        ElectricMeter _expected= meterService.getMeterReading(electricMeter.getId());

        //Assert
        Assertions.assertEquals(_expected, electricMeter);
        verify(meterDAO,atMost(1)).readMeter(electricMeter.getId());
        verify(meterDAO).readMeter(argumentCaptor.capture());
        assertEquals(electricMeter.getId(),argumentCaptor.getValue());

    }

    @Test
    void removeMeterReading() {

        //Arrange
        ArgumentCaptor<String> argumentCaptor= ArgumentCaptor.forClass(String.class);
        ElectricMeter electricMeter = getMeter();
        when(meterDAO.readMeter(electricMeter.getId())).thenReturn(Optional.of(electricMeter));
        doNothing().when(meterDAO).deleteMeter(electricMeter.getId());
        MeterService meterService= new MeterService(meterDAO);


        //Action
        meterService.removeMeterReading(electricMeter.getId());

        //Assert
        verify(meterDAO,atMost(1)).deleteMeter(electricMeter.getId());
        verify(meterDAO,atMost(1)).readMeter(electricMeter.getId());
        verify(meterDAO).deleteMeter(argumentCaptor.capture());

        assertEquals(electricMeter.getId(),argumentCaptor.getValue());

    }


    @Test
    void updateMeter() {
    }

    private ElectricMeter getMeter() {
        ElectricMeter electricMeter = ElectricMeter.builder()
                            .id("200000")
                            .position(Position.ELECTRICITY)
                             .reading(Reading.builder().readingtype(READINGTYPE.HT).units(100)
                                     .build()).build();
        return electricMeter;
    }
}