/* Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onepf.oms.data;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.onepf.oms.BillingApplication;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents an in-app product's listing details.
 */
public class SkuDetails {

    String _sku;
    String _type;
    String _price;
    String _title;
    String _description;

    public SkuDetails(String itemType, String sku, String name, String price, String description) {
        _type = itemType;
        _sku = sku;
        _title = name;
        _price = price;
        _description = description;
    }

    public SkuDetails(Element xml) {
        NamedNodeMap attributes = xml.getAttributes();

        _sku = attributes.getNamedItem("productId").getNodeValue();
        _type = attributes.getNamedItem("type").getNodeValue();

        NodeList localeList = xml.getElementsByTagName("locale");
        if (localeList != null && localeList.getLength() > 0) {
            _title = ((Element) localeList.item(0)).getAttribute("title");
            _description = ((Element) localeList.item(0)).getAttribute("description");
        }

        NodeList priceList = xml.getElementsByTagName("price");
        if (priceList != null && priceList.getLength() > 0) {
            NodeList countryList = ((Element) priceList.item(0)).getElementsByTagName("country");
            if (countryList != null && countryList.getLength() > 0) {
                _price = countryList.item(0).getTextContent();
            }
        }
    }

    public SkuDetails(String json) throws JSONException {
        JSONObject o = new JSONObject(json);
        _sku = o.getString("productId");
        _type = o.optString("type");
        _price = o.optString("price");
        _title = o.optString("title");
        _description = o.optString("description");
    }

    public String toJson() {
        JSONObject o = new JSONObject();
        try {
            o.put("productId", _sku);
            o.put("type", _type);
            o.put("price", _price);
            o.put("title", _title);
            o.put("description", _description);
        } catch (JSONException e) {
            Log.e(BillingApplication.TAG, "Couldn't serialize " + getClass().getSimpleName());
            return "";
        }
        return o.toString();
    }

    public String getSku() {
        return _sku;
    }

    public String getType() {
        return _type;
    }

    public String getPrice() {
        return _price;
    }

    public String getTitle() {
        return _title;
    }

    public String getDescription() {
        return _description;
    }

    @Override
    public String toString() {
        return String.format("SkuDetails: type = %s, SKU = %s, title = %s, price = %s, description = %s", _type, _sku, _title, _price, _description);
    }
}