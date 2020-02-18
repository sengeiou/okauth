/**
 * Copyright 2020 the original author or authors.
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
package test;

/**
 *
 * @author wautsns
 * @since Feb 18, 2020
 */
public class MyTest {

    public static void main(String[] args) {
        print("map data input stream reader");
    }

    private static void print(String description) {
        int n = (75 - description.length()) / 3;
        StringBuilder tmp = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            tmp.append('-');
        }
        StringBuilder bder = new StringBuilder();
        bder.append("// ").append(tmp);
        bder.append(' ').append(description).append(' ');
        bder.append(tmp).append(tmp);
        System.out.println(bder);
    }

}
