package xuggler;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.mediatool.event.VideoPictureEvent;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;

public class Resizer extends MediaToolAdapter{ //implements IRunnableWithProgress{

	private Integer width;
	private Integer height;
 
	private IVideoResampler videoResampler = null;
 
	public Resizer(Integer aWidth, Integer aHeight) {
		this.width = aWidth;
		this.height = aHeight;
	}
 
	@Override
	public void onVideoPicture(IVideoPictureEvent event) {
		System.out.println("2");
		IVideoPicture pic = event.getPicture();
		if (videoResampler == null) {
			videoResampler = IVideoResampler.make(width, height,IPixelFormat.Type.YUV420P, pic.getWidth(), pic
					.getHeight(), pic.getPixelType());
		}
		IVideoPicture out = IVideoPicture.make(IPixelFormat.Type.YUV420P, width, height);
		videoResampler.resample(out, pic);
 
		IVideoPictureEvent asc = new VideoPictureEvent(event.getSource(), out, event.getStreamIndex());
		super.onVideoPicture(asc);
		out.delete();
	}

//	@Override
//	public void run(IProgressMonitor monitor) throws InvocationTargetException,
//			InterruptedException {
//		// TODO Auto-generated method stub
//		
//	}

}
