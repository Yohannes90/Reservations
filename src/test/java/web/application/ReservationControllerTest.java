package web.application;

import com.example.reservations.buisness.domain.RoomReservation;
import com.example.reservations.buisness.service.ReservationService;
import com.example.web.application.ReservationController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {
    
    @MockBean
    private ReservationService reservationService;
    @Autowired
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyy-MM-dd");

    @Test
    public void getReservations() throws Exception {
        Date date = DATE_FORMAT.parse("2017-01-01");
        List<RoomReservation> mockReservationList = new ArrayList<>();
        RoomReservation mockRoomReservation = new RoomReservation();
        mockRoomReservation.setLastName("Test");
        mockRoomReservation.setFirstName("JUnit");
        mockRoomReservation.setDate(date);
        mockRoomReservation.setGuestId(1);
        mockRoomReservation.setRoomNumber("J1");
        mockRoomReservation.setRoomId(100);
        mockRoomReservation.setRoomName("JUnit Room");
        mockRoomReservationList.add(mockReservation);

        given(reservationService.getRoomReservationForDate(date)).willReturn(mockReservationList);
        this.mockMvc.perform(get("/reservations?date=2017-01-01"))
                .addExpect(status().isOk()).andExpect(content())
    }
}
