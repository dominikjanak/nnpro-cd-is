package cz.janakdom.backend.controller;

import cz.janakdom.backend.Creator;
import cz.janakdom.backend.controller.rest.internal.CarriageController;
import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Carriage;
import cz.janakdom.backend.model.dto.carriage.CarriageDto;
import cz.janakdom.backend.model.dto.carriage.CarriageUpdateDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CarriageIntegrationTest {

    @Autowired
    private Creator creator;

    @Autowired
    private CarriageController carriageController;

    @Test
    public void find() {
        Carriage carriage = creator.saveEntity(new Carriage());

        ApiResponse<Carriage> response = carriageController.findCarriage(carriage.getId());
        ApiResponse<Carriage> expected = new ApiResponse<>(200, "SUCCESS", carriage);

        Assertions.assertThat(response).isEqualTo(expected);
    }

    @Test
    public void findNonExistingEmptyDb() {
        ApiResponse<Carriage> response = carriageController.findCarriage(1);
        ApiResponse<Carriage> expected = new ApiResponse<>(404, "NOT-FOUND", null);

        Assertions.assertThat(response).isEqualTo(expected);
    }

    @Test
    public void findNonExisting() {
        Carriage carriage = creator.saveEntity(new Carriage());

        ApiResponse<Carriage> response = carriageController.findCarriage(carriage.getId() + 1);
        ApiResponse<Carriage> expected = new ApiResponse<>(404, "NOT-FOUND", null);

        Assertions.assertThat(response).isEqualTo(expected);
    }

    @Test
    public void findListEmptyDb() {
        ApiResponse<Page<Carriage>> response = carriageController.listCarriages(null);
        ApiResponse<Page<Carriage>> expected = new ApiResponse<>(200, "SUCCESS", Page.empty());

        Assertions.assertThat(response).isEqualTo(expected);
    }

    @Test
    public void findList() {
        List<Carriage> carriages = Arrays.asList(
                creator.saveEntity(new Carriage(1, "A")),
                creator.saveEntity(new Carriage(2, "B")),
                creator.saveEntity(new Carriage(3, "C"))
        );

        ApiResponse<Page<Carriage>> response = carriageController.listCarriages(null);
        ApiResponse<Page<Carriage>> expected = new ApiResponse<>(200, "SUCCESS", new PageImpl<>(carriages));

        Assertions.assertThat(response).isEqualTo(expected);
    }

    @Test
    public void crud() {
        verifyCrud(1, null, Arrays.asList()); // Empty DB.

        Carriage carriage1 = carriageController.createCarriage(new CarriageDto("A", "B", "C", "D")).getResult();
        Carriage carriage2 = carriageController.createCarriage(new CarriageDto("B", "C", "D", "E")).getResult();

        verifyCrud(carriage1.getId(), carriage1, Arrays.asList(carriage1, carriage2)); // Two added items.

        carriage1 = carriageController.updateCarriage(carriage1.getId(), new CarriageUpdateDto("C", "D", "E")).getResult();

        verifyCrud(carriage2.getId(), carriage2, Arrays.asList(carriage1, carriage2)); // Updated item.

        carriageController.deleteCarriage(carriage1.getId());

        verifyCrud(carriage1.getId(), null, Arrays.asList(carriage2)); // Deleted item.
    }

    /**
     * Test findCarriage, listCarriages and findCarriageSerialNumber methods for given carriages.
     */
    private void verifyCrud(int findById, Carriage expected, List<Carriage> expectedAll) {
        ApiResponse<Carriage> response = carriageController.findCarriage(findById);
        ApiResponse<Page<Carriage>> responseAll = carriageController.listCarriages(null);

        int expectedCode = expected == null ? 404 : 200;
        String expectedStatus = expected == null ? "NOT-FOUND" : "SUCCESS";
        ApiResponse<Carriage> expectedResponse = new ApiResponse<>(expectedCode, expectedStatus, expected);
        ApiResponse<Page<Carriage>> expectedAllResponse = new ApiResponse<>(200, "SUCCESS", new PageImpl<>(expectedAll));

        Assertions.assertThat(response).isEqualTo(expectedResponse);
        Assertions.assertThat(responseAll).isEqualTo(expectedAllResponse);

        if (expected != null) {
            ApiResponse<Carriage> responseSerialNumber = carriageController.findCarriageSerialNumber(expected.getSerialNumber());
            ApiResponse<Carriage> expectedSerialNumber = new ApiResponse<>(200, "SUCCESS", expected);

            Assertions.assertThat(responseSerialNumber).isEqualTo(expectedSerialNumber);
        }
    }

}
