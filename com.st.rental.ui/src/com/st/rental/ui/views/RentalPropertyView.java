package com.st.rental.ui.views;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.opcoach.training.rental.Rental;
import com.st.rental.core.RentalCoreActivator;


public class RentalPropertyView extends ViewPart implements ISelectionListener {

	private Label rentedObjectLabel;
	private Label renterNameLabel;
	private Group grpRentalDates;
	private Label labelStartDate;
	private Label labelEndDate;
	
	public RentalPropertyView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		infoGroup.setText("Informations");
		infoGroup.setLayout(new GridLayout(2, false));
		
		rentedObjectLabel = new Label(infoGroup, SWT.NONE);
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedObjectLabel.setLayoutData(gd);
		rentedObjectLabel.setText("Truc");
		
		Label renterNameText = new Label(infoGroup, SWT.NONE);
		renterNameText.setText("Rented to:");
		
		renterNameLabel = new Label(infoGroup, SWT.NONE);
		renterNameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		renterNameLabel.setText("Machin");
		
		grpRentalDates = new Group(parent, SWT.NONE);
		grpRentalDates.setText("Rental Dates");
		grpRentalDates.setLayout(new GridLayout(2, false));
		
		Label labelFrom = new Label(grpRentalDates, SWT.NONE);
		labelFrom.setText("from:");
		
		labelStartDate = new Label(grpRentalDates, SWT.NONE);
		labelStartDate.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		labelStartDate.setText("dummy");
		
		Label labelTo = new Label(grpRentalDates, SWT.NONE);
		labelTo.setText("to:");
		
		labelEndDate = new Label(grpRentalDates, SWT.NONE);
		labelEndDate.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		labelEndDate.setText("dummy");

		 setRental(RentalCoreActivator.getAgency().getRentals().get(0));

		 // Provide ability to drag and drop label texts to other apps
		 setLabelAsDragSource(rentedObjectLabel);
		 setLabelAsDragSource(renterNameLabel);
		 setLabelAsDragSource(labelStartDate);
		 setLabelAsDragSource(labelEndDate);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	public void setRental(Rental rental) {
		rentedObjectLabel.setText(rental.getRentedObject().getName());
		renterNameLabel.setText(rental.getCustomer().getDisplayName());
		labelStartDate.setText(rental.getStartDate().toString());
		labelEndDate.setText(rental.getEndDate().toString());
	}
	
	private void setLabelAsDragSource(Label label) {
		DragSource source = new DragSource(label, DND.DROP_MOVE);
		source.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		
		source.addDragListener(new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
					event.data = label.getText();
				}
				super.dragSetData(event);
			}
		});
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		site.getPage().addSelectionListener(this);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object selectedObject = ((IStructuredSelection) selection).getFirstElement();
			if (selectedObject instanceof Rental)
				setRental((Rental) selectedObject);
		}
	}

	@Override
	public void dispose() {
		getSite().getPage().removeSelectionListener(this);
		super.dispose();
	}
}
