package com.fuckcoolapk.module

import android.view.Gravity
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import com.fuckcoolapk.utils.CoolapkContext
import com.fuckcoolapk.utils.CoolapkSP
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.dp2px
import com.fuckcoolapk.utils.ktx.*
import com.fuckcoolapk.view.CheckBoxForHook
import com.fuckcoolapk.view.TextViewForHook
import de.robv.android.xposed.XposedHelpers
import org.w3c.dom.Text

class ModifyPictureWatermark {
    fun init() {
        val instance = getHookField(CoolapkContext.classLoader.loadClass("com.coolapk.market.view.settings.settingsynch.SettingSynchronized"), "INSTANCE")
        XposedHelpers.findClass("com.coolapk.market.view.feedv8.SubmitExtraViewPart", CoolapkContext.classLoader)
                .hookAfterMethod("initWith", "com.coolapk.market.view.feedv8.SubmitFeedV8Activity") {
                    val rootView = it.thisObject.callMethod("getView") as LinearLayout
                    val linearLayout = LinearLayout(CoolapkContext.activity)
                    val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(CoolapkContext.context, 48f))
                    lp.gravity = Gravity.CENTER_HORIZONTAL
                    linearLayout.layoutParams = lp
                    linearLayout.orientation = LinearLayout.VERTICAL
                    val checkBox = CheckBoxForHook(CoolapkContext.activity)
                    val lp2 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    lp2.gravity = Gravity.RIGHT
                    lp2.rightMargin = dp2px(CoolapkContext.context, 10f)
                    checkBox.layoutParams = lp2
                    checkBox.text = "临时关闭水印"
                    checkBox.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
                        val pictureWatermarkPosition = CoolapkSP.coolapkSP.getString("picture_watermark_position", "0")
                        if (b) {
                            if (pictureWatermarkPosition != "0") {
                                OwnSP.set("pictureWatermarkPosition", pictureWatermarkPosition!!)
                                instance.callMethod("uploadSetting", "picture_watermark_position", "0", 1)
                            }
                        } else {
                            if (pictureWatermarkPosition != "0") {
                                instance.callMethod("uploadSetting", "picture_watermark_position", OwnSP.ownSP.getString("pictureWatermarkPosition", "0"), 1)
                                OwnSP.remove("pictureWatermarkPosition")
                            }
                        }
                    }
                    linearLayout.addView(checkBox)
                    rootView.addView(linearLayout)
                }
        XposedHelpers.findClass("com.coolapk.market.view.feedv8.BaseFeedContentHolder\$startSubmitFeed$2", CoolapkContext.classLoader)
                .hookBeforeMethod("onNext", "com.coolapk.market.network.Result") {
                    val pictureWatermarkPosition = OwnSP.ownSP.getString("pictureWatermarkPosition", "-1")
                    if (pictureWatermarkPosition != "-1") {
                        instance.callMethod("uploadSetting", "picture_watermark_position", pictureWatermarkPosition, 1)
                        OwnSP.remove("pictureWatermarkPosition")
                    }
                }
        XposedHelpers.findClass("com.coolapk.market.view.feed.ReplyActivity",CoolapkContext.classLoader)
                .hookAfterMethod("initView"){
                    val viewBuilding = it.thisObject.getObjectField("binding")!!
                    val contentView = (viewBuilding.getObjectField("contentView") as LinearLayout).getChildAt(4) as LinearLayout
                    contentView.addView(CheckBox(CoolapkContext.activity).apply {
                        text="临时关闭水印"
                        setOnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
                            val pictureWatermarkPosition = CoolapkSP.coolapkSP.getString("picture_watermark_position", "0")
                            if (b) {
                                if (pictureWatermarkPosition != "0") {
                                    OwnSP.set("pictureWatermarkPosition", pictureWatermarkPosition!!)
                                    instance.callMethod("uploadSetting", "picture_watermark_position", "0", 1)
                                }
                            } else {
                                if (pictureWatermarkPosition != "0") {
                                    instance.callMethod("uploadSetting", "picture_watermark_position", OwnSP.ownSP.getString("pictureWatermarkPosition", "0"), 1)
                                    OwnSP.remove("pictureWatermarkPosition")
                                }
                            }
                        }
                    })
                }
        XposedHelpers.findClass("com.coolapk.market.view.feed.ReplyActivity\$doPost$1",CoolapkContext.classLoader)
                .hookBeforeMethod("onNext","com.coolapk.market.network.Result"){
                    val pictureWatermarkPosition = OwnSP.ownSP.getString("pictureWatermarkPosition", "-1")
                    if (pictureWatermarkPosition != "-1") {
                        instance.callMethod("uploadSetting", "picture_watermark_position", pictureWatermarkPosition, 1)
                        OwnSP.remove("pictureWatermarkPosition")
                    }
                }
    }
}