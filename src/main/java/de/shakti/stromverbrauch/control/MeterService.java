package de.shakti.stromverbrauch.control;

import de.shakti.stromverbrauch.entity.ElectricMeter;
import de.shakti.stromverbrauch.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeterService {

    private final MeterDAOImpl meterDAO;

    /**
     * Persist a new Meter Object.
     * @param electricMeter Meter Object
     * @return the object after persisting
     */
    public ElectricMeter createMeterEntry(ElectricMeter electricMeter)  {

        return meterDAO.save(electricMeter);

    }

    /**
     * Retrieves meter object based on ID.
     * if ID is not found, then throws exception
     * @param meterId
     * @return
     */
    public ElectricMeter getMeterReading(String meterId) {

        return meterDAO.readMeter(meterId).orElseThrow(
                () -> new ResourceNotFoundException("Meter not found with meterId " + meterId));

    }

    /**
     * Deletes a meter object from persistent object
     * if id is found or else throws an exception.
     * @param meterId
     */
    public void removeMeterReading(String meterId) {
        final var meter = meterDAO.readMeter(meterId);
        meter.ifPresentOrElse(value ->
                    meterDAO.deleteMeter(value.getId()),
                () -> new ResourceNotFoundException("Meter not found with meterId " + meterId));
    }

    /**
     * overwrites meter content
     * @param electricMeter
     * @return
     */
    public ElectricMeter updateMeter(ElectricMeter electricMeter) {
        return meterDAO.save(electricMeter);

    }
}
