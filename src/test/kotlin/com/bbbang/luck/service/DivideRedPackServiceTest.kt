package com.bbbang.luck.service

import com.bbbang.luck.domain.po.LuckSendLuckPO
import com.bbbang.luck.domain.vo.LuckSendLuckVO
import com.bbbang.luck.event.DivideRedPackEvent
import com.bbbang.luck.service.bot.service.DivideRedPackService
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.hibernate.reactive.stage.Stage
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import java.math.BigDecimal
import org.hibernate.SessionFactory

/**        普通                   发包人             代理
 *
 *
 * 未中雷   发：-                 发：-                发：-
 *         抢: (余额+明细)        抢: (余额+明细)       抢: (余额+明细)
 *
 *  中雷    发：(加余额+明细)             发：-                发：-
 *         抢: (减余额+明细)
 *         代理：分红
 *         上级：分红
 *
 *
 */
@MicronautTest
class DivideRedPackServiceTest {


    @Inject
    lateinit var divideRedPackService: DivideRedPackService
    @Inject
    lateinit var sessionFactory: SessionFactory

    @Inject
    lateinit var luckSendLuckService: LuckSendLuckService
    /**
     * 测试普通
     */
    @Test
    fun testDivideRedPack() {
        //
        var sessionFactoryStage: Stage.SessionFactory = sessionFactory.unwrap(Stage.SessionFactory::class.java)
        sessionFactoryStage.withTransaction {session->
            session.createQuery<Long>("UPDATE LuckWalletPO t SET credit = :credit ")
                .setParameter("credit", 100)
                .executeUpdate()
        }
        val  divideRedPackEvent= DivideRedPackEvent().apply {
            this.sendRedPackVO=luckSendLuckService.findById(1).block()
            this.oddsCredit= BigDecimal.valueOf(1.8)
            this.callbackQuery= CallbackQuery()
        }
       val result= divideRedPackService.divideRedPack(divideRedPackEvent)
        Assertions.assertTrue(result==Unit)
    }

    /**
     * 测试发包人-也抢包
     */
    @Test
    fun testHostGrabDivideRedPack() {
        val  divideRedPackEvent= DivideRedPackEvent().apply {
            this.sendRedPackVO= LuckSendLuckVO()
            this.oddsCredit= BigDecimal.valueOf(180)
            this.callbackQuery= CallbackQuery()
        }
        val result= divideRedPackService.divideRedPack(divideRedPackEvent)
        Assertions.assertTrue(result==Unit)
    }



    /**
     * 测试代理-也抢包
     */
    @Test
    fun testAgentGrabDivideRedPack() {
        val  divideRedPackEvent= DivideRedPackEvent().apply {
            this.sendRedPackVO= LuckSendLuckVO()
            this.oddsCredit= BigDecimal.valueOf(1.8)
            this.callbackQuery= CallbackQuery()
        }
        val result= divideRedPackService.divideRedPack(divideRedPackEvent)
        Assertions.assertTrue(result==Unit)
    }

}