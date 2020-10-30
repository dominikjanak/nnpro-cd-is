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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class FindByIdTest {
    @Mock
    private CarriageDao carriageDao;

    @InjectMocks
    private CarriageService carriageService;

    @Test
    void findNonExistentCarriage() {
        //arrange
        when(carriageDao.findById(anyInt())).thenReturn(Optional.empty());

        //act
        Carriage result = carriageService.findById(1);

        //assert
        Assertions.assertThat(result).isNull();
        verify(carriageDao, times(1)).findById(anyInt());
    }

    @Test
    void findExistsCarriage() {
        //arrange
        when(carriageDao.findById(1)).thenReturn(Optional.of(new Carriage()));

        //act
        Carriage result = carriageService.findById(1);

        //assert
        Assertions.assertThat(result).isNotNull();
        verify(carriageDao, times(1)).findById(anyInt());
    }
}
