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
package com.mikepenz.cryptocurrency_icons_typeface_library;

import android.content.Context;
import android.graphics.Typeface;

import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.typeface.ITypeface;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class CryptocurrencyIcons implements ITypeface {
    private static final String TTF_FILE = "cryptocurrency-icons-font-v1.0.0.ttf";
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
        return "Cryptocurrency Icons";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
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
        return "Coinranking";
    }

    @Override
    public String getUrl() {
        return "";
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
        return "https://raw.githubusercontent.com/coinranking/cryptocurrency-icons/master/LICENSE";
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
        cci_1st('\ue800'),
		cci_amp('\ue801'),
		cci_ans('\ue802'),
		cci_ardr('\ue803'),
		cci_ark('\ue804'),
		cci_bay('\ue805'),
		cci_bcap('\ue806'),
		cci_bcn('\ue807'),
		cci_btc('\ue808'),
		cci_btcc('\ue809'),
		cci_btcd('\ue80a'),
		cci_bts('\ue80b'),
		cci_clam('\ue80c'),
		cci_crbit('\ue80d'),
		cci_dash('\ue80e'),
		cci_dcr('\ue80f'),
		cci_dgb('\ue810'),
		cci_dgd('\ue811'),
		cci_doge('\ue812'),
		cci_emc('\ue813'),
		cci_etc('\ue814'),
		cci_eth('\ue815'),
		cci_fct('\ue816'),
		cci_game('\ue817'),
		cci_gbyte('\ue818'),
		cci_gno('\ue819'),
		cci_gnt('\ue81a'),
		cci_grc('\ue81b'),
		cci_gup('\ue81c'),
		cci_icn('\ue81d'),
		cci_hmq('\ue81e'),
		cci_ion('\ue81f'),
		cci_kmd('\ue820'),
		cci_lbc('\ue821'),
		cci_lkk('\ue822'),
		cci_lsk('\ue823'),
		cci_ltc('\ue824'),
		cci_maid('\ue825'),
		cci_mln('\ue826'),
		cci_mona('\ue827'),
		cci_mue('\ue828'),
		cci_nlg('\ue829'),
		cci_nmc('\ue82a'),
		cci_nvc('\ue82b'),
		cci_nxt('\ue82c'),
		cci_omni('\ue82d'),
		cci_pivx('\ue82e'),
		cci_ppc('\ue82f'),
		cci_rby('\ue830'),
		cci_rep('\ue831'),
		cci_rlc('\ue832'),
		cci_round('\ue833'),
		cci_sbd('\ue834'),
		cci_sc('\ue835'),
		cci_sjcx('\ue836'),
		cci_smc('\ue837'),
		cci_sngls('\ue838'),
		cci_steem('\ue839'),
		cci_strat('\ue83a'),
		cci_swt('\ue83b'),
		cci_sys('\ue83c'),
		cci_time('\ue83d'),
		cci_tkn('\ue83e'),
		cci_trst('\ue83f'),
		cci_ubq('\ue840'),
		cci_usdt('\ue841'),
		cci_vtc('\ue842'),
		cci_waves('\ue843'),
		cci_wings('\ue844'),
		cci_xaur('\ue845'),
		cci_xcp('\ue846'),
		cci_xem('\ue847'),
		cci_xlm('\ue848'),
		cci_xmr('\ue849'),
		cci_xpm('\ue84a'),
		cci_xrp('\ue84b'),
		cci_xzc('\ue84c'),
		cci_zec('\ue84d');

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
                typeface = new CryptocurrencyIcons();
            }
            return typeface;
        }
    }
}
