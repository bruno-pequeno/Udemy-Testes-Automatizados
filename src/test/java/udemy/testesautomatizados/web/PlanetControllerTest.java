package udemy.testesautomatizados.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static udemy.testesautomatizados.common.PlanetConstants.INVALID_PLANET;
import static udemy.testesautomatizados.common.PlanetConstants.PLANET;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import udemy.testesautomatizados.domain.Planet;
import udemy.testesautomatizados.domain.PlanetService;

import java.util.Optional;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlanetService planetService;

    @Test
    public void createPlanet_WithValidData_ReturnsCreated() throws Exception {
        when(planetService.create(PLANET)).thenReturn(PLANET);

        mockMvc.perform(post("/planets")
                .content(objectMapper.writeValueAsString(PLANET))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {
        Planet emptyPlanet = new Planet();

        mockMvc.perform(post("/planets")
                .content(objectMapper.writeValueAsString(INVALID_PLANET))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post("/planets")
                        .content(objectMapper.writeValueAsString(emptyPlanet))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createPlanet_WithExistingName_ReturnsConflict() throws Exception{
        when(planetService.create(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/planets")
                .content(objectMapper.writeValueAsString(PLANET))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() throws Exception{
       when(planetService.getById(1L)).thenReturn(Optional.of(PLANET));

       mockMvc.perform(get("/planets/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsPlanet() throws Exception{
        mockMvc.perform(get("/planets/2"))
                .andExpect(status().isNotFound());
    }
}
