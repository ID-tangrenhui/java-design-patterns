/**
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.typeobject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.iluwatar.typeobject.Candy.Type;

/**
 * The JsonParser class helps parse the json file candy.json to get all the
 * different candies.
 */

public class JsonParser {
  Hashtable<String, Candy> candies;
  
  JsonParser() {
    this.candies = new Hashtable<String, Candy>();
  }

  void parse() throws FileNotFoundException, IOException, ParseException {
    var parser = new JSONParser();
    var jo = (JSONObject) parser.parse(new FileReader(new File("").getAbsolutePath()
        + "\\src\\main\\java\\com\\iluwatar\\typeobject\\candy.json"));
    var a = (JSONArray) jo.get("candies");
    for (var o : a) {
      var candy = (JSONObject) o;
      var name = (String) candy.get("name");
      var parentName = (String) candy.get("parent");
      var t = (String) candy.get("type");
      Type type = null;
      if (t.equals("rewardFruit")) {
        type = Type.rewardFruit;
      } else {
        type = Type.crushableCandy;
      }
      var points = Integer.parseInt((String) candy.get("points"));
      var c = new Candy(name, parentName, type, points);
      this.candies.put(name, c);
    }
    setParentAndPoints();
  }
  
  void setParentAndPoints() {
    for (Enumeration<String> e = this.candies.keys(); e.hasMoreElements();) {
      var c = this.candies.get(e.nextElement());
      if (c.parentName == null) {
        c.parent = null;
      } else {
        c.parent = this.candies.get(c.parentName);
      }
      if (c.getPoints() == 0 && c.parent != null) {
        c.setPoints(c.parent.getPoints());
      }
    }
  }

}
