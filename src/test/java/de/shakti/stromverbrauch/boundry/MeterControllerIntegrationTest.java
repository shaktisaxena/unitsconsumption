package de.shakti.stromverbrauch.boundry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.shakti.stromverbrauch.control.MeterService;
import de.shakti.stromverbrauch.entity.Meter;
import de.shakti.stromverbrauch.entity.Position;
import de.shakti.stromverbrauch.entity.READINGTYPE;
import de.shakti.stromverbrauch.entity.Reading;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeterController.class)
class MeterControllerIntegrationTest {

    @MockBean
    private MeterService meterService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createMeterPosition() throws Exception {

        when(meterService.createMeterEntry(any())).thenReturn(getMeter());
        mockMvc.perform(post("/v1/meter")
                        .content(getMeterAsJsonString())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").value("1L"))
                        .andExpect(jsonPath("$.position").value(Position.ELECTRICITY.toString()));
    }

    @Test
    void createMeterPositionBadRequest() throws Exception {
        when(meterService.createMeterEntry(any())).thenReturn(null);

        mockMvc.perform(post("/v1/meter")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMeterReading() {
    }

    @Test
    void removeMeterReading() {
    }

    @Test
    void updateMeterPosition() {
    }

    private Meter getMeter() {
        Meter meter = Meter.builder()
                .id("1L")
                .position(Position.ELECTRICITY)
                .reading(Reading.builder().readingtype(READINGTYPE.HT).units(100)
                        .build()).build();
        return meter;
    }

    private String getMeterAsJsonString() throws JsonProcessingException {
        Meter meter = Meter.builder()
                .position(Position.ELECTRICITY)
                .reading(Reading.builder().readingtype(READINGTYPE.HT).units(100)
                        .build()).build();

        ObjectMapper mapper = new ObjectMapper();
        String meterJsonString= mapper.writeValueAsString(meter);
        return meterJsonString;
    }
}