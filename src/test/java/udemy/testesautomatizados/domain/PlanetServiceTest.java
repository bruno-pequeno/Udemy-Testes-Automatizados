package udemy.testesautomatizados.domain;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static udemy.testesautomatizados.common.PlanetConstants.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {

    @Mock
    // @MockBean
    PlanetRepository planetRepository;

    @InjectMocks
    // @Autowired
    private PlanetService planetService;

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet(){
        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        Planet sut = planetService.create(PLANET);

        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException(){
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet(){
        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(GET_PLANET));

        Optional<Planet> sut = planetService.getById(1L);

        assertThat(sut).isNotEmpty();
        assertThat(sut).isEqualTo(Optional.of(GET_PLANET));
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnEmpty(){
        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(new Planet()));

        Optional<Planet> sut = planetService.getById(5L);

        assertThat(sut).isEqualTo(Optional.of(new Planet()));
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet(){
        when(planetRepository.findByName(GET_PLANET.getName())).thenReturn(Optional.of(GET_PLANET));

        Optional<Planet> sut = planetService.getByName(GET_PLANET.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut).isEqualTo(Optional.of(GET_PLANET));
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnEmpty(){
        when(planetRepository.findByName("Lua")).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.getByName("Lua");

        assertThat(sut).isEmpty();
    }
}
