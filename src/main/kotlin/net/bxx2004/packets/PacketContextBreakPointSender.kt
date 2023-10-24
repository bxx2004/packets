package net.bxx2004.packets

import net.bxx2004.packets.identifier.ContextIdentifier
import java.util.*

class PacketContextBreakPointSender(val packets:PacketContext) {
    val id = UUID.randomUUID().toString()
    var point = 0
    private var split:List<ByteArray> = splitMultiByteArray(packets.serializable())
    fun source():PacketContext{
        return packets
    }
    fun hasNext():Boolean{
        return point < split.size
    }
    fun next():ByteArray{
        return split[point]
        point++
    }
    fun sendTo(channel: Channel,sender: PacketSender){
        var p = PacketContext.empty(PacketType.BREAK_POINT_HEADER)
        p.write(ContextIdentifier(ContextType.NUMBER,"breakpoint_size"),split.size)
        p.write(ContextIdentifier(ContextType.STRING,"breakpoint_id"),id)
        sender.sendPacket(channel,p)
        split.forEach {
            var p = PacketContext.empty(PacketType.BREAK_POINT)
            p.write(ContextIdentifier(ContextType.STRING,"breakpoint_id"),id)
            p.write(ContextIdentifier(ContextType.BYTEARRAY,"breakpoint_data"),it)
            sender.sendPacket(channel,p)
        }
        var e = PacketContext.empty(PacketType.BREAK_POINT_FOOTER)
        p.write(ContextIdentifier(ContextType.STRING,"breakpoint_id"),id)
        sender.sendPacket(channel,e)
    }
}