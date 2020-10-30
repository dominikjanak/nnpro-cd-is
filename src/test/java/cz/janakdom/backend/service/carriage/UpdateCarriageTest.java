package cz.janakdom.backend.service.carriage;

import cz.janakdom.backend.dao.CarriageDao;
import cz.janakdom.backend.model.database.Carriage;
import cz.janakdom.backend.model.dto.carriage.CarriageDto;
import cz.janakdom.backend.model.dto.carriage.CarriageUpdateDto;
import cz.janakdom.backend.service.CarriageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UpdateCarriageTest {
    @Mock
    private CarriageDao carriageDao;

    @InjectMocks
    private CarriageService carriageService;

    @Test
    void updateNonExistentCarriage() {
        //arrange
        CarriageUpdateDto carriageDto = new CarriageUpdateDto();
        carriageDto.setColor("Black");
        carriageDto.setHomeStation("HomeStation");
        carriageDto.setProducer("Producer");
        when(carriageDao.findById(1)).thenReturn(Optional.empty());

        //act
        Carriage carriage = carriageService.update(1, carriageDto);

        //assert
        verify(carriageDao, times(1)).findById(anyInt());
        verify(carriageDao, times(0)).save(any(Carriage.class));
        assertThat(carriage).isNull();
    }

    @Test
    void updateExistsCarriage() {
        //arrange
        CarriageUpdateDto carriageDto = new CarriageUpdateDto();
        carriageDto.setColor("Black");
        carriageDto.setHomeStation("HomeStation1");
        carriageDto.setProducer("Producer1");
        Carriage carriage = new Carriage();
        carriage.setId(1);
        carriage.setColor("Red");
        carriage.setHomeStation("HomeStation");
        carriage.setProducer("Producer");
        carriage.setSerialNumber("S123");
        when(carriageDao.findById(1)).thenReturn(Optional.of(carriage));

        //act
        Carriage result = carriageService.update(1, carriageDto);

        //assert
        verify(carriageDao, times(1)).findById(anyInt());
        verify(carriageDao, times(1)).save(any(Carriage.class));
        assertThat(result).isNotNull();
        assertThat(result.getColor()).isEqualTo("Black");
        assertThat(result.getHomeStation()).isEqualTo("HomeStation1");
        assertThat(result.getProducer()).isEqualTo("Producer1");
        assertThat(result.getSerialNumber()).isEqualTo("S123");
        assertThat(result.getSecurityIncidents()).isEmpty();
    }
}
