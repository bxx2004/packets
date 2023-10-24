package net.bxx2004.packets

import net.bxx2004.packets.identifier.ContextIdentifier
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.collections.HashMap


interface PacketSender {
    fun sendPacket(channel: Channel,context: PacketContext,breakPoint:Boolean = false)
    fun uuid():UUID
    fun name():String
    open fun postWithReturn(channel: Channel,context: PacketContext,breakPoint:Boolean = false):CompletableFuture<PacketContext>{
        sendPacket(channel,context, breakPoint)
        var cf = CompletableFuture<PacketContext>()
        response[context.read<String>(ContextIdentifier(ContextType.STRING,"packet_id"))!!] = null
        Timer().schedule(object :TimerTask(){
            override fun run() {
                if (response[context.read<String>(ContextIdentifier(ContextType.STRING,"packet_id"))!!] != null){
                    cf.complete(response[context.read<String>(ContextIdentifier(ContextType.STRING,"packet_id"))!!])
                    response.remove(context.read<String>(ContextIdentifier(ContextType.STRING,"packet_id"))!!)
                    cancel()
                }
            }

        },0,20)
        return cf
    }
    companion object{
        private val response = HashMap<String,PacketContext?>()
        fun init(){
            registerPacketReceiver{channel:Channel, context:PacketContext, sender:PacketSender ->
                if (response.containsKey(context.read<String>(ContextIdentifier(ContextType.STRING,"packet_id")))){
                    response[context.read<String>(ContextIdentifier(ContextType.STRING,"packet_id"))!!]= context
                }
                return@registerPacketReceiver null
            }
        }
    }
}