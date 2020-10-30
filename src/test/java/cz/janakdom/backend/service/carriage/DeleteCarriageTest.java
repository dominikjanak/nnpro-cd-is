package cz.janakdom.backend.service.carriage;

import cz.janakdom.backend.dao.CarriageDao;
import cz.janakdom.backend.model.database.Carriage;
import cz.janakdom.backend.model.database.SecurityIncident;
import cz.janakdom.backend.service.CarriageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import org.mockito.internal.util.reflection.FieldSetter;

@ExtendWith(MockitoExtension.class)
public class DeleteCarriageTest {
    @Mock
    private CarriageDao carriageDao;

    @InjectMocks
    private CarriageService carriageService;

    @Test
    void deleteNonExistentCarriage() {
        //arrange
        when(carriageDao.findById(1)).thenReturn(Optional.empty());

        //act
        boolean result = carriageService.delete(1);

        //assert
        Assertions.assertThat(result).isFalse();
        verify(carriageDao, times(1)).findById(anyInt());
        verify(carriageDao, times(0)).save(any(Carriage.class));
        verify(carriageDao, times(0)).delete(any(Carriage.class));
    }

    @Test
    void deleteExistsCarriageWithIncidents() throws NoSuchFieldException {
        //arrange
        Carriage carriage = new Carriage();
        List<SecurityIncident> securityIncidents = new ArrayList<>();
        securityIncidents.add(new SecurityIncident());
        FieldSetter.setField(carriage,carriage.getClass().getDeclaredField("securityIncidents"), securityIncidents);
        when(carriageDao.findById(1)).thenReturn(Optional.of(carriage));

        //act
        boolean result = carriageService.delete(1);

        //assert
        Assertions.assertThat(result).isTrue();
        verify(carriageDao, times(1)).findById(anyInt());
        verify(carriageDao, times(1)).save(any(Carriage.class));
        verify(carriageDao, times(0)).delete(any(Carriage.class));
    }

    @Test
    void deleteExistsCarriageWithoutIncidents() {
        //arrange
        Carriage carriage = new Carriage();

        when(carriageDao.findById(1)).thenReturn(Optional.of(carriage));

        //act
        boolean result = carriageService.delete(1);

        //assert
        Assertions.assertThat(result).isTrue();
        verify(carriageDao, times(1)).findById(anyInt());
        verify(carriageDao, times(0)).save(any(Carriage.class));
        verify(carriageDao, times(1)).delete(any(Carriage.class));
    }
}
