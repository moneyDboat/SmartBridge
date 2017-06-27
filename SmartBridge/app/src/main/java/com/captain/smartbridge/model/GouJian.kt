package com.captain.smartbridge.model

/**
 * Created by Captain on 17/6/27.
 */

/**
 * gjsldm : G00010001_200041
 * gjsl : 4
 * gjlxmc : 翼墙、耳墙
 * gjjgm : 下部结构
 * gjmc : 翼墙
 */

data class GouJian(
        var gjsldm: String? = null,
        var gjsl: Int = 0,
        var gjlxmc: String? = null,
        var gjjgm: String? = null,
        var gjmc: String? = null
)
