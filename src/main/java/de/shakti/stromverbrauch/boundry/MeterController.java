package de.shakti.stromverbrauch.boundry;

import de.shakti.stromverbrauch.control.MeterService;
import de.shakti.stromverbrauch.entity.ElectricMeter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Metric;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping(MeterController.ROOT_MAPPING)
@RequiredArgsConstructor
@Validated
public class MeterController {
    public static final String ROOT_MAPPING = "/v1";
    private final MeterService meterService;

    @PostMapping(value = "/meter",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Metric> createMeterPosition(@Valid @RequestBody  final ElectricMeter electricMeter, final UriComponentsBuilder uriComponentsBuilder) throws Exception {
        final var meterEntry = meterService.createMeterEntry(electricMeter);
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path(ROOT_MAPPING + "/{id}").buildAndExpand(meterEntry.getId()).toUri());
        return new ResponseEntity(meterEntry, headers, HttpStatus.CREATED);
    }

    @GetMapping("/meters/{id}")
    public ResponseEntity<Metric> getMeterReading(@PathVariable @NotEmpty @NotBlank @Size(min=5)  final String id) {
        final var meterEntry = meterService.getMeterReading(id);
        return new ResponseEntity(meterEntry, HttpStatus.FOUND);
    }

    @DeleteMapping("/meters/{id}")
    public ResponseEntity<Metric> removeMeterReading(@PathVariable @NotEmpty @NotBlank @NotNull  final String id) {
        meterService.removeMeterReading(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/meter")
    public ResponseEntity<Metric> updateMeterPosition(@RequestBody final ElectricMeter electricMeter, final UriComponentsBuilder uriComponentsBuilder) {
        final var meterEntry = meterService.updateMeter(electricMeter);
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path(ROOT_MAPPING + "/{id}").buildAndExpand(meterEntry.getId()).toUri());

        return new ResponseEntity(meterEntry, headers, HttpStatus.OK);
    }

}
