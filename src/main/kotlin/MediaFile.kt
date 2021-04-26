data class MediaFile(
    var createdAt: Long,
    var updatedAt: Long,
    var sku: String?,
    var type: String,
    var name: String = "",
    var id: Int = 0,
    var sort_index: Int = 0
) {
    override fun toString(): String {
        return "sku = $sku " +
                "updatedAt = $updatedAt " +
                "filename =$name " +
                "id = $id " +
                "sortindex = $sort_index "
    }
}