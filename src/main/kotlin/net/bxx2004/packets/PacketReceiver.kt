package net.bxx2004.packets

import net.bxx2004.packets.identifier.ContextIdentifier

interface PacketReceiver {
    fun onReceive(channel: Channel,context:PacketContext,sender:PacketSender)
    fun receiveWithReturn(channel: Channel,context:PacketContext,sender:PacketSender):PacketContext?
}
val eventBus:ArrayList<PacketReceiver> = ArrayList()
fun registerPacketReceiver(packetReceiver: PacketReceiver){
    eventBus.add(packetReceiver)
}
fun registerPacketReceiver(func:(channel: Channel,context:PacketContext,sender:PacketSender)->PacketContext?){
    eventBus.add(object : PacketReceiver{
        override fun onReceive(channel: Channel, context: PacketContext, sender: PacketSender) {
            func(channel,context, sender)
        }

        override fun receiveWithReturn(channel: Channel, context: PacketContext, sender: PacketSender): PacketContext? {
            return func(channel, context, sender)
        }
    })
}
private val cache = HashMap<String,PacketContextBreakPointReceiver>()
fun callPacketReceiver(channel: Channel,context:PacketContext,sender:PacketSender){
    if (context.type() == PacketType.BREAK_POINT || context.type() == PacketType.BREAK_POINT_HEADER){
        val id = context.read<String>(ContextIdentifier(ContextType.STRING,"breakpoint_id"))!!
        cache.putIfAbsent(id,PacketContextBreakPointReceiver(context))
        if (context.type() == PacketType.BREAK_POINT){
            cache[id]!!.write(context.read<ByteArray>(ContextIdentifier.of(ContextType.BYTEARRAY,"breakpoint_data"))!!)
        }
    }else{
        eventBus.forEach {
            it.onReceive(channel, (context as PacketContextImpl).takeFuture(), sender)
            val a = it.receiveWithReturn(channel, context.takeFuture(), sender)
            if (a!=null){
                sender.sendPacket(channel,a)
            }
        }
    }
}