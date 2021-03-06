package com.st.rental.ui.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.opcoach.training.rental.Customer;

import org.eclipse.swt.widgets.Label;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;

public class CustomerView extends ViewPart implements ISelectionListener {
	
	private Label labelCustomer;

	public CustomerView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		
		labelCustomer = new Label(parent, SWT.NONE);
		labelCustomer.setAlignment(SWT.CENTER);
		labelCustomer.setText("No customer selected");
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		site.getPage().addSelectionListener(this);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection.isEmpty())
			return;

		if (selection instanceof IStructuredSelection) {
			Object selectedObject = ((IStructuredSelection) selection).getFirstElement();

			Customer customer = Platform.getAdapterManager().getAdapter(selectedObject, Customer.class);
			if (customer != null)
				labelCustomer.setText(customer.getDisplayName());
		}
	}

	@Override
	public void dispose() {
		getSite().getPage().removeSelectionListener(this);
		super.dispose();
	}
}
