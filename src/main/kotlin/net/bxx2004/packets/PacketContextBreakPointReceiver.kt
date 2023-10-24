package net.bxx2004.packets

import net.bxx2004.packets.identifier.ContextIdentifier

class PacketContextBreakPointReceiver(header:PacketContext) {
    var result = ArrayList<ByteArray>()
    var point = 0
    val id = header.read<String>(ContextIdentifier.of(ContextType.STRING,"breakpoint_id"))!!
    val size = header.read<Int>(ContextIdentifier.of(ContextType.NUMBER,"breakpoint_size"))!!
    fun write(byteArray: ByteArray){
        if (point>= size){
            breakpointCache[id] = toPacket()
        }
        result.add(byteArray)
        point++
    }
    fun canWrite():Boolean{
        return point< size
    }
    fun toPacket():PacketContext{
        var r = combineMultiByteArray(result)
        return PacketContext.unSerializable(r)
    }
}
fun registerPacketContext(){

}
val breakpointCache = HashMap<String,PacketContext>()