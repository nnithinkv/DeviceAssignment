package com.assignment.device.data

import com.google.gson.annotations.SerializedName


data class DetailsIP(

    @SerializedName("ip") var ip: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("region") var region: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("loc") var loc: String? = null,
    @SerializedName("org") var org: String? = null,
    @SerializedName("postal") var postal: String? = null,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("readme") var readme: String? = null

)