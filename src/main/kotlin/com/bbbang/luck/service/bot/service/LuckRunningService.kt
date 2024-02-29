package com.bbbang.luck.service.bot.service

import com.bbbang.luck.configuration.LuckConfiguration
import com.bbbang.luck.domain.po.LuckGoodLuckPO
import com.bbbang.luck.domain.vo.LuckGoodLuckVO
import com.bbbang.parent.exception.BusinessException
import com.bbbang.parent.utils.BigDecimalUtils
import jakarta.inject.Singleton
import java.math.BigDecimal
import java.security.SecureRandom


@Singleton
class LuckRunningService(private val luckConfiguration: LuckConfiguration) {


    /**
     * 纯粹内存服务
     */
    fun divide( goodLuck: List<LuckGoodLuckPO>, amount: BigDecimal, numbers: Int,magic:String=luckConfiguration.magic): List<LuckGoodLuckPO> {
        if (numbers<2){
            throw BusinessException("[红包派发异常]红包个数必须大于2")
        }else if (numbers!=goodLuck.size){
            throw BusinessException("[红包派发异常]红包派发个数必须与人数相等")
        }else if (amount.multiply(BigDecimal.valueOf(100L)).compareTo(BigDecimal.valueOf(numbers.toLong()))==BigDecimalUtils.SMALL){
            throw BusinessException("[红包派发异常]红包派发最小总金额不能小于红包个数")
        }
        return  twiceTheMeanValueMethod(magic,goodLuck, amount,numbers)
    }

    private fun twiceTheMeanValueMethod(magic:String, luckUsers: List<LuckGoodLuckPO>, amount: BigDecimal, numbers: Int): List<LuckGoodLuckPO> {

        //剩余金额[分]
        var remainingAmount: Int = amount.multiply(BigDecimal.valueOf(100L)).toInt()
        //剩余红包个数
        var remainingRedPack: Int = numbers
        //随机数
        val magicTime=System.currentTimeMillis()
        val magicX="${magic}_${magicTime}"
        val random = SecureRandom(magicX.toByteArray())
        for (index in 0 until numbers step 1){
            //剩余1个红包
            if (remainingRedPack==1){
                luckUsers[index].numbers=1
                luckUsers[index].lastNumber=remainingAmount % 10
                luckUsers[index].credit= BigDecimal.valueOf(remainingAmount.toLong()).divide(BigDecimal.valueOf(100L))
                println("luckUsers:${ luckUsers[index].id}  ${ luckUsers[index].credit} ${ luckUsers[index].firstName}")
            }else{
                //随机范围：[1，剩余人均金额的2倍-1] 分
                val max=remainingAmount / remainingRedPack * 2 - 1
                val amount: Int = random.nextInt(max) + 1
                remainingAmount -= amount
                remainingRedPack--

                luckUsers[index].numbers=1
                luckUsers[index].lastNumber= amount % 10
                luckUsers[index].credit=BigDecimal.valueOf(amount.toLong()).divide(BigDecimal.valueOf(100.00))
                println("luckUsers:${ luckUsers[index].id}  ${ luckUsers[index].credit}  ${ luckUsers[index].firstName}")
            }
        }
        return luckUsers
    }



    //just for test
    private fun twiceTheMeanValueMethod1(magic:String, luckUsers: List<LuckGoodLuckPO>, amount: BigDecimal, numbers: Int): List<LuckGoodLuckPO> {

        luckUsers.forEachIndexed { index, luckGoodLuckPO ->
            if (index==0){
                luckGoodLuckPO.credit=BigDecimal.valueOf(2.45)
            }else  if (index==1){
                luckGoodLuckPO.credit=BigDecimal.valueOf(2.55)
            }
            luckGoodLuckPO.lastNumber=luckGoodLuckPO.credit?.multiply(BigDecimal.valueOf(100))?.toInt()!! % 10
        }
        return luckUsers
    }



}