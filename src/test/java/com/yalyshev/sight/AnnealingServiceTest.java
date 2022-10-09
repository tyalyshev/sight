package com.yalyshev.sight;

import com.yalyshev.sight.entity.Sight;
import com.yalyshev.sight.repo.SightRepo;
import com.yalyshev.sight.service.AnnealingServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.mock;

@TestInstance(PER_CLASS)
@ActiveProfiles("test")
@Tag("UnitTest")
@DisplayName("AnnealingServiceImpl Unit Tests")
public class AnnealingServiceTest {

    @Mock
    private SightRepo sightRepo;

    private AnnealingServiceImpl annealingService;

    @BeforeEach
    public void init() {
        sightRepo = mock(SightRepo.class);
        annealingService = new AnnealingServiceImpl(sightRepo);
    }

    @Test
    @DisplayName("given annealingService")
    public void firstTest() {

        Sight sight1 = new Sight(1, 60.01, 60.02);
        Sight sight2 = new Sight(2, 70.01, 70.02);
        Sight sight3 = new Sight(3, 55.75, 37.62);
        Sight sight4 = new Sight(4, 53.72, 91.43);
        Sight sight5 = new Sight(5, 43.43, 39.92);
        Sight sight6 = new Sight(6, 47.11, 39.42);
        Sight sight7 = new Sight(7, 56.40, 38.71);
        Sight sight8 = new Sight(8, 54.51, 37.07);
        Sight sight9 = new Sight(9, 54.90, 52.32);
        Sight sight10 = new Sight(10, 64.73, 177.51);

        Mockito.when(sightRepo.getAll()).thenReturn(asList(sight1, sight2, sight3, sight4, sight5, sight6, sight7, sight8, sight9, sight10));

        assertNotNull(annealingService);

        assertEquals(sightRepo.getAll().size(), annealingService.annealingRun(1, 2).size());

        assertEquals(sightRepo.getAll().get(0).getId(), annealingService.annealingRun(1, 2).get(0));

        assertEquals(sightRepo.getAll().get(9).getId(), annealingService.annealingRun(1,10).get(9));

    }
}
