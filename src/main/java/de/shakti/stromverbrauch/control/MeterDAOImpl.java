package de.shakti.stromverbrauch.control;

import de.shakti.stromverbrauch.entity.Meter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MeterDAOImpl {
    private final MeterRepository meterRepository;

    public Meter save(Meter meter)
    {
        return meterRepository.save(meter);
    }

    public Optional<Meter> readMeter(String id)
    {
        return meterRepository.findById(id);
    }

    public void deleteMeter(String id)
    {
         meterRepository.deleteById(id);
    }

}
