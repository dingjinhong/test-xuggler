package xuggler;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaViewer;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.ICodec;
public class SimpleTranscoder {
	public static boolean transcode(String inputFile, String outputFile) {
        try {
//        	MyVideoListener myVideoListener = new MyVideoListener(400, 400);
//    		Resizer resizer = new Resizer(400,400);
        //Create an IMediaReader using the ToolFactory and the pat to the input file.
        final IMediaReader reader = ToolFactory.makeReader(inputFile);
        //reader.addListener(resizer);
        //Attach a listener to the reader.  The listener is an IMediaWriter.
        //To specify the format, just give the output file the extension of the file
        //type you want.  For example, to create a MP4 file, make the output file's
        //extension ".mp4".  IMediaWriter will do all of the behind the scenes work for you.
       // IMediaWriter writer = ToolFactory.makeWriter(outputFile.getAbsolutePath(), reader);
		//resizer.addListener(writer);
	//	writer.addListener(myVideoListener);
        reader.addListener(ToolFactory.makeWriter(outputFile, reader));
       // writer.addListener(myVideoListener);
        //This is where the actual transcoding takes place.  You don't have to do anything in the loop.
        //Behind the scenes, the IMediaReader is feeding information to any registered listeners.  In
        //this case the only listener is an IMediaWriter, which takes the information given to it and
        //outputs it to the given file in the requested format.
        ProgressMonitorDialog dialog=new ProgressMonitorDialog(null);
		//System.out.println(reader.getContainer().getInputBufferLength());
		//System.out.println(reader.readPacket().getCurrentRefCount());
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
		
			
			@Override
			public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			// TODO Auto-generated method stub
//			int n=reader.getContainer().flushPackets();
			monitor.beginTask("converting",100);
			int m=0;
		
			while (reader.readPacket() == null) { 
				
				// continue coding
				monitor.setTaskName("converting");
//				Thread.sleep(100);
				monitor.worked(1);
				m++;
				//System.out.println("djh");
			}
			System.out.println(m);
			monitor.done();
		}
		};
		try {
			dialog.run(true,true,runnable);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        IMediaReader reader1 = ToolFactory.makeReader(outputFile);

	    //
	    // Create a MediaViewer object and tell it to play video only
	    //
	    reader1.addListener(ToolFactory.makeViewer(IMediaViewer.Mode.VIDEO_ONLY));

	    // read out the contents of the media file, and sit back and watch

	    while (reader1.readPacket() == null)
	      do {} while(false);
         
        return true;
    }
	
}
