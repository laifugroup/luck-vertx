package com.bbbang.luck.service

import com.bbbang.luck.domain.dto.PublicOpinionAnalysisDTO
import com.bbbang.luck.domain.vo.*
import jakarta.inject.Singleton
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.random.Random


@Singleton
class DataAnalysisService {

    fun findContentData(): Mono<List<ContentDataVO>> {

        val xs=ArrayList<String>()
        val ys=ArrayList<BigDecimal>()
        for (index in 0 until 24 step 1){
            val x="$index:00"
            xs.add(x)
            ys.add(BigDecimal.valueOf( (1000..3000).random()*1.00))
        }
        val v1=ContentDataVO("纯文本", xs,ys)
        val v2=ContentDataVO("图文类", xs,ys)
        val v3=ContentDataVO("视频类", xs,ys)
        return Mono.just(listOf(v1,v2,v3))
    }


    fun popularAuthor():  Mono<PopularAuthorWraperVO>{
        val list=ArrayList<PopularAuthorVO>()

        for (index in 0 until 7 step 1){
            val rank=index +1
        val v1=    PopularAuthorVO(rank, "wangwu$rank",BigDecimal.valueOf((1000..3000).random()*1.00),BigDecimal.valueOf( (1000..3000).random()*1.00))
            list.add(v1)
        }
        return Mono.just(PopularAuthorWraperVO(list))
    }



    fun publicOpinionAnalysis(publicOpinionAnalysisDTO: PublicOpinionAnalysisDTO): Mono<Any>{

       if (listOf("visitors","comment").contains( publicOpinionAnalysisDTO.quota) ){
           val list=ArrayList<ChartDataRecord>()
           for (index in 0 until 12 step 1){
               val v1= ChartDataRecord("${index+1}月",BigDecimal.valueOf((0..100).random()*1.00,),LocalDate.now().year)
               val v2= ChartDataRecord("${index+1}月",BigDecimal.valueOf((0..100).random()*1.00,),LocalDate.now().plusYears(-1).year)
               list.add(v1)
               list.add(v2)
           }
           val publicOpinionAnalysisVO=PublicOpinionAnalysisVO(BigDecimal.valueOf((1000..3000).random()*1.00),BigDecimal.valueOf((100..200).random()*1.00),list)
           return Mono.just(publicOpinionAnalysisVO)
       }else  if (listOf("published").contains( publicOpinionAnalysisDTO.quota) ){
           val list=ArrayList<ChartDataRecord>()
           for (index in 0 until 12 step 1){
               val v1= ChartDataRecord("${index+1}月",BigDecimal.valueOf((0..100).random()*1.00,),2021)
               list.add(v1)
           }
           val published=PublicOpinionAnalysisVO(BigDecimal.valueOf((1000..3000).random()*1.00),BigDecimal.valueOf((100..200).random()*1.00),list)
           return Mono.just(published)
       }
        val share1=ChartDataRecordShare("文本类", BigDecimal.valueOf(25.00), ItemStyle("#8D4EDA"))
        val share2=ChartDataRecordShare("图文类", BigDecimal.valueOf(35.00), ItemStyle("#165DFF"))
        val share3=ChartDataRecordShare("视频类", BigDecimal.valueOf(40.00), ItemStyle("#00B2FF"))
        val share=PublicOpinionAnalysisVO2(BigDecimal.valueOf((1000..3000).random()*1.00),BigDecimal.valueOf((100..200).random()*1.00), listOf(share1,share2,share3))
        return Mono.just(share)
    }


}