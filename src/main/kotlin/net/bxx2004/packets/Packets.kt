package net.bxx2004.packets

fun splitMultiByteArray(byteArray: ByteArray):ArrayList<ByteArray>{
    var data = byteArray
    var point = 0
    var result = ArrayList<ByteArray>()
    while (true){
        try {
            var d = ByteArray(10000)
            for (i in 0..9999) {
                d[i] = data[i]
            }
            result.add(d)
            point += 10000
        }catch (e:Exception){
            break
        }
    }
    return result
}
fun combineMultiByteArray(data:ArrayList<ByteArray>):ByteArray{
    var r = ByteArray(data.map { it.size }.sum())
    data.forEach {
        r += it
    }
    return r
}
fun toPacketContext(data:ArrayList<ByteArray>): PacketContext {
    return PacketContext.unSerializable(combineMultiByteArray(data))
}