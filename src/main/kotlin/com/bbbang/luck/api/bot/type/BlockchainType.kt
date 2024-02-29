package com.bbbang.luck.api.bot.type


/**
 * BNB RPC地址  https://docs.bnbchain.org/docs/beaconchain/develop/rpc
 *
 * bsc https://docs.bscscan.com/misc-tools-and-utilities/public-rpc-nodes
 */
enum class BlockchainType(val netName:String,val coin:String,channelId:Int,val url: String) {
    //https://bsc-dataseed.binance.org/
    BSC("BSC","BNB",56,"https://bsc-dataseed4.ninicoin.io/"),
    //ETHEREUM("ETH","https://ropsten.infura.io/v3/You Infura Project Id",55)
}