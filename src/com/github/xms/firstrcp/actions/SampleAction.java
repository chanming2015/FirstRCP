package com.github.xms.firstrcp.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.LegacyResourceSupport;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.NewWizard;
import org.eclipse.ui.internal.util.Util;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate
{
    private IWorkbenchWindow window;

    /**
     * The constructor.
     */
    public SampleAction()
    {
    }

    /**
     * The action has been activated. The argument of the
     * method represents the 'real' action sitting
     * in the workbench UI.
     * @see IWorkbenchWindowActionDelegate#run
     */
    @Override
    public void run(IAction action)
    {
        if (window == null)
        {
            return;
        }
        NewWizard wizard = new NewWizard();
        wizard.setCategoryId(null);
        wizard.setWindowTitle(null);
        ISelection selection = window.getSelectionService().getSelection();
        IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
        if (selection instanceof IStructuredSelection)
        {
            selectionToPass = (IStructuredSelection) selection;
        }
        else
        {
            Class resourceClass = LegacyResourceSupport.getResourceClass();
            if (resourceClass != null)
            {
                org.eclipse.ui.IWorkbenchPart part = window.getPartService().getActivePart();
                if (part instanceof IEditorPart)
                {
                    org.eclipse.ui.IEditorInput input = ((IEditorPart) part).getEditorInput();
                    Object resource = Util.getAdapter(input, resourceClass);
                    if (resource != null)
                        selectionToPass = new StructuredSelection(resource);
                }
            }
        }
        wizard.init(window.getWorkbench(), selectionToPass);
        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
        IDialogSettings wizardSettings = workbenchSettings.getSection("NewWizardAction");
        if (wizardSettings == null)
            wizardSettings = workbenchSettings.addNewSection("NewWizardAction");
        wizard.setDialogSettings(wizardSettings);
        wizard.setForcePreviousAndNextButtons(true);
        Shell parent = window.getShell();
        WizardDialog dialog = new WizardDialog(parent, wizard);
        dialog.create();
        dialog.getShell().setSize(Math.max(500, dialog.getShell().getSize().x), 500);
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(dialog.getShell(), "org.eclipse.ui.new_wizard_context");
        dialog.open();

        // NewProjectNameAndLocationWizardPage pythonNewProjectPage = new
        // NewProjectNameAndLocationWizardPage(
        // "NewProject");
    }

    /**
     * Selection in the workbench has been changed. We 
     * can change the state of the 'real' action here
     * if we want, but this can only happen after 
     * the delegate has been created.
     * @see IWorkbenchWindowActionDelegate#selectionChanged
     */
    @Override
    public void selectionChanged(IAction action, ISelection selection)
    {
    }

    /**
     * We can use this method to dispose of any system
     * resources we previously allocated.
     * @see IWorkbenchWindowActionDelegate#dispose
     */
    @Override
    public void dispose()
    {
    }

    /**
     * We will cache window object in order to
     * be able to provide parent shell for the message dialog.
     * @see IWorkbenchWindowActionDelegate#init
     */
    @Override
    public void init(IWorkbenchWindow window)
    {
        this.window = window;
    }
}