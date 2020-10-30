package cz.janakdom.backend.service.carriage;

import cz.janakdom.backend.dao.CarriageDao;
import cz.janakdom.backend.model.database.Carriage;
import cz.janakdom.backend.service.CarriageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindBySerialNumberTest {
    @Mock
    private CarriageDao carriageDao;

    @InjectMocks
    private CarriageService carriageService;

    @Test
    void findNonExistentCarriage() {
        //arrange
        when(carriageDao.findBySerialNumber("S123")).thenReturn(Optional.empty());

        //act
        Carriage result = carriageService.findBySerialNumber("S123");

        //assert
        Assertions.assertThat(result).isNull();
        verify(carriageDao, times(1)).findBySerialNumber(anyString());
    }

    @Test
    void findExistsCarriage() {
        //arrange
        when(carriageDao.findBySerialNumber("S123")).thenReturn(Optional.of(new Carriage()));

        //act
        Carriage result = carriageService.findBySerialNumber("S123");

        //assert
        Assertions.assertThat(result).isNotNull();
        verify(carriageDao, times(1)).findBySerialNumber(anyString());
    }
}
