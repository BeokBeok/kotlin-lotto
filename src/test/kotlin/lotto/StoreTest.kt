package lotto

import lotto.model.Buyer
import lotto.model.Lotto
import lotto.model.LottoNumber
import lotto.model.Prize
import lotto.model.Store
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StoreTest {

    private lateinit var buyer: Buyer

    @BeforeEach
    fun setup() {
        buyer = Buyer(2_000).apply {
            markLotto(Lotto.of("1,2,3,4,5,6"))
            markLotto(Lotto.of("1,2,3,4,5,45"))
        }
    }

    @Test
    fun 꽝() {
        val winnerHistory = Store.drawLottoNumber(buyer.autoLotto, Lotto.of("7, 8, 9, 10, 11, 12"), LottoNumber(45))

        assertThat(winnerHistory.contains(Prize.NOTHING)).isTrue()
    }

    @Test
    fun `5천원 당첨`() {
        val winnerHistory = Store.drawLottoNumber(buyer.autoLotto, Lotto.of("1, 2, 3, 14, 15, 16"), LottoNumber(45))

        assertThat(winnerHistory.contains(Prize.FOURTH)).isTrue()
    }

    @Test
    fun `5만원 당첨`() {
        val winnerHistory = Store.drawLottoNumber(buyer.autoLotto, Lotto.of("1, 2, 3, 4, 15, 16"), LottoNumber(45))

        assertThat(winnerHistory.contains(Prize.THIRD)).isTrue()
    }

    @Test
    fun `150만원 당첨`() {
        val winnerHistory = Store.drawLottoNumber(buyer.autoLotto, Lotto.of("1, 2, 3, 4, 5, 16"), LottoNumber(45))

        assertThat(winnerHistory.contains(Prize.SECOND)).isTrue()
    }

    @Test
    fun `3천만원 당첨`() {
        val winnerHistory = Store.drawLottoNumber(buyer.autoLotto, Lotto.of("1, 2, 3, 4, 5, 16"), LottoNumber(6))

        assertThat(winnerHistory.contains(Prize.BETWEEN_FIRST_AND_SECOND)).isTrue()
    }

    @Test
    fun `20억 당첨`() {
        val winnerHistory = Store.drawLottoNumber(buyer.autoLotto, Lotto.of("1, 2, 3, 4, 5, 6"), LottoNumber(45))

        assertThat(winnerHistory.contains(Prize.FIRST)).isTrue()
    }

    @Test
    fun `수익률 조회`() {
        val winnerHistory = Store.drawLottoNumber(buyer.autoLotto, Lotto.of("1, 2, 3, 14, 15, 16"), LottoNumber(45))

        assertThat(Store.getRateOfReturn(14_000, winnerHistory)).isEqualTo(0.71)
    }

    @Test
    fun `150만원과 3천만원 둘다 당첨될 경우`() {
        val winnerHistory = Store.drawLottoNumber(buyer.autoLotto, Lotto.of("1, 2, 3, 4, 5, 16"), LottoNumber(45))

        assertThat(winnerHistory.contains(Prize.SECOND) && winnerHistory.contains(Prize.BETWEEN_FIRST_AND_SECOND)).isTrue()
    }
}
