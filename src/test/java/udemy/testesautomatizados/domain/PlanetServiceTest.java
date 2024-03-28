package udemy.testesautomatizados.domain;

import static org.mockito.Mockito.when;
import static udemy.testesautomatizados.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
