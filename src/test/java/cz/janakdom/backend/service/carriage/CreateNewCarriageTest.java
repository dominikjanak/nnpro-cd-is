package cz.janakdom.backend.service.carriage;

import cz.janakdom.backend.dao.CarriageDao;
import cz.janakdom.backend.model.database.Carriage;
import cz.janakdom.backend.model.dto.carriage.CarriageDto;
import cz.janakdom.backend.service.CarriageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class CreateNewCarriageTest {
    @Mock
    private CarriageDao carriageDao;

    @InjectMocks
    private CarriageService carriageService;

    @Test
    void saveExistsSerialNumberDeletedCarriage() {
        //arrange
        Carriage carriage = new Carriage();
        carriage.setId(1);
        carriage.setSerialNumber("S123");
        carriage.setIsDeleted(true);
        when(carriageDao.findBySerialNumber(carriage.getSerialNumber())).thenReturn(Optional.of(carriage));
        when(carriageDao.save(carriage)).thenReturn(carriage);
        CarriageDto carriageDto = new CarriageDto();
        carriageDto.setSerialNumber("S123");
        Carriage newCarriage;

        //act
        newCarriage = carriageService.save(carriageDto);

        //assert
        verify(carriageDao, times(1)).findBySerialNumber(anyString());
        assertThat(newCarriage.getIsDeleted()).isFalse();
    }

    @Test
    void saveNewCarriage() {
        //arrange
        CarriageDto carriageDto = new CarriageDto();
        carriageDto.setSerialNumber("S123");
        carriageDto.setColor("Black");
        carriageDto.setHomeStation("HomeStation");
        carriageDto.setProducer("Producer");

        Carriage carriage = new Carriage();
        Carriage returnCarriage = new Carriage();
        BeanUtils.copyProperties(carriageDto, carriage);
        BeanUtils.copyProperties(carriage, returnCarriage);
        returnCarriage.setId(1);

        when(carriageDao.findBySerialNumber(carriageDto.getSerialNumber())).thenReturn(Optional.empty());
        when(carriageDao.save(carriage)).thenReturn(returnCarriage);

        Carriage newCarriage;

        //act
        newCarriage = carriageService.save(carriageDto);

        //assert
        verify(carriageDao, times(1)).findBySerialNumber(anyString());
        verify(carriageDao, times(1)).save(any(Carriage.class));
        assertThat(newCarriage.getId()).isEqualTo(1);
        assertThat(newCarriage.getSerialNumber()).isEqualTo("S123");
        assertThat(newCarriage.getSecurityIncidents()).isEmpty();
        assertThat(newCarriage.getHomeStation()).isEqualTo("HomeStation");
        assertThat(newCarriage.getProducer()).isEqualTo("Producer");
        assertThat(newCarriage.getIsDeleted()).isFalse();
    }
}
