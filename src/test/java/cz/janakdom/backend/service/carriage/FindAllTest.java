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
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindAllTest {
    @Mock
    private CarriageDao carriageDao;

    @InjectMocks
    private CarriageService carriageService;

    @Test
    void findCarriagesInEmptyDb() {
        /*
        //arrange
        when(carriageDao.findAllByIsDeletedFalse(any(Pageable.class))).thenReturn(new PageImpl<Carriage>(new ArrayList<>()));

        //act
        Page<Carriage> result = carriageService.findAll(PageRequest.of(1,1));

        //assert
        Assertions.assertThat(result.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(result.getTotalElements()).isEqualTo(0);
        verify(carriageDao, times(1)).findAllByIsDeletedFalse(any(Pageable.class));
        */
    }

    @Test
    void findCarriages() {
        /*
        //arrange
        List<Carriage> carriageList = new ArrayList<>();
        carriageList.add(new Carriage());
        carriageList.add(new Carriage());
        when(carriageDao.findAllByIsDeletedFalse(PageRequest.of(1,2))).thenReturn(new PageImpl<Carriage>(carriageList));

        //act
        Page<Carriage> result = carriageService.findAll(PageRequest.of(1,2));

        //assert
        Assertions.assertThat(result.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(result.getTotalElements()).isEqualTo(2);
        verify(carriageDao, times(1)).findAllByIsDeletedFalse(any(Pageable.class));
        */
    }
}
