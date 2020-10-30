package cz.janakdom.backend.Testing

import cz.janakdom.backend.Creator
import cz.janakdom.backend.model.database.User
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IntegrationTest {
    @Autowired
    private Creator creator

    @Autowired
    private BCryptPasswordEncoder encoder

    private final Logger log = LoggerFactory.getLogger(this.getClass())

    /*@Autowired
    private QuoteService quoteService

    @Test
    void fillQuoteTest() {
        Author a = creator.saveEntity(new Author(firstname: "q", surname: "d", country: "UK"))
        Category c1 = creator.saveEntity(new Category(name: "K11"))
        Category c2 = creator.saveEntity(new Category(name: "K22"))
        Category c3 = creator.saveEntity(new Category(name: "K33"))
        User u = creator.saveEntity(new User(username: "janakdom", password: encoder.encode("123456789"), email: "test@mail.cz",))

        String quote = "Život je nádherný"
        QuoteDto quoteDto = new QuoteDto()
        quoteDto.authorId = a.getId()
        quoteDto.quote = quote
        quoteDto.global = true
        quoteDto.categories.add(c1.getId())
        quoteDto.categories.add(c2.getId())
        quoteDto.categories.add(c3.getId())

        Quote q = quoteService.fillQuote(quoteDto, u.username, null)

        Assert.assertNotNull(q)
        Assert.assertEquals(a.firstname, q.author.firstname)
        Assert.assertEquals(a.surname, q.author.surname)
        Assert.assertEquals(quote, q.quote)
        Assert.assertTrue(q.global)
        Assert.assertEquals(0, q.scores.size())
        Assert.assertEquals("K11", q.categories.get(0).name)
        Assert.assertEquals("K22", q.categories.get(1).name)
        Assert.assertEquals("K33", q.categories.get(2).name)
    }

    @Test
    void outputQuoteTest() {
        this.PrepareDataOutputQuoteTest()

        Quote q = quoteService.findById(1, "janakdom")

        Assert.assertNotNull(q)
        OutputQuoteDto output = quoteService.convertQuote(q, "janakdom")
        Assert.assertEquals(output.getUserscore(), 1)
    }

    //Prepare data to previous test
    void PrepareDataOutputQuoteTest() {
        User[] users = new User[3]
        users[0] = new User(username: "janakdom", email: "janakdom@test.cz")
        users[1] = new User(username: "test", email: "test@test.cz")
        users[2] = new User(username: "user", email: "user@test.cz")

        Quote[] quotes = new Quote[2]
        quotes[0] = new Quote(quote: "q1")
        quotes[1] = new Quote(quote: "q2")

        Author[] authors = new Author[2]
        authors[0] = new Author(firstname: "q", surname: "d", country: "UK")
        authors[1] = new Author(firstname: "o", surname: "k", country: "US")

        RatingData[] ratingsData = new RatingData[6]
        QuoteRating[] ratings = new QuoteRating[6]
        ratingsData[0] = new RatingData(users[0], quotes[0], 1 as Byte)
        ratingsData[1] = new RatingData(users[0], quotes[1], 8 as Byte)
        ratingsData[2] = new RatingData(users[1], quotes[0], 6 as Byte)
        ratingsData[3] = new RatingData(users[1], quotes[1], 2 as Byte)
        ratingsData[4] = new RatingData(users[2], quotes[0], 3 as Byte)
        ratingsData[5] = new RatingData(users[2], quotes[1], 7 as Byte)

        for (int i = 0; i < users.length; i++) {
            users[i] = creator.saveEntity(users[i])
        }
        for (int i = 0; i < authors.length; i++) {
            authors[i] = creator.saveEntity(authors[i])
        }
        for (int i = 0; i < quotes.length; i++) {
            quotes[i].setAuthor(authors[i])
            quotes[i].setUser(users[i])
            quotes[i].setGlobal(true)
            quotes[i] = creator.saveEntity(quotes[i])
        }
        for (int i = 0; i < ratingsData.length; i++) {
            QuoteRating rating = new QuoteRating()
            QuoteScoreKey key = new QuoteScoreKey()
            key.setQuoteId(ratingsData[i].getQuote().getId())
            key.setUserId(ratingsData[i].getUser().getId())
            rating.setId(key)
            rating.setQuote(ratingsData[i].getQuote())
            rating.setUser(ratingsData[i].getUser())
            rating.setScore(ratingsData[i].getRating())
            ratings[i] = creator.saveEntity(rating)
        }
    }

    class RatingData {
        public User user
        public Quote quote
        public byte rating

        RatingData(User user, Quote quote, byte rating) {
            this.user = user
            this.quote = quote
            this.rating = rating
        }

        User getUser() { return user }

        Quote getQuote() { return quote }

        byte getRating() { return rating }
    }*/
}
