package com.captain.smartbridge.model

/**
 * Created by Captain on 17/7/1.
 */

//[
//        {
//        "gjdm": "100452",
//        "gjjgmc": "上部结构",
//        "jtgj": [
//        {
//        "ks": "第1垮",
//        "jtgjs": [
//        {
//        "gjsldm": "G00010005_100452_1",
//        "gjtjdm": "G00010005_100452_1_1",
//        "gjjtmc": "湿接缝_1_1"
//        },
//        {
//        "gjsldm": "G00010005_100452_1",
//        "gjtjdm": "G00010005_100452_1_2",
//        "gjjtmc": "湿接缝_1_2"
//        },
//        {
//        "gjsldm": "G00010005_100452_1",
//        "gjtjdm": "G00010005_100452_1_3",
//        "gjjtmc": "湿接缝_1_3"
//        }
//        ]
//        }

class BuildEntryRes {

    var gjdm: String? = null
    var gjjgmc: String? = null
    var jglx: Any? = null
    var ks_sum: Int = 0
    var gjmc: String? = null
    var jtgj: List<JtgjBean>? = null

    class JtgjBean {
        var ks: String? = null
        var jtgjs: List<JtgjsBean>? = null

        class JtgjsBean {
            var gjsldm: String? = null
            var gjtjdm: String? = null
            var gjjtmc: String? = null
        }
    }
}
