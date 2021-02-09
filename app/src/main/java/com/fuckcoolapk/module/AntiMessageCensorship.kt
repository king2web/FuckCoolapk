package com.fuckcoolapk.module

import android.app.Activity
import android.widget.EditText
import com.fuckcoolapk.utils.CoolapkContext
import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import de.robv.android.xposed.XposedHelpers
import org.json.JSONObject

class AntiMessageCensorship {
    private val d = "{\"\u00a2\": \"\uffe0\", \"\u00a3\": \"\uffe1\", \"\u00a5\": \"\uffe5\", \"\u00a6\": \"\uffe4\", \"\u00ac\": \"\uffe2\", \"\u00b7\": \"\u0387\", \"\u00c5\": \"\u212b\", \"\u0127\": \"\u210f\", \"\u018e\": \"\u1d32\", \"\u0190\": \"\u2107\", \"\u01ab\": \"\u1db5\", \"\u0222\": \"\u1d3d\", \"\u025f\": \"\u1da1\", \"\u0263\": \"\u02e0\", \"\u0268\": \"\u1da4\", \"\u0269\": \"\u1da5\", \"\u026a\": \"\u1da6\", \"\u026b\": \"\uab5e\", \"\u026d\": \"\u1da9\", \"\u0283\": \"\u1db4\", \"\u028b\": \"\u1db9\", \"\u029f\": \"\u1dab\", \"\u02b9\": \"\u0374\", \"\u0300\": \"\u0340\", \"\u0301\": \"\u0341\", \"\u0313\": \"\u0343\", \"\u03a5\": \"\u03d2\", \"\u03ac\": \"\u1f71\", \"\u03ad\": \"\u1f73\", \"\u03b2\": \"\u03d0\", \"\u03b8\": \"\u03d1\", \"\u03bc\": \"\u00b5\", \"\u03c6\": \"\u03d5\", \"\u05d0\": \"\ufb21\", \"\u05d1\": \"\u2136\", \"\u05d2\": \"\u2137\", \"\u05d3\": \"\u2138\", \"\u05d4\": \"\ufb23\", \"\u05db\": \"\ufb24\", \"\u05dc\": \"\ufb25\", \"\u05dd\": \"\ufb26\", \"\u05e2\": \"\ufb20\", \"\u05e8\": \"\ufb27\", \"\u05ea\": \"\ufb28\", \"\u0621\": \"\ufe80\", \"\u0622\": \"\ufe81\", \"\u0623\": \"\ufe83\", \"\u0624\": \"\ufe85\", \"\u0625\": \"\ufe87\", \"\u0626\": \"\ufe89\", \"\u0627\": \"\ufe8d\", \"\u0628\": \"\ufe8f\", \"\u0629\": \"\ufe93\", \"\u062a\": \"\ufe95\", \"\u062b\": \"\ufe99\", \"\u062c\": \"\ufe9d\", \"\u062d\": \"\ufea1\", \"\u062e\": \"\ufea5\", \"\u062f\": \"\ufea9\", \"\u0630\": \"\ufeab\", \"\u0631\": \"\ufead\", \"\u0632\": \"\ufeaf\", \"\u0633\": \"\ufeb1\", \"\u0634\": \"\ufeb5\", \"\u0635\": \"\ufeb9\", \"\u0636\": \"\ufebd\", \"\u0637\": \"\ufec1\", \"\u0638\": \"\ufec5\", \"\u0639\": \"\ufec9\", \"\u063a\": \"\ufecd\", \"\u0641\": \"\ufed1\", \"\u0642\": \"\ufed5\", \"\u0643\": \"\ufed9\", \"\u0644\": \"\ufedd\", \"\u0645\": \"\ufee1\", \"\u0646\": \"\ufee5\", \"\u0647\": \"\ufee9\", \"\u0648\": \"\ufeed\", \"\u0649\": \"\ufbe8\", \"\u064a\": \"\ufef1\", \"\u0671\": \"\ufb50\", \"\u0679\": \"\ufb66\", \"\u067a\": \"\ufb5e\", \"\u067b\": \"\ufb52\", \"\u067e\": \"\ufb56\", \"\u067f\": \"\ufb62\", \"\u0680\": \"\ufb5a\", \"\u0683\": \"\ufb76\", \"\u0684\": \"\ufb72\", \"\u0686\": \"\ufb7a\", \"\u0687\": \"\ufb7e\", \"\u0688\": \"\ufb88\", \"\u068c\": \"\ufb84\", \"\u068d\": \"\ufb82\", \"\u068e\": \"\ufb86\", \"\u0691\": \"\ufb8c\", \"\u0698\": \"\ufb8a\", \"\u06a4\": \"\ufb6a\", \"\u06a6\": \"\ufb6e\", \"\u06a9\": \"\ufb8e\", \"\u06ad\": \"\ufbd3\", \"\u06af\": \"\ufb92\", \"\u06b1\": \"\ufb9a\", \"\u06b3\": \"\ufb96\", \"\u06ba\": \"\ufb9e\", \"\u06bb\": \"\ufba0\", \"\u06be\": \"\ufbaa\", \"\u06c0\": \"\ufba4\", \"\u06c1\": \"\ufba6\", \"\u06c5\": \"\ufbe0\", \"\u06c6\": \"\ufbd9\", \"\u06c7\": \"\ufbd7\", \"\u06c8\": \"\ufbdb\", \"\u06c9\": \"\ufbe2\", \"\u06cb\": \"\ufbde\", \"\u06cc\": \"\ufbfc\", \"\u06d0\": \"\ufbe4\", \"\u06d2\": \"\ufbae\", \"\u06d3\": \"\ufbb0\", \"\u0f0b\": \"\u0f0c\", \"\u10dc\": \"\u10fc\", \"\u1100\": \"\u3131\", \"\u1101\": \"\u3132\", \"\u1102\": \"\u3134\", \"\u1103\": \"\u3137\", \"\u1104\": \"\u3138\", \"\u1105\": \"\u3139\", \"\u1106\": \"\u3141\", \"\u1107\": \"\u3142\", \"\u1108\": \"\u3143\", \"\u1109\": \"\u3145\", \"\u110a\": \"\u3146\", \"\u110b\": \"\u3147\", \"\u110c\": \"\u3148\", \"\u110d\": \"\u3149\", \"\u110e\": \"\u314a\", \"\u110f\": \"\u314b\", \"\u1110\": \"\u314c\", \"\u1111\": \"\u314d\", \"\u1112\": \"\u314e\", \"\u1114\": \"\u3165\", \"\u1115\": \"\u3166\", \"\u111a\": \"\u3140\", \"\u111c\": \"\u316e\", \"\u111d\": \"\u3171\", \"\u111e\": \"\u3172\", \"\u1120\": \"\u3173\", \"\u1121\": \"\u3144\", \"\u1122\": \"\u3174\", \"\u1123\": \"\u3175\", \"\u1127\": \"\u3176\", \"\u1129\": \"\u3177\", \"\u112b\": \"\u3178\", \"\u112c\": \"\u3179\", \"\u112d\": \"\u317a\", \"\u112e\": \"\u317b\", \"\u112f\": \"\u317c\", \"\u1132\": \"\u317d\", \"\u1136\": \"\u317e\", \"\u1140\": \"\u317f\", \"\u1147\": \"\u3180\", \"\u114c\": \"\u3181\", \"\u1157\": \"\u3184\", \"\u1158\": \"\u3185\", \"\u1159\": \"\u3186\", \"\u1160\": \"\u3164\", \"\u1161\": \"\u314f\", \"\u1162\": \"\u3150\", \"\u1163\": \"\u3151\", \"\u1164\": \"\u3152\", \"\u1165\": \"\u3153\", \"\u1166\": \"\u3154\", \"\u1167\": \"\u3155\", \"\u1168\": \"\u3156\", \"\u1169\": \"\u3157\", \"\u116a\": \"\u3158\", \"\u116b\": \"\u3159\", \"\u116c\": \"\u315a\", \"\u116d\": \"\u315b\", \"\u116e\": \"\u315c\", \"\u116f\": \"\u315d\", \"\u1170\": \"\u315e\", \"\u1171\": \"\u315f\", \"\u1172\": \"\u3160\", \"\u1173\": \"\u3161\", \"\u1174\": \"\u3162\", \"\u1175\": \"\u3163\", \"\u1184\": \"\u3187\", \"\u1185\": \"\u3188\", \"\u1188\": \"\u3189\", \"\u1191\": \"\u318a\", \"\u1192\": \"\u318b\", \"\u1194\": \"\u318c\", \"\u119e\": \"\u318d\", \"\u11a1\": \"\u318e\", \"\u11aa\": \"\u3133\", \"\u11ac\": \"\u3135\", \"\u11ad\": \"\u3136\", \"\u11b0\": \"\u313a\", \"\u11b1\": \"\u313b\", \"\u11b2\": \"\u313c\", \"\u11b3\": \"\u313d\", \"\u11b4\": \"\u313e\", \"\u11b5\": \"\u313f\", \"\u11c7\": \"\u3167\", \"\u11c8\": \"\u3168\", \"\u11cc\": \"\u3169\", \"\u11ce\": \"\u316a\", \"\u11d3\": \"\u316b\", \"\u11d7\": \"\u316c\", \"\u11d9\": \"\u316d\", \"\u11dd\": \"\u316f\", \"\u11df\": \"\u3170\", \"\u11f1\": \"\u3182\", \"\u11f2\": \"\u3183\", \"\u1d02\": \"\u1d46\", \"\u1d16\": \"\u1d54\", \"\u1d17\": \"\u1d55\", \"\u1d1c\": \"\u1db8\", \"\u1d1d\": \"\u1d59\", \"\u1d25\": \"\u1d5c\", \"\u1d7b\": \"\u1da7\", \"\u1d85\": \"\u1daa\", \"\u1e61\": \"\u1e9b\", \"\u2010\": \"\u2011\", \"\u2013\": \"\ufe32\", \"\u2014\": \"\ufe58\", \"\u20a9\": \"\uffe6\", \"\u2190\": \"\uffe9\", \"\u2191\": \"\uffea\", \"\u2192\": \"\uffeb\", \"\u2193\": \"\uffec\", \"\u2502\": \"\uffe8\", \"\u25cb\": \"\uffee\", \"\u2d61\": \"\u2d6f\", \"\u3001\": \"\uff64\", \"\u3002\": \"\uff61\", \"\u3008\": \"\u2329\", \"\u3009\": \"\u232a\", \"\u300a\": \"\ufe3d\", \"\u300b\": \"\ufe3e\", \"\u300c\": \"\ufe41\", \"\u300d\": \"\uff63\", \"\u300e\": \"\ufe43\", \"\u300f\": \"\ufe44\", \"\u3012\": \"\u3036\", \"\u3014\": \"\ufe5d\", \"\u3015\": \"\ufe5e\", \"\u3016\": \"\ufe17\", \"\u3017\": \"\ufe18\", \"\u3099\": \"\uff9e\", \"\u309a\": \"\uff9f\", \"\u30a1\": \"\uff67\", \"\u30a2\": \"\uff71\", \"\u30a3\": \"\uff68\", \"\u30a4\": \"\u32d1\", \"\u30a5\": \"\uff69\", \"\u30a6\": \"\uff73\", \"\u30a7\": \"\uff6a\", \"\u30a8\": \"\uff74\", \"\u30a9\": \"\uff6b\", \"\u30aa\": \"\uff75\", \"\u30ab\": \"\u32d5\", \"\u30ad\": \"\u32d6\", \"\u30af\": \"\uff78\", \"\u30b1\": \"\uff79\", \"\u30b3\": \"\uff7a\", \"\u30b5\": \"\uff7b\", \"\u30b7\": \"\uff7c\", \"\u30b9\": \"\uff7d\", \"\u30bb\": \"\uff7e\", \"\u30bd\": \"\uff7f\", \"\u30bf\": \"\uff80\", \"\u30c1\": \"\uff81\", \"\u30c3\": \"\uff6f\", \"\u30c4\": \"\uff82\", \"\u30c6\": \"\uff83\", \"\u30c8\": \"\uff84\", \"\u30ca\": \"\uff85\", \"\u30cb\": \"\uff86\", \"\u30cc\": \"\uff87\", \"\u30cd\": \"\u32e7\", \"\u30ce\": \"\uff89\", \"\u30cf\": \"\uff8a\", \"\u30d2\": \"\uff8b\", \"\u30d5\": \"\uff8c\", \"\u30d8\": \"\u32ec\", \"\u30db\": \"\uff8e\", \"\u30de\": \"\uff8f\", \"\u30df\": \"\uff90\", \"\u30e0\": \"\uff91\", \"\u30e1\": \"\u32f1\", \"\u30e2\": \"\uff93\", \"\u30e3\": \"\uff6c\", \"\u30e4\": \"\u32f3\", \"\u30e5\": \"\uff6d\", \"\u30e6\": \"\uff95\", \"\u30e7\": \"\uff6e\", \"\u30e8\": \"\uff96\", \"\u30e9\": \"\uff97\", \"\u30ea\": \"\uff98\", \"\u30eb\": \"\uff99\", \"\u30ec\": \"\uff9a\", \"\u30ed\": \"\uff9b\", \"\u30ef\": \"\uff9c\", \"\u30f0\": \"\u32fc\", \"\u30f1\": \"\u32fd\", \"\u30f2\": \"\uff66\", \"\u30f3\": \"\uff9d\", \"\u30fb\": \"\uff65\", \"\u30fc\": \"\uff70\", \"\u3b9d\": \"\ufad2\", \"\u4018\": \"\ufad3\", \"\u4e00\": \"\u2f00\", \"\u4e01\": \"\u319c\", \"\u4e03\": \"\u3286\", \"\u4e09\": \"\u3194\", \"\u4e0a\": \"\u3196\", \"\u4e0b\": \"\u3198\", \"\u4e0d\": \"\uf967\", \"\u4e19\": \"\u319b\", \"\u4e28\": \"\u2f01\", \"\u4e2d\": \"\u3197\", \"\u4e32\": \"\uf905\", \"\u4e36\": \"\u2f02\", \"\u4e39\": \"\uf95e\", \"\u4e3f\": \"\u2f03\", \"\u4e59\": \"\u2f04\", \"\u4e5d\": \"\u3288\", \"\u4e82\": \"\uf91b\", \"\u4e85\": \"\u2f05\", \"\u4e86\": \"\uf9ba\", \"\u4e8c\": \"\u2f06\", \"\u4ea0\": \"\u2f07\", \"\u4eae\": \"\uf977\", \"\u4eba\": \"\u2f08\", \"\u4ec0\": \"\uf9fd\", \"\u4ee4\": \"\uf9a8\", \"\u4f80\": \"\ufa73\", \"\u4f86\": \"\uf92d\", \"\u4f8b\": \"\uf9b5\", \"\u4fae\": \"\ufa30\", \"\u4fbf\": \"\uf965\", \"\u502b\": \"\uf9d4\", \"\u50da\": \"\uf9bb\", \"\u50e7\": \"\ufa31\", \"\u513f\": \"\u2f09\", \"\u5140\": \"\ufa0c\", \"\u514d\": \"\ufa32\", \"\u5165\": \"\u2f0a\", \"\u5169\": \"\uf978\", \"\u516b\": \"\u2f0b\", \"\u516d\": \"\uf9d1\", \"\u5182\": \"\u2f0c\", \"\u5196\": \"\u2f0d\", \"\u51ab\": \"\u2f0e\", \"\u51b7\": \"\uf92e\", \"\u51c9\": \"\uf979\", \"\u51cc\": \"\uf955\", \"\u51dc\": \"\uf954\", \"\u51de\": \"\ufa15\", \"\u51e0\": \"\u2f0f\", \"\u51f5\": \"\u2f10\", \"\u5200\": \"\u2f11\", \"\u5207\": \"\ufa00\", \"\u5217\": \"\uf99c\", \"\u5229\": \"\uf9dd\", \"\u523a\": \"\uf9ff\", \"\u5289\": \"\uf9c7\", \"\u529b\": \"\u2f12\", \"\u52a3\": \"\uf99d\", \"\u52c9\": \"\ufa33\", \"\u52d2\": \"\uf952\", \"\u52de\": \"\uf92f\", \"\u52e4\": \"\ufa34\", \"\u52f5\": \"\uf97f\", \"\u52f9\": \"\u2f13\", \"\u5315\": \"\u2f14\", \"\u5317\": \"\uf963\", \"\u531a\": \"\u2f15\", \"\u5338\": \"\u2f16\", \"\u533f\": \"\uf9eb\", \"\u5341\": \"\u2f17\", \"\u5344\": \"\u3039\", \"\u5351\": \"\ufa35\", \"\u535c\": \"\u2f18\", \"\u5369\": \"\u2f19\", \"\u5375\": \"\uf91c\", \"\u5382\": \"\u2f1a\", \"\u53b6\": \"\u2f1b\", \"\u53c3\": \"\uf96b\", \"\u53c8\": \"\u2f1c\", \"\u53e3\": \"\u2f1d\", \"\u53e5\": \"\uf906\", \"\u540f\": \"\uf9de\", \"\u541d\": \"\uf9ed\", \"\u5442\": \"\uf980\", \"\u54bd\": \"\uf99e\", \"\u5555\": \"\ufa79\", \"\u5587\": \"\uf90b\", \"\u559d\": \"\ufa36\", \"\u55c0\": \"\ufa0d\", \"\u5606\": \"\ufa37\", \"\u5668\": \"\ufa38\", \"\u56d7\": \"\u2f1e\", \"\u56db\": \"\u3195\", \"\u56f9\": \"\uf9a9\", \"\u571f\": \"\u2f1f\", \"\u5730\": \"\u319e\", \"\u5840\": \"\ufa39\", \"\u585a\": \"\ufa10\", \"\u585e\": \"\uf96c\", \"\u58a8\": \"\ufa3a\", \"\u58d8\": \"\uf94a\", \"\u58df\": \"\uf942\", \"\u58eb\": \"\u2f20\", \"\u5902\": \"\u2f21\", \"\u590a\": \"\u2f22\", \"\u5915\": \"\u2f23\", \"\u5927\": \"\u2f24\", \"\u5929\": \"\u319d\", \"\u5948\": \"\uf90c\", \"\u5951\": \"\uf909\", \"\u5973\": \"\u2f25\", \"\u5b28\": \"\ufa81\", \"\u5b50\": \"\u2f26\", \"\u5b80\": \"\u2f27\", \"\u5b85\": \"\ufa04\", \"\u5b97\": \"\u32aa\", \"\u5be7\": \"\uf9aa\", \"\u5bee\": \"\uf9bc\", \"\u5bf8\": \"\u2f28\", \"\u5c0f\": \"\u2f29\", \"\u5c22\": \"\u2f2a\", \"\u5c38\": \"\u2f2b\", \"\u5c3f\": \"\uf9bd\", \"\u5c62\": \"\uf94b\", \"\u5c64\": \"\ufa3b\", \"\u5c65\": \"\uf9df\", \"\u5c6e\": \"\u2f2c\", \"\u5c71\": \"\u2f2d\", \"\u5d19\": \"\uf9d5\", \"\u5d50\": \"\uf921\", \"\u5dba\": \"\uf9ab\", \"\u5ddb\": \"\u2f2e\", \"\u5de5\": \"\u2f2f\", \"\u5de6\": \"\u32a7\", \"\u5df1\": \"\u2f30\", \"\u5dfe\": \"\u2f31\", \"\u5e72\": \"\u2f32\", \"\u5e74\": \"\uf98e\", \"\u5e7a\": \"\u2f33\", \"\u5e7f\": \"\u2f34\", \"\u5ea6\": \"\ufa01\", \"\u5ec9\": \"\uf9a2\", \"\u5eca\": \"\uf928\", \"\u5ed3\": \"\ufa0b\", \"\u5eec\": \"\uf982\", \"\u5ef4\": \"\u2f35\", \"\u5efe\": \"\u2f36\", \"\u5f04\": \"\uf943\", \"\u5f0b\": \"\u2f37\", \"\u5f13\": \"\u2f38\", \"\u5f50\": \"\u2f39\", \"\u5f61\": \"\u2f3a\", \"\u5f73\": \"\u2f3b\", \"\u5f8b\": \"\uf9d8\", \"\u5fa9\": \"\uf966\", \"\u5fc3\": \"\u2f3c\", \"\u5ff5\": \"\uf9a3\", \"\u6012\": \"\uf960\", \"\u601c\": \"\uf9ac\", \"\u6075\": \"\ufa6b\", \"\u6094\": \"\ufa3d\", \"\u60e1\": \"\uf9b9\", \"\u6144\": \"\uf9d9\", \"\u6168\": \"\ufa3e\", \"\u618e\": \"\ufa3f\", \"\u6190\": \"\uf98f\", \"\u61f2\": \"\ufa40\", \"\u61f6\": \"\uf90d\", \"\u6200\": \"\uf990\", \"\u6208\": \"\u2f3d\", \"\u622e\": \"\uf9d2\", \"\u6236\": \"\u2f3e\", \"\u624b\": \"\u2f3f\", \"\u62c9\": \"\uf925\", \"\u62cf\": \"\uf95b\", \"\u62d3\": \"\ufa02\", \"\u62fe\": \"\uf973\", \"\u637b\": \"\uf9a4\", \"\u63a0\": \"\uf975\", \"\u649a\": \"\uf991\", \"\u64c4\": \"\uf930\", \"\u652f\": \"\u2f40\", \"\u6534\": \"\u2f41\", \"\u654f\": \"\ufa41\", \"\u6578\": \"\uf969\", \"\u6587\": \"\u2f42\", \"\u6597\": \"\u2f43\", \"\u6599\": \"\uf9be\", \"\u65a4\": \"\u2f44\", \"\u65b9\": \"\u2f45\", \"\u65c5\": \"\uf983\", \"\u65e0\": \"\u2f46\", \"\u65e2\": \"\ufa42\", \"\u65e5\": \"\u2f47\", \"\u6613\": \"\uf9e0\", \"\u6674\": \"\ufa12\", \"\u6688\": \"\uf9c5\", \"\u6691\": \"\ufa43\", \"\u66b4\": \"\ufa06\", \"\u66c6\": \"\uf98b\", \"\u66f0\": \"\u2f48\", \"\u66f4\": \"\uf901\", \"\u6708\": \"\u2f49\", \"\u6717\": \"\uf929\", \"\u6728\": \"\u2f4a\", \"\u674e\": \"\uf9e1\", \"\u677b\": \"\uf9c8\", \"\u6797\": \"\uf9f4\", \"\u67f3\": \"\uf9c9\", \"\u6817\": \"\uf9da\", \"\u6881\": \"\uf97a\", \"\u6885\": \"\ufa44\", \"\u68a8\": \"\uf9e2\", \"\u6a02\": \"\uf914\", \"\u6a13\": \"\uf94c\", \"\u6ad3\": \"\uf931\", \"\u6b04\": \"\uf91d\", \"\u6b20\": \"\u2f4b\", \"\u6b62\": \"\u2f4c\", \"\u6b77\": \"\uf98c\", \"\u6b79\": \"\u2f4d\", \"\u6bae\": \"\uf9a5\", \"\u6bb3\": \"\u2f4e\", \"\u6bba\": \"\uf970\", \"\u6bcb\": \"\u2f4f\", \"\u6bcd\": \"\u2e9f\", \"\u6bd4\": \"\u2f50\", \"\u6bdb\": \"\u2f51\", \"\u6c0f\": \"\u2f52\", \"\u6c14\": \"\u2f53\", \"\u6c34\": \"\u2f54\", \"\u6c88\": \"\uf972\", \"\u6ccc\": \"\uf968\", \"\u6ce5\": \"\uf9e3\", \"\u6d1b\": \"\uf915\", \"\u6d1e\": \"\ufa05\", \"\u6d41\": \"\uf9ca\", \"\u6d6a\": \"\uf92a\", \"\u6d77\": \"\ufa45\", \"\u6dcb\": \"\uf9f5\", \"\u6dda\": \"\uf94d\", \"\u6dea\": \"\uf9d6\", \"\u6e1a\": \"\ufa46\", \"\u6e9c\": \"\uf9cb\", \"\u6eba\": \"\uf9ec\", \"\u6ed1\": \"\uf904\", \"\u6f0f\": \"\uf94e\", \"\u6f22\": \"\ufa47\", \"\u6f23\": \"\uf992\", \"\u6feb\": \"\uf922\", \"\u6ffe\": \"\uf984\", \"\u706b\": \"\u2f55\", \"\u7099\": \"\uf9fb\", \"\u70c8\": \"\uf99f\", \"\u70d9\": \"\uf916\", \"\u7149\": \"\uf993\", \"\u716e\": \"\ufa48\", \"\u71ce\": \"\uf9c0\", \"\u71d0\": \"\uf9ee\", \"\u7210\": \"\uf932\", \"\u721b\": \"\uf91e\", \"\u722a\": \"\u2f56\", \"\u722b\": \"\ufa49\", \"\u7236\": \"\u2f57\", \"\u723b\": \"\u2f58\", \"\u723f\": \"\u2f59\", \"\u7247\": \"\u2f5a\", \"\u7259\": \"\u2f5b\", \"\u725b\": \"\u2f5c\", \"\u7262\": \"\uf946\", \"\u72ac\": \"\u2f5d\", \"\u72c0\": \"\uf9fa\", \"\u72fc\": \"\uf92b\", \"\u732a\": \"\ufa16\", \"\u7375\": \"\uf9a7\", \"\u7384\": \"\u2f5e\", \"\u7387\": \"\uf961\", \"\u7389\": \"\u2f5f\", \"\u73b2\": \"\uf9ad\", \"\u73de\": \"\uf917\", \"\u7406\": \"\uf9e4\", \"\u7409\": \"\uf9cc\", \"\u7422\": \"\ufa4a\", \"\u7469\": \"\uf9ae\", \"\u7489\": \"\uf994\", \"\u7498\": \"\uf9ef\", \"\u74dc\": \"\u2f60\", \"\u74e6\": \"\u2f61\", \"\u7518\": \"\u2f62\", \"\u751f\": \"\u2f63\", \"\u7528\": \"\u2f64\", \"\u7530\": \"\u2f65\", \"\u7532\": \"\u3199\", \"\u7559\": \"\uf9cd\", \"\u7565\": \"\uf976\", \"\u7570\": \"\uf962\", \"\u758b\": \"\u2f66\", \"\u7592\": \"\u2f67\", \"\u75e2\": \"\uf9e5\", \"\u7642\": \"\uf9c1\", \"\u7669\": \"\uf90e\", \"\u7676\": \"\u2f68\", \"\u767d\": \"\u2f69\", \"\u76ae\": \"\u2f6a\", \"\u76bf\": \"\u2f6b\", \"\u76ca\": \"\ufa17\", \"\u76e7\": \"\uf933\", \"\u76ee\": \"\u2f6c\", \"\u7701\": \"\uf96d\", \"\u77db\": \"\u2f6d\", \"\u77e2\": \"\u2f6e\", \"\u77f3\": \"\u2f6f\", \"\u786b\": \"\uf9ce\", \"\u788c\": \"\uf93b\", \"\u7891\": \"\ufa4b\", \"\u78ca\": \"\uf947\", \"\u78fb\": \"\uf964\", \"\u792a\": \"\uf985\", \"\u793a\": \"\u2f70\", \"\u793c\": \"\ufa18\", \"\u793e\": \"\ufa4c\", \"\u7948\": \"\ufa4e\", \"\u7949\": \"\ufa4d\", \"\u7950\": \"\ufa4f\", \"\u7956\": \"\ufa50\", \"\u795d\": \"\ufa51\", \"\u795e\": \"\ufa19\", \"\u7965\": \"\ufa1a\", \"\u797f\": \"\uf93c\", \"\u798d\": \"\ufa52\", \"\u798e\": \"\ufa53\", \"\u798f\": \"\ufa1b\", \"\u79ae\": \"\uf9b6\", \"\u79b8\": \"\u2f71\", \"\u79be\": \"\u2f72\", \"\u79ca\": \"\uf995\", \"\u7a1c\": \"\uf956\", \"\u7a40\": \"\ufa54\", \"\u7a74\": \"\u2f73\", \"\u7a81\": \"\ufa55\", \"\u7ab1\": \"\ufaac\", \"\u7acb\": \"\u2f74\", \"\u7af9\": \"\u2f75\", \"\u7b20\": \"\uf9f8\", \"\u7bc0\": \"\ufa56\", \"\u7c3e\": \"\uf9a6\", \"\u7c60\": \"\uf944\", \"\u7c73\": \"\u2f76\", \"\u7c92\": \"\uf9f9\", \"\u7cbe\": \"\ufa1d\", \"\u7cd6\": \"\ufa03\", \"\u7ce7\": \"\uf97b\", \"\u7cf8\": \"\u2f77\", \"\u7d10\": \"\uf9cf\", \"\u7d22\": \"\uf96a\", \"\u7d2f\": \"\uf94f\", \"\u7da0\": \"\uf93d\", \"\u7dbe\": \"\uf957\", \"\u7df4\": \"\uf996\", \"\u7e09\": \"\ufa58\", \"\u7e37\": \"\uf950\", \"\u7e41\": \"\ufa59\", \"\u7f36\": \"\u2f78\", \"\u7f51\": \"\u2f79\", \"\u7f72\": \"\ufa5a\", \"\u7f79\": \"\uf9e6\", \"\u7f85\": \"\uf90f\", \"\u7f8a\": \"\u2f7a\", \"\u7f9a\": \"\uf9af\", \"\u7fbd\": \"\u2f7b\", \"\u8001\": \"\u2f7c\", \"\u8005\": \"\ufa5b\", \"\u800c\": \"\u2f7d\", \"\u8012\": \"\u2f7e\", \"\u8033\": \"\u2f7f\", \"\u8046\": \"\uf9b0\", \"\u806f\": \"\uf997\", \"\u807e\": \"\uf945\", \"\u807f\": \"\u2f80\", \"\u8089\": \"\u2f81\", \"\u808b\": \"\uf953\", \"\u81d8\": \"\uf926\", \"\u81e3\": \"\u2f82\", \"\u81e8\": \"\uf9f6\", \"\u81ea\": \"\u2f83\", \"\u81ed\": \"\ufa5c\", \"\u81f3\": \"\u2f84\", \"\u81fc\": \"\u2f85\", \"\u820c\": \"\u2f86\", \"\u8218\": \"\ufa6d\", \"\u821b\": \"\u2f87\", \"\u821f\": \"\u2f88\", \"\u826e\": \"\u2f89\", \"\u826f\": \"\uf97c\", \"\u8272\": \"\u2f8a\", \"\u8278\": \"\u2f8b\", \"\u8279\": \"\ufa5d\", \"\u82e5\": \"\uf974\", \"\u8336\": \"\uf9fe\", \"\u83c9\": \"\uf93e\", \"\u83f1\": \"\uf958\", \"\u843d\": \"\uf918\", \"\u8449\": \"\uf96e\", \"\u8457\": \"\ufa5f\", \"\u84ee\": \"\uf999\", \"\u84fc\": \"\uf9c2\", \"\u85cd\": \"\uf923\", \"\u85fa\": \"\uf9f0\", \"\u8606\": \"\uf935\", \"\u8612\": \"\ufa20\", \"\u862d\": \"\uf91f\", \"\u863f\": \"\uf910\", \"\u864d\": \"\u2f8c\", \"\u865c\": \"\uf936\", \"\u866b\": \"\u2f8d\", \"\u8779\": \"\ufab5\", \"\u87ba\": \"\uf911\", \"\u881f\": \"\uf927\", \"\u8840\": \"\u2f8e\", \"\u884c\": \"\u2f8f\", \"\u8863\": \"\u2f90\", \"\u88c2\": \"\uf9a0\", \"\u88cf\": \"\uf9e7\", \"\u88e1\": \"\uf9e8\", \"\u88f8\": \"\uf912\", \"\u8910\": \"\ufa60\", \"\u8964\": \"\uf924\", \"\u897e\": \"\u2f91\", \"\u898b\": \"\u2f92\", \"\u8996\": \"\ufa61\", \"\u89d2\": \"\u2f93\", \"\u8a00\": \"\u2f94\", \"\u8aaa\": \"\uf96f\", \"\u8ad2\": \"\uf97d\", \"\u8ad6\": \"\uf941\", \"\u8af8\": \"\ufa22\", \"\u8afe\": \"\uf95d\", \"\u8b01\": \"\ufa62\", \"\u8b39\": \"\ufa63\", \"\u8b58\": \"\uf9fc\", \"\u8b80\": \"\uf95a\", \"\u8c37\": \"\u2f95\", \"\u8c46\": \"\u2f96\", \"\u8c48\": \"\uf900\", \"\u8c55\": \"\u2f97\", \"\u8c78\": \"\u2f98\", \"\u8c9d\": \"\u2f99\", \"\u8cc2\": \"\uf948\", \"\u8cc8\": \"\uf903\", \"\u8cd3\": \"\ufa64\", \"\u8d08\": \"\ufa65\", \"\u8d64\": \"\u2f9a\", \"\u8d70\": \"\u2f9b\", \"\u8db3\": \"\u2f9c\", \"\u8def\": \"\uf937\", \"\u8eab\": \"\u2f9d\", \"\u8eca\": \"\u2f9e\", \"\u8f26\": \"\uf998\", \"\u8f2a\": \"\uf9d7\", \"\u8f3b\": \"\ufa07\", \"\u8f9b\": \"\u2f9f\", \"\u8fb0\": \"\u2fa0\", \"\u8fb5\": \"\u2fa1\", \"\u8fb6\": \"\ufa66\", \"\u9023\": \"\uf99a\", \"\u9038\": \"\ufa25\", \"\u907c\": \"\uf9c3\", \"\u908f\": \"\uf913\", \"\u9091\": \"\u2fa2\", \"\u90ce\": \"\uf92c\", \"\u90fd\": \"\ufa26\", \"\u9149\": \"\u2fa3\", \"\u916a\": \"\uf919\", \"\u9199\": \"\ufac4\", \"\u91b4\": \"\uf9b7\", \"\u91c6\": \"\u2fa4\", \"\u91cc\": \"\u2fa5\", \"\u91cf\": \"\uf97e\", \"\u91d1\": \"\u2fa6\", \"\u9234\": \"\uf9b1\", \"\u9304\": \"\uf93f\", \"\u934a\": \"\uf99b\", \"\u9577\": \"\u2fa7\", \"\u9580\": \"\u2fa8\", \"\u95ad\": \"\uf986\", \"\u961c\": \"\u2fa9\", \"\u962e\": \"\uf9c6\", \"\u964d\": \"\ufa09\", \"\u9675\": \"\uf959\", \"\u9678\": \"\uf9d3\", \"\u9686\": \"\uf9dc\", \"\u96a3\": \"\uf9f1\", \"\u96b6\": \"\u2faa\", \"\u96b8\": \"\uf9b8\", \"\u96b9\": \"\u2fab\", \"\u96e2\": \"\uf9ea\", \"\u96e3\": \"\ufa68\", \"\u96e8\": \"\u2fac\", \"\u96f6\": \"\uf9b2\", \"\u96f7\": \"\uf949\", \"\u9732\": \"\uf938\", \"\u9748\": \"\uf9b3\", \"\u9751\": \"\u2fad\", \"\u9756\": \"\ufa1c\", \"\u975e\": \"\u2fae\", \"\u9762\": \"\u2faf\", \"\u9769\": \"\u2fb0\", \"\u97cb\": \"\u2fb1\", \"\u97ed\": \"\u2fb2\", \"\u97f3\": \"\u2fb3\", \"\u97ff\": \"\ufa69\", \"\u9801\": \"\u2fb4\", \"\u980b\": \"\ufacb\", \"\u9818\": \"\uf9b4\", \"\u983b\": \"\ufa6a\", \"\u985e\": \"\uf9d0\", \"\u98a8\": \"\u2fb5\", \"\u98db\": \"\u2fb6\", \"\u98df\": \"\u2fb7\", \"\u98ef\": \"\ufa2a\", \"\u98fc\": \"\ufa2b\", \"\u9928\": \"\ufa2c\", \"\u9996\": \"\u2fb8\", \"\u9999\": \"\u2fb9\", \"\u99ac\": \"\u2fba\", \"\u99f1\": \"\uf91a\", \"\u9a6a\": \"\uf987\", \"\u9aa8\": \"\u2fbb\", \"\u9ad8\": \"\u2fbc\", \"\u9adf\": \"\u2fbd\", \"\u9b25\": \"\u2fbe\", \"\u9b2f\": \"\u2fbf\", \"\u9b32\": \"\u2fc0\", \"\u9b3c\": \"\u2fc1\", \"\u9b5a\": \"\u2fc2\", \"\u9b6f\": \"\uf939\", \"\u9c57\": \"\uf9f2\", \"\u9ce5\": \"\u2fc3\", \"\u9db4\": \"\ufa2d\", \"\u9dfa\": \"\uf93a\", \"\u9e1e\": \"\uf920\", \"\u9e75\": \"\u2fc4\", \"\u9e7f\": \"\u2fc5\", \"\u9e97\": \"\uf988\", \"\u9e9f\": \"\uf9f3\", \"\u9ea5\": \"\u2fc6\", \"\u9ebb\": \"\u2fc7\", \"\u9ec3\": \"\u2fc8\", \"\u9ecd\": \"\u2fc9\", \"\u9ece\": \"\uf989\", \"\u9ed1\": \"\u2fca\", \"\u9ef9\": \"\u2fcb\", \"\u9efd\": \"\u2fcc\", \"\u9f0e\": \"\u2fcd\", \"\u9f13\": \"\u2fce\", \"\u9f20\": \"\u2fcf\", \"\u9f3b\": \"\u2fd0\", \"\u9f4a\": \"\u2fd1\", \"\u9f52\": \"\u2fd2\", \"\u9f8d\": \"\u2fd3\", \"\u9f8e\": \"\ufad9\", \"\u9f9c\": \"\u2fd4\", \"\u9fa0\": \"\u2fd5\", \"\ua727\": \"\uab5c\", \"\ua76f\": \"\ua770\", \"\uab37\": \"\uab5d\", \"\uab52\": \"\uab5f\", \"\uac00\": \"\u326e\", \"\ub098\": \"\u326f\", \"\ub2e4\": \"\u3270\", \"\ub77c\": \"\u3271\", \"\ub9c8\": \"\u3272\", \"\ubc14\": \"\u3273\", \"\uc0ac\": \"\u3274\", \"\uc544\": \"\u3275\", \"\uc6b0\": \"\u327e\", \"\uc790\": \"\u3276\", \"\ucc28\": \"\u3277\", \"\uce74\": \"\u3278\", \"\ud0c0\": \"\u3279\", \"\ud30c\": \"\u327a\", \"\ud558\": \"\u327b\"}"
    private lateinit var activity: Activity
    private val charBuilder = StringBuilder()
    private var isEnable = false
    fun init() {
        if (OwnSP.ownSP.getBoolean("antiMessageCensorship", false)) {
            XposedHelpers.findClass("com.coolapk.market.view.message.ChattingActivity", CoolapkContext.classLoader)
                    .hookAfterMethod("initData") {
                        activity = it.thisObject as Activity
                        val userID = activity.intent.getStringExtra("USER_ID")
                        //请勿删除，否则后果自负！
                        isEnable = userID !in listOf("798985", "917649", "12202", "10002", "97100", "408649", "662435", "1353127", "1603081", "413952", "499228", "514025", "427832", "1123602", "897371", "611629", "899823")
                        if (!isEnable) LogUtil.toast("私信反和谐已关闭", true)
                    }
            XposedHelpers.findClass("com.coolapk.market.view.message.ChattingActivity", CoolapkContext.classLoader)
                    .hookBeforeMethod("sendMessage", String::class.java, String::class.java, String::class.java) {
                        if (isEnable) {
                            charBuilder.clear()
                            val jsonObject = JSONObject(d)
                            val message = (it.args[0] as String).toCharArray()
                            val messageBuilder = StringBuilder()
                            for (i in message.indices) {
                                val char = jsonObject.optString(message[i].toString())
                                if (message[i] == '[') isEnable = false
                                if ((char == "") or (!isEnable)) {
                                    messageBuilder.append(message[i])
                                } else {
                                    messageBuilder.append(char)
                                    charBuilder.append("「${message[i]}」")
                                }
                                if (message[i] == ']') isEnable = true
                            }
                            if (messageBuilder.length <= 8) {
                                it.args[0] = "\u202e${messageBuilder.reversed()}"
                            } else {
                                it.args[0] = messageBuilder.toString()
                            }
                        }
                    }
            XposedHelpers.findClass("com.coolapk.market.view.message.ChattingActivity\$sendMessage$2", CoolapkContext.classLoader)
                    .hookAfterMethod("onCompleted") {
                        if (isEnable && (charBuilder.toString() != "")) {
                            LogUtil.toast("已替换 $charBuilder", true)
                        }
                        val editText = activity.callMethod("getEditText") as EditText
                        editText.setText("")
                    }
        }
    }
}