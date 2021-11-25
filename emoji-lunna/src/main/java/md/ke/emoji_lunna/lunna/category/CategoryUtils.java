/*
 * Copyright (C) 2016 - Niklas Baudy, Ruben Gees, Mario Đanić and contributors
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
 *
 */

package md.ke.emoji_lunna.lunna.category;

import java.util.Arrays;

import md.ke.emoji_lunna.lunna.LunnaEmoji;

final class CategoryUtils {
  static LunnaEmoji[] concatAll(final LunnaEmoji[] first, final LunnaEmoji[]... rest) {
    int totalLength = first.length;
    for (final LunnaEmoji[] array : rest) {
      totalLength += array.length;
    }

    final LunnaEmoji[] result = Arrays.copyOf(first, totalLength);

    int offset = first.length;
    for (final LunnaEmoji[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }

    return result;
  }

  private CategoryUtils() {
    // No instances.
  }
}
