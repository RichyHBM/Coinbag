/*
 * Copyright 2014 Mike Penz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mikepenz.cryptocoinsicons_typeface_library;

import android.content.Context;
import android.graphics.Typeface;

import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.typeface.ITypeface;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class CryptocoinsIcons implements ITypeface {
    private static final String TTF_FILE = "cryptocoinsicons-font-v2017.5.ttf";
    private static Typeface typeface = null;
    private static HashMap<String, Character> mChars;

    @Override
    public IIcon getIcon(String key) {
        return Icon.valueOf(key);
    }

    @Override
    public HashMap<String, Character> getCharacters() {
        if (mChars == null) {
            HashMap<String, Character> aChars = new HashMap<String, Character>();
            for (Icon v : Icon.values()) {
                aChars.put(v.name(), v.character);
            }
            mChars = aChars;
        }
        return mChars;
    }

    @Override
    public String getMappingPrefix() {
        return "cci";
    }

    @Override
    public String getFontName() {
        return "CryptocoinsIcons";
    }

    @Override
    public String getVersion() {
        return "2017.5";
    }

    @Override
    public int getIconCount() {
        return mChars.size();
    }

    @Override
    public Collection<String> getIcons() {
        Collection<String> icons = new LinkedList<String>();
        for (Icon value : Icon.values()) {
            icons.add(value.name());
        }
        return icons;
    }

    @Override
    public String getAuthor() {
        return "Martin Allien";
    }

    @Override
    public String getUrl() {
        return "https://github.com/allienworks/cryptocoins";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getLicense() {
        return "MIT License";
    }

    @Override
    public String getLicenseUrl() {
        return "https://raw.githubusercontent.com/allienworks/cryptocoins/master/LICENCE";
    }

    @Override
    public Typeface getTypeface(Context context) {
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + TTF_FILE);
            } catch (Exception e) {
                return null;
            }
        }
        return typeface;
    }

    public enum Icon implements IIcon {
        cci_ADC('\ue900'),
		cci_ADC_alt('\ue901'),
		cci_AEON('\ue902'),
		cci_AEON_alt('\ue903'),
		cci_AMP('\ue904'),
		cci_AMP_alt('\ue905'),
		cci_ANC('\ue906'),
		cci_ANC_alt('\ue907'),
		cci_ARCH('\ue908'),
		cci_ARCH_alt('\ue909'),
		cci_ARDR('\ue90a'),
		cci_ARDR_alt('\ue90b'),
		cci_AUR('\ue90c'),
		cci_AUR_alt('\ue90d'),
		cci_BANX('\ue90e'),
		cci_BANX_alt('\ue90f'),
		cci_BAY('\ue910'),
		cci_BAY_alt('\ue911'),
		cci_BC('\ue912'),
		cci_BC_alt('\ue913'),
		cci_BCN('\ue914'),
		cci_BCN_alt('\ue915'),
		cci_BFT('\ue916'),
		cci_BFT_alt('\ue917'),
		cci_BRK('\ue918'),
		cci_BRK_alt('\ue919'),
		cci_BRX('\ue91a'),
		cci_BRX_alt('\ue91b'),
		cci_BSD('\ue91c'),
		cci_BSD_alt('\ue91d'),
		cci_BTA('\ue91e'),
		cci_BTC('\ue91f'),
		cci_BTC_alt('\ue920'),
		cci_BTCD('\ue921'),
		cci_BTCD_alt('\ue922'),
		cci_BTS('\ue923'),
		cci_BTS_alt('\ue924'),
		cci_CLAM('\ue925'),
		cci_CLAM_alt('\ue926'),
		cci_CLOAK('\ue927'),
		cci_CLOAK_alt('\ue928'),
		cci_DAO('\ue929'),
		cci_DAO_alt('\ue92a'),
		cci_DASH('\ue92b'),
		cci_DASH_alt('\ue92c'),
		cci_DCR('\ue92d'),
		cci_DCR_alt('\ue92e'),
		cci_DGB('\ue92f'),
		cci_DGB_alt('\ue930'),
		cci_DGD('\ue931'),
		cci_DGX('\ue932'),
		cci_DMD('\ue933'),
		cci_DMD_alt('\ue934'),
		cci_DOGE('\ue935'),
		cci_DOGE_alt('\ue936'),
		cci_EMC('\ue937'),
		cci_EMC_alt('\ue938'),
		cci_ERC('\ue939'),
		cci_ERC_alt('\ue93a'),
		cci_ETC('\ue93b'),
		cci_ETC_alt('\ue93c'),
		cci_ETH('\ue93d'),
		cci_ETH_alt('\ue93e'),
		cci_FC2('\ue93f'),
		cci_FC2_alt('\ue940'),
		cci_FCT('\ue941'),
		cci_FCT_alt('\ue942'),
		cci_FLO('\ue943'),
		cci_FLO_alt('\ue944'),
		cci_FRK('\ue945'),
		cci_FRK_alt('\ue946'),
		cci_FTC('\ue947'),
		cci_FTC_alt('\ue948'),
		cci_GAME('\ue949'),
		cci_GAME_alt('\ue94a'),
		cci_GDC('\ue94b'),
		cci_GDC_alt('\ue94c'),
		cci_GEMZ('\ue94d'),
		cci_GEMZ_alt('\ue94e'),
		cci_GLD('\ue94f'),
		cci_GLD_alt('\ue950'),
		cci_GNT('\ue951'),
		cci_GNT_alt('\ue952'),
		cci_GRC('\ue953'),
		cci_GRC_alt('\ue954'),
		cci_GRS('\ue955'),
		cci_HEAT('\ue956'),
		cci_HEAT_alt('\ue957'),
		cci_ICN('\ue958'),
		cci_ICN_alt('\ue959'),
		cci_IFC('\ue95a'),
		cci_IFC_alt('\ue95b'),
		cci_INCNT('\ue95c'),
		cci_INCNT_alt('\ue95d'),
		cci_IOC('\ue95e'),
		cci_IOC_alt('\ue95f'),
		cci_JBS('\ue960'),
		cci_JBS_alt('\ue961'),
		cci_KMD('\ue962'),
		cci_KMD_alt('\ue963'),
		cci_KOBO('\ue964'),
		cci_KORE('\ue965'),
		cci_KORE_alt('\ue966'),
		cci_LBC('\ue967'),
		cci_LBC_alt('\ue968'),
		cci_LDOGE('\ue969'),
		cci_LDOGE_alt('\ue96a'),
		cci_LISK('\ue96b'),
		cci_LISK_alt('\ue96c'),
		cci_LTC('\ue96d'),
		cci_LTC_alt('\ue96e'),
		cci_MAID('\ue96f'),
		cci_MAID_alt('\ue970'),
		cci_MINT('\ue971'),
		cci_MINT_alt('\ue972'),
		cci_MONA('\ue973'),
		cci_MONA_alt('\ue974'),
		cci_MRC('\ue975'),
		cci_MSC('\ue976'),
		cci_MSC_alt('\ue977'),
		cci_MTR('\ue978'),
		cci_MTR_alt('\ue979'),
		cci_MUE('\ue97a'),
		cci_MUE_alt('\ue97b'),
		cci_NBT('\ue97c'),
		cci_NEOS('\ue97d'),
		cci_NEOS_alt('\ue97e'),
		cci_NEU('\ue97f'),
		cci_NEU_alt('\ue980'),
		cci_NLG('\ue981'),
		cci_NLG_alt('\ue982'),
		cci_NMC('\ue983'),
		cci_NMC_alt('\ue984'),
		cci_NOTE('\ue985'),
		cci_NOTE_alt('\ue986'),
		cci_NVC('\ue987'),
		cci_NVC_alt('\ue988'),
		cci_NXT('\ue989'),
		cci_NXT_alt('\ue98a'),
		cci_OK('\ue98b'),
		cci_OK_alt('\ue98c'),
		cci_OMNI('\ue98d'),
		cci_OMNI_alt('\ue98e'),
		cci_OPAL('\ue98f'),
		cci_OPAL_alt('\ue990'),
		cci_PIGGY('\ue991'),
		cci_PIGGY_alt('\ue992'),
		cci_PINK('\ue993'),
		cci_PINK_alt('\ue994'),
		cci_PIVX('\ue995'),
		cci_PIVX_alt('\ue996'),
		cci_POT('\ue997'),
		cci_POT_alt('\ue998'),
		cci_PPC('\ue999'),
		cci_PPC_alt('\ue99a'),
		cci_QRK('\ue99b'),
		cci_QRK_alt('\ue99c'),
		cci_RBIES('\ue99d'),
		cci_RBIES_alt('\ue99e'),
		cci_RBT('\ue99f'),
		cci_RBT_alt('\ue9a0'),
		cci_RBY('\ue9a1'),
		cci_RBY_alt('\ue9a2'),
		cci_RDD('\ue9a3'),
		cci_RDD_alt('\ue9a4'),
		cci_REP('\ue9a5'),
		cci_REP_alt('\ue9a6'),
		cci_RISE('\ue9a7'),
		cci_RISE_alt('\ue9a8'),
		cci_SAR('\ue9a9'),
		cci_SAR_alt('\ue9aa'),
		cci_SCOT('\ue9ab'),
		cci_SCOT_alt('\ue9ac'),
		cci_SDC('\ue9ad'),
		cci_SDC_alt('\ue9ae'),
		cci_SIA('\ue9af'),
		cci_SIA_alt('\ue9b0'),
		cci_SJCX('\ue9b1'),
		cci_SJCX_alt('\ue9b2'),
		cci_SLG('\ue9b3'),
		cci_SLG_alt('\ue9b4'),
		cci_SLS('\ue9b5'),
		cci_SLS_alt('\ue9b6'),
		cci_SNRG('\ue9b7'),
		cci_SNRG_alt('\ue9b8'),
		cci_START('\ue9b9'),
		cci_START_alt('\ue9ba'),
		cci_STEEM('\ue9bb'),
		cci_STEEM_alt('\ue9bc'),
		cci_STR('\ue9bd'),
		cci_STR_alt('\ue9be'),
		cci_STRAT('\ue9bf'),
		cci_STRAT_alt('\ue9c0'),
		cci_SWIFT('\ue9c1'),
		cci_SWIFT_alt('\ue9c2'),
		cci_SYNC('\ue9c3'),
		cci_SYNC_alt('\ue9c4'),
		cci_SYS('\ue9c5'),
		cci_SYS_alt('\ue9c6'),
		cci_TRIG('\ue9c7'),
		cci_TRIG_alt('\ue9c8'),
		cci_TX('\ue9c9'),
		cci_TX_alt('\ue9ca'),
		cci_UBQ('\ue9cb'),
		cci_UBQ_alt('\ue9cc'),
		cci_UNITY('\ue9cd'),
		cci_UNITY_alt('\ue9ce'),
		cci_USDT('\ue9cf'),
		cci_USDT_alt('\ue9d0'),
		cci_VIOR('\ue9d1'),
		cci_VIOR_alt('\ue9d2'),
		cci_VNL('\ue9d3'),
		cci_VNL_alt('\ue9d4'),
		cci_VPN('\ue9d5'),
		cci_VPN_alt('\ue9d6'),
		cci_VRC('\ue9d7'),
		cci_VRC_alt('\ue9d8'),
		cci_VTC('\ue9d9'),
		cci_VTC_alt('\ue9da'),
		cci_WAVES('\ue9db'),
		cci_WAVES_alt('\ue9dc'),
		cci_XAI('\ue9dd'),
		cci_XAI_alt('\ue9de'),
		cci_XBS('\ue9df'),
		cci_XBS_alt('\ue9e0'),
		cci_XCP('\ue9e1'),
		cci_XCP_alt('\ue9e2'),
		cci_XEM('\ue9e3'),
		cci_XEM_alt('\ue9e4'),
		cci_XMR('\ue9e5'),
		cci_XPM('\ue9e6'),
		cci_XPM_alt('\ue9e7'),
		cci_XRP('\ue9e8'),
		cci_XRP_alt('\ue9e9'),
		cci_XVG('\ue9ea'),
		cci_XVG_alt('\ue9eb'),
		cci_YBC('\ue9ec'),
		cci_YBC_alt('\ue9ed'),
		cci_ZEC('\ue9ee'),
		cci_ZEC_alt('\ue9ef'),
		cci_ZEIT('\ue9f0'),
		cci_ZEIT_alt('\ue9f1');

        char character;

        Icon(char character) {
            this.character = character;
        }

        public String getFormattedName() {
            return "{" + name() + "}";
        }

        public char getCharacter() {
            return character;
        }

        public String getName() {
            return name();
        }

        // remember the typeface so we can use it later
        private static ITypeface typeface;

        public ITypeface getTypeface() {
            if (typeface == null) {
                typeface = new CryptocoinsIcons();
            }
            return typeface;
        }
    }
}
