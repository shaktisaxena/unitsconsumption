package de.shakti.stromverbrauch.control;

import de.shakti.stromverbrauch.entity.ElectricMeter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MeterDAOImpl {
    private final MeterRepository meterRepository;

    public ElectricMeter save(ElectricMeter electricMeter)
    {
        return meterRepository.save(electricMeter);
    }

    public Optional<ElectricMeter> readMeter(String id)
    {
        return meterRepository.findById(id);
    }

    public void deleteMeter(String id)
    {
         meterRepository.deleteById(id);
    }

}
