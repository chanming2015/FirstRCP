package com.github.xms.firstrcp;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory
{

    @Override
    public void createInitialLayout(IPageLayout layout)
    {
        String editorArea = layout.getEditorArea();
        layout.addView("com.github.xms.firstrcp.actions.views.TestView", IPageLayout.RIGHT, 0.2f,
                editorArea);
    }
}
