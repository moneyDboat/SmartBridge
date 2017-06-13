package com.captain.smartbridge.model

/**
 * Created by fish on 17-6-13.
 */
/**
 * gjsldm : G00010001_100012
 * gjsl : 20
 * gjlxmc : 侧墙
 * gjjgm : 桥面系
 * gjmc : 侧墙
 */

data class BuildRes(
    var gjsldm: String? = null,
    var gjsl: Int = 0,
    var gjlxmc: String? = null,
    var gjjgm: String? = null,
    var gjmc: String? = null
)
