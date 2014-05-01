/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.android.inputmethod.latin;

import android.test.suitebuilder.annotation.LargeTest;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;

import com.android.inputmethod.latin.Constants;
import com.android.inputmethod.latin.WordComposer;

@LargeTest
public class ShiftModeTests extends InputTestsBase {

    @Override
    protected EditorInfo enrichEditorInfo(final EditorInfo ei) {
        ei.inputType |= TextUtils.CAP_MODE_SENTENCES;
        ei.initialCapsMode = TextUtils.CAP_MODE_SENTENCES;
        return ei;
    }

    private boolean isCapsModeAutoShifted() {
        return mLatinIME.mKeyboardSwitcher.getKeyboardShiftMode()
                == WordComposer.CAPS_MODE_AUTO_SHIFTED;
    }

    public void testTypicalSentence() {
        assertTrue("Initial auto caps state", isCapsModeAutoShifted());
        type("Test");
        assertFalse("Caps after letter", isCapsModeAutoShifted());
        type(" ");
        assertFalse("Caps after space", isCapsModeAutoShifted());
        type("some,");
        assertFalse("Caps after comma", isCapsModeAutoShifted());
        type(" ");
        assertFalse("Caps after comma space", isCapsModeAutoShifted());
        type("words.");
        assertFalse("Caps directly after period", isCapsModeAutoShifted());
        type(" ");
        assertTrue("Caps after period space", isCapsModeAutoShifted());
    }

    public void testBackspace() {
        assertTrue("Initial auto caps state", isCapsModeAutoShifted());
        type("A");
        assertFalse("Caps state after one letter", isCapsModeAutoShifted());
        type(Constants.CODE_DELETE);
        assertTrue("Auto caps state at start after delete", isCapsModeAutoShifted());
    }

    public void testRepeatingBackspace() {
        final String SENTENCE_TO_TYPE = "Test sentence. Another.";
        final int BACKSPACE_COUNT =
                SENTENCE_TO_TYPE.length() - SENTENCE_TO_TYPE.lastIndexOf(' ') - 1;

        type(SENTENCE_TO_TYPE);
        assertFalse("Caps after typing \"" + SENTENCE_TO_TYPE + "\"", isCapsModeAutoShifted());
        type(Constants.CODE_DELETE);
        for (int i = 1; i < BACKSPACE_COUNT; ++i) {
            repeatKey(Constants.CODE_DELETE);
        }
        assertFalse("Caps immediately after repeating Backspace a lot", isCapsModeAutoShifted());
        sleep(DELAY_TO_WAIT_FOR_PREDICTIONS);
        runMessages();
        assertTrue("Caps after a while after repeating Backspace a lot", isCapsModeAutoShifted());
    }
}