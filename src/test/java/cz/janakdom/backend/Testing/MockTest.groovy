package cz.janakdom.backend.Testing

import cz.janakdom.backend.model.database.User
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.annotation.DirtiesContext

import static org.mockito.Mockito.when

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MockTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass())

    /*@MockBean
    private QuoteDao quoteDao

    @Autowired
    private QuoteService quoteService

    @Autowired
    private BCryptPasswordEncoder encoder

    private Quote mockedQuote() {
        Author a = new Author(firstname: "Albert", surname: "Einstein", country: "USA", id: 1)
        User u1 = new User(username: "janakdom", firstname: "Dominik", surname: "Janák", password: encoder.encode("123456789"), email: "test@mail.cz", id: 1)
        User u2 = new User(username: "test", firstname: "test", surname: "test", password: encoder.encode("test"), email: "test@test.cz", id: 2)
        User u3 = new User(username: "user", firstname: "user", surname: "user", password: encoder.encode("user"), email: "user@user.cz", id: 3)
        Quote q = new Quote(id: 1, author: a, quote: "Existuje tisíce způsobů, jak zabít čas, ale žádný, jak ho vzkřísit.", global: true, user: u1)

        q.categories.add(new Category(id: 1, name: "K1"))
        q.categories.add(new Category(id: 5, name: "K2"))
        q.categories.add(new Category(id: 10, name: "K3"))

        QuoteScoreKey k1 = new QuoteScoreKey(quoteId: 1, userId: 1)
        QuoteScoreKey k2 = new QuoteScoreKey(quoteId: 1, userId: 2)
        QuoteScoreKey k3 = new QuoteScoreKey(quoteId: 1, userId: 3)
        q.scores.add(new QuoteRating(id: k1, user: u1, score: 1))
        q.scores.add(new QuoteRating(id: k2, user: u2, score: 7))
        q.scores.add(new QuoteRating(id: k3, user: u3, score: 5))

        return q
    }

    @Test
    void ConvertQuoteTest() {
        when(quoteDao.findById(1)).thenReturn(Optional.of(this.mockedQuote()))

        Quote f = quoteService.findById(1)
        OutputQuoteDto oq = quoteService.convertQuote(f, "janakdom")

        Assert.assertNotNull(oq)
        Assert.assertEquals((double) 13 / 3, oq.getScore(), 0.001)
        Assert.assertEquals(1, oq.getUserscore())
        Assert.assertEquals(3, oq.getCategories().size())
        Assert.assertTrue(oq.isUservoted())
    }*/
}
