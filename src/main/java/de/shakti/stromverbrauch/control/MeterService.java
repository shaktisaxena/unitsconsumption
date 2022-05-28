package de.shakti.stromverbrauch.control;

import de.shakti.stromverbrauch.entity.Meter;
import de.shakti.stromverbrauch.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeterService {

    private final MeterDAOImpl meterDAO;

    /**
     * Persist a new Meter Object.
     * @param meter Meter Object
     * @return the object after persisting
     */
    public Meter createMeterEntry(Meter meter) {
        return meterDAO.save(meter);

    }

    /**
     * Retrieves meter object based on ID.
     * if ID is not found, then throws exception
     * @param meterId
     * @return
     */
    public Meter getMeterReading(String meterId) {

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
     * @param meter
     * @return
     */
    public Meter updateMeter(Meter meter) {
        return meterDAO.save(meter);

    }
}
