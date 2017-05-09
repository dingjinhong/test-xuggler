package xuggler;

import java.awt.Dialog;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaViewer;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

public class ViewPart1 extends ViewPart {

	public ViewPart1() {
		// TODO Auto-generated constructor stub
	}
	public String filepath=null;
	public String mp3filepath=null;
	Browser browser=null;
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		final Composite com=new Composite(parent,SWT.NONE);
		FormLayout layout=new FormLayout();
		layout.marginWidth=5;
		layout.marginHeight=5;
		com.setLayout(layout);
		FormData fd=null;
		Button  setflv=new Button(com,SWT.NONE);
		fd=new FormData();
		fd.top=new FormAttachment(10);
		fd.left=new FormAttachment(5);
		setflv.setText("selectFlv");
		setflv.setLayoutData(fd);
		final Text textflv=new Text(com,SWT.NONE);
		fd=new FormData(0,20);
		fd.top=new FormAttachment(10,2);
		fd.left=new FormAttachment(setflv,10);
		fd.bottom=new FormAttachment(14,-3);
		fd.right=new FormAttachment(90);
		textflv.setLayoutData(fd);
		Button converttoogg=new Button(com,SWT.NONE);
		fd=new FormData();
		fd.top=new FormAttachment(setflv,10);
		fd.left=new FormAttachment(5);
		converttoogg.setLayoutData(fd);
		converttoogg.setText("ogg");
		Button converttomp4=new Button(com,SWT.NONE);
		fd=new FormData();
		fd.top=new FormAttachment(setflv,10);
		fd.left=new FormAttachment(converttoogg,20);
		converttomp4.setLayoutData(fd);
		converttomp4.setText("mp4");
		Button setmp3=new Button(com,SWT.NONE);
		fd=new FormData();
		fd.top=new FormAttachment(converttoogg,100);
		fd.left=new FormAttachment(5);
		setmp3.setLayoutData(fd);
		setmp3.setText("selectmp3");
		final Text textmp3=new Text(com,SWT.NONE);
		fd=new FormData(0,20);
		fd.top=new FormAttachment(converttoogg,102);
		fd.left=new FormAttachment(setmp3,10);
		fd.right=new FormAttachment(90);
		textmp3.setLayoutData(fd);
		Button converttooga=new Button(com,SWT.NONE);
		converttooga.setText("oga");
		fd=new FormData();
		fd.top=new FormAttachment(setmp3,10);
		fd.left=new FormAttachment(5);
		converttooga.setLayoutData(fd);
		browser=new Browser(com,SWT.BORDER);
		fd=new FormData(300,300);
		fd.top=new FormAttachment(converttooga,50);
		fd.left=new FormAttachment(35);
		browser.setLayoutData(fd);
		
		setflv.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TOFileDialog fd=new FileDialog(menu.getShell(),SWT.OPEN);
				filepath=null;
				FileDialog fd=new FileDialog(com.getShell(),SWT.OPEN);
				fd.setFilterPath("c:/");  
				fd.setFilterExtensions(new String[]{"*.flv","*.*"});  
				fd.setFilterNames(new String[]{"Text Files(*.flv)","All Files(*.*)"});  
				String file=fd.open();
				if(file!=null){
					filepath=file;
					textflv.setText(file);
					//Program.launch(file);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		converttoogg.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				convertMedia(filepath,"ogg");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		converttomp4.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				convertMedia(filepath,"mp4");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		setmp3.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				mp3filepath=null;
				FileDialog fd=new FileDialog(com.getShell(),SWT.OPEN);
				fd.setFilterPath("c:/");  
				fd.setFilterExtensions(new String[]{"*.mp3","*.*"});  
				fd.setFilterNames(new String[]{"Text Files(*.mp3)","All Files(*.*)"});  
				String file=fd.open();
				if(file!=null){
					mp3filepath=file;
					textmp3.setText(file);
					//Program.launch(file);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		converttooga.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				convertMedia(mp3filepath,"");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	public void convertMedia(String input,String outputtype){
		if(input!=null&&input.endsWith(".flv")){
			int width=0;
			int height=0;
			String outputfile=input.substring(0, input.length()-3)+outputtype;
			System.out.println(outputfile);
			 IContainer container = IContainer.make();
			  int result = container.open(input, IContainer.Type.READ, null);
			  if (result<0){
				  
			  }else{
				  int numStreams = container.getNumStreams();
				  for (int i=0; i<numStreams; i++) {
						IStream stream = container.getStream(i);
						IStreamCoder coder = stream.getStreamCoder();
						if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
							if(outputtype.equals("ogg")){
								width=coder.getWidth();
								height=coder.getHeight();
							}else if(outputtype.equals("mp4")){
								width=coder.getWidth()/2*2;
								height=coder.getHeight()/2*2;
							}
						} 
						
				  }
			  }
			MyVideoListener myVideoListener = new MyVideoListener(width, height,outputtype);
			Resizer resizer = new Resizer(width, height);
	 
			// reader
			final IMediaReader reader = ToolFactory.makeReader(input);
			reader.addListener(resizer);
			// writer
			IMediaWriter writer = ToolFactory.makeWriter(outputfile, reader);
			resizer.addListener(writer);
			writer.addListener(myVideoListener);
		//	reader.addListener(ToolFactory.makeViewer(IMediaViewer.Mode.VIDEO_ONLY));
			// show video when encoding
		//	reader.addListener(ToolFactory.makeViewer(true));
			ProgressMonitorDialog dialog=new ProgressMonitorDialog(null);
			//System.out.println(reader.getContainer().getInputBufferLength());
			//System.out.println(reader.readPacket().getCurrentRefCount());
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
			

			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				// TODO Auto-generated method stub
//				int n=reader.getContainer().flushPackets();
				monitor.beginTask("converting",100);
			int m=0;
			
				while (reader.readPacket() == null) { 
					//reader.getContainer().get
					// continue coding
					monitor.setTaskName("converting");
//					Thread.sleep(100);
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
//			String p=getPluginPath();
//			if(outputtype.equals("mp4")){
//			String s="";
//			try {
//				s=FileUtils.readFileToString(new File(p+"openMedia.html")).replace("MP4VIDEOPATH", "file://"+outputfile);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			try {
//				FileUtils.writeStringToFile(new File(p+"test.html"), s);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			browser.setUrl(p+"test.html");
//			}else {
				  IMediaReader reader1 = ToolFactory.makeReader(outputfile);

				    //
				    // Create a MediaViewer object and tell it to play video only
				    //
				    reader1.addListener(ToolFactory.makeViewer(IMediaViewer.Mode.VIDEO_ONLY));

				    // read out the contents of the media file, and sit back and watch

				    while (reader1.readPacket() == null)
				      do {} while(false);
				//writer.addListener(ToolFactory.makeViewer(IMediaViewer.Mode.VIDEO_ONLY));
		//	}			
			//Program.launch(outputfile);
		}else if(input!=null&&input.endsWith(".amr")){
			String outputfile=input.substring(0, input.length()-3)+"mp3";
			SimpleTranscoder.transcode(input, outputfile);
			Program.launch(outputfile);
		}
	}
	private String getPluginPath(){
		 String path;
		// System.out.println(Platform.getBundle("SB3editor.html5tool").getVersion().toString());
	       try {
	             path= Platform.asLocalURL(Platform.getBundle("com.smartbuilder.sb3.test.xuggler").getEntry("")).getPath();
	             path=path.substring(path.indexOf("/")+1,path.length());
	       } catch (IOException e) {
	             path="";
	             e.printStackTrace();//master
	       }
	       return path;
	}
//	 private static void Run(String filePath)
//	    {
//	        Runtime r = Runtime.getRuntime();
//	        try 
//	        {
//	            r.exec("cmd /c start "+filePath);
//	        } catch (IOException e) 
//	        {
//	            System.out.println(e);
//	        }
//	    }
}
