package com.company.actions;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.event.ActionEvent;

/**
 * Класс SuperscriptAction будет отвечать за стиль "Надстрочный знак".
 */
public class SuperscriptAction extends StyledEditorKit.StyledTextAction {

    public SuperscriptAction() {
        super(StyleConstants.Superscript.toString());
    }

    public void actionPerformed(ActionEvent actionEvent) {
        JEditorPane editor = getEditor(actionEvent);
        if (editor != null) {
            MutableAttributeSet mutableAttributeSet = getStyledEditorKit(editor).getInputAttributes();
            SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
            StyleConstants.setSuperscript(simpleAttributeSet, !StyleConstants.isSuperscript(mutableAttributeSet));
            setCharacterAttributes(editor, simpleAttributeSet, false);
        }
    }
}
