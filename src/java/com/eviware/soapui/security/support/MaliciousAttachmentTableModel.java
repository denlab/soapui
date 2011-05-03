package com.eviware.soapui.security.support;

import java.io.File;
import java.io.IOException;

import com.eviware.soapui.impl.wsdl.AttachmentContainer;
import com.eviware.soapui.security.tools.AttachmentElement;
import com.eviware.soapui.security.tools.AttachmentHolder;
import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.editor.inspectors.attachments.AttachmentsTableModel;

public abstract class MaliciousAttachmentTableModel extends AttachmentsTableModel
{

	public MaliciousAttachmentTableModel( AttachmentContainer request )
	{
		super( request );
	}

	protected AttachmentHolder holder = new AttachmentHolder();

	public int getRowCount()
	{
		return holder.size();
	}

	public void addResult( File file, String contentType, Boolean enabled, Boolean cached )
	{
		try
		{
			holder.addElement( file, contentType, enabled, cached );
			addFile( file, cached );
			fireTableDataChanged();
		}
		catch( IOException e )
		{
			UISupport.showErrorMessage( e );
		}
	}

	public void removeResult( int i )
	{
		if( UISupport.confirm( "Remove selected attachments?", "Remove Attachments" ) )
		{
			holder.removeElement( i );
			removeAttachment( new int[] { i } );
			fireTableDataChanged();
		}
	}

	public void clear()
	{
		holder.clear();
		fireTableDataChanged();
	}

	public AttachmentElement getRowValue( int rowIndex )
	{
		return holder.getList().get( rowIndex );
	}

	public abstract int getColumnCount();

	public abstract String getColumnName( int column );

	public abstract Object getValueAt( int rowIndex, int columnIndex );

}
